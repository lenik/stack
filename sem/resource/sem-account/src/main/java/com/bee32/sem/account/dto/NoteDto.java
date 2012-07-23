package com.bee32.sem.account.dto;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.sem.account.entity.CurrentAccount;
import com.bee32.sem.account.entity.Note;
import com.bee32.sem.people.dto.OrgDto;


public class NoteDto
        extends AccountEdDto {

    private static final long serialVersionUID = 1L;

    NoteBalancingDto noteBalancing;

    String acceptBank;
    OrgDto acceptOrg;
    String billNo;
    BillTypeDto billType;

    public NoteDto() {
        super();
    }

    public NoteDto(int mask) {
        super(mask);
    }

    @Override
    protected void _marshal(CurrentAccount source) {
        super._marshal(source);

        Note note = (Note)source;

        noteBalancing = marshal(NoteBalancingDto.class, note.getNoteBalancing());
        acceptBank = note.getAcceptBank();
        acceptOrg = marshal(OrgDto.class, note.getAcceptOrg());
        billNo = note.getBillNo();
        billType = mref(BillTypeDto.class, note.getBillType());
    }

    @Override
    protected void _unmarshalTo(CurrentAccount target) {
        super._unmarshalTo(target);

        Note note = (Note)target;

        merge(note, "noteBalancing", noteBalancing);
        note.setAcceptBank(acceptBank);
        merge(note, "acceptOrg", acceptOrg);
        note.setBillNo(billNo);
        merge(note, "billType", billType);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public NoteBalancingDto getNoteBalancing() {
        return noteBalancing;
    }

    public void setNoteBalancing(NoteBalancingDto noteBalancing) {
        this.noteBalancing = noteBalancing;
    }


    @NLength(min = 2, max = Note.ACCEPT_BANK_LENGTH)
    public String getAcceptBank() {
        return acceptBank;
    }

    public void setAcceptBank(String acceptBank) {
        this.acceptBank = acceptBank;
        this.acceptOrg = new OrgDto().ref();
    }

    public OrgDto getAcceptOrg() {
        return acceptOrg;
    }

    public void setAcceptOrg(OrgDto acceptOrg) {
        this.acceptOrg = acceptOrg;
        this.acceptBank = "";
    }

    @NLength(min = 2, max = Note.BILL_NO_LENGTH)
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public BillTypeDto getBillType() {
        return billType;
    }

    public void setBillType(BillTypeDto billType) {
        this.billType = billType;
    }
}
