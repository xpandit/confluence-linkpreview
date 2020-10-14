/*
 * 
 */
package com.xpandit.confluence.plugins.link.preview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.atlassian.config.ConfigurationException;
import com.atlassian.confluence.core.ConfluenceActionSupport;
import com.atlassian.confluence.core.ConfluenceSidManager;
import com.ibm.icu.text.MessageFormat;
import com.opensymphony.xwork.ActionContext;
import com.xpandit.confluence.plugins.link.preview.ao.entity.Property;
import com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService;
import com.xpandit.confluence.plugins.link.preview.util.ContentTypeUtil;
import com.xpandit.confluence.plugins.link.preview.util.Util;

/**
 * This class is responsible to provide all administration methods in administration console.
 * 
 * @author <a href="mailto:jlatino@sapo.pt">Joel Latino</a>
 * @version $Revision: 666 $
 * 
 */
public class AdminConfiguration extends ConfluenceActionSupport {

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(AdminConfiguration.class);

    /** The serial Version. */
    private static final long serialVersionUID = 1177708827608416774L;

    /** The property service. */
    private transient PropertyService propertyService;

    /** The apply elements. */
    private transient Property applyElements;

    /** The image types. */
    private transient Property imageTypes;

    /** The other types. */
    private transient Property otherTypes;

    /** The pdf preview. */
    private transient Property pdfPreview;

    /** The license key. */
    private transient Property licenseKey;

    /** The valid license key. */
    private transient Property validLicenseKey;

    /** The apply css. */
    private transient Property applyCSS;

    /** The header color. */
    private transient Property headerColor;

    /** The header color. */
    private transient Property previewWebPages;

    /** The show resource name. */
    private transient Property showResourceName;

    /** The fixed image preview. */
    private transient Property fixedImagePreview;

    /** The click to preview. */
    private transient Property clickToPreview;

    /** The active configuration. */
    private transient Property activeConfiguration;

    /** The errors. */
    private List<String> errors = new ArrayList<String>();

    /** The save action. */
    private boolean saveAction = false;

    /** The restore action. */
    private boolean restoreAction = false;

    private ConfluenceSidManager sidManager;

    /**
     * Sets the property service.
     * 
     * @param propertyService the new property service
     */
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    /**
     * Execute.
     * 
     * @return the string
     * @see com.opensymphony.xwork.ActionSupport#execute()
     */
    @Override
    public String execute() {
        this.saveAction = false;
        errors.clear();
        refreshData();
        return SUCCESS;
    }

    /**
     * Saves all properties submitted.
     * 
     * @return the result of processing request.
     */
    public String save() {
        try {
            System.out.println("Server id:" + getSidManager().getSid().toString());
        } catch (ConfigurationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        clearActions();
        this.saveAction = true;
        errors.clear();
        final Map<?, ?> params = (Map<?, ?>) ActionContext.getContext().get("parameters");
        try {

            if (!Util.isParameterEmpty(params.get(Util.APPLY_ELEMENTS))) {
                propertyService.saveConfigure(Util.APPLY_ELEMENTS, null,
                        ((String[]) params.get(Util.APPLY_ELEMENTS))[0]);
            } else {
                errors.add(MessageFormat.format(getText(Util.FORM_FIELD_EMPTY), new String[] { "Apply Elements" }));
            }

            if (!Util.isParameterEmpty(params.get(Util.IMAGE_TYPES))) {
                if (ContentTypeUtil.validateContentTypes(((String[]) params.get(Util.IMAGE_TYPES))[0],
                        ContentTypeUtil.getImagePrefix())) {
                    propertyService.saveConfigure(Util.IMAGE_TYPES, null, ((String[]) params.get(Util.IMAGE_TYPES))[0]);
                } else {
                    errors.add(MessageFormat.format(getText(Util.FORM_INVALID_CONTENT_TYPE), new String[] { "image" }));
                }
            } else {
                errors.add(MessageFormat.format(getText(Util.FORM_FIELD_EMPTY), new String[] { "Image Types" }));
            }

            if (!Util.isParameterEmpty(params.get(Util.PDF_PREVIEW))
                    && (Util.CONST_YES_VALUE.equalsIgnoreCase(((String[]) params.get(Util.PDF_PREVIEW))[0])
                            || Util.CONST_NO_VALUE.equalsIgnoreCase(((String[]) params.get(Util.PDF_PREVIEW))[0]))) {
                LOGGER.info(Util.PDF_PREVIEW + Util.VALUE_EXISTS + ((String[]) params.get(Util.PDF_PREVIEW))[0]);
                propertyService.saveConfigure(Util.PDF_PREVIEW, null, ((String[]) params.get(Util.PDF_PREVIEW))[0]);
            } else {
                propertyService.saveConfigure(Util.PDF_PREVIEW, null, Util.CONST_NO_VALUE);
                LOGGER.info(Util.PDF_PREVIEW + " have no value.");
            }

            if (!Util.isParameterEmpty(params.get(Util.OTHER_TYPES))) {
                if (ContentTypeUtil.validateContentTypes(((String[]) params.get(Util.OTHER_TYPES))[0],
                        ContentTypeUtil.getOtherPrefix())) {
                    propertyService.saveConfigure(Util.OTHER_TYPES, null, ((String[]) params.get(Util.OTHER_TYPES))[0]);
                } else {
                    errors.add(MessageFormat.format(getText(Util.FORM_INVALID_CONTENT_TYPE), new String[] { "other" }));
                }
            } else {
                errors.add(MessageFormat.format(getText(Util.FORM_FIELD_EMPTY), new String[] { "Other Types" }));
            }

            if (!Util.isParameterEmpty(params.get(Util.APPLY_CSS))) {
                propertyService.saveConfigure(Util.APPLY_CSS, null, ((String[]) params.get(Util.APPLY_CSS))[0]);
            } else {
                errors.add(MessageFormat.format(getText(Util.FORM_FIELD_EMPTY), new String[] { "Apply CSS" }));
            }

            if (!Util.isParameterEmpty(params.get(Util.HEADER_COLOR))
                    && (Util.CONST_YES_VALUE.equalsIgnoreCase(((String[]) params.get(Util.HEADER_COLOR))[0])
                            || Util.CONST_NO_VALUE.equalsIgnoreCase(((String[]) params.get(Util.HEADER_COLOR))[0]))) {
                LOGGER.info(Util.HEADER_COLOR + Util.VALUE_EXISTS + ((String[]) params.get(Util.HEADER_COLOR))[0]);
                propertyService.saveConfigure(Util.HEADER_COLOR, null, ((String[]) params.get(Util.HEADER_COLOR))[0]);
            } else {
                propertyService.saveConfigure(Util.HEADER_COLOR, null, Util.CONST_NO_VALUE);
                LOGGER.info(Util.HEADER_COLOR + Util.NO_VALUE_EXISTS);
            }

            if (!Util.isParameterEmpty(params.get(Util.PREVIEW_WEBPAGES)) && (Util.CONST_YES_VALUE
                    .equalsIgnoreCase(((String[]) params.get(Util.PREVIEW_WEBPAGES))[0])
                    || Util.CONST_NO_VALUE.equalsIgnoreCase(((String[]) params.get(Util.PREVIEW_WEBPAGES))[0]))) {
                LOGGER.info(
                        Util.PREVIEW_WEBPAGES + Util.VALUE_EXISTS + ((String[]) params.get(Util.PREVIEW_WEBPAGES))[0]);
                propertyService.saveConfigure(Util.PREVIEW_WEBPAGES, null,
                        ((String[]) params.get(Util.PREVIEW_WEBPAGES))[0]);
            } else {
                propertyService.saveConfigure(Util.PREVIEW_WEBPAGES, null, Util.CONST_NO_VALUE);
                LOGGER.info(Util.PREVIEW_WEBPAGES + Util.NO_VALUE_EXISTS);
            }

            if (!Util.isParameterEmpty(params.get(Util.SHOW_RECOURCE_NAME)) && (Util.CONST_YES_VALUE
                    .equalsIgnoreCase(((String[]) params.get(Util.SHOW_RECOURCE_NAME))[0])
                    || Util.CONST_NO_VALUE.equalsIgnoreCase(((String[]) params.get(Util.SHOW_RECOURCE_NAME))[0]))) {
                LOGGER.info(Util.SHOW_RECOURCE_NAME + Util.VALUE_EXISTS
                        + ((String[]) params.get(Util.SHOW_RECOURCE_NAME))[0]);
                propertyService.saveConfigure(Util.SHOW_RECOURCE_NAME, null,
                        ((String[]) params.get(Util.SHOW_RECOURCE_NAME))[0]);
            } else {
                propertyService.saveConfigure(Util.SHOW_RECOURCE_NAME, null, Util.CONST_NO_VALUE);
                LOGGER.info(Util.SHOW_RECOURCE_NAME + Util.NO_VALUE_EXISTS);
            }

            if (!Util.isParameterEmpty(params.get(Util.FIXED_IMAGE_PREVIEW)) && (Util.CONST_YES_VALUE
                    .equalsIgnoreCase(((String[]) params.get(Util.FIXED_IMAGE_PREVIEW))[0])
                    || Util.CONST_NO_VALUE.equalsIgnoreCase(((String[]) params.get(Util.FIXED_IMAGE_PREVIEW))[0]))) {
                LOGGER.info(Util.FIXED_IMAGE_PREVIEW + Util.VALUE_EXISTS
                        + ((String[]) params.get(Util.FIXED_IMAGE_PREVIEW))[0]);
                propertyService.saveConfigure(Util.FIXED_IMAGE_PREVIEW, null,
                        ((String[]) params.get(Util.FIXED_IMAGE_PREVIEW))[0]);
            } else {
                propertyService.saveConfigure(Util.FIXED_IMAGE_PREVIEW, null, Util.CONST_NO_VALUE);
                LOGGER.info(Util.FIXED_IMAGE_PREVIEW + Util.NO_VALUE_EXISTS);
            }

            if (!Util.isParameterEmpty(params.get(Util.CLICK_TO_PREVIEW)) && (Util.CONST_YES_VALUE
                    .equalsIgnoreCase(((String[]) params.get(Util.CLICK_TO_PREVIEW))[0])
                    || Util.CONST_NO_VALUE.equalsIgnoreCase(((String[]) params.get(Util.CLICK_TO_PREVIEW))[0]))) {
                LOGGER.info(
                        Util.CLICK_TO_PREVIEW + Util.VALUE_EXISTS + ((String[]) params.get(Util.CLICK_TO_PREVIEW))[0]);
                propertyService.saveConfigure(Util.CLICK_TO_PREVIEW, null,
                        ((String[]) params.get(Util.CLICK_TO_PREVIEW))[0]);
            } else {
                propertyService.saveConfigure(Util.CLICK_TO_PREVIEW, null, Util.CONST_NO_VALUE);
                LOGGER.info(Util.CLICK_TO_PREVIEW + Util.NO_VALUE_EXISTS);
            }

        } catch (Exception e) {
            errors.add(getText(Util.GENERAL_ERROR));
            LOGGER.error(e.getMessage(), e);
        }
        refreshData();
        return SUCCESS;
    }

    /**
     * Restore.
     * 
     * @return the string
     */
    public String restore() {
        clearActions();
        this.restoreAction = true;
        errors.clear();
        try {
            propertyService.saveConfigure(Util.APPLY_ELEMENTS, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.APPLY_ELEMENTS));

            propertyService.saveConfigure(Util.IMAGE_TYPES, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.IMAGE_TYPES));
            propertyService.saveConfigure(Util.PDF_PREVIEW, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.PDF_PREVIEW));
            propertyService.saveConfigure(Util.OTHER_TYPES, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.OTHER_TYPES));

            propertyService.saveConfigure(Util.APPLY_CSS, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.APPLY_CSS));
            propertyService.saveConfigure(Util.PREVIEW_WEBPAGES, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.PREVIEW_WEBPAGES));
            propertyService.saveConfigure(Util.HEADER_COLOR, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.HEADER_COLOR));
            propertyService.saveConfigure(Util.SHOW_RECOURCE_NAME, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.SHOW_RECOURCE_NAME));
            propertyService.saveConfigure(Util.FIXED_IMAGE_PREVIEW, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.FIXED_IMAGE_PREVIEW));

            propertyService.saveConfigure(Util.LICENSE_KEY, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.LICENSE_KEY));

            propertyService.saveConfigure(Util.VALID_LICENSE_KEY, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.VALID_LICENSE_KEY));

            propertyService.saveConfigure(Util.CLICK_TO_PREVIEW, null,
                    Util.getInstance().getDefaultConfiguration().get(Util.CLICK_TO_PREVIEW));
        } catch (Exception e) {
            errors.add(getText(Util.GENERAL_ERROR));
            LOGGER.error(e.getMessage(), e);
        }
        refreshData();
        return SUCCESS;
    }

    /**
     * Refresh data.
     */
    private void refreshData() {

        this.applyElements = propertyService.getPropertyByKey(Util.APPLY_ELEMENTS, null);
        this.applyCSS = propertyService.getPropertyByKey(Util.APPLY_CSS, null);

        this.imageTypes = propertyService.getPropertyByKey(Util.IMAGE_TYPES, null);
        this.pdfPreview = propertyService.getPropertyByKey(Util.PDF_PREVIEW, null);
        this.otherTypes = propertyService.getPropertyByKey(Util.OTHER_TYPES, null);

        this.previewWebPages = propertyService.getPropertyByKey(Util.PREVIEW_WEBPAGES, null);
        this.headerColor = propertyService.getPropertyByKey(Util.HEADER_COLOR, null);
        this.fixedImagePreview = propertyService.getPropertyByKey(Util.FIXED_IMAGE_PREVIEW, null);
        this.showResourceName = propertyService.getPropertyByKey(Util.SHOW_RECOURCE_NAME, null);
        this.activeConfiguration = propertyService.getPropertyByKey(Util.ACTIVE_CONFIGURATION, null);

        this.licenseKey = propertyService.getPropertyByKey(Util.LICENSE_KEY, null);
        this.validLicenseKey = propertyService.getPropertyByKey(Util.VALID_LICENSE_KEY, null);

        this.clickToPreview = propertyService.getPropertyByKey(Util.CLICK_TO_PREVIEW, null);
    }

    /**
     * Clear actions.
     */
    private void clearActions() {
        this.saveAction = false;
        this.restoreAction = false;
    }

    /**
     * Gets message error.
     * 
     * @return message error.
     */
    public String getMessageError() {
        if (errors.size() > 0) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errors.size(); i++) {
                sb.append(errors.get(i));
                if (i + 1 < errors.size()) {
                    sb.append(", ");
                }
            }
            sb.append(".");
            return MessageFormat.format(getText(Util.FORM_MSG_ERROR), new String[] { sb.toString() });
        }
        return null;
    }

    /**
     * Gets the save action.
     * 
     * @return the save action
     */
    public boolean getSaveAction() {
        return this.saveAction;
    }

    /**
     * Gets the apply elements.
     * 
     * @return the apply elements
     */
    public String getApplyElements() {
        return this.applyElements == null ? Util.getInstance().getDefaultConfiguration().get(Util.APPLY_ELEMENTS)
                : this.applyElements.getValue();
    }

    /**
     * Gets the apply css.
     * 
     * @return the apply css
     */
    public String getApplyCSS() {
        return this.applyCSS == null ? Util.getInstance().getDefaultConfiguration().get(Util.APPLY_CSS)
                : this.applyCSS.getValue();
    }

    /**
     * Gets the image types.
     * 
     * @return the image types
     */
    public String getImageTypes() {
        return this.imageTypes == null ? Util.getInstance().getDefaultConfiguration().get(Util.IMAGE_TYPES)
                : this.imageTypes.getValue();
    }

    /**
     * Gets the other types.
     * 
     * @return the other types
     */
    public String getOtherTypes() {
        return this.otherTypes == null ? Util.getInstance().getDefaultConfiguration().get(Util.OTHER_TYPES)
                : this.otherTypes.getValue();
    }

    /**
     * Gets the pdf preview.
     * 
     * @return the pdf preview
     */
    public String getPdfPreview() {
        return this.pdfPreview == null ? Util.getInstance().getDefaultConfiguration().get(Util.PDF_PREVIEW)
                : this.pdfPreview.getValue();
    }

    /**
     * Gets the license key.
     * 
     * @return the license key
     */
    public String getLicenseKey() {
        return this.licenseKey == null ? Util.getInstance().getDefaultConfiguration().get(Util.LICENSE_KEY)
                : this.licenseKey.getValue();
    }

    /**
     * Gets the valid license key.
     * 
     * @return the valid license key
     */
    public String getValidLicenseKey() {
        return this.validLicenseKey == null ? Util.getInstance().getDefaultConfiguration().get(Util.VALID_LICENSE_KEY)
                : this.validLicenseKey.getValue();
    }

    /**
     * Gets the header color.
     * 
     * @return the header color
     */
    public String getHeaderColor() {
        return this.headerColor == null ? Util.getInstance().getDefaultConfiguration().get(Util.HEADER_COLOR)
                : this.headerColor.getValue();
    }

    /**
     * Gets the preview web pages.
     * 
     * @return the preview web pages
     */
    public String getPreviewWebPages() {
        return this.previewWebPages == null ? Util.getInstance().getDefaultConfiguration().get(Util.PREVIEW_WEBPAGES)
                : this.previewWebPages.getValue();
    }

    /**
     * Gets the show resource name.
     * 
     * @return the show resource name
     */
    public String getShowResourceName() {
        return this.showResourceName == null ? Util.getInstance().getDefaultConfiguration().get(Util.SHOW_RECOURCE_NAME)
                : this.showResourceName.getValue();
    }

    /**
     * Gets the fixed image preview.
     * 
     * @return the fixed image preview
     */
    public String getFixedImagePreview() {
        return this.fixedImagePreview == null
                ? Util.getInstance().getDefaultConfiguration().get(Util.FIXED_IMAGE_PREVIEW)
                : this.fixedImagePreview.getValue();
    }

    /**
     * Gets the click to preview.
     * 
     * @return the click to preview
     */
    public String getClickToPreview() {
        return this.clickToPreview == null ? Util.getInstance().getDefaultConfiguration().get(Util.CLICK_TO_PREVIEW)
                : this.clickToPreview.getValue();
    }

    /**
     * Gets the active configuration.
     * 
     * @return the active configuration
     */
    public String getActiveConfiguration() {
        return this.activeConfiguration == null
                ? Util.getInstance().getDefaultConfiguration().get(Util.ACTIVE_CONFIGURATION)
                : this.activeConfiguration.getValue();
    }

    /**
     * Checks if is restore action.
     * 
     * @return true, if is restore action
     */
    public boolean isRestoreAction() {
        return restoreAction;
    }

    /**
     * @return the sidManager
     */
    public ConfluenceSidManager getSidManager() {
        return sidManager;
    }

    /**
     * @param sidManager the sidManager to set
     */
    public void setSidManager(ConfluenceSidManager sidManager) {
        this.sidManager = sidManager;
    }

}
