package com.bee32.ape.html.apex.beans;

import org.activiti.workflow.simple.converter.WorkflowDefinitionConversionFactory;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite(reason = "maybe optional")
public class ApexWorkflowDefinitionConversionFactory
        extends WorkflowDefinitionConversionFactory {

}
