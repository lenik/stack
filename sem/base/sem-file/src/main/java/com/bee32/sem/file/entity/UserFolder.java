package com.bee32.sem.file.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Index;

import com.bee32.plover.ox1.config.BatchConfig;
import com.bee32.plover.ox1.tree.TreeEntityAuto;

/**
 * 用户文件夹
 *
 * <p lang="en">
 * User Folder
 */
@Entity
@BatchSize(size = BatchConfig.TREE)
@SequenceGenerator(name = "idgen", sequenceName = "user_folder_seq", allocationSize = 1)
public class UserFolder
        extends TreeEntityAuto<Integer, UserFolder> {

    private static final long serialVersionUID = 1L;

    public static final int NAME_LENGTH = 200;

    String name = "noname";
    List<UserFile> files;

    int fileCount;
    int fileCountRec;

    public UserFolder() {
        super();
    }

    public UserFolder(UserFolder parent, String name) {
        super(parent);
        setName(name);
    }

    /**
     * 名称
     *
     * <p lang="en">
     * Name
     */
    @Index(name = "user_folder_name")
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        if (name.isEmpty())
            throw new IllegalArgumentException("name is empty");
        if (name.contains("/") || name.contains("\\") || name.contains("*") || name.contains("?"))
            throw new IllegalArgumentException("Illegal name: " + name);
        this.name = name;
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

    @Transient
    public int getFileCountRec() {
        if (fileCountRec == 0) {
            int c = getFileCount();
            for (UserFolder child : getChildren())
                c += child.getFileCountRec();
            fileCountRec = c;
        }
        return fileCountRec;
    }

}
