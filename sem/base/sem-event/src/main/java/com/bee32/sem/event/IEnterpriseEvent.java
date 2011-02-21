package com.bee32.sem.event;

import java.io.Serializable;
import java.util.Date;

import com.bee32.icsf.principal.IPrincipal;

/**
 * Base type for all enterprise events.
 */
public interface IEnterpriseEvent
        extends Serializable {

    Long getId();

    /**
     * The source class which initiated the event.
     *
     * @return The source class, <code>null</code> if not applicable.
     */
    Class<?> getSourceClass();

    /**
     * Key to the source instance.
     *
     * <strike>For combined keys, the serialization form may be used.</strike>
     *
     * @return Key of the source event, <code>null</code> if not applicable. (Thus, null-key is not
     *         supported)
     */
    Object getSourceKey();

    /**
     * The start time of the event.
     *
     * @return Non-<code>null</code> time.
     */
    Date getTime();

    /**
     * When the event is ended.
     *
     * @return The end time, <code>null</code> if the event is happened at a specific time point, or
     *         the end time is unknown.
     */
    Date getEndTime();

    /**
     * Get the actor who initiated the event.
     *
     * @return The actor who performed the event.
     *         <p>
     *         If multiple actors are participated to this event, only the main actor is known from
     *         this interface.
     *         <p>
     *         Return <code>null</code> if no actor is participated.
     */
    IPrincipal getActor();

    /**
     * Subject of the event.
     *
     * @return Non-<code>null</code> subject text.
     */
    String getSubject();

    /**
     * Additional message for the event.
     *
     * @return Message text, <code>null</code> if none.
     */
    String getMessage();

}
