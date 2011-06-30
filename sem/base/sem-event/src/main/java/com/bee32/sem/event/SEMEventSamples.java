package com.bee32.sem.event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Role;
import com.bee32.plover.orm.util.EntitySamplesContribution;
import com.bee32.plover.orm.util.ImportSamples;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventStatus;
import com.bee32.sem.event.entity.Task;

@ImportSamples(IcsfPrincipalSamples.class)
public class SEMEventSamples
        extends EntitySamplesContribution {

    public static final EventPriority URGENT = new EventPriority(EventPriority.URGENT, "urgent", "紧急");
    public static final EventPriority HIGH = new EventPriority(EventPriority.HIGH, "high", "高");
    public static final EventPriority NORMAL = new EventPriority(EventPriority.NORMAL, "normal", "普通");
    public static final EventPriority LOW = new EventPriority(EventPriority.LOW, "low", "低");

    public static final EventStatus UNKNOWN = new EventStatus(GenericState.UNKNOWN, "unknown", "无状态");
    public static final EventStatus RUNNING = new EventStatus(GenericState.RUNNING, "running", "进行中");
    public static final EventStatus SUSPENDED = new EventStatus(GenericState.SUSPENDED, "suspended", "挂起");
    public static final EventStatus CANCELED = new EventStatus(GenericState.CANCELED, "canceled", "取消");
    public static final EventStatus DONE = new EventStatus(GenericState.DONE, "done", "成功");
    public static final EventStatus FAILED = new EventStatus(GenericState.FAILED, "failed", "失败");
    public static final EventStatus ERRORED = new EventStatus(GenericState.ERRORED, "errored", "异常");

    public static final EventCategory weather = new EventCategory("weather", "天气预报");
    public static final EventCategory special = new EventCategory("special", "特种");

    public static List<Event> rains = new ArrayList<Event>();
    public static Task killAngel;

    static Event mkRain(double relativeDay, Double duration, String title) {
        Event rain = new Event();
        rain.setCategory(weather);
        rain.setSourceClass(SEMEventSamples.class);
        rain.setPriority(LOW.getPriority());
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

        rain.setObservers(Arrays.asList(IcsfPrincipalSamples.solaRobots));
        return rain;
    }

    static {
        rains.add(mkRain(-28.5, 1.0, "28.5 天前"));
        rains.add(mkRain(-29.9, null, "29.9 天前"));
        rains.add(mkRain(-30.2, 1.0, "30.1 天前"));
        rains.add(mkRain(0, null, "今天"));
        rains.add(mkRain(1.5, null, "1.5天之后"));

        killAngel = new Task();
        killAngel.setCategory(special);
        killAngel.setSourceClass(SEMEventSamples.class);
        killAngel.setPriority(HIGH.getPriority());
        killAngel.setState(GenericState.RUNNING);
        killAngel.setClosed(false);
        killAngel.setSubject("EVA 奉命去消灭第18使徒");
        killAngel.setMessage("[绝密] 最好能活捉。");
        killAngel.setBeginTime(new Date(System.currentTimeMillis() + 10));
        killAngel.setObservers(Arrays.asList(//
                IcsfPrincipalSamples.solaRobots, //
                Role.adminRole));
    }

    @Override
    protected void preamble() {
        addNormalSample(URGENT, HIGH, NORMAL, LOW);
        addNormalSample(UNKNOWN, RUNNING, SUSPENDED, CANCELED, DONE, FAILED, ERRORED);

        addNormalSample(weather, special);

        for (Event rain : rains)
            addNormalSample(rain);

        addNormalSample(killAngel);
    }

}
