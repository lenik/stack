package com.bee32.sem.event.entity;

import java.util.Date;
import java.util.Set;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.sem.event.EventFlags;

/**
 * Base type for all enterprise events.
 */
public interface IEvent {

    /**
     * The event category.
     *
     * For Tasks, it maybe from personal calendar program, or system generated tasks.
     *
     * @return The event category, maybe <code>null</code> for uncategorified events.
     */
    EventCategory getCategory();

    /**
     * Class of the source which originated the event.
     *
     * @return Non-<code>null</code> source class.
     */
    Class<?> getSourceClass();

    /**
     * Priority of the event.
     *
     * The smaller number indicates the higher priority.
     *
     * @return The priority integer.
     */
    int getPriority();

    /**
     * Event flags.
     *
     * @see EventFlags
     */
    int getFlags();

    /**
     * Whether the event is closed.
     *
     * Only open events will be listed.
     */
    boolean isClosed();

    /**
     * Get the internal event state.
     *
     * @return Internal event state.
     */
    int getStateInt();

    /**
     * Get the user event status
     *
     * @return User event status.
     */
    EventStatus getStatus();

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
     * For tasks, this is the scheduled start time.
     *
     * @return Non-<code>null</code> time.
     */
    Date getBeginTime();

    /**
     * When the event is ended.
     *
     * For tasks, this is the actual end time.
     *
     * @return The end time, same to the begin time if the event is happened at a specific time
     *         point, or the end time is unknown.
     */
    Date getEndTime();

    /**
     * For tasks, this is the scheduled end time.
     */
    Date getScheduledEndTime();

    /**
     * The class of the referenced object.
     */
    Class<?> getRefType();

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

    /**
     * Get the set of observers.
     *
     * @return Set of observer principals.
     */
    Set<? extends IPrincipal> getObservers();

}
