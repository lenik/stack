package com.bee32.sems.security;

import java.util.Collection;

import com.bee32.sems.security.access.IPrincipal;

public interface Realm {

    Collection<? extends IPrincipal> getPrincipals();

}
