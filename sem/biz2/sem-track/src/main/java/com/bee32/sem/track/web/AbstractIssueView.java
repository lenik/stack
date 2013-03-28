package com.bee32.sem.track.web;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import org.activiti.explorer.I18nManager;
import org.activiti.explorer.util.time.HumanTime;
import org.primefaces.model.StreamedContent;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.file.web.ContentDisposition;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.entity.Issue;

public abstract class AbstractIssueView
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AbstractIssueView(ICriteriaElement... criteriaElements) {
        super(Issue.class, IssueDto.class, 0, criteriaElements);
    }

    /**
     * Need to care about dependencies..
     */
    public String relativeDateTo(Date date) {
        I18nManager i18nManager = BEAN(I18nManager.class);
        i18nManager.setLocale(Locale.getDefault());
        String humanTime = new HumanTime(i18nManager).format(date);
        return humanTime;
    }

    private String replyText;
    private String url;
    private String urlName;
    private String urlDescription;

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    public void removeHref(long id) {
    }

    public void removeAttachment(long id) {
    }

    public StreamedContent downloadFile(UserFileDto attachment)
            throws IOException {
        return null;
    }

    public String contentDisposition(UserFileDto attachment) {
        boolean downloadAsAttachment = true;
        if (attachment == null)
            throw new NullPointerException("attachment");
        String filename = attachment.getFileName().toString();
        return ContentDisposition.format(filename, downloadAsAttachment, !downloadAsAttachment);
    }

}
