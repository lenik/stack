package com.bee32.sem.event;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

import javax.free.Dates;
import javax.inject.Inject;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventType;
import com.bee32.sem.event.util.EventCriteria;

@Using(SEMEventUnit.class)
public class EventObserverFeat
        extends WiredDaoFeat<EventObserverFeat> {

    @Inject
    IcsfPrincipalSamples principals;

    void cleanUp() {
        ctx.data.access(Event.class).deleteAll();
    }

    Event aa;
    Event bb;

    static Date parseDate(String s) {
        Date date;
        try {
            date = Dates.YYYY_MM_DD.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return date;
    }

    void setup() {
        aa = new Event(this, EventType.TASK);
        aa.setLabel("Attack fish");
        aa.addObserver(principals.solaRobots); // {eva, alice}
        aa.setBeginTime(parseDate("2011-1-1"));

        bb = new Event(this, EventType.EVENT);
        bb.setLabel("B-event.");
        bb.addObserver(Role.adminRole); // eva, tom
        bb.addObserver(principals.sunCorp); // +tom, +kate, alice
        aa.setBeginTime(parseDate("2011-2-1"));

        ctx.data.access(Event.class).saveOrUpdateAll(aa, bb);
    }

    void list(User user) {
        Collection<Event> list = new LinkedHashSet<Event>(ctx.data.access(Event.class).list(//
                EventCriteria.observedBy(user), //
                Order.asc("beginTime")));
        System.out.println("Event observed by " + user.getDisplayName());
        for (Event event : list) {
            System.out.println("  > " + event.getLabel());
        }
    }

    void list() {
        list(principals.alice); // a, b
        list(principals.eva); // a, b
        list(principals.kate); // b
        list(principals.tom); // b
        list(principals.wallE); // a
    }

    public static void main(String[] args)
            throws IOException {
        new EventObserverFeat().mainLoop(new ICoordinator<EventObserverFeat>() {
            @Override
            public void main(EventObserverFeat feat)
                    throws Exception {
                feat.cleanUp();
                feat.setup();
                feat.list();
            }
        });
    }

}
