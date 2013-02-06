package com.bee32.ape.engine.beans;

import javax.inject.Inject;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.site.scope.PerSite;

@Configuration
@PerSite
public class ApeActivitiServices {

    @Inject
    ProcessEngine processEngine;

    @Bean
    @Scope("site")
    public RepositoryService getRepositoryService() {
        return processEngine.getRepositoryService();
    }

    @Bean
    @Scope("site")
    public RuntimeService getRuntimeService() {
        return processEngine.getRuntimeService();
    }

    @Bean
    @Scope("site")
    public FormService getFormService() {
        return processEngine.getFormService();
    }

    @Bean
    @Scope("site")
    public TaskService getTaskService() {
        return processEngine.getTaskService();
    }

    @Bean
    @Scope("site")
    public HistoryService getHistoryService() {
        return processEngine.getHistoryService();
    }

    @Bean
    @Scope("site")
    public IdentityService getIdentityService() {
        return processEngine.getIdentityService();
    }

    @Bean
    @Scope("site")
    public ManagementService getManagementService() {
        return processEngine.getManagementService();
    }

}
