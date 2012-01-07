package com.bee32.icsf.principal;

import java.util.ArrayList;
import java.util.List;

public class PrincipalAware {

    List<IPrincipalChangeListener> principalChangeListeners = new ArrayList<IPrincipalChangeListener>();

    public void addPrincipalChangeListener(IPrincipalChangeListener listener) {
        principalChangeListeners.add(listener);
    }

    public void removePrincipalChangeListener(IPrincipalChangeListener listener) {
        principalChangeListeners.remove(listener);
    }

    public void firePrincipalChange(PrincipalChangeEvent event) {
        for (IPrincipalChangeListener listener : principalChangeListeners)
            listener.principalChanged(event);
    }

    static final PrincipalAware instance = new PrincipalAware();

    public static PrincipalAware getInstance() {
        return instance;
    }

}
