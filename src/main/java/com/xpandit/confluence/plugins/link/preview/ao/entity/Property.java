package com.xpandit.confluence.plugins.link.preview.ao.entity;

import java.util.Date;

import net.java.ao.Entity;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.StringLength;
import net.java.ao.schema.Table;
import net.java.ao.schema.Unique;

// TODO: Auto-generated Javadoc
/**
 * The Interface Property.
 */
@Table("LPreviewProp")
public interface Property extends Entity {

    /** The Constant ID_PARAM. */
    String ID_PARAM = "ID";

    /** The Constant KEY_PROP_PARAM. */
    String KEY_PROP_PARAM = "keyProperty";

    /** The Constant VALUE_PROP_PARAM. */
    String VALUE_PROP_PARAM = "value";

    /**
     * Gets the key property.
     * 
     * @return the key property
     */
    @NotNull
    @Unique
    String getKeyProperty();

    /**
     * Sets the key property.
     * 
     * @param keyProperty the new key property
     */
    void setKeyProperty(String keyProperty);

    /**
     * Gets the value.
     * 
     * @return the value
     */
    @NotNull
    @StringLength(value = StringLength.UNLIMITED)
    String getValue();

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    void setValue(String value);

    /**
     * Gets the creator.
     * 
     * @return the creator
     */
    @NotNull
    String getCreator();

    /**
     * Sets the creator.
     * 
     * @param creator the new creator
     */
    void setCreator(String creator);

    /**
     * Gets the creation date.
     * 
     * @return the creation date
     */
    @NotNull
    Date getCreationDate();

    /**
     * Sets the creation date.
     * 
     * @param creationDate the new creation date
     */
    void setCreationDate(Date creationDate);

    /**
     * Gets the last modifier.
     * 
     * @return the last modifier
     */
    @NotNull
    String getLastModifier();

    /**
     * Sets the last modifier.
     * 
     * @param lastModifier the new last modifier
     */
    void setLastModifier(String lastModifier);

    /**
     * Gets the last mod date.
     * 
     * @return the last mod date
     */
    @NotNull
    Date getLastModDate();

    /**
     * Sets the last mod date.
     * 
     * @param lastModDate the new last mod date
     */
    void setLastModDate(Date lastModDate);
}
