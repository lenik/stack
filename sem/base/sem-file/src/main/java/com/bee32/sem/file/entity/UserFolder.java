package com.bee32.sem.file.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import com.bee32.plover.ox1.config.BatchConfig;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 文件夹，文件分类
 *
 */
@Entity
@BatchSize(size = BatchConfig.TREE)
@SequenceGenerator(name = "idgen", sequenceName = "user_folder_seq", allocationSize = 1)
public class UserFolder extends TreeEntityAuto<Integer, UserFolder> {

    private static final long serialVersionUID = 1L;

    public static final int PATH_LENGTH = 300;
    public static final int GID_LENGTH = 20;
    public static final int MODE_LENGTH = 20;

    String path;
    String gid;
    String mode;
    List<UserFile> files;

    int fileCount;

    /**
     * （冗余， = ... + parent.name + name)
     */
    @Column(length = PATH_LENGTH)
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 属组id
     * @return
     */
    @Column(length = GID_LENGTH)
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    /**
     * 类似unix的模式
     * @return
     */
    @Column(length = MODE_LENGTH)
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * 文件列表
     *
     * 本文件夹下的文件列表
     */
    @OneToMany(mappedBy = "folder")
    public List<UserFile> getFiles() {
        return files;
    }

    public void setFiles(List<UserFile> files) {
        this.files = files;
    }

    @Formula("(select count(*) from user_file f where f.folder=id)")
    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }



}
