package com.xpandit.confluence.plugins.link.preview.util;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Util.
 */
public final class Util {

    /** The Constant FORM_MSG_ERROR. */
    public static final String FORM_MSG_ERROR = "link.preview.confluence.form.msg.error";

    /** The Constant FORM_FIELD_EMPTY. */
    public static final String FORM_FIELD_EMPTY = "link.preview.confluence.form.field.empty";

    /** The Constant FORM_INVALID_CONTENT_TYPE. */
    public static final String FORM_INVALID_CONTENT_TYPE = "link.preview.confluence.form.invalid.content.type";

    /** The Constant FORM_INVALID_KEY. */
    public static final String FORM_INVALID_KEY = "link.preview.confluence.license.key.invalid";

    /** The Constant FORM_EXPIRED_KEY. */
    public static final String FORM_EXPIRED_KEY = "link.preview.confluence.license.key.expired";

    /** The Constant SERVER_ID_ERROR. */
    public static final String SERVER_ID_ERROR = "link.preview.confluence.license.serverid.error";

    /** The Constant GENERAL_ERROR. */
    public static final String GENERAL_ERROR = "link.preview.confluence.config.error";

    /** The Constant APPLY_ELEMENTS. */
    public static final String APPLY_ELEMENTS = "APPLY_ELEMENTS";

    /** The Constant IMAGE_TYPES. */
    public static final String IMAGE_TYPES = "IMAGE_TYPES";

    /** The Constant FILE_TYPES. */
    public static final String FILE_TYPES = "FILE_TYPES";

    /** The Constant OTHER_TYPES. */
    public static final String OTHER_TYPES = "OTHER_TYPES";

    /** The Constant PDF_PREVIEW. */
    public static final String PDF_PREVIEW = "PDF_PREVIEW";

    /** The Constant LICENSE_KEY. */
    public static final String LICENSE_KEY = "LICENSE_KEY";

    /** The Constant VALID_LICENSE_KEY. */
    public static final String VALID_LICENSE_KEY = "VALID_LICENSE_KEY";

    /** The Constant APPLY_CSS. */
    public static final String APPLY_CSS = "APPLY_CSS";

    /** The Constant HEADER_COLOR. */
    public static final String HEADER_COLOR = "HEADER_COLOR";

    /** The Constant HEADER_COLOR. */
    public static final String PREVIEW_WEBPAGES = "PREVIEW_WEBPAGES";

    /** The Constant WEBPAGES_CONTENT_TYPES. */
    public static final String WEBPAGES_CONTENT_TYPES_JS = "WEBPAGES_CONTENT_TYPES_JS";

    /** The Constant IMAGE_CONTENT_TYPES_JS. */
    public static final String IMAGE_CONTENT_TYPES_JS = "IMAGE_CONTENT_TYPES_JS";

    /** The Constant PDF_CONTENT_TYPES_JS. */
    public static final String PDF_CONTENT_TYPES_JS = "PDF_CONTENT_TYPES_JS";

    /** The Constant VERSION. */
    public static final String VERSION = "VERSION";

    /** The Constant HEADER_COLOR. */
    public static final String SHOW_RECOURCE_NAME = "SHOW_RESOURCE_NAME";

    /** The Constant HEADER_COLOR. */
    public static final String FIXED_IMAGE_PREVIEW = "FIXED_IMAGE_PREVIEW";

    /** The Constant ACTIVE. */
    public static final String ACTIVE_CONFIGURATION = "ACTIVE_CONFIGURATION";

    /** The Constant ATTACHMENTS. */
    public static final String ATTACHMENTS = "ATTACHMENTS";

    /** The Constant CLICK_TO_PREVIEW. */
    public static final String CLICK_TO_PREVIEW = "CLICK_TO_PREVIEW";

    /** The Constant IMAGE_CONTENT_TYPES. */
    public static final List<String> IMAGE_CONTENT_TYPES = Arrays.asList("image/gif", "image/jpeg", "image/pjpeg",
            "image/png", "image/svg+xml", "image/tiff");

    /** The Constant PDF_CONTENT_TYPES. */
    public static final List<String> PDF_CONTENT_TYPES = Arrays.asList("application/pdf");

    /** The Constant OTHER_CONTENT_TYPES. */
    public static final List<String> OTHER_CONTENT_TYPES = Arrays.asList("text/html");

    /** The Constant X_FRAME_OPTION_HEADER_PARAM. */
    public static final String X_FRAME_OPTION_HEADER_PARAM = "x-frame-options";

    /** The Constant CONST_YES_VALUE. */
    public static final String CONST_YES_VALUE = "Y";

    /** The Constant CONST_NO_VALUE. */
    public static final String CONST_NO_VALUE = "N";

    /** The Constant VALUE_EXISTS. */
    public static final String VALUE_EXISTS = " Exist, value: ";

    /** The Constant NO_VALUE_EXISTS. */
    public static final String NO_VALUE_EXISTS = " have no value.";

    /** The Constant logger. */
    private static final Logger LOGGER = Logger.getLogger(Util.class);

    /** The instance. */
    private static volatile Util instance = null;

    /** The default value. */
    private Map<String, String> defaultValue = null;

    /** The project version. */
    private String projectVersion = "202";

    /**
     * Instantiates a new util.
     */
    private Util() {
        defaultValue = new HashMap<String, String>();
        defaultValue.put(APPLY_ELEMENTS, "a.filename\na.external-link\n" + ".quick-links-section a.acs-nav-item-link\n"
                + "div.results-container a:not([class])\n" + "p a\n" + ".confluenceTable a");

        defaultValue.put(IMAGE_TYPES, "image/bmp\nimage/gif\nimage/jpeg\nimage/png");
        defaultValue.put(OTHER_TYPES, "text/plain\ntext/html");
        defaultValue.put(PDF_PREVIEW, CONST_YES_VALUE);

        defaultValue.put(LICENSE_KEY, "");
        defaultValue.put(VALID_LICENSE_KEY, CONST_NO_VALUE);

        defaultValue.put(HEADER_COLOR, CONST_YES_VALUE);
        defaultValue.put(ACTIVE_CONFIGURATION, CONST_YES_VALUE);
        defaultValue.put(SHOW_RECOURCE_NAME, CONST_YES_VALUE);
        defaultValue.put(PREVIEW_WEBPAGES, CONST_YES_VALUE);
        defaultValue.put(FIXED_IMAGE_PREVIEW, CONST_YES_VALUE);
        defaultValue.put(APPLY_CSS,
                "#link-preview{\n position:absolute; \n border:1px solid #ccc; \n background:#ffffff; \n "
                        + "padding:5px 5px 1px; \n display:none; \n color:#fff; z-index:10; \n}\n"
                        + "#link-preview img{ \n "
                        + "width: expression(document.body.clientWidth <= 600? \"auto\" : \"600px\");"
                        + "\n max-width: 600px;"
                        + "height: expression(document.body.clientWidth <= 300? \"auto\" : \"300px\");"
                        + "\n max-height: 300px; \n}\np#link-preview iframe, p#link-preview.pdf object{ \n"
                        + "width: 600px;\n  height: 300px;\n}\n");

        defaultValue.put(CLICK_TO_PREVIEW, CONST_NO_VALUE);

        final Properties prop = new Properties();
        try {
            final URL urlObject = Util.class.getClassLoader().getResource("/appLnkPrvw.properties");
            if (urlObject != null) {
                prop.load(urlObject.openStream());

                if (prop != null) {
                    this.projectVersion = (String) prop.getProperty("link.preview.plugin.version");
                    if (this.projectVersion != null) {
                        this.projectVersion = this.projectVersion.replaceAll("[.]", "");
                    } else {
                        this.projectVersion = "202";
                    }

                }
            }
        } catch (IOException e) {
            LOGGER.error("Error loading plugin version.");
        }

    }

    /**
     * Gets the project version.
     * 
     * @return the project version
     */
    public String getProjectVersion() {
        return projectVersion;
    }

    /**
     * Gets the single instance of Util.
     * 
     * @return single instance of Util
     */
    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                instance = new Util();
            }
        }
        return instance;
    }

    /**
     * Gets the default configuration.
     * 
     * @return the default configuration
     */
    public Map<String, String> getDefaultConfiguration() {
        return defaultValue;
    }

    /**
     * Checks if is parameter empty.
     * 
     * @param obj the obj
     * @return true, if is parameter empty
     */
    public static boolean isParameterEmpty(Object obj) {
        return obj == null || ((String[]) obj).length == 0 || ((String[]) obj)[0] == null
                || "".equals(((String[]) obj)[0]);
    }
}
