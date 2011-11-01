package com.bee32.plover.inject.scope;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PloverScopes
        extends CustomScopeConfigurer {

    public PloverScopes() {
        Map<String, Object> scopes = new HashMap<String, Object>();

        // scopes.put("session", new SessionScope());
        scopes.put("state", new StateScope());
        scopes.put("thread", new ThreadScope());
        scopes.put("tgroup", new ThreadGroupScope());

        this.setScopes(scopes);
    }

}
