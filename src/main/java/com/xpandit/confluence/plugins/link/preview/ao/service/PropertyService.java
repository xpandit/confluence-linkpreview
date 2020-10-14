package com.xpandit.confluence.plugins.link.preview.ao.service;

import java.util.Map;

import com.atlassian.activeobjects.tx.Transactional;
import com.xpandit.confluence.plugins.link.preview.ao.entity.Property;

// TODO: Auto-generated Javadoc
/**
 * The Interface PropertyService.
 */
@Transactional
public interface PropertyService {

    /**
     * Save configure.
     * 
     * @param key the key
     * @param spaceKey the space key
     * @param value the value
     * @return the property
     */
    Property saveConfigure(String key, String spaceKey, String value);

    /**
     * Gets the all.
     * 
     * @return the all
     */
    Map<String, Property> getAll();

    /**
     * Gets the property by id.
     * 
     * @param id the id
     * @return the property by id
     */
    Property getPropertyById(String id);

    /**
     * Gets the active property by key.
     * 
     * @param key the key
     * @param spaceKey the space key
     * @return the property by key
     */
    Property getPropertyByKey(String key, String spaceKey);
}
