package com.bee32.sem.event.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.PrincipalDto;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.ParameterMap;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventStatus;

public abstract class AbstractEventDto<E extends Event>
        extends EntityDto<E, Long>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    public static final int OBSERVERS = 1;

    private EventCategoryDto category;
    private Class<?> sourceClass;

    private int priority;

    private int flags;
    private int stateIndex;
    private boolean closed;

    private EventStatusDto status;

    private UserDto actor;

    private String subject;
    private String message;
    private Date beginTime;
    private Date endTime;

    private long refId;
    private String refAlt;

    private List<PrincipalDto> observers;

    private String controlPage;

    public AbstractEventDto() {
        super();
    }

    public AbstractEventDto(E source) {
        super(source);
    }

    @Override
    protected void _marshal(E source) {
        category = new EventCategoryDto(source.getCategory());
        sourceClass = source.getSourceClass();

        priority = source.getPriority();
        stateIndex = source.getState();

        status = new EventStatusDto(source.getStatus());
        actor = new UserDto(source.getActor());

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

        with(context, (Event) target) //
                .unmarshal(categoryProperty, category)//
                .unmarshal(statusProperty, status)//
                .unmarshal(actorProperty, actor);

        target.setSourceClass(sourceClass);

        target.setPriority(priority);
        target.setState(stateIndex);

        target.setSubject(subject);
        target.setMessage(message);
        target.setBeginTime(beginTime);
        target.setEndTime(endTime);

        target.setRefId(refId);
        target.setRefAlt(refAlt);

        if (selection.contains(OBSERVERS))
            with(context, target)//
                    .unmarshalSet(observersProperty, observers);
    }

    @Override
    public void _parse(ParameterMap map)
            throws ParseException, TypeConvertException {

        category = new EventCategoryDto().ref(map.getNInt("categoryId"));

        try {
            sourceClass = ABBR.expand(map.getString("source"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        priority = map.getInt("priority");

        flags = map.getInt("flags");
        stateIndex = map.getInt("state");
        closed = map.getBoolean("closed");

        status = new EventStatusDto().ref(map.getNInt("statusId"));
        actor = new UserDto().ref(map.getNLong("actorId"));

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
            observers = new ArrayList<PrincipalDto>();

            for (String _observerId : _observerIds) {
                long observerId = Long.parseLong(_observerId);
                PrincipalDto observer = new PrincipalDto().ref(observerId);
                observers.add(observer);
            }
        }
    }

    public EventCategoryDto getCategory() {
        return category;
    }

    public void setCategory(EventCategoryDto category) {
        this.category = category;
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public String getSourceName() {
        String displayName = ClassUtil.getDisplayName(sourceClass);
        return displayName;
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
            EventState eventState = EventState.valueOf(stateIndex);
            statusText = eventState.getName();
        }

        if (statusText == null)
            statusText = "";

        if (!closed)
            statusText = statusText + "+";

        return statusText;
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

    public String getControlPage() {
        return controlPage;
    }

    public void setControlPage(String controlPage) {
        this.controlPage = controlPage;
    }

    static final PropertyAccessor<Event, EventCategory> categoryProperty = new PropertyAccessor<Event, EventCategory>(
            EventCategory.class) {

        @Override
        public EventCategory get(Event entity) {
            return entity.getCategory();
        }

        @Override
        public void set(Event entity, EventCategory category) {
            entity.setCategory(category);
        }

    };
    static final PropertyAccessor<Event, EventStatus> statusProperty = new PropertyAccessor<Event, EventStatus>(
            EventStatus.class) {

        @Override
        public EventStatus get(Event entity) {
            return entity.getStatus();
        }

        @Override
        public void set(Event entity, EventStatus status) {
            entity.setStatus(status);
        }

    };

    static final PropertyAccessor<Event, User> actorProperty = new PropertyAccessor<Event, User>(User.class) {

        @Override
        public User get(Event entity) {
            return entity.getActor();
        }

        @Override
        public void set(Event entity, User actor) {
            entity.setActor(actor);
        }

    };

    final PropertyAccessor<E, Set<Principal>> observersProperty = new PropertyAccessor<E, Set<Principal>>(Set.class) {

        @Override
        public Set<Principal> get(E entity) {
            return entity.getObservers();
        }

        @Override
        public void set(E entity, Set<Principal> observers) {
            entity.setObservers(observers);
        }

    };

}
