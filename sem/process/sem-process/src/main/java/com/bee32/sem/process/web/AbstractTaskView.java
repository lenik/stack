package com.bee32.sem.process.web;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.explorer.I18nManager;
import org.activiti.explorer.util.time.HumanTime;
import org.primefaces.model.SortOrder;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.Strs;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.misc.AbstractSimpleEntityView;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public abstract class AbstractTaskView
        extends AbstractSimpleEntityView {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(TaskEntity.class);
    }

    @Override
    protected Task create() {
        TaskService taskService = BEAN(TaskService.class);
        Task task = taskService.newTask();
        task.setOwner(getLoggedInUserName());
        return task;
    }

    @Override
    protected boolean saveImpl(int saveFlags, String hint, boolean creating) {
        TaskService taskService = BEAN(TaskService.class);
        Task task = getOpenedObject();
        try {
            taskService.saveTask(task);
        } catch (Exception e) {
            uiLogger.error("无法保存任务", e);
            return false;
        }
        uiLogger.info("保存成功");
        return true;
    }

    public String relativeDateTo(Date date) {
        I18nManager i18nManager = BEAN(I18nManager.class);
        i18nManager.setLocale(Locale.getDefault());
        String humanTime = new HumanTime(i18nManager).format(date);
        return humanTime;
    }

    public void setNewOwner(PrincipalDto newOwner) {
        String owner = null;
        if (newOwner != null)
            owner = newOwner.getName();

        Task task = getOpenedObject();
        task.setOwner(owner);
    }

    public void setNewAssignee(PrincipalDto newAssignee) {
        String assignee = null;
        if (newAssignee != null)
            assignee = newAssignee.getName();

        Task task = getOpenedObject();
        task.setAssignee(assignee);
    }

    public void assignToMe() {
        Task task = getOpenedObject();
        task.setAssignee(getLoggedInUserName());
    }

    public void completeSelection() {
        TaskService service = BEAN(TaskService.class);
        for (Object sel : getSelection()) {
            Task task = (Task) sel;
            try {
                service.complete(task.getId());
                uiLogger.info("提交成功。");
            } catch (Exception e) {
                uiLogger.info("提交失败", e);
            }
        }
    }

    public void complete() {
        Task task = getOpenedObject();
        if (task == null)
            uiLogger.error("未选择要提交的任务。");

        TaskService service = BEAN(TaskService.class);
        try {
            service.complete(task.getId());
            uiLogger.info("提交成功。");
        } catch (Exception e) {
            uiLogger.info("提交失败", e);
        }
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean activeOnly;
    boolean suspendedOnly;
    boolean unassignedOnly;
    boolean excludeSubtasks;

    String executionId;
    String processDefinitionId;
    String processDefinitionKey;
    String processDefinitionName;
    String processInstanceBusinessKey;
    String processInstanceId;

    String taskId;
    String taskInvolvedUser;
    String taskMaxPriority;
    String taskMinPriority;
    String taskPriority;
    String taskOwner;
    String taskAssignee;
    String taskCandidateUser;
    String taskCandidateGroup;
    List<String> taskCandidateGroups;
    Date taskCreatedBefore;
    Date taskCreatedAfter;
    Date taskDueBefore;
    Date taskDueAfter;
    Date taskDueDate;
    String taskDefinitionKeyLike;
    DelegationState taskDelegationState;
    String taskNameLike;
    String taskDescriptionLike;

    protected TaskQuery createQuery() {
        TaskService service = BEAN(TaskService.class);
        TaskQuery query = service.createTaskQuery();
        return query;
    }

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

    public boolean isExcludeSubtasks() {
        return excludeSubtasks;
    }

    public void setExcludeSubtasks(boolean excludeSubtasks) {
        this.excludeSubtasks = excludeSubtasks;
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

    public Date getTaskCreatedBefore() {
        return taskCreatedBefore;
    }

    public void setTaskCreatedBefore(Date taskCreatedBefore) {
        this.taskCreatedBefore = taskCreatedBefore;
    }

    public Date getTaskCreatedAfter() {
        return taskCreatedAfter;
    }

    public void setTaskCreatedAfter(Date taskCreatedAfter) {
        this.taskCreatedAfter = taskCreatedAfter;
    }

    public Date getTaskDueBefore() {
        return taskDueBefore;
    }

    public void setTaskDueBefore(Date taskDueBefore) {
        this.taskDueBefore = taskDueBefore;
    }

    public Date getTaskDueAfter() {
        return taskDueAfter;
    }

    public void setTaskDueAfter(Date taskDueAfter) {
        this.taskDueAfter = taskDueAfter;
    }

    public Date getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(Date taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public String getTaskDefinitionKeyLike() {
        return taskDefinitionKeyLike;
    }

    public void setTaskDefinitionKeyLike(String taskDefinitionKeyLike) {
        this.taskDefinitionKeyLike = taskDefinitionKeyLike;
    }

    public DelegationState getTaskDelegationState() {
        return taskDelegationState;
    }

    public void setTaskDelegationState(DelegationState taskDelegationState) {
        this.taskDelegationState = taskDelegationState;
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

    public String getTaskInvolvedUser() {
        return taskInvolvedUser;
    }

    public void setTaskInvolvedUser(String taskInvolvedUser) {
        this.taskInvolvedUser = taskInvolvedUser;
    }

    public String getTaskMaxPriority() {
        return taskMaxPriority;
    }

    public void setTaskMaxPriority(String taskMaxPriority) {
        this.taskMaxPriority = taskMaxPriority;
    }

    public String getTaskMinPriority() {
        return taskMinPriority;
    }

    public void setTaskMinPriority(String taskMinPriority) {
        this.taskMinPriority = taskMinPriority;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    public void deleteSelection() {
        setSelection(null);

        TaskService taskService = BEAN(TaskService.class);

        for (Object sel : getSelection()) {
            Task task = (Task) sel;
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
            extends LazyDataModelImpl<Task> {

        private static final long serialVersionUID = 1L;

        @Override
        protected int countImpl() {
            TaskQuery query = createQuery();
            int count = (int) query.count();
            return count;
        }

        @Override
        protected List<?> loadImpl(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            TaskQuery query = createQuery();

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
                case "createTime":
                    query.orderByTaskCreateTime();
                    break;
                case "dueDate":
                    query.orderByDueDate();
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

            if (activeOnly)
                query.active();
            if (suspendedOnly)
                query.suspended();
            if (unassignedOnly)
                query.taskUnassigned();
            if (excludeSubtasks)
                query.excludeSubtasks();

            if (taskDueAfter != null)
                query.dueAfter(taskDueAfter);
            if (taskDueBefore != null)
                query.dueBefore(taskDueBefore);
            if (taskDueDate != null)
                query.dueDate(taskDueDate);

            if (Strs.isNotEmpty(executionId))
                query.executionId(executionId);

            if (Strs.isNotEmpty(processDefinitionId))
                query.processDefinitionId(processDefinitionId);
            if (Strs.isNotEmpty(processDefinitionKey))
                query.processDefinitionKey(processDefinitionKey);
            if (Strs.isNotEmpty(processDefinitionName))
                query.processDefinitionName(processDefinitionName);
            if (Strs.isNotEmpty(processInstanceBusinessKey))
                query.processInstanceBusinessKey(processInstanceBusinessKey);
            if (Strs.isNotEmpty(processInstanceId))
                query.processInstanceId(processInstanceId);

            if (Strs.isNotEmpty(taskId))
                query.taskId(taskId);
            if (Strs.isNotEmpty(taskOwner))
                query.taskOwner(taskOwner);
            if (Strs.isNotEmpty(taskInvolvedUser))
                query.taskInvolvedUser(taskInvolvedUser);
            if (Strs.isInteger(taskMaxPriority))
                query.taskMaxPriority(Integer.parseInt(taskMaxPriority));
            if (Strs.isInteger(taskMinPriority))
                query.taskMinPriority(Integer.parseInt(taskMinPriority));
            if (Strs.isInteger(taskPriority))
                query.taskPriority(Integer.parseInt(taskPriority));
            if (Strs.isNotEmpty(taskNameLike))
                query.taskNameLike(taskNameLike);
            if (Strs.isNotEmpty(taskDescriptionLike))
                query.taskDescriptionLike(taskDescriptionLike);
            if (Strs.isNotEmpty(taskAssignee))
                query.taskAssignee(taskAssignee);
            if (Strs.isNotEmpty(taskCandidateGroup))
                query.taskCandidateGroup(taskCandidateGroup);
            if (taskCandidateGroups != null)
                query.taskCandidateGroupIn(taskCandidateGroups);
            if (Strs.isNotEmpty(taskCandidateUser))
                query.taskCandidateUser(taskCandidateUser);
            if (taskCreatedAfter != null)
                query.taskCreatedAfter(taskCreatedAfter);
            if (taskCreatedBefore != null)
                query.taskCreatedBefore(taskCreatedBefore);
            if (Strs.isNotEmpty(taskDefinitionKeyLike))
                query.taskDefinitionKeyLike(taskDefinitionKeyLike);
            if (taskDelegationState != null)
                query.taskDelegationState(taskDelegationState);

            List<Task> list = query.listPage(first, pageSize);
            return list;
        }

        @Override
        public String getRowKey(Task object) {
            return object.getId();
        }

    }

}
