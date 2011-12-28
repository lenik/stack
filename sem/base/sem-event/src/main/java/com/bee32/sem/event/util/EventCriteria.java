package com.bee32.sem.event.util;

import java.util.Date;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.event.entity.Event;

public class EventCriteria
        extends CriteriaSpec {

    @LeftHand(Event.class)
    public static ICriteriaElement observedBy(User user) {
        return compose(//
                alias("observers", "observer"), //
                PrincipalCriteria.inImSet("observer", user));
    }

    @LeftHand(Event.class)
    public static ICriteriaElement observedByCurrentUser() {
        User currentUser = SessionUser.getInstance().getInternalUser();
        return observedBy(currentUser);
    }

    @LeftHand(Event.class)
    public static CriteriaElement typeOf(String type) {
        if (type == null)
            return null;
        if (type.length() != 1)
            throw new IllegalArgumentException("Type is not a single char: " + type);
        // EventType eventType = EventType.valueOf(type.charAt(0));
        return equals("_type", type);
    }

    @LeftHand(Event.class)
    public static CriteriaElement closed(Boolean closed) {
        return _equals("closed", closed);
    }

    public static CriteriaElement beganFrom(Date date) {
        return _greaterThan("beginTime", date);
    }

}
