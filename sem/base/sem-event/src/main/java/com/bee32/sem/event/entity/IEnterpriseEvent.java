package com.bee32.sem.event.entity;

import java.util.Date;

import com.bee32.icsf.principal.IPrincipal;

/**
 * Base type for all enterprise events.
 */
public interface IEnterpriseEvent {

    /**
     * The source category name.
     *
     * Usually it's a class name.
     *
     * @return The category name, should not be <code>null</code>.
     */
    String getCategory();

    /**
     * The category class.
     */
    Class<?> getCategoryClass();

    /**
     * Source id if fittable in a long integer.
     */
    long getSource();

    /**
     * Alternative source key. (REDUNDENT)
     *
     * Get the unique of the source object which is intance of the category class.
     *
     * <strike>For combined keys, the serialization form may be used.</strike>
     *
     * @return Key of the source object, <code>null</code> if not applicable. (Thus, null-key is not
     *         supported)
     */
    String getSourceAlt();

    /**
     * The start time of the event.
     *
     * @return Non-<code>null</code> time.
     */
    Date getBeginTime();

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
     * Message body of the event.
     *
     * This should also short message.
     *
     * For large message, one should go with the mail system.
     *
     * @return Message text, <code>null</code> if none.
     */
    String getMessage();

}
