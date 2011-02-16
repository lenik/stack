package com.bee32.icsf.principal.realm;

import java.util.Collection;

import com.bee32.icsf.principal.IPrincipal;

public interface Realm {

    Collection<? extends IPrincipal> getPrincipals();

}
