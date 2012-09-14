package com.bee32.plover.ox1.dict;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.model.ModelTemplate;

@ModelTemplate
@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "id", column = @Column(length = ShortNameDict.ID_LENGTH)),
/*    */@AttributeOverride(name = "label", column = @Column(length = ShortNameDict.LABEL_LENGTH)) })
public abstract class ShortNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public static final int ID_LENGTH = 10;
    public static final int LABEL_LENGTH = 30;

    public ShortNameDict() {
        super();
    }

    public ShortNameDict(String name, String label) {
        super(name, label);
    }

    public ShortNameDict(String name, String label, String description) {
        super(name, label, description);
    }

    public ShortNameDict(int order, String name, String label) {
        super(order, name, label);
    }

    public ShortNameDict(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

}
