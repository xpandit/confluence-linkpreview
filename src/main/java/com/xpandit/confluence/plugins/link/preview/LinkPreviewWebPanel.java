/*
 * 
 */
package com.xpandit.confluence.plugins.link.preview;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.pages.Attachment;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.GeneralUtil;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.plugin.web.model.WebPanel;
import com.google.gson.Gson;
import com.xpandit.confluence.plugins.link.preview.ao.entity.Property;
import com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService;
import com.xpandit.confluence.plugins.link.preview.util.SimpleAttachment;
import com.xpandit.confluence.plugins.link.preview.util.Util;

// TODO: Auto-generated Javadoc
/**
 * Web panel where is processing the properties to apply in the page.
 * 
 * @author <a href="mailto:jlatino@sapo.pt">Joel Latino</a>
 * @version $Revision: 666 $
 * 
 */
public class LinkPreviewWebPanel implements WebPanel {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(LinkPreviewWebPanel.class);
    
    private static final String ACTIONCONTEXT = "action";

    /** The Constant Gson. */
    private static final Gson GSON = new Gson();

    /** The property service. */
    private PropertyService propertyService;

    /**
     * Instantiates a new link preview web panel.
     * 
     * @param propertyService the property service
     */
    public LinkPreviewWebPanel(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**
     * Gets the html.
     *
     * @param context the context
     * @return the html
     * @see com.atlassian.plugin.web.model.WebPanel#getHtml(java.util.Map)
     */
    @Override
    public String getHtml(Map<String, Object> context) {
        Property activeConfiguration = null;
        String spaceKey = null;
        try {
            spaceKey = context.get(ACTIONCONTEXT) == null ? null : ((ConfluenceActionSupport) context.get(ACTIONCONTEXT))
                    .getWebInterfaceContext().getSpace().getKey().toUpperCase();
            activeConfiguration = propertyService.getPropertyByKey(Util.ACTIVE_CONFIGURATION, spaceKey);
            if (activeConfiguration == null || !"true".equalsIgnoreCase(activeConfiguration.getValue())) {
                spaceKey = null;
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        final Property applyCSS = propertyService.getPropertyByKey(Util.APPLY_CSS, spaceKey);
        final StringBuilder sb = new StringBuilder();
        sb.append("<style type=\"text/css\">\n");
        sb.append(applyCSS != null ? applyCSS.getValue() : Util.getInstance().getDefaultConfiguration()
                .get(Util.APPLY_CSS));
        sb.append("\n</style>");

        final Map<String, Object> velocityContext = MacroUtils.defaultVelocityContext();
        velocityContext.put("generalUtil", new GeneralUtil());
        final Property applyElementsProp = propertyService.getPropertyByKey(Util.APPLY_ELEMENTS, spaceKey);
        final String applyElements = applyElementsProp != null ? applyElementsProp.getValue() : Util.getInstance()
                .getDefaultConfiguration().get(Util.APPLY_ELEMENTS);
        velocityContext.put(Util.APPLY_ELEMENTS, GSON.toJson(Arrays.asList(applyElements.split("\n")), List.class));

        final Property imageTypesProp = propertyService.getPropertyByKey(Util.IMAGE_TYPES, spaceKey);
        final String imageTypes = imageTypesProp != null ? imageTypesProp.getValue() : Util.getInstance()
                .getDefaultConfiguration().get(Util.IMAGE_TYPES);
        LOGGER.info(GSON.toJson(Arrays.asList(imageTypes.split("\n")), List.class));
        velocityContext.put(Util.IMAGE_TYPES, GSON.toJson(Arrays.asList(imageTypes.split("\n")), List.class));

        final Property pdfPreview = propertyService.getPropertyByKey(Util.PDF_PREVIEW, spaceKey);
        velocityContext.put(Util.PDF_PREVIEW, pdfPreview != null ? pdfPreview.getValue() : Util.getInstance()
                .getDefaultConfiguration().get(Util.PDF_PREVIEW));

        final Property otherTypesProp = propertyService.getPropertyByKey(Util.OTHER_TYPES, spaceKey);
        final String otherTypes = otherTypesProp != null ? otherTypesProp.getValue() : Util.getInstance()
                .getDefaultConfiguration().get(Util.OTHER_TYPES);
        LOGGER.info(GSON.toJson(Arrays.asList(otherTypes.split("\n")), List.class));
        velocityContext.put(Util.OTHER_TYPES, GSON.toJson(Arrays.asList(otherTypes.split("\n")), List.class));

        final Property useHeaderColor = propertyService.getPropertyByKey(Util.HEADER_COLOR, spaceKey);
        velocityContext.put(Util.HEADER_COLOR, useHeaderColor != null ? useHeaderColor.getValue() : Util.getInstance()
                .getDefaultConfiguration().get(Util.HEADER_COLOR));

        final Property showResourceName = propertyService.getPropertyByKey(Util.SHOW_RECOURCE_NAME, spaceKey);
        velocityContext.put(Util.SHOW_RECOURCE_NAME, showResourceName != null ? showResourceName.getValue() : Util
                .getInstance().getDefaultConfiguration().get(Util.SHOW_RECOURCE_NAME));

        final Property fixedImagePreview = propertyService.getPropertyByKey(Util.FIXED_IMAGE_PREVIEW, spaceKey);
        velocityContext.put(Util.FIXED_IMAGE_PREVIEW, fixedImagePreview != null ? fixedImagePreview.getValue() : Util
                .getInstance().getDefaultConfiguration().get(Util.FIXED_IMAGE_PREVIEW));

        final Property webpagesPreview = propertyService.getPropertyByKey(Util.PREVIEW_WEBPAGES, spaceKey);
        velocityContext.put(Util.PREVIEW_WEBPAGES, webpagesPreview != null ? webpagesPreview.getValue() : Util
                .getInstance().getDefaultConfiguration().get(Util.PREVIEW_WEBPAGES));

        /**
         * Doesnt exist in the space configuration just the admin one If its added to the space configuration change
         * from null to spaceKey
         **/
        final Property clickToPreview = propertyService.getPropertyByKey(Util.CLICK_TO_PREVIEW, null);
        velocityContext.put(Util.CLICK_TO_PREVIEW, clickToPreview != null ? clickToPreview.getValue() : Util
                .getInstance().getDefaultConfiguration().get(Util.CLICK_TO_PREVIEW));

        velocityContext.put(Util.WEBPAGES_CONTENT_TYPES_JS, GSON.toJson(Util.OTHER_CONTENT_TYPES, List.class));
        LOGGER.info(GSON.toJson(Util.OTHER_CONTENT_TYPES, List.class));
        velocityContext.put(Util.IMAGE_CONTENT_TYPES_JS, GSON.toJson(Util.IMAGE_CONTENT_TYPES, List.class));
        LOGGER.info(GSON.toJson(Util.IMAGE_CONTENT_TYPES, List.class));
        velocityContext.put(Util.PDF_CONTENT_TYPES_JS, GSON.toJson(Util.PDF_CONTENT_TYPES, List.class));

        final Property licenseKey = propertyService.getPropertyByKey(Util.LICENSE_KEY, null);
        velocityContext.put(Util.LICENSE_KEY, licenseKey != null ? licenseKey.getValue() : Util.getInstance()
                .getDefaultConfiguration().get(Util.LICENSE_KEY));

        if (((ConfluenceActionSupport) context.get(ACTIONCONTEXT)).getWebInterfaceContext().getPage() != null) {
            velocityContext.put(Util.ATTACHMENTS, GSON.toJson(getAllAttachments(context)));
        } else {
            velocityContext.put(Util.ATTACHMENTS, GSON.toJson(""));
        }

        velocityContext.put(Util.VERSION, Util.getInstance().getProjectVersion());

        return sb.append(VelocityUtils.getRenderedTemplate("/templates/LinkPreviewWebPanel.vm", velocityContext))
                .toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.atlassian.plugin.web.model.WebPanel#writeHtml(java.io.Writer, java.util.Map)
     */
    @Override
    public void writeHtml(Writer writer, Map<String, Object> context) throws IOException {
        // TODO Auto-generated method stub
        return;
    }

    /**
     * Gets the all attachments.
     * 
     * @param context the context
     * @return the all attachments
     */
    private List<SimpleAttachment> getAllAttachments(Map<String, Object> context) {
        final List<SimpleAttachment> attachmentList = new ArrayList<SimpleAttachment>();
        for (Attachment a : ((ConfluenceActionSupport) context.get(ACTIONCONTEXT)).getWebInterfaceContext().getPage()
                .getAttachments()) {
            attachmentList.add(new SimpleAttachment(a.getDownloadPath(), a.getContentType()));
        }
        return attachmentList;
    }

}
