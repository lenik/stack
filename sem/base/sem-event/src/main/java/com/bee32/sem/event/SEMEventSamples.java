package com.bee32.sem.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.plover.orm.sample.SampleList;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventPriorities;
import com.bee32.sem.event.entity.EventType;

public class SEMEventSamples
        extends NormalSamples {

    public final EventCategory weather = new EventCategory("weather", PREFIX + "天气预报");
    public final EventCategory special = new EventCategory("special", PREFIX + "特种");

    public List<Event> rains = new ArrayList<Event>();
    public final Event killAngel = new Event(SEMEventSamples.class, EventType.TASK);

    IcsfPrincipalSamples principals = predefined(IcsfPrincipalSamples.class);
    Users users = predefined(Users.class);
    EventPriorities eventPriorities = predefined(EventPriorities.class);

    int rainIndex = 0;

    Event mkRain(double relativeDay, Double duration, String title, Principal observer) {
        Event rain = new Event(SEMEventSamples.class, EventType.EVENT);
        rain.setAltId("rain" + (++rainIndex));
        rain.setCategory(weather);
        rain.setPriority(eventPriorities.LOW);
        rain.setEventState(GenericState.UNKNOWN);
        rain.setClosed(duration != null);

        long beginMs = (long) (System.currentTimeMillis() + relativeDay * 86400 * 1000);
        rain.setBeginTime(new Date(beginMs));
        if (duration != null) {
            long durMs = (long) (duration * 86400 * 1000);
            rain.setEndTime(new Date(beginMs + durMs));
        }

        rain.setSubject(PREFIX + "在 " + title + " (" + rain.getBeginTime() + ") 时刻有流星雨，请大家出门带上望远镜。");
        rain.setMessage("你以为带上望远镜就能看到土卫3的流行雨吗？少年哟，别做梦了，那是不可能的。");

        rain.addObservers(observer);
        return rain;
    }

    @Override
    protected void wireUp() {
        Principal robots = principals.solaRobots;

        rains.add(mkRain(-28.5, 1.0, "28.5 天前", robots));
        rains.add(mkRain(-29.9, null, "29.9 天前", robots));
        rains.add(mkRain(-30.2, 1.0, "30.1 天前", robots));
        rains.add(mkRain(0, null, "今天", robots));
        rains.add(mkRain(1.5, null, "1.5天之后", robots));

        killAngel.setCategory(special);
        killAngel.setSourceClass(SEMEventSamples.class);
        killAngel.setPriority(eventPriorities.HIGH);
        killAngel.setEventState(GenericState.RUNNING);
        killAngel.setClosed(false);
        killAngel.setSubject(PREFIX + "EVA 奉命去消灭第18使徒");
        killAngel.setMessage("[绝密] 最好能活捉。");
        killAngel.setBeginTime(new Date(System.currentTimeMillis() + 10));
        killAngel.addObservers(//
                robots, //
                users.adminRole);
    }

    @Override
    protected void getSamples(SampleList samples, boolean grouped) {
        samples.add("weather", weather);
        samples.add("special", special);
        // samples.addBatch(rains);
        samples.addAll(rains);
        samples.add("killAngel", killAngel);
    }

}
