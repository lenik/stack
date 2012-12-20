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
public class UserFolder
        extends TreeEntityAuto<Integer, UserFolder> {

    private static final long serialVersionUID = 1L;

    public static final int PATH_LENGTH = 300;

    String path;
    List<UserFile> files;

    int fileCount;

    /**
     * （冗余， = ... + parent.name + name)
     */
    @Column(length = PATH_LENGTH)
    public synchronized String getPath() {
        if (path == null) {
            StringBuilder buf = new StringBuilder(PATH_LENGTH);
            UserFolder node = this;
            buf.append(node.getLabel());
        }
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
