package com.bee32.sem.process.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.primefaces.model.SortOrder;

import com.bee32.plover.arch.util.Strs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public class HistoricTaskView
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(HistoricTaskInstanceEntity.class);
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean finishedOnly;
    boolean unfinishedOnly;
    boolean unassignedOnly;
    boolean excludeSubtasks;

    String executionId;
    String processDefinitionId;
    String processDefinitionKey;
    String processDefinitionName;
    String processInstanceId;

    String taskId;
    String taskPriority;
    String taskOwnerLike;
    Date taskDueBefore;
    Date taskDueAfter;
    Date taskDueDate;
    String taskAssignee;
    String taskCandidateUser;
    String taskCandidateGroup;
    List<String> taskCandidateGroups;
    String taskDefinitionKey;
    String taskNameLike;
    String taskDescriptionLike;

    protected HistoricTaskInstanceQuery createQuery() {
        HistoryService service = BEAN(HistoryService.class);
        HistoricTaskInstanceQuery query = service.createHistoricTaskInstanceQuery();
        return query;
    }

    public boolean isActiveOnly() {
        return finishedOnly;
    }

    public void setActiveOnly(boolean activeOnly) {
        this.finishedOnly = activeOnly;
    }

    public boolean isSuspendedOnly() {
        return unfinishedOnly;
    }

    public void setSuspendedOnly(boolean suspendedOnly) {
        this.unfinishedOnly = suspendedOnly;
    }

    public boolean isUnassignedOnly() {
        return unassignedOnly;
    }

    public void setUnassignedOnly(boolean unassignedOnly) {
        this.unassignedOnly = unassignedOnly;
    }

    public boolean isExcludeSubtasks() {
        return excludeSubtasks;
    }

    public void setExcludeSubtasks(boolean excludeSubtasks) {
        this.excludeSubtasks = excludeSubtasks;
    }

    public Date getDueBefore() {
        return taskDueBefore;
    }

    public void setDueBefore(Date dueBefore) {
        this.taskDueBefore = dueBefore;
    }

    public Date getDueAfter() {
        return taskDueAfter;
    }

    public void setDueAfter(Date dueAfter) {
        this.taskDueAfter = dueAfter;
    }

    public Date getDueDate() {
        return taskDueDate;
    }

    public void setDueDate(Date dueDate) {
        this.taskDueDate = dueDate;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
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

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    public String getTaskCandidateUser() {
        return taskCandidateUser;
    }

    public void setTaskCandidateUser(String taskCandidateUser) {
        this.taskCandidateUser = taskCandidateUser;
    }

    public String getTaskCandidateGroup() {
        return taskCandidateGroup;
    }

    public void setTaskCandidateGroup(String taskCandidateGroup) {
        this.taskCandidateGroup = taskCandidateGroup;
    }

    public List<String> getTaskCandidateGroups() {
        return taskCandidateGroups;
    }

    public void setTaskCandidateGroups(List<String> taskCandidateGroups) {
        this.taskCandidateGroups = taskCandidateGroups;
    }

    public String getTaskDefinitionKeyLike() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKeyLike(String taskDefinitionKeyLike) {
        this.taskDefinitionKey = taskDefinitionKeyLike;
    }

    public String getTaskNameLike() {
        return taskNameLike;
    }

    public void setTaskNameLike(String taskNameLike) {
        this.taskNameLike = taskNameLike;
    }

    public String getTaskDescriptionLike() {
        return taskDescriptionLike;
    }

    public void setTaskDescriptionLike(String taskDescriptionLike) {
        this.taskDescriptionLike = taskDescriptionLike;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskOwnerLike() {
        return taskOwnerLike;
    }

    public void setTaskOwnerLike(String taskOwnerLike) {
        this.taskOwnerLike = taskOwnerLike;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    public void deleteSelection() {
        setSelection(null);

        TaskService taskService = BEAN(TaskService.class);

        for (Object sel : getSelection()) {
            HistoricTaskInstance task = (HistoricTaskInstance) sel;
            try {
                taskService.deleteTask(task.getId(), true);
            } catch (Exception e) {
                uiLogger.error("无法删除任务 " + task.getId(), e);
                return;
            }
        }
        uiLogger.info("删除成功。");

        // if ((deleteFlags & DELETE_NO_REFRESH) == 0)
        refreshRowCount();
    }

    class DataModel
            extends LazyDataModelImpl<HistoricTaskInstance> {

        private static final long serialVersionUID = 1L;

        @Override
        protected int countImpl() {
            HistoricTaskInstanceQuery query = createQuery();
            int count = (int) query.count();
            return count;
        }

        @Override
        protected List<?> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            HistoricTaskInstanceQuery query = createQuery();

            if (sortField != null)
                switch (sortField) {
                case "id":
                    query.orderByTaskId();
                    break;
                case "name":
                    query.orderByTaskName();
                    break;
                case "description":
                    query.orderByTaskDescription();
                    break;
                case "priority":
                    query.orderByTaskPriority();
                    break;
                case "startTime":
                    query.orderByHistoricTaskInstanceStartTime();
                    break;
                case "endTime":
                    query.orderByHistoricTaskInstanceEndTime();
                    break;
                case "duration":
                    query.orderByHistoricTaskInstanceDuration();
                    break;
                case "taskDueDate":
                    query.orderByTaskDueDate();
                    break;
                case "assignee":
                    query.orderByTaskAssignee();
                    break;
                case "executionId":
                    query.orderByExecutionId();
                    break;
                case "processInstanceId":
                    query.orderByProcessInstanceId();
                    break;
                default:
                    sortOrder = SortOrder.UNSORTED;
                } // switch

            if (sortOrder != null && sortOrder != SortOrder.UNSORTED)
                if (sortOrder == SortOrder.ASCENDING)
                    query.asc();
                else
                    query.desc();

            if (finishedOnly)
                query.finished();
            if (unfinishedOnly)
                query.unfinished();

            if (taskDueAfter != null)
                query.taskDueAfter(taskDueAfter);
            if (taskDueBefore != null)
                query.taskDueBefore(taskDueBefore);
            if (taskDueDate != null)
                query.taskDueDate(taskDueDate);

            if (Strs.isNotEmpty(executionId))
                query.executionId(executionId);

            if (Strs.isNotEmpty(processDefinitionId))
                query.processDefinitionId(processDefinitionId);
            if (Strs.isNotEmpty(processDefinitionKey))
                query.processDefinitionKey(processDefinitionKey);
            if (Strs.isNotEmpty(processDefinitionName))
                query.processDefinitionName(processDefinitionName);
            if (Strs.isNotEmpty(processInstanceId))
                query.processInstanceId(processInstanceId);

            if (Strs.isNotEmpty(taskId))
                query.taskId(taskId);
            if (Strs.isNotEmpty(taskOwnerLike))
                query.taskOwner(taskOwnerLike);
            if (Strs.isInteger(taskPriority))
                query.taskPriority(Integer.parseInt(taskPriority));
            if (Strs.isNotEmpty(taskNameLike))
                query.taskNameLike(taskNameLike);
            if (Strs.isNotEmpty(taskDescriptionLike))
                query.taskDescriptionLike(taskDescriptionLike);
            if (Strs.isNotEmpty(taskAssignee))
                query.taskAssignee(taskAssignee);
            if (Strs.isNotEmpty(taskDefinitionKey))
                query.taskDefinitionKey(taskDefinitionKey);

            List<HistoricTaskInstance> list = query.listPage(first, pageSize);
            return list;
        }

        @Override
        public String getRowKey(HistoricTaskInstance object) {
            return object.getId();
        }

    }

}
