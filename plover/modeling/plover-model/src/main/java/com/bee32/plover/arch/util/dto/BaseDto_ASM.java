package com.bee32.plover.arch.util.dto;

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
abstract class BaseDto_ASM<S>
        implements IDataTransferObject<S> {

    private static final long serialVersionUID = 1L;

    transient Stack<IMarshalSession> sessionStack;

    /**
     * Enter session
     *
     * @param session
     *            <code>null</code> to create a default one.
     * @see #getDefaultContext() The default context used by default session.
     */
    protected final void enter(IMarshalSession session) {
        if (session == null)
            session = getDefaultSession();

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

    protected IMarshalSession getSession() {
        if (sessionStack == null || sessionStack.isEmpty())
            throw new IllegalStateException("No marshal session.");
        return sessionStack.lastElement();
    }

    protected IMarshalSession peekSession() {
        if (sessionStack == null || sessionStack.isEmpty())
            return null;
        return sessionStack.lastElement();
    }

    IMarshalSession createOrReuseSession() {
        if (sessionStack == null || sessionStack.isEmpty())
            return new MarshalSession();
        else
            return sessionStack.lastElement();
    }

    @Override
    public synchronized final <D extends BaseDto<? super S>> D marshal(IMarshalSession session, S source) {
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

    abstract <D extends BaseDto<? super S>> D marshalImpl(S source);

    abstract S mergeImpl(S target);

    abstract void parseImpl(Map<String, ?> map)
            throws ParseException;

    abstract void exportImpl(Map<String, Object> map);

    // [A] Context/Session wrapper

    protected IMarshalSession getDefaultSession() {
        IMarshalSession session = createOrReuseSession();
        return session;
    }

    public final <D extends BaseDto<S>> D marshal(S source) {
        return marshal(createOrReuseSession(), source);
    }

    public final S merge(S source) {
        return merge(createOrReuseSession(), source);
    }

    public final void parse(Map<String, ?> map)
            throws ParseException {
        parse(createOrReuseSession(), map);
    }

    public final void export(Map<String, Object> map) {
        export(createOrReuseSession(), map);
    }

}
