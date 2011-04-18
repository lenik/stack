package com.bee32.sem.process.verify.builtin;

import java.util.ArrayList;
import java.util.List;

import com.bee32.icsf.principal.User;
import com.bee32.sem.process.verify.VerifyContext;

public class PassEvents
        extends VerifyContext
        implements IPassEvents {

    private static final long serialVersionUID = 1L;

    private PassToNext policy;
    private List<VerifyEvent> events;
    private int position;

    public PassEvents(PassToNext policy) {
        this(policy, 0, new ArrayList<VerifyEvent>());
    }

    public PassEvents(PassToNext policy, int position, List<VerifyEvent> events) {
        if (policy == null)
            throw new NullPointerException("policy");
        if (events == null)
            throw new NullPointerException("events");
        this.policy = policy;
        this.position = position;
        this.events = events;
    }

    public PassToNext getPolicy() {
        return policy;
    }

    public void setPolicy(PassToNext policy) {
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

    @Override
    public String toString() {
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
