package com.bee32.sem.process.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Event;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.web.ContentDisposition;

public abstract class GenericTaskView
        extends AbstractApexView {

    private static final long serialVersionUID = 1L;

    private List<Event> events;
    private List<Comment> comments;
    private List<Attachment> attachments;

    protected void reloadTaskDetails(String taskId) {
        TaskService service = BEAN(TaskService.class);
        events = service.getTaskEvents(taskId);
        comments = service.getTaskComments(taskId);
        attachments = service.getTaskAttachments(taskId);
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void removeAttachment(String attachmentId, String type) {
        if (attachmentId == null)
            throw new NullPointerException("attachmentId");
        TaskService taskService = BEAN(TaskService.class);
        try {
            taskService.deleteAttachment(attachmentId);
        } catch (Exception e) {
            uiLogger.error("无法删除" + type, e);
            return;
        }

        Iterator<Attachment> iterator = attachments.iterator();
        while (iterator.hasNext()) {
            Attachment att = iterator.next();
            if (att.getId().equals(attachmentId))
                iterator.remove();
        }
    }

    /*************************************************************************
     * Section: Attachment upload/download
     *************************************************************************/

    public StreamedContent downloadFile(Attachment attachment)
            throws IOException {
        if (attachment == null)
            throw new NullPointerException("attachment");

        TaskService taskService = BEAN(TaskService.class);

        InputStream fakeContent = taskService.getAttachmentContent(attachment.getId());
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int byt;
        while ((byt = fakeContent.read()) != -1)
            buf.write(byt);
        String blobId = new String(buf.toByteArray());

        FileBlob fileBlob = DATA(FileBlob.class).get(blobId);
        if (fileBlob == null) {
            uiLogger.error("文件数据不存在: " + blobId);
            return null;
        }

        String contentType = fileBlob.getContentType();
        InputStream in = fileBlob.newInputStream();
        StreamedContent stream = new DefaultStreamedContent(in, contentType, attachment.getName());
        return stream;
    }

    public String contentDisposition(Attachment attachment) {
        boolean downloadAsAttachment = true;
        if (attachment == null)
            throw new NullPointerException("attachment");
        String filename = attachment.getName();
        return ContentDisposition.format(filename, downloadAsAttachment, !downloadAsAttachment);
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

}
