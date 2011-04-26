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
import com.bee32.sem.process.verify.IAllowedByContext;

public abstract class AllowedBySupportDto<E extends AllowedBySupport<K, ? extends IAllowedByContext>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    private boolean verified;
    private UserDto verifier;

    private Date verifiedDate;
    private boolean allowed;
    private String rejectReason;

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
        rejectReason = source.getRejectReason();
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);

        with(context, target).unmarshal(verifierProperty, verifier);

        target.setVerifiedDate(verifiedDate);
        target.setAllowed(allowed);
        target.setRejectReason(rejectReason);
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        String _verifierId = map.getString("verifierId");
        if (_verifierId == null)
            verifier = null;
        else
            verifier = new UserDto().ref(Long.parseLong(_verifierId));

        long _verifiedDate = map.getLong("verifiedDate");
        verifiedDate = new Date(_verifiedDate);

        allowed = map.getBoolean("allowed");
        rejectReason = map.getString("rejectReason");
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationState() {
        return verified ? "Verified" : "Not-Verified";
    }

    public UserDto getVerifier() {
        return verifier;
    }

    public Long getVerifierId() {
        return id(verifier);
    }

    public String getVerifierName() {
        return verifier == null ? null : verifier.getDisplayName();
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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
