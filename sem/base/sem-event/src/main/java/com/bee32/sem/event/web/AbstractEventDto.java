package com.bee32.sem.event.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.entity.Event;

public abstract class AbstractEventDto<E extends Event>
        extends EntityDto<E, Long> {

    private static final long serialVersionUID = 1L;

    public static final int OBSERVERS = 1;

    private String category;
    private int priority;

    private int flags;
    private int stateIndex;
    private boolean closed;

    private int statusId;
    private EventStatusDto status;

    private Long actorId;
    private UserDto actor;

    private String subject;
    private String message;
    private Date beginTime;
    private Date endTime;

    private long refId;
    private String refAlt;

    private List<PrincipalDto> observers;
    private List<Long> observerIds;

    private String controlPage;

    public AbstractEventDto() {
        super();
    }

    public AbstractEventDto(E source) {
        super(source);
    }

    @Override
    protected void _marshal(E source) {
        category = source.getCategory();
        priority = source.getPriority();
        stateIndex = source.getState();

        actorId = id(actor = new UserDto().marshal(source.getActor()));

        subject = source.getSubject();
        message = source.getMessage();
        beginTime = source.getBeginTime();
        endTime = source.getEndTime();

        refId = source.getRefId();
        refAlt = source.getRefAlt();

        if (selection.contains(OBSERVERS))
            observers = marshalList(PrincipalDto.class, source.getObservers());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, E target) {
        target.setCategory(category);
        target.setPriority(priority);
        target.setState(stateIndex);

        target.setSubject(subject);
        target.setMessage(message);
        target.setBeginTime(beginTime);
        target.setEndTime(endTime);

        target.setRefId(refId);
        target.setRefAlt(refAlt);

        WithContext<Event> wc = with(context, (Event) target);
        wc.unmarshal(Event.actorProperty, actorId, actor);

        if (selection.contains(OBSERVERS))
            if (observerIds != null) {
                Set<Principal> observers = new HashSet<Principal>();
                for (Long observerId : observerIds) {
                    Principal observer = context.loadEntity(Principal.class, observerId);
                    observers.add(observer);
                }
                target.setObservers(observers);
            }
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        category = map.getString("category");
        priority = map.getInt("priority");

        flags = map.getInt("flags");
        stateIndex = map.getInt("state");
        closed = map.getBoolean("closed");
        statusId = map.getInt("statusId");

        actorId = map.getLong("actorId");

        subject = map.getString("subject");
        message = map.getString("message");
        beginTime = map.getDate("beginTime");
        endTime = map.getDate("endTime");

        String _refId = map.getString("refId");
        if (_refId != null)
            refId = Long.parseLong(_refId);
        else
            refAlt = map.getString("refAlt");

        if (selection.contains(OBSERVERS)) {
            String[] _observerIds = map.getStringArray("observerIds");
            observerIds = new ArrayList<Long>();
            for (String _observerId : _observerIds) {
                long observerId = Long.parseLong(_observerId);
                observerIds.add(observerId);
            }
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getState() {
        return stateIndex;
    }

    public void setState(int state) {
        this.stateIndex = state;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public EventStatusDto getStatus() {
        return status;
    }

    public void setStatus(EventStatusDto status) {
        this.status = status;
    }

    public String getStatusText() {
        String statusText = null;
        if (status != null)
            statusText = status.getDisplayName();

        if (statusText == null && stateIndex != 0) {
            EventState eventState = EventState.get(stateIndex);
            statusText = eventState.getName();
        }

        if (statusText == null)
            statusText = "";

        if (!closed)
            statusText = statusText + "+";

        return statusText;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public UserDto getActor() {
        return actor;
    }

    public void setActor(UserDto actor) {
        this.actor = actor;
    }

    public String getActorName() {
        if (actor == null)
            return null;
        else
            return actor.getDisplayName();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public String getRefAlt() {
        return refAlt;
    }

    public void setRefAlt(String refAlt) {
        this.refAlt = refAlt;
    }

    public List<PrincipalDto> getObservers() {
        return observers;
    }

    public void setObservers(List<PrincipalDto> observers) {
        this.observers = observers;
    }

    public List<Long> getObserverIds() {
        return observerIds;
    }

    public void setObserverIds(List<Long> observerIds) {
        this.observerIds = observerIds;
    }

    public String getControlPage() {
        return controlPage;
    }

    public void setControlPage(String controlPage) {
        this.controlPage = controlPage;
    }

}
