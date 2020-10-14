package com.xpandit.confluence.plugins.link.preview.rest.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

// TODO: Auto-generated Javadoc
/**
 * The Class LinkResolverResponseBean.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LinkResolverResponseBean {

    /** The link. */
    @XmlAttribute
    private String link;

    /** The obj type. */
    @XmlAttribute
    private String objType;

    /**
     * Instantiates a new link resolver response bean.
     * 
     * @param link the link
     * @param objType the obj type
     */
    public LinkResolverResponseBean(String link, String objType) {
        super();
        this.link = link;
        this.objType = objType;
    }

    /**
     * Instantiates a new link resolver response bean.
     */
    public LinkResolverResponseBean() {
        super();
    }

    /**
     * Gets the link.
     * 
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link.
     * 
     * @param link the new link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the obj type.
     * 
     * @return the obj type
     */
    public String getObjType() {
        return objType;
    }

    /**
     * Sets the obj type.
     * 
     * @param objType the new obj type
     */
    public void setObjType(String objType) {
        this.objType = objType;
    }

}
