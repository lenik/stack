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
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public class HistoricTaskView
        extends GenericTaskView {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(HistoricTaskInstanceEntity.class);
    }

    @Override
    protected void openSelection(int fmask) {
        HistoricTaskInstance task = (HistoricTaskInstance) getSingleSelection();
        if (task == null) {
            uiLogger.error("没有选择要打开的对象。");
            return;
        }

        setOpenedObject(task);
        reloadTaskDetails(task.getId());
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

    @Override
    protected boolean saveImpl(int saveFlags, String hint, boolean creating) {
        throw new UnsupportedOperationException("Can't save historic task.");
    }

    public void deleteSelection() {
        TaskService taskService = BEAN(TaskService.class);
        HistoryService historyService = BEAN(HistoryService.class);

        for (Object sel : getSelection()) {
            HistoricTaskInstance task = (HistoricTaskInstance) sel;
            try {
                taskService.deleteTask(task.getId());
                historyService.deleteHistoricTaskInstance(task.getId());
            } catch (Exception e) {
                uiLogger.error("无法删除作业 " + task.getId(), e);
                return;
            }
        }
        uiLogger.info("删除成功。");

        setSelection(null);
        // if ((deleteFlags & DELETE_NO_REFRESH) == 0)
        refreshRowCount();
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    private boolean finishedOnly;
    private boolean unfinishedOnly;

    private String executionId;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processDefinitionName;
    private String processInstanceId;

    private String taskId;
    private String taskPriority;
    private String taskOwnerLike;
    private Date taskDueBefore;
    private Date taskDueAfter;
    private Date taskDueDate;
    private String taskAssignee;
    private String taskDefinitionKey;
    private String taskNameLike;
    private String taskDescriptionLike;

    protected HistoricTaskInstanceQuery createQuery() {
        HistoryService service = BEAN(HistoryService.class);
        HistoricTaskInstanceQuery query = service.createHistoricTaskInstanceQuery();
        return query;
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

    public String getTaskOwnerLike() {
        return taskOwnerLike;
    }

    public void setTaskOwnerLike(String taskOwnerLike) {
        this.taskOwnerLike = taskOwnerLike;
    }

    /*************************************************************************
     * Section: Data Model
     *************************************************************************/

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
