package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bee32.icsf.principal.User;
import com.bee32.sem.process.verify.AbstractVerifyContext;
import com.bee32.sem.process.verify.VerifyEvent;

/**
 * 递进事件上下文
 *
 * <p lang="en">
 * Pass Events
 */
@Embeddable
public class PassEvents
        extends AbstractVerifyContext
        implements IPassEvents {

    private static final long serialVersionUID = 1L;

    private PassToNextPolicy policy;
    private List<VerifyEvent> events;
    private int position;

    public PassEvents() {
    }

    public PassEvents(PassToNextPolicy policy) {
        this(policy, 0, new ArrayList<VerifyEvent>());
    }

    public PassEvents(PassToNextPolicy policy, int position, List<VerifyEvent> events) {
        if (policy == null)
            throw new NullPointerException("policy");
        if (events == null)
            throw new NullPointerException("events");
        this.policy = policy;
        this.position = position;
        this.events = events;
    }

    @Override
    public void populate(Object source) {
        super.populate(source);
        if (source instanceof PassEvents) {
            PassEvents o = (PassEvents) source;
            this.policy = o.policy;
            this.position = o.position;
            this.events = o.events;
        }
    }

    public PassToNextPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(PassToNextPolicy policy) {
        this.policy = policy;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void passBy(User user) {
        if (user == null)
            throw new NullPointerException("user");
        events.add(new VerifyEvent(true, user));
    }

    @Override
    public void rejectBy(User user) {
        if (user == null)
            throw new NullPointerException("user");
        events.add(new VerifyEvent(false, user));
    }

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public void clear() {
        events.clear();
    }

    @Override
    public VerifyEvent getEvent(int index) {
        return events.get(index);
    }

    @Override
    public void removeEvent(int index) {
        events.remove(index);
    }

    @Transient
    public String getGraph() {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        for (VerifyEvent stepState : events) {
            if (index++ != 0)
                sb.append(", ");

            sb.append("审核" + index + " ");
            sb.append(stepState);
            sb.append("\n");
        }

        return sb.toString();
    }

}
