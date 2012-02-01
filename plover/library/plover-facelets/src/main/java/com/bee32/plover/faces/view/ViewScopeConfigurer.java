package com.bee32.plover.faces.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ViewScopeConfigurer
        extends CustomScopeConfigurer {

    public ViewScopeConfigurer() {
        Map<String, Object> scopes = new HashMap<String, Object>();

        scopes.put("view", new ViewScope());

        this.setScopes(scopes);
    }

}
