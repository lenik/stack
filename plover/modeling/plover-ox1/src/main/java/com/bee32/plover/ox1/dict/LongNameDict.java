package com.bee32.plover.ox1.dict;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.model.ModelTemplate;

@ModelTemplate
@MappedSuperclass
@AttributeOverrides({//
/*    */@AttributeOverride(name = "id", column = @Column(length = LongNameDict.ID_LENGTH)),
/*    */@AttributeOverride(name = "label", column = @Column(length = LongNameDict.LABEL_LENGTH)) })
public abstract class LongNameDict
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public static final int ID_LENGTH = 50;
    public static final int LABEL_LENGTH = 50;

    public LongNameDict() {
        super();
    }

    public LongNameDict(String name, String label) {
        super(name, label);
    }

    public LongNameDict(String name, String label, String description) {
        super(name, label, description);
    }

    public LongNameDict(int order, String name, String label) {
        super(order, name, label);
    }

    public LongNameDict(int order, String name, String label, String description) {
        super(order, name, label, description);
    }

}
