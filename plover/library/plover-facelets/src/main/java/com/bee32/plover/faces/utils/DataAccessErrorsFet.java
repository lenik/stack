package com.bee32.plover.faces.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.free.UnexpectedException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.faces.AbstractFet;

public class DataAccessErrorsFet
        extends AbstractFet {

    static final Map<String, DaoEmtFilter> registry;
    static {
        registry = new HashMap<String, DaoEmtFilter>();
    }

    static void register(String pattern, DaoEmtFilter filter) {
        registry.put(pattern, filter);
    }

    @Override
    public int handle(Throwable exception) {
        String message = exception.getMessage();
        if (message == null)
            return SKIP;
        if (message.contains("org.hibernate."))
            return REPLACE;
        else
            return SKIP;
    }

    @Override
    public String translate(Throwable exception, String message) {
        for (Entry<String, DaoEmtFilter> entry : registry.entrySet()) {
            String pattern = entry.getKey();
            if (message.contains(pattern)) {
                DaoEmtFilter filter = entry.getValue();
                return filter.eval(message);
            }
        }
        return message;
    }

    static class SimpleReplace
            extends DaoEmtFilter {

        final String replacement;

        public SimpleReplace(String replacement) {
            this.replacement = replacement;
        }

        @Override
        public String filter(String message) {
            return replacement;
        }

    }

    static class _ImmutableNaturalId
            extends DaoEmtFilter {

        static Pattern PATTERN = Pattern.compile("immutable natural identifier of an instance of (\\S+) was altered");

        @Override
        public String filter(String message) {
            Matcher m = PATTERN.matcher(message);
            if (!m.find())
                return message;
            String fqcn = m.group(1);
            Class<?> type;
            try {
                type = Class.forName(fqcn);
            } catch (ClassNotFoundException e) {
                throw new UnexpectedException(e.getMessage(), e);
            }
            String typeName = ClassUtil.getTypeName(type);
            return typeName + "的键属性已固化。";
        }
    }

    static {
        register("ConstraintViolationException", new SimpleReplace("记录被自动保护"));
        register("immutable natural identifier", new _ImmutableNaturalId());
    }

}
