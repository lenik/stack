package com.bee32.sem.process.verify.util;

import java.io.Serializable;
import java.util.Date;

import javax.free.IVariantLookupMap;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.event.EventState;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.event.web.TaskDto;
import com.bee32.sem.process.verify.IAllowedByContext;

public abstract class AllowedBySupportDto<E extends AllowedBySupport<K, ? extends IAllowedByContext>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    private UserDto verifier;
    private Date verifiedDate;
    private boolean allowed;
    private String rejectedReason;

    private EventState verifyState;
    private String verifyError;
    private Date verifyEvalDate;
    private TaskDto verifyTask;

    public AllowedBySupportDto() {
        super();
    }

    public AllowedBySupportDto(E source) {
        super(source);
    }

    public AllowedBySupportDto(int selection) {
        super(selection);
    }

    public AllowedBySupportDto(E source, int selection) {
        super(source, selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        verifier = new UserDto(source.getVerifier());
        verifiedDate = source.getVerifiedDate();
        allowed = source.isAllowed();
        rejectedReason = source.getRejectedReason();

        verifyState = source.getVerifyState();
        verifyError = source.getVerifyError();
        verifyEvalDate = source.getVerifyEvalDate();

        verifyTask = new TaskDto().ref(source.getVerifyTask());
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);

        target.setVerifiedDate(verifiedDate);
        target.setAllowed(allowed);
        target.setRejectedReason(rejectedReason);

        with(context, target)//
                .unmarshal(verifierProperty, verifier)//
                .unmarshal(verifyTaskProperty, verifyTask);
    }

    @Override
    public void _parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {

        String _verifierId = map.getString("verifierId");
        if (_verifierId == null)
            verifier = null;
        else
            verifier = new UserDto().ref(Long.parseLong(_verifierId));

        long _verifiedDate = map.getLong("verifiedDate");
        verifiedDate = new Date(_verifiedDate);

        allowed = map.getBoolean("allowed");
        rejectedReason = map.getString("rejectedReason");
    }

    public UserDto getVerifier() {
        return verifier;
    }

    public void setVerifier(UserDto verifier) {
        this.verifier = verifier;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public EventState getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(EventState verifyState) {
        this.verifyState = verifyState;
    }

    public String getVerifyError() {
        return verifyError;
    }

    public void setVerifyError(String verifyError) {
        this.verifyError = verifyError;
    }

    public Date getVerifyEvalDate() {
        return verifyEvalDate;
    }

    public void setVerifyEvalDate(Date verifyEvalDate) {
        this.verifyEvalDate = verifyEvalDate;
    }

    public String getVerifierName() {
        return verifier == null ? null : verifier.getName();
    }

    final PropertyAccessor<E, User> verifierProperty;
    {
        verifierProperty = new PropertyAccessor<E, User>(User.class) {

            @Override
            public User get(E entity) {
                return entity.getVerifier();
            }

            @Override
            public void set(E entity, User verifier) {
                entity.setVerifier(verifier);
            }

        };
    }

    final PropertyAccessor<E, Task> verifyTaskProperty = new PropertyAccessor<E, Task>(Task.class) {

        @Override
        public Task get(E entity) {
            return entity.getVerifyTask();
        }

        @Override
        public void set(E entity, Task verifyTask) {
            entity.setVerifyTask(verifyTask);
        }

    };

}
