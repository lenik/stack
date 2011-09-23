package com.bee32.plover.site.scope;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.stereotype.Component;

@Component
public class SiteScopeConfigurer
        extends CustomScopeConfigurer {

    public SiteScopeConfigurer() {
        Map<String, Object> scopes = new HashMap<String, Object>();

        scopes.put("site", new SiteScope());

        this.setScopes(scopes);
    }

}
