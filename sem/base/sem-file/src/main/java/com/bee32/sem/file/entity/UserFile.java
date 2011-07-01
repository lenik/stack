package com.bee32.sem.file.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CollectionOfElements;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.entity.EntityAuto;

@Entity
public class UserFile
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    User owner;
    FileBlob fileBlob;

    String origPath;
    String filename;
    String subject = "";

    Set<UserFileTagname> tags = new HashSet<UserFileTagname>();

    /**
     * 文件的属主用户。
     */
    @ManyToOne(optional = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * 文件数据。
     */
    @ManyToOne(optional = false)
    public FileBlob getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(FileBlob fileBlob) {
        this.fileBlob = fileBlob;
    }

    /**
     * 用户上传文件时的原始路径。
     *
     * Generally, the original path is not used.
     *
     * It's just existed for reference purpose, or for reconstruct the original fs-tree.
     */
    @Column(length = 100)
    public String getOrigPath() {
        return origPath;
    }

    public void setOrigPath(String origPath) {
        this.origPath = origPath;
    }

    /**
     * 用户对上传的文件重命名。
     */
    @Column(length = 80, nullable = false)
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * 用户填写的文件标题（用语显示）。
     */
    @Column(length = 200)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    /**
     * 用户为文件添加的标签。
     */
    @CollectionOfElements
    public Set<UserFileTagname> getTags() {
        return tags;
    }

    public void setTags(Set<UserFileTagname> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

}
