package com.bee32.sem.file.entity;

import java.util.HashSet;
import java.util.Set;

import javax.free.FilePath;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.ext.color.Green;

/**
 * 用户文件。
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "user_file_seq", allocationSize = 1)
public class UserFile
        extends EntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    FileBlob fileBlob;

    String origPath;
    String fileName;
    String subject = "";

    Set<UserFileTagname> tags = new HashSet<UserFileTagname>();

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
    @Column(length = 120)
    public String getOrigPath() {
        return origPath;
    }

    public void setOrigPath(String origPath) {
        this.origPath = origPath;
    }

    /**
     * 用户对上传的文件重命名。
     */
    @Column(length = 50)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName != null) {
            fileName = fileName.trim();
            if (fileName.isEmpty())
                fileName = null;
        }
        this.fileName = fileName;
    }

    @Transient
    public String getDownloadName() {
        if (fileName != null)
            return fileName;
        String path = getOrigPath();
        String ext = FilePath.getExtension(path, true);
        String name = "Noname" + ext;
        return name;
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
    @ManyToMany
    @JoinTable(name = "UserFileTags", //
    /*            */joinColumns = @JoinColumn(name = "userFile"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "tag"))
    public Set<UserFileTagname> getTags() {
        return tags;
    }

    public void setTags(Set<UserFileTagname> tags) {
        if (tags == null)
            throw new NullPointerException("tags");
        this.tags = tags;
    }

}
