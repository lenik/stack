package com.bee32.sem.account.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.account.entity.CurrentAccount;
import com.bee32.sem.account.entity.Note;
import com.bee32.sem.people.dto.PartyDto;


public class NoteDto
        extends AccountEdDto {

    private static final long serialVersionUID = 1L;

    String bank;
    PartyDto party;
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

        bank = note.getBank();
        party = marshal(PartyDto.class, note.getParty());
        billNo = note.getBillNo();
        billType = marshal(BillTypeDto.class, note.getBillType());
    }

    @Override
    protected void _unmarshalTo(CurrentAccount target) {
        super._unmarshalTo(target);

        Note note = (Note)target;

        note.setBank(bank);
        merge(note, "party", party);
        note.setBillNo(billNo);
        merge(note, "billType", billType);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        super._parse(map);
    }


}
