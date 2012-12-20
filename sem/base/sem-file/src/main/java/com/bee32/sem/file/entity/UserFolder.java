package com.bee32.sem.file.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Index;

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

    public static final int NAME_LENGTH = 200;
    public static final int PATH_LENGTH = 300;

    String name = "noname";
    String path;
    List<UserFile> files;

    int fileCount;

    public UserFolder() {
        super();
    }

    public UserFolder(UserFolder parent, String name) {
        super(parent);
        setName(name);
    }

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
     * （冗余， = ... + parent.name + name)
     */
    @Column(length = PATH_LENGTH)
    public synchronized String getPath() {
        if (path == null) {
            StringBuilder buf = new StringBuilder(PATH_LENGTH);
            UserFolder node = this;
            while (node != null) {
                if (buf.length() != 0)
                    buf.append("/");

                String baseName = node.getName();
                int len = baseName.length();
                for (int i = len - 1; i >= 0; i--)
                    buf.append(baseName.charAt(i));

                node = node.getParent();
            }
            path = buf.reverse().toString();
        }
        return path;
    }

    public void setPath(String path) {
        StringBuilder buf = new StringBuilder(PATH_LENGTH);
        if(this.getParent() != null) {
            buf.append(this.getParent().getPath());
            buf.append("/");
            buf.append(path);
        } else {
            buf.append(path);
        }
        this.path = buf.toString();
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
