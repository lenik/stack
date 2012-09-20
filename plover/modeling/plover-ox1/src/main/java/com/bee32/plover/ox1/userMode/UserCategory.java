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
 * 自定义分类
 *
 * 对于文本内容，自定义分类可以提供一个下拉型选择，以方便用户输入。
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

    /**
     * 数据类型
     *
     * 分类项目的数据类型。
     */
    @Column(nullable = false)
    public char get_type() {
        return type.getValue();
    }

    public void set_type(char _type) {
        this.type = UserDataType.forValue(_type);
    }

    /**
     * 精度
     *
     * 文本型分类项目的最大长度，或者数值型分类项目的数值精度。
     */
    @Column(nullable = false)
    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    /**
     * 小数位数
     *
     * 数值型分类项目的小数位数。
     */
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

    /**
     * 项目列表
     *
     * 该分类下的具体项目的列表。
     */
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
