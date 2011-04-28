package com.bee32.sem.process.verify.util;

import java.util.Date;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.PropertyAccessor;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.IAllowedByContext;

public abstract class AllowedBySupportDto<E extends AllowedBySupport<K, ? extends IAllowedByContext>, K extends Number>
        extends VerifiableEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    private UserDto verifier;
    private Date verifiedDate;
    private boolean allowed;
    private String rejectedReason;

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
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);

        target.setVerifiedDate(verifiedDate);
        target.setAllowed(allowed);
        target.setRejectedReason(rejectedReason);

        with(context, target)//
                .unmarshal(verifierProperty, verifier);
    }

    @Override
    public void _parse(TextMap map)
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

}
