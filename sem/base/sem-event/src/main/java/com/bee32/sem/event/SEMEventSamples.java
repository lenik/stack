package com.bee32.sem.event;

import java.util.Arrays;
import java.util.Date;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
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

    public static final EventPriority URGENT = new EventPriority("urgent", "紧急", EventPriority.URGENT);
    public static final EventPriority HIGH = new EventPriority("high", "高", EventPriority.HIGH);
    public static final EventPriority NORMAL = new EventPriority("normal", "普通", EventPriority.NORMAL);
    public static final EventPriority LOW = new EventPriority("low", "低", EventPriority.LOW);

    public static final EventStatus UNKNOWN = new EventStatus("unknown", "无状态", GenericState.UNKNOWN);
    public static final EventStatus RUNNING = new EventStatus("running", "进行中", GenericState.RUNNING);
    public static final EventStatus SUSPENDED = new EventStatus("suspended", "挂起", GenericState.SUSPENDED);
    public static final EventStatus CANCELED = new EventStatus("canceled", "取消", GenericState.CANCELED);
    public static final EventStatus DONE = new EventStatus("done", "成功", GenericState.DONE);
    public static final EventStatus FAILED = new EventStatus("failed", "失败", GenericState.FAILED);
    public static final EventStatus ERRORED = new EventStatus("errored", "异常", GenericState.ERRORED);

    public static final EventCategory weather = new EventCategory("weather", "天气预报");
    public static final EventCategory special = new EventCategory("special", "特种");

    public static Event rain;
    public static Task killAngel;

    static {
        rain = new Event();
        rain.setCategory(weather);
        rain.setSourceClass(SEMEventSamples.class);
        rain.setPriority(LOW.getPriority());
        rain.setState(GenericState.UNKNOWN);
        rain.setClosed(true);
        rain.setSubject("明天早晨会有流星雨，请大家出门带上望远镜。");
        rain.setMessage("你以为带上望远镜就能看到土卫3的流行雨吗？少年哟，别做梦了，那是不可能的。");
        rain.setBeginTime(new Date(System.currentTimeMillis() - 7200));
        rain.setBeginTime(new Date(System.currentTimeMillis() + 7200));
        rain.setObservers(Arrays.asList(IcsfPrincipalSamples.solaRobots));

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
                IcsfPrincipalSamples.adminRole));
    }

    @Override
    protected void preamble() {
        addNormalSample(URGENT, HIGH, NORMAL, LOW);
        addNormalSample(UNKNOWN, RUNNING, SUSPENDED, CANCELED, DONE, FAILED, ERRORED);

        addNormalSample(weather, special);

        addNormalSample(rain);
        addNormalSample(killAngel);
    }

}
