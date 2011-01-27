package com.bee32.plover.model;

import com.bee32.plover.arch.Component;

public class Model
        extends Component
        implements IModel {

    private ISchema schema;

    @Override
    public ISchema getSchema() {
        if (schema == null) {
            Class<?> modelClass = getClass();
            try {
                schema = SchemaLoader.loadSchema(modelClass);
            } catch (SchemaBuilderException e) {
                _throw(e);
            }
        }
        return schema;
    }

}
