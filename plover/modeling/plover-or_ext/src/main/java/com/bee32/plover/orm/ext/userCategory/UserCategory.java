package com.bee32.plover.orm.ext.userCategory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.ext.dict.NameDict;

@Entity
public class UserCategory
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public static final char INTEGER = 'i';
    public static final char DECIMAL = 'f';
    public static final char TEXT = 'a';

    char type;
    int precision;
    int scale;

    List<UserCategoryItem> items;

    public UserCategory() {
        super();
    }

    public UserCategory(char type, String name, String label) {
        super(name, label);
        setType(type);
    }

    public UserCategory(char type, String name, String label, String description) {
        super(name, label, description);
        setType(type);
    }

    @Column(nullable = false)
    public char getType() {
        return type;
    }

    public void setType(char type) {
        switch (type) {
        case INTEGER:
        case DECIMAL:
        case TEXT:
            break;
        default:
            throw new IllegalArgumentException("Bad type: " + type);
        }
        this.type = type;
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
