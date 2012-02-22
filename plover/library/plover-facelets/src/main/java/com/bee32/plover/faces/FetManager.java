package com.bee32.plover.faces;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.PriorityComparator;

public class FetManager {

    static Set<IFacesExceptionTranslator> translators;
    static {
        translators = new TreeSet<IFacesExceptionTranslator>(PriorityComparator.INSTANCE);
        for (IFacesExceptionTranslator emt : ServiceLoader.load(IFacesExceptionTranslator.class)) {
            translators.add(emt);
        }
    }

    public static String translateMessage(Throwable exception) {
        String mesg = exception.getMessage();
        for (IFacesExceptionTranslator emt : translators) {
            int type = emt.handle(exception);
            switch (type) {
            case IFacesExceptionTranslator.SKIP:
                continue;
            case IFacesExceptionTranslator.FILTER:
                mesg = emt.translate(exception, exception.getMessage());
                continue;
            case IFacesExceptionTranslator.REPLACE:
                mesg = emt.translate(exception, exception.getMessage());
                return mesg;
            default:
                throw new IllegalUsageException("Bad handle-type: " + type + " (EMT=" + emt + ")");
            }
        }
        return mesg;
    }

}
