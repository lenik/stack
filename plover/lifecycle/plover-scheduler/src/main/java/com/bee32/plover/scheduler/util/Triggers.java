package com.bee32.plover.scheduler.util;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.impl.triggers.SimpleTriggerImpl;

public class Triggers {

    /**
     * Retry once, a minute later.
     *
     * @see #timeout(String, int, int, int)
     */
    public static SimpleTriggerImpl timeout(String name, int ms) {
        return timeout(name, ms, 60000, 1);
    }

    /**
     * 当调度的时候 trigger time 已经过去了，则安排到下一次 repeat 再执行。
     *
     * 在 job 执行中要确保不可重入，并在 job 结尾重新调度一个新的 trigger。
     *
     * 因此对 trigger 的有求有：
     * <ul>
     * <li>不可重入 (See JobReentrantTest) 只要在 Job 上添加 {@link DisallowConcurrentExecution} 标注即可。
     * <li>中间空缺的窗口不应该计入 repeat count。即 (now - trigger.beginTime) / repeatInterval 这部分应该不做考虑。 (See
     * JobFastforwardTest)
     * </ul>
     *
     * @param retryInterval
     *            In milliseconds.
     */
    public static SimpleTriggerImpl timeout(String name, int ms, int retryInterval, int retryCount) {
        SimpleTriggerImpl trigger = new SimpleTriggerImpl();

        trigger.setName(name);

        long t = new Date().getTime();

        trigger.setStartTime(new Date(t + ms));

        // miss strategy
        trigger.setRepeatInterval(retryInterval);
        trigger.setRepeatCount(retryCount);

        return trigger;
    }

}
