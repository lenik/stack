package com.bee32.plover.arch.util;

import java.util.Map;
import java.util.Stack;

import javax.free.ParseException;

/**
 * <pre>
 * LAYER 1
 * -----------------------------------------------------------------------
 *      This layer introduce in auto session management.
 *
 *      These methods are the only public ones, to enforce the
 *      synchronization between method calls.
 * </pre>
 */
abstract class BaseDto_ASM<S, C>
        implements IDataTransferObject<S, C> {

    Stack<IMarshalSession> sessionStack;

    /**
     * Enter session
     */
    protected final void enter(IMarshalSession session) {
        if (session == null)
            throw new NullPointerException("session");

        if (sessionStack == null)
            sessionStack = new Stack<IMarshalSession>();

        sessionStack.push(session);
    }

    /**
     * Leave a session
     */
    protected final void leave() {
        if (sessionStack == null || sessionStack.isEmpty())
            throw new IllegalStateException("Session stack underflow.");

        sessionStack.pop();
    }

    @Override
    public synchronized final <D extends BaseDto<S, C>> D marshal(IMarshalSession session, S source) {
        enter(session);
        try {
            return marshalImpl(source);
        } finally {
            leave();
        }
    }

    @Override
    public synchronized final S merge(IMarshalSession session, S target) {
        enter(session);
        try {
            return mergeImpl(target);
        } finally {
            leave();
        }
    }

    @Override
    public synchronized final void parse(IMarshalSession session, Map<String, ?> map)
            throws ParseException {
        enter(session);
        try {
            parseImpl(map);
        } finally {
            leave();
        }
    }

    @Override
    public synchronized final void export(IMarshalSession session, Map<String, Object> map) {
        enter(session);
        try {
            exportImpl(map);
        } finally {
            leave();
        }
    }

    abstract <D extends IDataTransferObject<S, C>> D marshalImpl(S source);

    abstract S mergeImpl(S target);

    abstract void parseImpl(Map<String, ?> map)
            throws ParseException;

    abstract void exportImpl(Map<String, Object> map);

}
