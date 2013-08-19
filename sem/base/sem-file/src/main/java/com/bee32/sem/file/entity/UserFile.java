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

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.people.entity.Party;

/**
 * 用户文件
 *
 * 用户管理的文件。
 *
 * label = 用户填写的文件标题（用于显示）。
 *
 * <p lang="en">
 * User File
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
    UserFolder folder;
    String name = "";
    Date fileDate;
    Date expiredDate;
    Set<UserFileTagname> tags = new HashSet<UserFileTagname>();
    User operator;
    Party party;

    Class<?> refType;
    String refId;

    @Override
    public void populate(Object source) {
        if (source instanceof UserFile)
            _populate((UserFile) source);
        else
            super.populate(source);
    }

    protected void _populate(UserFile o) {
        super._populate(o);
        fileBlob = o.fileBlob;
        name = o.name;
        fileDate = o.fileDate;
        expiredDate = o.expiredDate;
        tags = new HashSet<UserFileTagname>(o.tags);
        refType = o.refType;
        refId = o.refId;
        folder = o.folder;
    }

    /**
     * 文件数据
     *
     * 大块的文件数据。
     *
     * <p lang="en">
     * File Bllob
     */
    @ManyToOne(optional = false)
    public FileBlob getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(FileBlob fileBlob) {
        this.fileBlob = fileBlob;
    }

    /**
     * 文化名
     *
     * 用户对上传文件的命名。
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

    /**
     * 文件夹
     *
     * 用于文件的分类。
     *
     * 若未指定，则在垃圾箱中。
     *
     * <p lang="en">
     * Folder
     */
    @ManyToOne
    public UserFolder getFolder() {
        return folder;
    }

    public void setFolder(UserFolder folder) {
        this.folder = folder;
    }

    /**
     * 文件日期
     *
     * 用于说明文件相关事件的日期（业务日期）。
     *
     * <p lang="en">
     * File Date
     */
    @Temporal(TemporalType.DATE)
    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    /**
     * 过期时间
     *
     * 用于说明文件相关事件的有效期。
     *
     * <p lang="en">
     * Expired Date
     */
    @Temporal(TemporalType.DATE)
    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * 标签集
     *
     * 用户为文件添加的标签。
     *
     * <p lang="en">
     * Tags
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
     * 经办人
     *
     * 文件相关事件的经办人。
     */
    @ManyToOne
    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    /**
     * 客户
     *
     * 文件相关事件的客户人员或公司。
     */
    @ManyToOne
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
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

    /**
     * 关联的对象类型
     *
     * 引用本文件的源对象的类型。
     */
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
     *
     * 引用本文件的源对象的标识符。
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
