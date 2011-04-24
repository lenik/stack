package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityBean;

@MappedSuperclass
public abstract class DictEntity<K extends Serializable>
        extends EntityBean<K> {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected String displayName;
    protected String description;
    protected String icon;

    public DictEntity() {
    }

    public DictEntity(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public DictEntity(String name, String displayName, String icon) {
        this.name = name;
        this.displayName = displayName;
        this.icon = icon;
    }

    @Column(length = 20, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 显示名称：一般用本地语言表示，不能用于搜索。（如果要用显示名称搜索，建议通过全文索引）
     */
    @Column(length = 30)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(length = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}