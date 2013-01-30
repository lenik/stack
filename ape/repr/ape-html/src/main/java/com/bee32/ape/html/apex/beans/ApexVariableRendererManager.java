package com.bee32.ape.html.apex.beans;

import org.activiti.explorer.ui.variable.VariableRendererManager;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite(reason = "stated")
public class ApexVariableRendererManager
        extends VariableRendererManager {

}
