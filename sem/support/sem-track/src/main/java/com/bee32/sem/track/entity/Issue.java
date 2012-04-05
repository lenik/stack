package com.bee32.sem.track.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.file.entity.UserFile;

@Entity
public class Issue
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int PROBLEM_LENGTH = 4000;
    public static final int REPLAY_LENGTH = 500;

    List<IssueTagname> tags = new ArrayList<IssueTagname>();
    String commitish;

    String problem;
    String replay;

    List<UserFile> attachments = new ArrayList<UserFile>();
    List<IssueReply> replies = new ArrayList<IssueReply>();

    @Column(length = PROBLEM_LENGTH)
    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        if (problem == null)
            throw new NullPointerException("problem");
        this.problem = problem;
    }

    @Column(length = REPLAY_LENGTH)
    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        this.replay = replay;
    }

    @ElementCollection
    @JoinTable(name = "IssueAttachment", //
    /*        */inverseJoinColumns = @JoinColumn(name = "userFile"))
    public List<UserFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFile> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    @OneToMany(mappedBy = "issue", orphanRemoval = true)
    public List<IssueReply> getReplies() {
        return replies;
    }

    public void setReplies(List<IssueReply> replies) {
        if (replies == null)
            throw new NullPointerException("replies");
        this.replies = replies;
    }

}
