package com.bee32.plover.arch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.PriorityComparator;

public class Application {

    static Logger logger = LoggerFactory.getLogger(Application.class);

    static List<IApplicationLifecycle> alcv;
    static {
        alcv = new ArrayList<IApplicationLifecycle>();
        for (IApplicationLifecycle alc : ServiceLoader.load(IApplicationLifecycle.class))
            alcv.add(alc);

        Collections.sort(alcv, PriorityComparator.INSTANCE);
    }

    public static void initialize() {
        for (IApplicationLifecycle alc : alcv) {
            logger.info("Initialize ALC: " + alc.getClass().getName());
            alc.initialize();
        }
    }

    public static void terminate() {
        for (int i = alcv.size() - 1; i >= 0; i--) {
            IApplicationLifecycle alc = alcv.get(i);
            logger.info("Terminate ALC: " + alc.getClass().getName());
            alc.terminate();
        }
    }

}
