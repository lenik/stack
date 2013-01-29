package com.bee32.ape.html.apex.beans.standalone;

import org.activiti.explorer.ui.login.DefaultLoginHandler;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class ApexLoginHandler
        extends DefaultLoginHandler {

}
