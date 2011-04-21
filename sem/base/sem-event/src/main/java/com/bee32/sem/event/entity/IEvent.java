package com.bee32.sem.event.entity;

import java.util.Date;

import com.bee32.icsf.principal.IUserPrincipal;

/**
 * Base type for all enterprise events.
 */
public interface IEvent {

    /**
     * The category name.
     *
     * Usually it's a FQCN or an alias.
     *
     * @return The category name, should not be <code>null</code>.
     */
    String getCategory();

    /**
     * Priority of the event.
     *
     * The smaller number indicates the higher priority.
     *
     * @return The priority integer.
     */
    int getPriority();

    public static final String EVENT_NOTICE = "notice";
    public static final String EVENT_WARNING = "warning";
    public static final String EVENT_ERROR = "error";
    public static final String EVENT_CLEARED = "cleared";

    /**
     * Get the event state.
     *
     * @return State name, should be non-<code>null</code> string.
     */
    String getState();

    /**
     * Get the actor who initiated the event.
     *
     * @return The actor who initiated the event.
     *         <p>
     *         If multiple actors are participated to this event, only the main actor is known from
     *         this interface.
     *         <p>
     *         Return <code>null</code> if no actor is participated.
     */
    IUserPrincipal getActor();

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
     * The class of the referenced object.
     */
    Class<?> getRefClass();

    /**
     * Ref id if it can fit into a long integer.
     */
    long getRefId();

    /**
     * Alternative ref key. (REDUNDENT)
     *
     * Get the unique of the referenced object.
     *
     * <strike>For combined keys, the serialization form may be used.</strike>
     *
     * @return Alt key of the referenced object, <code>null</code> if not applicable. (Thus,
     *         null-key is not supported)
     */
    String getRefAlt();

}
