package com.bee32.sem.track.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.activiti.explorer.I18nManager;
import org.activiti.explorer.util.time.HumanTime;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.file.dto.FileBlobDto;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.entity.FileBlob;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.file.web.ContentDisposition;
import com.bee32.sem.file.web.IncomingFile;
import com.bee32.sem.file.web.IncomingFileBlobAdapter;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.track.dto.IssueAttachmentDto;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.dto.IssueHrefDto;
import com.bee32.sem.track.dto.IssueObserverDto;
import com.bee32.sem.track.dto.IssueReplyDto;
import com.bee32.sem.track.entity.Issue;
import com.bee32.sem.track.entity.IssueHref;
import com.bee32.sem.track.entity.IssueReply;
import com.bee32.sem.track.util.TrackCriteria;

public abstract class AbstractIssueView
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AbstractIssueView(ICriteriaElement... criteriaElements) {
        super(Issue.class, IssueDto.class, 0, criteriaElements);
    }

    @Override
    protected IssueDto create() {
        IssueDto issue = (IssueDto) super.create();

        // User me = SessionUser.getInstance().getInternalUser();
        // IssueObserver self = new IssueObserver();
        // self.setIssue(issue);
        // self.setObserver(me);
        // self.setLabel("发起人");
        // issue.setObservers(new ArrayList<IssueObserver>(self));

        return issue;
    }

    /**
     * Need to care about APEX dependencies..
     */
    public String relativeDateTo(Date date) {
        I18nManager i18nManager = BEAN(I18nManager.class);
        i18nManager.setLocale(Locale.getDefault());
        String humanTime = new HumanTime(i18nManager).format(date);
        return humanTime;
    }

    /*************************************************************************
     * Section: Reply / Comment
     *************************************************************************/

    private String replyText;
    private List<IssueReplyDto> replies;

    @NLength(max = IssueReply.TEXT_LENGTH)
    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = TextUtil.normalizeSpace(replyText);
    }

    public List<IssueReplyDto> getReplies() {
        if (replies == null) {
            IssueDto issue = getOpenedObject();

            if (issue.getId() == null)
                replies = Collections.emptyList();
            else {
                List<IssueReply> _replies = DATA(IssueReply.class).list(//
                        TrackCriteria.repliesForIssue(issue.getId(), true));

                replies = DTOs.marshalList(IssueReplyDto.class, 0, _replies);
            }
        }
        return replies;
    }

    public void refreshReplies() {
        replies = null;
    }

    public void addReply() {
        IssueDto issue = getOpenedObject();
        Long issueId = issue.getId();
        if (issueId == null) {
            uiLogger.error("问题尚未建立。");
            return;
        }

        try {
            Issue _issue = DATA(Issue.class).get(issueId);
            if (_issue == null) {
                uiLogger.error("问题已被其他人删除。");
                return;
            }

            int userId = SessionUser.getInstance().getId();
            User me = DATA(User.class).get(userId);

            IssueReply _reply = new IssueReply();
            _reply.setIssue(_issue);
            _reply.setUser(me);
            _reply.setText(replyText);

            try {
                DATA(IssueReply.class).save(_reply);
            } catch (Exception e) {
                uiLogger.error("无法保存评论。", e);
            }
        } catch (Exception e) {
            uiLogger.error("创建评论时失败。", e);
        }

        refreshReplies();
        replyText = "";
    }

    /*************************************************************************
     * Section: Href/URL references.
     *************************************************************************/

    private String url = "";
    private String urlLabel = "";
    private String urlDescription = "";

    @NLength(max = IssueHref.URL_LENGTH)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = TextUtil.normalizeSpace(url);
    }

    @NLength(max = IssueHref.LABEL_LENGTH)
    public String getUrlLabel() {
        return urlLabel;
    }

    public void setUrlLabel(String urlLabel) {
        this.urlLabel = TextUtil.normalizeSpace(urlLabel);
    }

    @NLength(max = IssueHref.DESCRIPTION_LENGTH)
    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = TextUtil.normalizeSpace(urlDescription);
    }

    public void newHref() {
        url = "";
        urlLabel = "";
        urlDescription = "";
    }

    public void addHref() {
        if (url.isEmpty()) {
            uiLogger.error("未输入网址。");
            return;
        }
        if (urlLabel.isEmpty()) {
            uiLogger.error("未输入链接名称。");
            return;
        }

        IssueDto issue = getOpenedObject();
        IssueHrefDto issueHref = new IssueHrefDto();
        issueHref.setIssue(issue);
        issue.getHrefs().add(issueHref);
        issueHref.setLabel(urlLabel);
        issueHref.setDescription(urlDescription);
        issueHref.setUrl(url);
    }

    public void removeHref(long removeId) {
        IssueDto issue = getOpenedObject();
        Iterator<IssueHrefDto> iterator = issue.getHrefs().iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next().getId();
            if (id == null)
                continue;
            if (id == removeId)
                iterator.remove();
        }
    }

    /*************************************************************************
     * Section: Attachment Files
     *************************************************************************/

    private String fileBlobId = null;
    private String filePath = "";
    private String fileLabel = "";
    private String fileDescription = "";
    private String fileContentType = "";

    @NLength(max = UserFile.NAME_LENGTH)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = TextUtil.normalizeSpace(filePath);
    }

    @NLength(max = UserFile.LABEL_LENGTH)
    public String getFileLabel() {
        return fileLabel;
    }

    public void setFileLabel(String fileLabel) {
        this.fileLabel = TextUtil.normalizeSpace(fileLabel);
    }

    @NLength(max = UserFile.DESCRIPTION_LENGTH)
    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = TextUtil.normalizeSpace(fileDescription);
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public void newFile() {
        fileBlobId = null;
        filePath = "";
        fileLabel = "";
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

            if (fileLabel.isEmpty())
                fileLabel = filePath;

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
        if (fileLabel.isEmpty()) {
            uiLogger.error("未输入附件名称。");
            return;
        }

        IssueDto issue = getOpenedObject();

        IssueAttachmentDto attachment = new IssueAttachmentDto();
        FileBlobDto fileBlob = new FileBlobDto().ref(fileBlobId);
        attachment.setIssue(issue);
        attachment.setFileBlob(fileBlob);
        attachment.setLabel(fileLabel);
        attachment.setDescription(fileDescription);
        issue.getAttachments().add(attachment);
    }

    public StreamedContent downloadFile(IssueAttachmentDto attachment)
            throws IOException {
        if (attachment == null)
            throw new NullPointerException("attachment");

        String blobId = attachment.getFileBlob().getId();

        FileBlob fileBlob = DATA(FileBlob.class).get(blobId);
        if (fileBlob == null) {
            uiLogger.error("文件数据不存在: " + blobId);
            return null;
        }

        String contentType = fileBlob.getContentType();
        InputStream in = fileBlob.newInputStream();
        StreamedContent stream = new DefaultStreamedContent(in, contentType, attachment.getLabel());
        return stream;
    }

    public String contentDisposition(IssueAttachmentDto attachment) {
        boolean downloadAsAttachment = true;
        if (attachment == null)
            throw new NullPointerException("attachment");
        String filename = attachment.getLabel().toString();
        return ContentDisposition.format(filename, downloadAsAttachment, !downloadAsAttachment);
    }

    public void removeAttachment(long removeId) {
        IssueDto issue = getOpenedObject();
        Iterator<IssueAttachmentDto> iterator = issue.getAttachments().iterator();
        while (iterator.hasNext()) {
            Long id = iterator.next().getId();
            if (id == null)
                continue;
            if (id == removeId)
                iterator.remove();
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<IssueObserverDto> observersMBean = ListMBean.fromEL(this,//
            "openedObject.observers", IssueObserverDto.class);
    final ListMBean<IssueObserverDto> ccGroupsMBean = ListMBean.fromEL(this,//
            "openedObject.ccGroups", IssueObserverDto.class);
    final ListMBean<IssueHrefDto> hrefsMBean = ListMBean.fromEL(this,//
            "openedObject.hrefs", IssueHrefDto.class);
    final ListMBean<UserFileDto> attachmentsMBean = ListMBean.fromEL(this,//
            "openedObject.attachments", UserFileDto.class);
    final ListMBean<IssueReplyDto> repliesMBean = ListMBean.fromEL(this,//
            "openedObject.replies", IssueReplyDto.class);

    public ListMBean<IssueObserverDto> getObserversMBean() {
        return observersMBean;
    }

    public ListMBean<IssueObserverDto> getCcGroupsMBean() {
        return ccGroupsMBean;
    }

    public ListMBean<IssueHrefDto> getHrefsMBean() {
        return hrefsMBean;
    }

    public ListMBean<UserFileDto> getAttachmentsMBean() {
        return attachmentsMBean;
    }

    public ListMBean<IssueReplyDto> getRepliesMBean() {
        return repliesMBean;
    }

    public String getChanceText() {
        IssueDto issue = getOpenedObject();
        ChanceDto chance = issue.getChance();
        if (chance == null || chance.getId() == null)
            return "";
        else
            return chance.getId() + ": " + chance.getSubject();
    }

    public String getStockOrderText() {
        IssueDto issue = getOpenedObject();
        StockOrderDto stockOrder = issue.getStockOrder();
        if (stockOrder == null || stockOrder.getId() == null)
            return "";

        StockOrderSubject subject = stockOrder.getSubject();
        return subject + " " + stockOrder.getId() + ": " + stockOrder.getLabel();
    }

// public void setStockOrderText(String stockOrderText) {
// }

}
