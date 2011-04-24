package com.bee32.sem.process.verify.util;

import java.io.Serializable;
import java.util.Date;

import javax.free.IVariantLookupMap;
import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.process.verify.IAllowedByContext;

public abstract class AllowedBySupportDto<E extends AllowedBySupport<K, ? extends IAllowedByContext>, K extends Serializable>
        extends EntityDto<E, K> {

    private static final long serialVersionUID = 1L;

    private Long verifierId;
    private String verifierName;

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

    public Long getVerifierId() {
        return verifierId;
    }

    public void setVerifierId(Long verifierId) {
        this.verifierId = verifierId;
    }

    public String getVerifierName() {
        return verifierName;
    }

    public void setVerifierName(String verifierName) {
        this.verifierName = verifierName;
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

    @Override
    protected void __marshal(E source) {
        super.__marshal(source);

        User verifier = source.getVerifier();
        verifierId = verifier == null ? null : verifier.getId();
        verifierName = verifier == null ? null : verifier.getName();

        verifiedDate = source.getVerifiedDate();
        allowed = source.isAllowed();
        rejectReason = source.getRejectReason();
    }

    @Override
    protected void __unmarshalTo(IUnmarshalContext context, E target) {
        super.__unmarshalTo(context, target);

        target.setVerifiedDate(verifiedDate);
        target.setAllowed(allowed);
        target.setRejectReason(rejectReason);

        throw new NotImplementedException("unmarshal verifier.");
    }

    @Override
    public void parse(IVariantLookupMap<String> map)
            throws ParseException, TypeConvertException {
        super.parse(map);

        String _verifierId = map.getString("verifierId");
        verifierId = _verifierId == null ? null : Long.parseLong(_verifierId);

        verifierName = map.getString("verifierName");

        long _verifiedDate = map.getLong("verifiedDate");
        verifiedDate = new Date(_verifiedDate);

        allowed = map.getBoolean("allowed");
        rejectReason = map.getString("rejectReason");
    }

}
