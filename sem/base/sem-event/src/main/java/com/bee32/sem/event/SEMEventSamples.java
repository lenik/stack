package com.bee32.sem.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Role;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.plover.orm.util.SampleContribution;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventType;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMEventSamples
        extends SampleContribution {

    public static final EventCategory weather = new EventCategory("weather", "天气预报");
    public static final EventCategory special = new EventCategory("special", "特种");

    public static List<Event> rains = new ArrayList<Event>();
    public static Event killAngel;

    static Event mkRain(double relativeDay, Double duration, String title) {
        Event rain = new Event(SEMEventSamples.class, EventType.EVENT);
        rain.setCategory(weather);
        rain.setPriority(EventPriority.LOW);
        rain.setState(GenericState.UNKNOWN);
        rain.setClosed(duration != null);

        long beginMs = (long) (System.currentTimeMillis() + relativeDay * 86400 * 1000);
        rain.setBeginTime(new Date(beginMs));
        if (duration != null) {
            long durMs = (long) (duration * 86400 * 1000);
            rain.setEndTime(new Date(beginMs + durMs));
        }

        rain.setSubject("在 " + title + " (" + rain.getBeginTime() + ") 时刻有流星雨，请大家出门带上望远镜。");
        rain.setMessage("你以为带上望远镜就能看到土卫3的流行雨吗？少年哟，别做梦了，那是不可能的。");

        rain.addObservers(IcsfPrincipalSamples.solaRobots);
        return rain;
    }

    static {
        rains.add(mkRain(-28.5, 1.0, "28.5 天前"));
        rains.add(mkRain(-29.9, null, "29.9 天前"));
        rains.add(mkRain(-30.2, 1.0, "30.1 天前"));
        rains.add(mkRain(0, null, "今天"));
        rains.add(mkRain(1.5, null, "1.5天之后"));

        killAngel = new Event(SEMEventSamples.class, EventType.TASK);
        killAngel.setCategory(special);
        killAngel.setSourceClass(SEMEventSamples.class);
        killAngel.setPriority(EventPriority.HIGH);
        killAngel.setState(GenericState.RUNNING);
        killAngel.setClosed(false);
        killAngel.setSubject("EVA 奉命去消灭第18使徒");
        killAngel.setMessage("[绝密] 最好能活捉。");
        killAngel.setBeginTime(new Date(System.currentTimeMillis() + 10));
        killAngel.addObservers(//
                IcsfPrincipalSamples.solaRobots, //
                Role.adminRole);
    }

    @Override
    protected void preamble() {
        addBulk(weather, special);

        for (Event rain : rains)
            add(rain);

        add(killAngel);
    }

}
