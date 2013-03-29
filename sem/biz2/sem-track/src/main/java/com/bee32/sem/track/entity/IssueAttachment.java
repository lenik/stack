package com.bee32.sem.track.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.file.entity.FileBlob;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_attachment_seq", allocationSize = 1)
public class IssueAttachment
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    private Issue issue;
    private FileBlob fileBlob;

    @ManyToOne(optional = false)
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        if (issue == null)
            throw new NullPointerException("issue");
        this.issue = issue;
    }

    @ManyToOne(optional = false)
    public FileBlob getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(FileBlob fileBlob) {
        if (fileBlob == null)
            throw new NullPointerException("fileBlob");
        this.fileBlob = fileBlob;
    }

}
