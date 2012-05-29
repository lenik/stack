package com.bee32.plover.ox1.userMode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.dict.NameDict;

/**
 * General purpose category.
 */
@Entity
public class UserCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    UserDataType type = UserDataType.TEXT;
    int precision;
    int scale;

    List<UserCategoryItem> items;

    public UserCategory() {
        super();
    }

    public UserCategory(UserDataType type, String name, String label) {
        super(name, label);
        setType(type);
    }

    public UserCategory(UserDataType type, String name, String label, String description) {
        super(name, label, description);
        setType(type);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof UserCategory)
            _populate((UserCategory) source);
        else
            super.populate(source);
    }

    protected void _populate(UserCategory o) {
        super._populate(o);
        type = o.type;
        precision = o.precision;
        scale = o.scale;
        items = CopyUtils.copyList(o.items);
    }

    @Transient
    public UserDataType getType() {
        return type;
    }

    public void setType(UserDataType type) {
        this.type = type;
    }

    @Column(nullable = false)
    char get_type() {
        return type.getValue();
    }

    void set_type(char _type) {
        this.type = UserDataType.forValue(_type);
    }

    @Column(nullable = false)
    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Column(nullable = false)
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Transient
    public int getLength() {
        return precision;
    }

    public void setLength(int length) {
        this.precision = length;
    }

    @OneToMany
    @Cascade(CascadeType.ALL)
    public List<UserCategoryItem> getItems() {
        if (items == null) {
            synchronized (this) {
                if (items == null) {
                    items = new ArrayList<UserCategoryItem>();
                }
            }
        }
        return items;
    }

    public void setItems(List<UserCategoryItem> items) {
        this.items = items;
    }

}
