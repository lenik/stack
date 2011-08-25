package com.bee32.plover.web.faces;

import java.util.List;

import javax.faces.context.FacesContext;

import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class FacesVolatileFragments {

    public boolean isStatic() {
        return false;
    }

    public abstract List<String> getFragmentIds(FacesContext facesContext);

}
