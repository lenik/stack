package com.bee32.sem.file.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bee32.plover.orm.util.ITypeAbbrAware;
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
        extends UIEntityAuto<Long>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    public static final int DIR_LENGTH = 100;
    public static final int NAME_LENGTH = 100;
    public static final int REF_ID_LENGTH = 30;

    FileBlob fileBlob;

    String dir = "";
    String name = "";
    Date fileDate;
    Date expiredDate;
    Set<UserFileTagname> tags = new HashSet<UserFileTagname>();

    Class<?> refType;
    String refId;

X-Population

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

    @Temporal(TemporalType.DATE)
    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    @Temporal(TemporalType.DATE)
    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * 用户为文件添加的标签。
     */
    @ManyToMany(fetch = FetchType.EAGER)
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

    /**
     * Get the type which as the primary key.
     *
     * @return Non-<code>null</code> expanded type.
     */
    @Transient
    public Class<?> getRefType() {
        return refType;
    }

    public void setRefType(Class<?> refType) {
        this.refType = refType;
        getEntityFlags().setHidden(refType == null);
    }

    @Column(name = "refType", length = ABBR_LEN)
    String getRefTypeId() {
        return ABBR.abbr(refType);
    }

    void setRefTypeId(String typeId) {
        Class<?> type;
        if (typeId == null) {
            type = null;
        } else {
            try {
                type = ABBR.expand(typeId);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Bad type id: " + typeId, e);
            }
        }
        setRefType(type);
    }

    /**
     * 关联的对象 ID
     */
    @Column(length = REF_ID_LENGTH)
    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public void setRefId(Integer refId) {
        if (refId == null)
            this.refId = null;
        else
            this.refId = String.valueOf(refId);
    }

    public void setRefId(Long refId) {
        if (refId == null)
            this.refId = null;
        else
            this.refId = String.valueOf(refId);
    }

}
