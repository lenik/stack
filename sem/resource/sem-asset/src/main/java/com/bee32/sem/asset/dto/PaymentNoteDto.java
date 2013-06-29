package com.bee32.sem.asset.dto;

import javax.free.ParseException;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.asset.entity.PaymentNote;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class PaymentNoteDto extends FundFlowDto implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    PersonDto whoPay;

    SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport;

    UserDto approveUser;
    boolean approved;
    String approveMessage;

    public PaymentNoteDto() {
        super();
    }

    @Override
    protected void _marshal(FundFlow _source) {
        super._marshal(_source);

        PaymentNote source = (PaymentNote) _source;

        whoPay = mref(PersonDto.class, source.getWhoPay());

        singleVerifierWithNumberSupport = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());

        approveUser = mref(UserDto.class, source.getApproveUser());
        approved = source.isApproved();
        approveMessage = source.getApproveMessage();
    }

    @Override
    protected void _unmarshalTo(FundFlow _target) {
        super._unmarshalTo(_target);

        PaymentNote target = (PaymentNote) _target;

        merge(target, "whoPay", whoPay);
        merge(target, "approveUser", approveUser);
        target.setApproved(approved);
        target.setApproveMessage(approveMessage);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
    }

    public PersonDto getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(PersonDto whoPay) {
        this.whoPay = whoPay;
    }

    @Override
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
    }

    public UserDto getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(UserDto approveUser) {
        this.approveUser = approveUser;
    }

    public void approveByMe() {
        UserDto loginUser = SessionUser.getInstance().getUser();
        setApproveUser(loginUser);
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @NLength(max = Chance.APPROVE_MESSAGE_LENGTH)
    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        this.approveMessage = TextUtil.normalizeSpace(approveMessage);
    }

}
