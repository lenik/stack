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

import overlay.Overlay;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.GeneralFormatter;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.entity.Task;

@MappedSuperclass
public abstract class AbstractVerifyContext
        implements Serializable, IVerifyContext, IMultiFormat {

    private static final long serialVersionUID = 1L;

    protected final Entity<?> entity;

    EventState verifyState = VerifyState.UNKNOWN;
    String verifyError;
    Date verifyEvalDate;
    Task verifyTask;

    private class VLockPred
            extends Pred0 {

        @Override
        public boolean test() {
            if (VerifyState.VERIFIED.equals(verifyState))
                return true;
            return false;
        }

    }

    public AbstractVerifyContext(Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        this.entity = entity;
        EntityAccessor.addLockPredicate(entity, new VLockPred());
    }

    @Column(name = "verifyState", nullable = false)
    int getVerifyState_() {
        return verifyState.getValue();
    }

    void setVerifyState_(int stateValue) {
        verifyState = VerifyState.valueOf(stateValue);
    }

    @Transient
    public EventState getVerifyState() {
        return verifyState;
    }

    void setVerifyState(EventState verifyState) {
        if (verifyState == null)
            throw new NullPointerException("verifyState");
        this.verifyState = verifyState;
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
    public boolean isVerified() {
        return VerifyState.VERIFIED.equals(verifyState);
    }

    @Column(length = 100)
    public String getVerifyError() {
        return verifyError;
    }

    void setVerifyError(String error) {
        this.verifyError = error;
    }

    @OneToOne(orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    public Task getVerifyTask() {
        return verifyTask;
    }

    public void setVerifyTask(Task verifyTask) {
        this.verifyTask = verifyTask;
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
