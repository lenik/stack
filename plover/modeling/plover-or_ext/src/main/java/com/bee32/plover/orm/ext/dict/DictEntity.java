package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntityExp;

@MappedSuperclass
public abstract class DictEntity<K extends Serializable>
        extends EntityExp<K> {

    private static final long serialVersionUID = 1L;

    protected String alias;
    protected String description;
    protected String icon;

    public DictEntity() {
    }

    public DictEntity(String alias, String description) {
        this.alias = alias;
        this.description = description;
    }

    /**
     * 显示名称：一般用本地语言表示，不能用于搜索。（如果要用显示名称搜索，建议通过全文索引）
     */
    @Column(length = 30)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
