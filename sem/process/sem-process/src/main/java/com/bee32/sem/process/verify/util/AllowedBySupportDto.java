package com.bee32.sem.process.verify.util;

import java.util.Date;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.sem.process.verify.ISingleVerifier;

public abstract class AllowedBySupportDto<E extends SingleVerifierSupport<K, ? extends ISingleVerifier>, K extends Number>
        extends VerifiableEntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    private UserDto verifier;
    private Date verifiedDate;
    private boolean allowed;
    private String rejectedReason;

    public AllowedBySupportDto() {
        super();
    }

    public AllowedBySupportDto(int selection) {
        super(selection);
    }

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        verifier = mref(UserDto.class, source.getVerifier());
        verifiedDate = source.getVerifiedDate();
        allowed = source.isAccepted();
        rejectedReason = source.getRejectedReason();
    }

    @Override
    protected void __unmarshalTo(E target) {
        super.__unmarshalTo(target);

        target.setVerifiedDate(verifiedDate);
        target.setAllowed(allowed);
        target.setRejectedReason(rejectedReason);

        merge(target, "verifier", verifier);
    }

    @Override
    public void __parse(TextMap map)
            throws ParseException, TypeConvertException {
        super.__parse(map);

        verifier = new UserDto(0).parseRef(map.getString("verifierId"));

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

}
