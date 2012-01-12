package com.bee32.sem.process.verify;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.free.Pred0;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import overlay.Overlay;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.GeneralFormatter;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.event.entity.Event;

@MappedSuperclass
public abstract class AbstractVerifyContext
        implements Serializable, IVerifyContext, IMultiFormat {

    private static final long serialVersionUID = 1L;

    protected Entity<?> entity;

    VerifyEvalState verifyEvalState = VerifyEvalState.UNKNOWN;
    String verifyError;
    Date verifyEvalDate;
    Event verifyEvent;

    public AbstractVerifyContext() {
    }

    public AbstractVerifyContext(Entity<?> entity) {
        bind(entity);
    }

    private class VLockPred
            extends Pred0
            implements Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public boolean test() {
            if (VerifyEvalState.VERIFIED.equals(verifyEvalState))
                return true;
            return false;
        }

    }

    public void bind(Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        this.entity = entity;
        EntityAccessor.addLockPredicate(entity, new VLockPred());
    }

    /**
     * 清楚审核数据，以便重新审核
     */
    public void clear() {
        verifyEvalState = VerifyEvalState.UNKNOWN;
        verifyError = null;
        verifyEvalDate = null;
        verifyEvent = null;
    }

    @Column(name = "verifyEvalState", nullable = false)
    @DefaultValue("" + 0x02000001 /* 33554433, VerifyEvalState.UNKNOWN */)
    int get_verifyEvalState() {
        return verifyEvalState.getValue();
    }

    void set_verifyEvalState(int stateValue) {
        if (stateValue == -1)
            verifyEvalState = VerifyEvalState.UNKNOWN;
        else
            verifyEvalState = VerifyEvalState.valueOf(stateValue);
    }

    @Transient
    @Override
    public VerifyEvalState getVerifyEvalState() {
        return verifyEvalState;
    }

    void setVerifyEvalState(VerifyEvalState verifyEvalState) {
        if (verifyEvalState == null)
            throw new NullPointerException("verifyEvalState");
        this.verifyEvalState = verifyEvalState;
    }

    @Temporal(TIMESTAMP)
    public Date getVerifyEvalDate() {
        return verifyEvalDate;
    }

    void setVerifyEvalDate(Date verifyEvalDate) {
        this.verifyEvalDate = verifyEvalDate;
    }

    @Transient
    @Column(nullable = false)
    @DefaultValue("false")
    public boolean isVerified() {
        return VerifyEvalState.VERIFIED.equals(verifyEvalState);
    }

    @Column(length = 100)
    public String getVerifyError() {
        return verifyError;
    }

    void setVerifyError(String error) {
        this.verifyError = error;
    }

    @OneToOne(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public Event getVerifyEvent() {
        return verifyEvent;
    }

    public void setVerifyEvent(Event verifyEvent) {
        this.verifyEvent = verifyEvent;
    }

    @Override
    public final String toString() {
        return toString(FormatStyle.DEFAULT);
    }

    @Override
    public final String toString(FormatStyle format) {
        PrettyPrintStream out = new PrettyPrintStream();
        toString(out, format);
        return out.toString();
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        toString(out, format, null, 0);
    }

    @Overlay
    public void toString(PrettyPrintStream out, FormatStyle format, Set<Object> occurred, int depth) {
        GeneralFormatter formatter = new GeneralFormatter(out, occurred);
        formatter.format(this, format, depth);
    }

}
