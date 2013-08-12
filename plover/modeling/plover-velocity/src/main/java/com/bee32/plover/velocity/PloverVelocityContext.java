package com.bee32.plover.velocity;

import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

/**
 * 普拉瓦 Apache-Velocity 变量上下文。
 */
public class PloverVelocityContext
        extends VelocityContext {

    public PloverVelocityContext() {
        super();
        init();
    }

    public PloverVelocityContext(Context innerContext) {
        super(innerContext);
        init();
    }

    public PloverVelocityContext(Map<String, Object> context, Context innerContext) {
        super(context, innerContext);
        init();
    }

    public PloverVelocityContext(Map<String, Object> context) {
        super(context);
        init();
    }

    private void init() {
    }

}
