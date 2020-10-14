/*
 * linkpreview-confluence Project
 * 
 * Copyright (C) 2015 Xpand IT.
 * 
 * This software is proprietary.
 */
package com.xpandit.confluence.plugins.link.preview.util;

import java.util.ArrayList;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentTypeUtil.
 */
public final class ContentTypeUtil {

    /**
     * Instantiates a new content type util.
     */
    private ContentTypeUtil() {

    }

    /**
     * Validate content types.
     * 
     * @param contentTypes the content types
     * @param validPrevix the valid previx
     * @return true, if successful
     */
    public static boolean validateContentTypes(String contentTypes, String[] validPrevix) {
        final String[] contentTypesArray = contentTypes.split("\n");
        final ArrayList<String> validContentType = new ArrayList<String>();
        validContentType.addAll(Arrays.asList(validPrevix));
        for (String ct : contentTypesArray) {
            if (ct.contains("!")) {
                continue;
            }
            final String[] ctSplit = ct.split("/");
            if (ctSplit.length < 2 || ctSplit[1].length() < 1 || !validContentType.contains(ctSplit[0])
                    || "\r".equals(ctSplit[1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the image prefix.
     * 
     * @return the image prefix
     */
    public static String[] getImagePrefix() {
        return new String[] { "image" };
    }

    /**
     * Gets the other prefix.
     * 
     * @return the other prefix
     */
    public static String[] getOtherPrefix() {
        return new String[] { "application", "audio", "message", "text", "video", "x-world" };
    }

}
