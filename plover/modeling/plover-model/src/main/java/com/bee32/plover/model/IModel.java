package com.bee32.plover.model;

import com.bee32.plover.arch.IComponent;
import com.bee32.plover.model.schema.ISchema;
import com.bee32.plover.model.stage.IModelStage;
import com.bee32.plover.model.stage.ModelLoadException;
import com.bee32.plover.model.stage.ModelStageException;

public interface IModel
        extends IComponent {

    ISchema getSchema();

    void stage(IModelStage stage)
            throws ModelStageException;

    void load(IModelStage stage)
            throws ModelLoadException;

}
