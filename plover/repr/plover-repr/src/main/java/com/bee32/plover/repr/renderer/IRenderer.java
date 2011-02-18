package com.bee32.plover.repr.renderer;

import com.bee32.plover.model.stage.IModelStage;

public interface IRenderer {

    void render(IDisplay display, Object obj);

    void render(IDisplay display, IModelStage obj);

}
