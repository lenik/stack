package com.bee32.plover.ox1.dict;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.icsf.access.DefaultPermission;
import com.bee32.icsf.access.Permission;
import com.bee32.plover.ox1.c.CEntitySpec;

@DefaultPermission(Permission.R_X)
@MappedSuperclass
public abstract class DictEntity<K extends Serializable>
        extends CEntitySpec<K> {

    private static final long serialVersionUID = 1L;

    public static final int LABEL_LENGTH = 30;
    public static final int DESCRIPTION_LENGTH = 200;

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

    @SuppressWarnings("unchecked")
    @Override
    public void populate(Object source) {
        if (source instanceof DictEntity)
            _populate((DictEntity<K>) source);
        else
            super.populate(source);
    }

    protected void _populate(DictEntity<K> o) {
        super._populate(o);
        label = o.label;
        description = o.description;
    }

    /**
     * 别名：一般用本地语言表示，不能用于搜索。（如果要用显示名称搜索，建议通过全文索引）
     */
    @Column(length = LABEL_LENGTH)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Column(length = DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected void formatEntryText(StringBuilder buf) {
        buf.append(getLabel());
        buf.append('/');
        buf.append(getId());
    }

}
