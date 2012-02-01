package com.bee32.plover.faces.tag.composite;

import org.apache.myfaces.view.facelets.tag.AbstractTagLibrary;

/**
 * @see org.apache.myfaces.view.facelets.tag.composite.CompositeLibrary
 */
public class CompositeLibrary
        extends AbstractTagLibrary {

    public final static String NAMESPACE = "http://java.sun.com/jsf/composite";

    public CompositeLibrary() {
        super(NAMESPACE);

        this.addTagHandler("insertRawFacet", InsertRawFacetHandler.class);
    }

}
