package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.entity.EntitySpec;

@MappedSuperclass
public abstract class DictEntity<K extends Serializable>
        extends EntitySpec<K> {

    private static final long serialVersionUID = 1L;

    protected String label;
    protected String description;

    public DictEntity() {
    }

    public DictEntity(String label) {
        this(label, null);
    }

    public DictEntity(String label, String description) {
        this.label = label;
        this.description = description;
    }

    /**
     * 别名：一般用本地语言表示，不能用于搜索。（如果要用显示名称搜索，建议通过全文索引）
     */
    @Column(length = 30)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
