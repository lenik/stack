package com.bee32.sem.process.verify;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;
import java.util.Set;

import javax.free.IllegalUsageException;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import overlay.Overlay;

import com.bee32.plover.arch.util.ILockable;
import com.bee32.plover.orm.entity.EmbeddablePiece;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IPopulatable;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.IMultiFormat;
import com.bee32.plover.util.PrettyPrintStream;
import com.bee32.sem.event.entity.Event;

@MappedSuperclass
public abstract class AbstractVerifyContext
        extends EmbeddablePiece
        implements IVerifyContext, IMultiFormat, ILockable, IPopulatable, Cloneable {

    private static final long serialVersionUID = 1L;

    protected transient Entity<?> entity;

    VerifyEvalState verifyEvalState = VerifyEvalState.UNKNOWN;
    String verifyError;
    Date verifyEvalDate;
    Event verifyEvent;

    @Override
    public AbstractVerifyContext clone() {
        try {
            AbstractVerifyContext copy = getClass().newInstance();
            copy.populate(this);
            return copy;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void populate(Object source) {
        if (source instanceof AbstractVerifyContext)
            _populate((AbstractVerifyContext) source);
        else
            throw new IllegalUsageException("Unsupport source for population: " + source);
    }

    protected void _populate(AbstractVerifyContext o) {
        this.verifyEvalState = o.verifyEvalState;
        this.verifyError = o.verifyError;
        this.verifyEvalDate = o.verifyEvalDate;
        this.verifyEvent = o.verifyEvent;
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

    /**
     * 审核状态
     *
     * 记录审核运算求值返回的审核结果。
     */
    @Column(name = "verifyEvalState", nullable = false)
    @DefaultValue("" + 0x02000001 /* 33554433, VerifyEvalState.UNKNOWN */)
    int get_verifyEvalState() {
        return verifyEvalState.getValue();
    }

    void set_verifyEvalState(int stateValue) {
        if (stateValue == -1)
            verifyEvalState = VerifyEvalState.UNKNOWN;
        else
            verifyEvalState = VerifyEvalState.forValue(stateValue);
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

    /**
     * 审核求值时间
     *
     * 记录系统中审核运算的求值时间。
     */
    @Temporal(TIMESTAMP)
    public Date getVerifyEvalDate() {
        return verifyEvalDate;
    }

    void setVerifyEvalDate(Date verifyEvalDate) {
        this.verifyEvalDate = verifyEvalDate;
    }

    /**
     * 已通过审核
     */
    @Transient
    @Column(nullable = false)
    @DefaultValue("false")
    public boolean isVerified() {
        return VerifyEvalState.VERIFIED.equals(verifyEvalState);
    }

    /**
     * 审核错误消息
     *
     * 记录审核运算求值返回的错误消息（或空表示求值成功）。
     */
    @Column(length = 100)
    public String getVerifyError() {
        return verifyError;
    }

    void setVerifyError(String error) {
        this.verifyError = error;
    }

    /**
     * 审核事件
     *
     * 事件系统中的事件消息对象，用于消息分发。
     */
    @OneToOne(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public Event getVerifyEvent() {
        return verifyEvent;
    }

    public void setVerifyEvent(Event verifyEvent) {
        this.verifyEvent = verifyEvent;
    }

    @Transient
    @Override
    public boolean isLocked() {
        return verifyEvalState == VerifyEvalState.VERIFIED;
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
        VerifyContextFormatter formatter = new VerifyContextFormatter(out, occurred);
        formatter.format(this, format, depth);
    }

}
