package com.bee32.sem.file.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 用户文件。
 *
 * label = 用户填写的文件标题（用于显示）。
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "user_file_seq", allocationSize = 1)
public class UserFile
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    public static final int DIR_LENGTH = 100;
    public static final int NAME_LENGTH = 50;

    FileBlob fileBlob;

    String dir = "";
    String name = "";

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

    @Column(length = DIR_LENGTH, nullable = false)
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        if (dir == null)
            throw new NullPointerException("dir");
        this.dir = dir;
    }

    /**
     * 用户对上传的文件重命名。
     */
    @Column(length = NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        this.name = name.trim();
    }

    @Transient
    public String getPath() {
        return dir + "/" + name;
    }

    public void setPath(String path) {
        if (path == null)
            throw new NullPointerException("path");
        path = path.trim();
        path = path.replace('\\', '/');
        int slash = path.lastIndexOf('/');
        if (slash == -1) {
            dir = "";
            name = path;
        } else {
            dir = path.substring(0, slash);
            name = path.substring(slash + 1);
        }
    }

    @Transient
    public String getDownloadName() {
        return name;
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
