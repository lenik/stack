package com.bee32.plover.faces.tag;

import org.apache.myfaces.view.facelets.tag.AbstractTagLibrary;

/**
 * Suggested namespace prefix: pl:*.
 */
public class PloverFacesTagLibrary
        extends AbstractTagLibrary {

    public static final String namespace = "http://bee32.com/plover/faces";

    public PloverFacesTagLibrary() {
        super(namespace);

        // this.addTagHandler("insertChildren2", InsertChildren2Handler.class);
    }

}
