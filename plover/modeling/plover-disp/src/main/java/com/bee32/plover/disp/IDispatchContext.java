package com.bee32.plover.disp;

import java.util.Date;

public interface IDispatchContext {

    IDispatchContext getParent();

    String[] getConsumedTokens();

    String getConsumedPath();

    Object getObject();

    Date getExpires();

}