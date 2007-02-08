/*
 * Created on 2005-11-2 By Futaba
 */
package org.commonfarm.search;

/**
 * The item of Search
 * 
 * @author Junhao Yang
 */
public class SearchItem {
    private String name;//condition name
    private String type;
    private String mode;
    private String title;
    private String association;//eg: Order --> OrderItem
    private String[] properties;//eg: between property1 and property2

    public SearchItem() {
    }

    /**
     * @return Returns the mode.
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode
     *            The mode to set.
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return Returns the association.
     */
    public String getAssociation() {
        return association;
    }
    /**
     * @param association The association to set.
     */
    public void setAssociation(String association) {
        this.association = association;
    }
    /**
     * @return Returns the properties.
     */
    public String[] getProperties() {
        return properties;
    }
    /**
     * @param properties The properties to set.
     */
    public void setProperties(String[] properties) {
        this.properties = properties;
    }
    /**
     * @return Returns the title.
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}