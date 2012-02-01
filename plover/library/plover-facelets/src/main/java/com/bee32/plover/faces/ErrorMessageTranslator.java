package com.bee32.plover.faces;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.PriorityComparator;
import com.bee32.plover.inject.ServiceTemplate;

@ServiceTemplate
public abstract class ErrorMessageTranslator
        implements IErrorMessageTranslator {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public int handle(String message) {
        return FILTER;
    }

    static Set<IErrorMessageTranslator> translators;
    static {
        translators = new TreeSet<IErrorMessageTranslator>(PriorityComparator.INSTANCE);
        for (IErrorMessageTranslator emt : ServiceLoader.load(IErrorMessageTranslator.class)) {
            translators.add(emt);
        }
    }

    public static String translateMessage(String s) {
        for (IErrorMessageTranslator emt : translators) {
            int type = emt.handle(s);
            switch (type) {
            case IErrorMessageTranslator.SKIP:
                continue;
            case IErrorMessageTranslator.FILTER:
                s = emt.translate(s);
                continue;
            case IErrorMessageTranslator.REPLACE:
                s = emt.translate(s);
                return s;
            default:
                throw new IllegalUsageException("Bad handle-type: " + type + " (EMT=" + emt + ")");
            }
        }
        return s;
    }

}
