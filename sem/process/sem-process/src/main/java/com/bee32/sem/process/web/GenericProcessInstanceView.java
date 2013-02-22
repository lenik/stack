package com.bee32.sem.process.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public abstract class GenericProcessInstanceView
        extends AbstractApexView {

    private static final long serialVersionUID = 1L;

    Map<String, String> nameCache = new HashMap<>();
    Map<String, String> descriptionCache = new HashMap<>();

    void loadProcessDefinitionToCache(String processDefinitionId) {
        String name = nameCache.get(processDefinitionId);
        String description = descriptionCache.get(processDefinitionId);

        if (name == null) {
            RepositoryService repositoryService = BEAN(RepositoryService.class);
            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
            if (processDefinition == null)
                name = "(n/a)";
            else {
                name = processDefinition.getName();
                description = processDefinition.getDescription();
            }

            nameCache.put(processDefinitionId, name);
            descriptionCache.put(processDefinitionId, description);
        }
    }

    public String getProcessDefinitionName(String processDefinitionId) {
        if (processDefinitionId == null)
            throw new NullPointerException("processDefinitionId");
        loadProcessDefinitionToCache(processDefinitionId);
        return nameCache.get(processDefinitionId);
    }

    public String getProcessDefinitionDescription(String processDefinitionId) {
        if (processDefinitionId == null)
            throw new NullPointerException("processDefinitionId");
        loadProcessDefinitionToCache(processDefinitionId);
        return descriptionCache.get(processDefinitionId);
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean activeOnly;
    boolean suspendedOnly;
    boolean unassignedOnly;

    String processDefinitionId;
    String processDefinitionKey;
    String processInstanceBusinessKey;
    String processInstanceId;
    Set<String> processInstanceIds;
    String subProcessInstanceId;
    String superProcessInstanceId;

    public boolean isActiveOnly() {
        return activeOnly;
    }

    public void setActiveOnly(boolean activeOnly) {
        this.activeOnly = activeOnly;
    }

    public boolean isSuspendedOnly() {
        return suspendedOnly;
    }

    public void setSuspendedOnly(boolean suspendedOnly) {
        this.suspendedOnly = suspendedOnly;
    }

    public boolean isUnassignedOnly() {
        return unassignedOnly;
    }

    public void setUnassignedOnly(boolean unassignedOnly) {
        this.unassignedOnly = unassignedOnly;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessInstanceBusinessKey() {
        return processInstanceBusinessKey;
    }

    public void setProcessInstanceBusinessKey(String processInstanceBusinessKey) {
        this.processInstanceBusinessKey = processInstanceBusinessKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Set<String> getProcessInstanceIds() {
        return processInstanceIds;
    }

    public void setProcessInstanceIds(Set<String> processInstanceIds) {
        this.processInstanceIds = processInstanceIds;
    }

}
