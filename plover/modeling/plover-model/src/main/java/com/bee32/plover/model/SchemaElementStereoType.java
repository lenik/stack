package com.bee32.plover.model;

import com.bee32.plover.arch.Component;

public class SchemaElementStereoType
        extends Component {

    public SchemaElementStereoType(String name) {
        super(name);
    }

    public boolean isProperty() {
        return this == PROPERTY;
    }

    public boolean isMethod() {
        return this == METHOD;
    }

    private static final int typeHash = SchemaElementStereoType.class.hashCode();

    @Override
    public int hashCode() {
        int hash = typeHash;
        if (name != null)
            hash += name.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    public static final SchemaElementStereoType PROPERTY = new SchemaElementStereoType("Property");
    public static final SchemaElementStereoType METHOD = new SchemaElementStereoType("Method");

}
