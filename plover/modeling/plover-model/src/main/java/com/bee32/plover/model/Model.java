package com.bee32.plover.model;

import com.bee32.plover.arch.Component;
import com.bee32.plover.model.schema.ISchema;
import com.bee32.plover.model.schema.SchemaBuilderException;
import com.bee32.plover.model.schema.SchemaLoader;
import com.bee32.plover.model.stage.IModelStage;
import com.bee32.plover.model.stage.ModelLoadException;
import com.bee32.plover.model.stage.ModelStageException;

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

    @Override
    public void stage(IModelStage stage)
            throws ModelStageException {
    }

    @Override
    public void load(IModelStage stage)
            throws ModelLoadException {
    }

}
