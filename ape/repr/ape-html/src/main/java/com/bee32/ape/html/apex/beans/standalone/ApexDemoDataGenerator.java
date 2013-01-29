package com.bee32.ape.html.apex.beans.standalone;

import javax.inject.Inject;

import org.activiti.engine.ProcessEngine;
import org.activiti.explorer.demo.DemoDataGenerator;
import org.springframework.stereotype.Component;

import com.bee32.plover.site.scope.PerSite;

@Component
@PerSite
public class ApexDemoDataGenerator
        extends DemoDataGenerator {

    public ApexDemoDataGenerator() {
        setCreateDemoUsersAndGroups(true);
        setCreateDemoProcessDefinitions(true);
        setCreateDemoModels(true);
    }

    @Inject
    @Override
    public void setProcessEngine(ProcessEngine processEngine) {
        super.setProcessEngine(processEngine);
    }

}
