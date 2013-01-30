package com.bee32.ape.html.apex.beans.standalone;

import javax.inject.Inject;

import org.activiti.engine.IdentityService;
import org.activiti.explorer.ui.login.DefaultLoginHandler;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class ApexLoginHandler
        extends DefaultLoginHandler {

    @Inject
    @Override
    public void setIdentityService(IdentityService identityService) {
        super.setIdentityService(identityService);
    }

}
