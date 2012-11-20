package com.bee32.sem.track.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.file.entity.UserFile;

/**
 * 问题
 *
 * 需要解决的问题。
 *
 * -- 用 issue 而不用 topic, question，以突出这个问题是需要解决的具有任务性质的。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "issue_seq", allocationSize = 1)
public class Issue
        extends MomentInterval {

    private static final long serialVersionUID = 1L;

    public static final int TEXT_LENGTH = 4000;
    public static final int REPLAY_LENGTH = 500;
    public static final int COMMITISH_LENGTH = 32;
    public static final int TAGS_LENGTH = 100;

    IssueState state = IssueState.NEW;

    String text = "";
    String replay = "";

    String tags = "";
    String commitish;

    List<UserFile> attachments = new ArrayList<UserFile>();
    List<IssueReply> replies = new ArrayList<IssueReply>();

    /**
     * 状态
     *
     * 问题的当前状态，如已解决、挂起等。
     */
    @Transient
    public IssueState getState() {
        return state;
    }

    public void setState(IssueState state) {
        this.state = state;
    }

    @Column(name = "state", nullable = false)
    char get_state() {
        return state.getValue();
    }

    void set_state(char _state) {
        state = IssueState.forValue(_state);
    }

    /**
     * 详细信息
     *
     * 详细描述当前存在的问题。
     */
    @Column(length = TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null)
            throw new NullPointerException("problem");
        this.text = text;
    }

    /**
     * 问题重现
     *
     * 描述使问题重现的具体的操作步骤。
     */
    @Column(length = REPLAY_LENGTH)
    public String getReplay() {
        return replay;
    }

    public void setReplay(String replay) {
        if (replay == null)
            throw new NullPointerException("replay");
        this.replay = replay;
    }

    /**
     * 标签集
     *
     * 问题关联的标签集，用空格分隔。
     */
    @Column(length = TAGS_LENGTH)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

    /**
     * 提交标识
     *
     * 和问题相关的提交标识。（未使用）
     */
    @Column(length = COMMITISH_LENGTH)
    public String getCommitish() {
        return commitish;
    }

    public void setCommitish(String commitish) {
        this.commitish = commitish;
    }

    /**
     * 相关附件
     *
     * 和问题相关的附件，如屏幕截图、产品缺陷照片等等。
     */
    @ElementCollection
    @JoinTable(name = "IssueAttachment", //
    /*        */joinColumns = @JoinColumn(name = "issue"),
    /*        */inverseJoinColumns = @JoinColumn(name = "userFile"))
    public List<UserFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFile> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    /**
     * 回复信息
     *
     * 对本问题的跟踪回复。
     */
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
