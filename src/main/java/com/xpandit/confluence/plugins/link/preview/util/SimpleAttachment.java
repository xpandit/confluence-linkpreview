/*
 * linkpreview-confluence Project
 * 
 * Copyright (C) 2015 Xpand IT.
 * 
 * This software is proprietary.
 */
package com.xpandit.confluence.plugins.link.preview.util;

// TODO: Auto-generated Javadoc
/**
 * Class description.
 * 
 * @author <a href="mailto:ampr@xpand-it.com">ampr</a>
 * @version $Revision: 666 $
 * 
 */
public final class SimpleAttachment {

    /** The url. */
    private String url;

    /** The type. */
    private String type;

    /**
     * Instantiates a new simple attachment.
     *
     * @param url the url
     * @param type the type
     */
    public SimpleAttachment(String url, String type) {
        this.url = url;
        this.type = type;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
