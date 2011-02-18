package com.bee32.plover.repr.renderer;

import com.bee32.plover.model.stage.IModelStage;

public abstract class StageRenderer
        implements IRenderer {

    @Override
    public void render(IDisplay display, Object obj) {
        if (obj instanceof IModelStage) {
            IModelStage stage = (IModelStage) obj;
        }
    }

}
