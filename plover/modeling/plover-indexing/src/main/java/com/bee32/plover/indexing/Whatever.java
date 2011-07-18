package com.bee32.plover.indexing;

class ModelRequest {
}

class ModelResponse {
}

interface Dispatcher {
    // req = Negotiation
    Object dispatch(Object context, String path);
}

interface IRenderer {
    void render(Object obj, IDisplay display);
}

interface IDisplay {

}

interface IHtmlDisplay
        extends IDisplay {
}
