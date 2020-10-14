/*
 * linkpreview-confluence2 Project
 * 
 * Copyright (C) 2015 Xpand IT.
 * 
 * This software is proprietary.
 */
package com.xpandit.confluence.plugins.link.preview.upgradeTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;

import com.atlassian.confluence.spaces.Space;
import com.atlassian.confluence.spaces.SpaceManager;
import com.atlassian.sal.api.message.Message;
import com.atlassian.sal.api.upgrade.PluginUpgradeTask;
import com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService;
import com.xpandit.confluence.plugins.link.preview.util.Util;

// TODO: Auto-generated Javadoc
/**
 * Class description.
 * 
 * @author <a href="mailto:ampr@xpand-it.com">ampr</a>
 * @version $Revision: 666 $
 * 
 */
public class ContentTypeUpgradeTask implements PluginUpgradeTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ContentTypeUpgradeTask.class);

    /** The build number. */
    private static final int BUILDNUMBER = 100030000;

    /** The property service. */
    private PropertyService propertyService;

    /** The space manager. */
    private SpaceManager spaceManager;

    /** The tika. */
    private final Tika tika = new Tika();

    /**
     * Instantiates a new content type upgrade task.
     * 
     * @param propertyService the property service
     * @param spaceManager the space manager
     */
    public ContentTypeUpgradeTask(PropertyService propertyService, SpaceManager spaceManager) {
        this.propertyService = propertyService;
        this.spaceManager = spaceManager;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.atlassian.sal.api.upgrade.PluginUpgradeTask#getBuildNumber()
     */
    @Override
    public int getBuildNumber() {
        return BUILDNUMBER;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.atlassian.sal.api.upgrade.PluginUpgradeTask#getShortDescription()
     */
    @Override
    public String getShortDescription() {
        return "Migrates extentions to content types";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.atlassian.sal.api.upgrade.PluginUpgradeTask#doUpgrade()
     */
    @Override
    public Collection<Message> doUpgrade() {

        LOGGER.info("--------------------------UPGRADE_TASK--------------------------");

        upgradeSpace(null);
        for (Space space : spaceManager.getAllSpaces()) {
            upgradeSpace(space.getKey());
        }

        LOGGER.info("-----------------------END_UPGRADE_TASK-------------------------");
        return Collections.emptySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.atlassian.sal.api.upgrade.PluginUpgradeTask#getPluginKey()
     */
    @Override
    public String getPluginKey() {
        return "com.xpandit.plugins.linkpreview-confluence";
    }

    /**
     * Upgrade space.
     * 
     * @param spaceKey the space key
     */
    private void upgradeSpace(String spaceKey) {
        LOGGER.info("-------------------UPGRADE_TASK " + spaceKey);
        String contentTypesImage = "";
        String contentTypesOther = "";
        boolean previewPDF = false;
        final List<String> fileExtensions;
        final ArrayList<String> notProcessedExtensions = new ArrayList<String>();
        if (propertyService.getPropertyByKey(Util.FILE_TYPES, spaceKey) != null) {
            fileExtensions = Arrays.asList(propertyService.getPropertyByKey(Util.FILE_TYPES, spaceKey).getValue()
                    .split("\r\n"));
            LOGGER.info(propertyService.getPropertyByKey(Util.FILE_TYPES, spaceKey).getValue());
            for (String fileExtension : fileExtensions) {
                if ("pdf".equals(fileExtension)) {
                    previewPDF = true;
                    continue;
                }

                final String guessedType = tika.detect("tika." + fileExtension);
                LOGGER.info(guessedType);
                if (guessedType != null) {
                    if ("application/octet-stream".equals(guessedType)) {
                        notProcessedExtensions.add(fileExtension);
                    } else if (guessedType.contains("image/")) {
                        LOGGER.info("adding to image types");
                        if ("".equals(contentTypesImage)) {
                            contentTypesImage = guessedType;
                        } else {
                            contentTypesImage += "\n" + guessedType;
                        }
                    } else {
                        LOGGER.info("adding to other types");
                        if ("".equals(contentTypesOther)) {
                            contentTypesOther = guessedType;
                        } else {
                            contentTypesOther += "\n" + guessedType;
                        }
                    }
                }
            }

            for (String extension : notProcessedExtensions) {
                LOGGER.info("Couldn't guess " + extension);
                contentTypesOther = contentTypesOther.concat("\n!" + extension);
            }

            if (!"".equals(contentTypesImage)) {
                LOGGER.info("IMAGE " + contentTypesImage);
                propertyService.saveConfigure(Util.IMAGE_TYPES, spaceKey, contentTypesImage);
            }

            if (previewPDF) {
                propertyService.saveConfigure(Util.PDF_PREVIEW, spaceKey, Util.CONST_YES_VALUE);
            } else {
                propertyService.saveConfigure(Util.PDF_PREVIEW, spaceKey, Util.CONST_NO_VALUE);
            }

            if (!"".equals(contentTypesOther)) {
                LOGGER.info("OTHER " + contentTypesOther);
                propertyService.saveConfigure(Util.OTHER_TYPES, spaceKey, contentTypesOther);
            }
        }

        LOGGER.info("-----------------END_UPGRADE_TASK " + spaceKey);
    }

}
