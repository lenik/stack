package com.bee32.plover.arch.util.res;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import com.bee32.plover.arch.util.PriorityComparator;

public class NlsMessageProcessors {

    static final Set<INlsMessageProcessor> processors;
    static {
        processors = new TreeSet<INlsMessageProcessor>(PriorityComparator.INSTANCE);

        for (INlsMessageProcessor processor : ServiceLoader.load(INlsMessageProcessor.class))
            processors.add(processor);
    }

    public static String processMessage(String message) {
        for (INlsMessageProcessor processor : processors)
            message = processor.processMessage(message);
        return message;
    }

}
