package com.bee32.sem.process.verify.builtin.dto;

import java.util.Date;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;
import com.bee32.sem.process.verify.dto.VerifyContextDto;

public class SingleVerifierSupportDto
        extends VerifyContextDto<SingleVerifierSupport> {

    private static final long serialVersionUID = 1L;

    private PrincipalDto verifier1;
    private Date verifiedDate1;
    private boolean accepted1;
    private String rejectedReason1;

    public SingleVerifierSupportDto() {
        super();
    }

    public SingleVerifierSupportDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(SingleVerifierSupport source) {
        verifier1 = mref(PrincipalDto.class, source.getVerifier1());
        verifiedDate1 = source.getVerifiedDate1();
        accepted1 = source.isAccepted1();
        rejectedReason1 = source.getRejectedReason1();
    }

    @Override
    protected void _unmarshalTo(SingleVerifierSupport target) {
        merge(target, "verifier1", verifier1);

        target.setVerifiedDate1(verifiedDate1);
        target.setAccepted1(accepted1);
        target.setRejectedReason1(rejectedReason1);
    }

    @Override
    public void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        verifier1 = new PrincipalDto().parseRef(map.getString("verifierId1"));

        long _verifiedDate1 = map.getLong("verifiedDate1");
        verifiedDate1 = new Date(_verifiedDate1);

        accepted1 = map.getBoolean("accepted1");
        rejectedReason1 = map.getString("rejectedReason1");
    }

    public PrincipalDto getVerifier1() {
        return verifier1;
    }

    public void setVerifier1(PrincipalDto verifier1) {
        this.verifier1 = verifier1;
    }

    public Date getVerifiedDate1() {
        return verifiedDate1;
    }

    public void setVerifiedDate1(Date verifiedDate1) {
        this.verifiedDate1 = verifiedDate1;
    }

    public boolean isAccepted1() {
        return accepted1;
    }

    public void setAccepted1(boolean accepted1) {
        this.accepted1 = accepted1;
    }

    public String getRejectedReason1() {
        return rejectedReason1;
    }

    public void setRejectedReason1(String rejectedReason1) {
        this.rejectedReason1 = rejectedReason1;
    }

    public String getVerifierName1() {
        return verifier1 == null ? null : verifier1.getName();
    }

}
