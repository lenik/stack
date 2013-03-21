package com.bee32.sem.event.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.free.IllegalUsageException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.NoSuchEnumException;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.c.CEntityDto;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.entity.Event;

public abstract class AbstractEventDto<E extends Event>
        extends CEntityDto<E, Long>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    public static final int OBSERVERS = 0x00010000;

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

    private String seeAlsos;

    private List<PrincipalDto> observers;

    public AbstractEventDto() {
        super();
    }

    public AbstractEventDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(E source) {
        category = mref(EventCategoryDto.class, source.getCategory());
        sourceClass = source.getSourceClass();

        priority = source.getPriority();
        stateIndex = source.getState();

        status = mref(EventStatusDto.class, source.getStatus());
        actor = mref(UserDto.class, source.getActor());

        subject = source.getSubject();
        message = source.getMessage();
        beginTime = source.getBeginTime();
        endTime = source.getEndTime();

        refId = source.getRefId();
        refAlt = source.getRefAlt();

        seeAlsos = source.getSeeAlsos();

        if (selection.contains(OBSERVERS))
            observers = marshalList(PrincipalDto.class, source.getObservers());
    }

    @Override
    protected void _unmarshalTo(E target) {

        merge(target, "category", category);
        merge(target, "status", status);
        merge(target, "actor", actor);

        if (sourceClass != null)
            target.setSourceClass(sourceClass);

        target.setPriority(priority);
        target.setState(stateIndex);

        target.setSubject(subject);
        target.setMessage(message);
        target.setBeginTime(beginTime);
        target.setEndTime(endTime);

        target.setRefId(refId);
        target.setRefAlt(refAlt);

        target.setSeeAlsos(seeAlsos);

        if (selection.contains(OBSERVERS))
            mergeSet(target, "observers", observers);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {

        category = new EventCategoryDto().ref(map.getString("category"));
        String sourceAbbr = map.getString("sourceAbbr");
        try {
            sourceClass = ABBR.expand(sourceAbbr);
        } catch (ClassNotFoundException e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }

        priority = map.getInt("priority");

        flags = map.getInt("flags");
        stateIndex = map.getInt("state");
        closed = map.getBoolean("closed");

        status = new EventStatusDto().parseRef(map.getString("statusId"));
        actor = new UserDto(0).parseRef(map.getString("actorId"));

        subject = map.getString("subject");
        message = map.getString("message");
        beginTime = map.getDate("beginTime");
        endTime = map.getDate("endTime");

        String _refId = map.getString("refId");
        if (_refId != null)
            refId = Long.parseLong(_refId);
        else
            refAlt = map.getString("refAlt");

        seeAlsos = map.getString("seeAlsos");

        if (selection.contains(OBSERVERS)) {
            String[] _observerIds = map.getStringArray("observerIds");
            observers = new ArrayList<PrincipalDto>();

            for (String _observerId : _observerIds) {
                PrincipalDto observer = new PrincipalDto().parseRef(_observerId);
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

    public String getSource() {
        return ABBR.abbr(sourceClass);
    }

    public void setSource(String sourceAbbr) {
        try {
            sourceClass = ABBR.expand(sourceAbbr);
        } catch (ClassNotFoundException e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }
    }

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public String getSourceName() {
        if (sourceClass == null)
            return "(unknown)";
        else
            return ClassUtil.getTypeName(sourceClass);
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
            statusText = status.getLabel();

        if (statusText == null && stateIndex != 0) {
            EventState<?> eventState;
            try {
                eventState = EventState.forValue(stateIndex);
                statusText = eventState.getName();
            } catch (NoSuchEnumException e) {
                statusText = "???";
            }
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

    public String getSeeAlsos() {
        return seeAlsos;
    }

    public void setSeeAlsos(String seeAlsos) {
        this.seeAlsos = seeAlsos;
    }

    public List<String> getSeeAlsoList() {
        List<String> list = new ArrayList<String>();
        if (seeAlsos != null)
            for (String line : seeAlsos.split("\n")) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#"))
                    continue;
                list.add(line);
            }
        return list;
    }

    public void setSeeAlsoList(List<String> list) {
        if (list == null)
            seeAlsos = null;

        else {
            StringBuilder sb = new StringBuilder(1000);
            for (String line : list) {
                sb.append(line);
                sb.append("\n");
            }
            seeAlsos = sb.toString();
        }
    }

}
