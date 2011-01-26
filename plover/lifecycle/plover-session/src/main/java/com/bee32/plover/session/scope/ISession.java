package com.bee32.plover.session.scope;

import java.util.Map;

public interface ISession {

    ISession getParent();

    String getName();

    String getDescription();

    Map<String, Object> getParameters();

}
