package com.bee32.sem.process.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.free.FilePath;

import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.primefaces.model.SortOrder;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.Strs;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.util.Mime;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.web.IncomingFile;
import com.bee32.sem.file.web.IncomingFileBlobAdapter;
import com.bee32.sem.sandbox.LazyDataModelImpl;

/**
 * @see com.bee32.ape.engine.beans.ApeActivitiServices
 */
public abstract class AbstractTaskView
        extends GenericTaskView
        implements ILocationConstants {

    private static final long serialVersionUID = 1L;

    DataModel dataModel = new DataModel();

    String commentText = "";
    String url = "";
    String urlName = "";
    String urlDescription = "";

    String fileBlobId = null;
    String filePath = "";
    String fileName = "";
    String fileDescription = "";
    String fileContentType = "";

    @Override
    public DataModel getDataModel() {
        return dataModel;
    }

    public String getEntityTypeAbbr() {
        return ITypeAbbrAware.ABBR.abbr(TaskEntity.class);
    }

    @Override
    protected void openSelection(int fmask) {
        Task task = (Task) getSingleSelection();
        if (task == null) {
            uiLogger.error("没有选择要打开的对象。");
            return;
        }

        setOpenedObject(task);
        reloadTaskDetails(task.getId());
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        if (commentText == null)
            throw new NullPointerException("commentText");
        this.commentText = TextUtil.normalizeSpace(commentText);
    }

    public void addComment() {
        if (commentText.isEmpty()) {
            uiLogger.error("未输入评论内容。");
            return;
        }

        IdentityService identityService = BEAN(IdentityService.class);
        identityService.setAuthenticatedUserId(getLoggedInUserName());

        TaskService taskService = BEAN(TaskService.class);
        Task task = getOpenedObject();

        try {
            if (task.getId() == null) {
                taskService.saveTask(task);
                uiLogger.info("新任务已创建。");
            }

            taskService.addComment(task.getId(), task.getProcessInstanceId(), commentText);
            reloadTaskDetails(task.getId());
        } catch (Exception e) {
            uiLogger.error("无法添加评论", e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url == null)
            throw new NullPointerException("url");
        this.url = TextUtil.normalizeSpace(url);
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        if (urlName == null)
            throw new NullPointerException("urlName");
        this.urlName = TextUtil.normalizeSpace(urlName);
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        if (urlDescription == null)
            throw new NullPointerException("urlDescription");
        this.urlDescription = TextUtil.normalizeSpace(urlDescription);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        if (filePath == null)
            throw new NullPointerException("filePath");
        this.filePath = TextUtil.normalizeSpace(filePath);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName == null)
            throw new NullPointerException("fileName");
        this.fileName = TextUtil.normalizeSpace(fileName);
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        if (fileDescription == null)
            throw new NullPointerException("fileDescription");
        this.fileDescription = TextUtil.normalizeSpace(fileDescription);
    }

    public void addURL() {
        if (url.isEmpty()) {
            uiLogger.error("未输入网址。");
            return;
        }
        if (urlName.isEmpty()) {
            uiLogger.error("未输入链接名称。");
            return;
        }

        IdentityService identityService = BEAN(IdentityService.class);
        identityService.setAuthenticatedUserId(getLoggedInUserName());

        TaskService taskService = BEAN(TaskService.class);
        Task task = getOpenedObject();

        try {
            if (task.getId() == null) {
                taskService.saveTask(task);
                uiLogger.info("新任务已创建。");
            }

            String contentType = "text/html";

            String file = url;
            int quest = file.lastIndexOf('?');
            if (quest != -1)
                file = file.substring(0, quest);
            String extension = FilePath.getExtension(file, false);
            Mime mime = Mime.getInstanceByExtension(extension);
            if (mime != null)
                contentType = mime.getContentType();

            /**
             * indication of the type of content that this attachment refers to. Can be mime type or
             * any other indication.
             */
            taskService.createAttachment(contentType, task.getId(), task.getProcessInstanceId(), urlName,
                    urlDescription, url);
        } catch (Exception e) {
            uiLogger.error("无法添加链接", e);
            return;
        }

        reloadTaskDetails(task.getId());
    }

    public void newFile() {
        fileBlobId = null;
        filePath = "";
        fileName = "";
        fileDescription = "";
        fileContentType = "";
    }

    public Object getUploadFileListener() {
        return new UploadFileListener();
    }

    class UploadFileListener
            extends IncomingFileBlobAdapter {

        @Override
        protected void process(FileBlob fileBlob, IncomingFile incomingFile)
                throws IOException {
            fileBlobId = fileBlob.getId();
            filePath = incomingFile.getFileName();
            fileContentType = incomingFile.getContentType();

            if (fileName.isEmpty())
                fileName = filePath;

            DATA(FileBlob.class).saveOrUpdate(fileBlob);
        }

        @Override
        protected void reportError(String message, Exception exception) {
            uiLogger.error(message, exception);
        }

    }

    public void addFile() {
        if (fileBlobId == null) {
            uiLogger.error("尚未上传附件。");
            return;
        }
        if (filePath == null) {
            uiLogger.error("未指定文件名。");
            return;
        }
        if (fileName.isEmpty()) {
            uiLogger.error("未输入附件名称。");
            return;
        }

        IdentityService identityService = BEAN(IdentityService.class);
        identityService.setAuthenticatedUserId(getLoggedInUserName());

        TaskService taskService = BEAN(TaskService.class);
        Task task = getOpenedObject();

        try {
            if (task.getId() == null) {
                taskService.saveTask(task);
                uiLogger.info("新任务已创建。");
            }

            InputStream fakeContent = new ByteArrayInputStream(fileBlobId.getBytes());

            taskService.createAttachment(fileContentType, task.getId(), task.getProcessInstanceId(), fileName,
                    fileDescription, fakeContent);
        } catch (Exception e) {
            uiLogger.error("无法添加附件", e);
            return;
        }

        reloadTaskDetails(task.getId());
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/

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

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    protected TaskQuery createQuery() {
        TaskService service = BEAN(TaskService.class);
        TaskQuery query = service.createTaskQuery();
        return query;
    }

    /*************************************************************************
     * Section: Data Model
     *************************************************************************/

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
