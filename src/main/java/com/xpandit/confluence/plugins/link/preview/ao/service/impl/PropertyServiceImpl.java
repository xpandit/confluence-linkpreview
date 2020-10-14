package com.xpandit.confluence.plugins.link.preview.ao.service.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.java.ao.Query;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.user.UserManager;
import com.ibm.icu.util.Calendar;
import com.xpandit.confluence.plugins.link.preview.ao.entity.Property;
import com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyServiceImpl.
 */
public final class PropertyServiceImpl implements PropertyService {

    /** The ao. */
    private final ActiveObjects ao;

    /** The user manager. */
    private final UserManager userManager;

    /**
     * Instantiates a new property service impl.
     * 
     * @param ao the ao
     * @param userManager the user manager
     */
    public PropertyServiceImpl(ActiveObjects ao, UserManager userManager) {
        this.ao = checkNotNull(ao);
        this.userManager = checkNotNull(userManager);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService#getAll()
     */
    @Override
    public Map<String, Property> getAll() {
        final ArrayList<Property> props = newArrayList(ao.find(Property.class));
        final HashMap<String, Property> mapProps = new HashMap<String, Property>();
        for (Property prop : props) {
            mapProps.put(prop.getKeyProperty(), prop);
        }
        return mapProps;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService#getAll()
     */
    @Override
    public Property getPropertyByKey(String key, String spaceKey) {
        final String spaceKeyFormatted = spaceKey == null ? "" : spaceKey.toUpperCase() + "_";
        final Property[] property = ao.find(Property.class,
                Query.select().where("KEY_PROPERTY = ? ", spaceKeyFormatted + key));
        if (property != null && property.length >= 1) {
            return property[0];
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService#getPropertyById(java.lang.String)
     */
    @Override
    public Property getPropertyById(String id) {
        final Property[] property = ao.find(Property.class, Query.select().where("ID = ?", id));
        if (property != null && property.length >= 1) {
            return property[0];
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xpandit.confluence.plugins.link.preview.ao.service.PropertyService#saveConfigure(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Property saveConfigure(String key, String spaceKey, String value) {
        final String spaceKeyFormatted = spaceKey == null ? "" : spaceKey.toUpperCase() + "_";
        Property prop = getPropertyByKey(spaceKeyFormatted + key, null);
        if (prop == null) {
            final HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("KEY_PROPERTY", spaceKeyFormatted + key);
            map.put("VALUE", value);
            map.put("CREATOR", userManager.getRemoteUsername());
            map.put("CREATION_DATE", Calendar.getInstance().getTime());
            map.put("LAST_MODIFIER", userManager.getRemoteUsername());
            map.put("LAST_MOD_DATE", Calendar.getInstance().getTime());
            prop = ao.create(Property.class, map);
            prop.save();
            ao.flushAll();
            return prop;
        }
        prop.setValue(value);
        prop.setLastModDate(Calendar.getInstance().getTime());
        prop.setLastModifier(userManager.getRemoteUsername());
        prop.save();
        ao.flushAll();
        return prop;
    }
}
