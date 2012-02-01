package com.bee32.plover.faces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class PageLocalFvf
        extends FacesVolatileFragments {

    @Override
    public List<String> getFragmentIds(FacesContext facesContext) {
        UIViewRoot root = facesContext.getViewRoot();
        if (root == null)
            return null;

        UIComponent head = root.findComponent("head");
        if (head == null)
            return null;

        Map<String, Object> attributes = head.getAttributes();

        String _volatile = (String) attributes.get("volatile");
        if (_volatile == null)
            return Collections.emptyList();

        List<String> ids = new ArrayList<String>();
        for (String id : _volatile.split(",")) {
            id = id.trim();
            ids.add(id);
        }
        return ids;
    }

}
