package com.seccaproject.plover.arch.i18n;

import java.util.Locale;
import java.util.Stack;

import net.bodz.bas.api.types.Executable;

public class LocaleContext {

    static ThreadLocal<Stack<Locale>> tlsLocaleStack;

    public static Locale getContextLocale() {
        Stack<Locale> localeStack = tlsLocaleStack.get();
        if (localeStack == null)
            tlsLocaleStack.set(localeStack = new Stack<Locale>());
        if (localeStack.isEmpty())
            throw new IllegalStateException("Thread locale isn't properly set");
        return localeStack.peek();
    }

    public static void run(Executable<Exception> l10nTarget, Locale locale)
            throws Exception {
        Stack<Locale> localeStack = tlsLocaleStack.get();
        if (localeStack == null)
            tlsLocaleStack.set(localeStack = new Stack<Locale>());
        localeStack.push(locale);
        try {
            l10nTarget.execute();
        } finally {
            localeStack.pop();
        }
    }

    public static void dump() {
        Thread.currentThread().getThreadGroup().getParent();
    }

}
