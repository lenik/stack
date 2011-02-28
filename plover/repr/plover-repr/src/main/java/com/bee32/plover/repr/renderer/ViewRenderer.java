package com.bee32.plover.repr.renderer;

import com.bee32.plover.model.stage.IModelStage;

public abstract class ViewRenderer
        implements IRenderer {

    @Override
    public void render(IDisplay display, Object obj) {
    }

    @Override
    public void render(IDisplay display, IModelStage obj) {
    }

}
