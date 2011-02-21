package com.bee32.sem.event;

import java.util.Date;

public interface IEventLog {

    long getId();

    Class<?> getSourceClass();

    /**
     * For combined keys, the serialization form may be used.
     *
     * @return Key of the source event.
     */
    String getSourceKey();

    Date getTimestamp();

    Object getOperator();

    String getSubject();

    String getMessage();

}
