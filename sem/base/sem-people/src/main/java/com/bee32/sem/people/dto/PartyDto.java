package com.bee32.sem.people.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.xp.EntityExtDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyXP;
import com.bee32.sem.people.util.PeopleUtil;

public class PartyDto
        extends EntityExtDto<Party, Integer, PartyXP> {

    private static final long serialVersionUID = 1L;

    public static final int CONTACTS = 1;
    public static final int RECORDS = 2;
    public static final int ROLES = 4;
    public static final int ROLES_CHAIN = 8 | ROLES;

    UserDto owner;

    String name;
    String fullName;
    String nickName;

    PartySidTypeDto sidType;
    String sid;

    Date birthday;
    String interests;

    String bank;
    String bankAccount;

    String memo;

    List<PartyTagnameDto> tags;
    List<ContactDto> contacts;
    List<PartyRecordDto> records;

    public PartyDto() {
        super();
    }

    public PartyDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Party source) {
        name = source.getName();
        fullName = source.getFullName();

        sidType = mref(PartySidTypeDto.class, source.getSidType());
        sid = source.getSid();

        birthday = source.getBirthday();
        interests = source.getInterests();

        bank = source.getBank();
        bankAccount = source.getBankAccount();

        memo = source.getMemo();

        tags = mrefList(PartyTagnameDto.class, source.getTags());

        if (selection.contains(CONTACTS))
            contacts = mrefList(ContactDto.class, source.getContacts());
        else
            contacts = Collections.emptyList();

        if (selection.contains(RECORDS))
            records = mrefList(PartyRecordDto.class, source.getRecords());
        else
            records = Collections.emptyList();
    }

    @Override
    protected void _unmarshalTo(Party target) {
        target.setName(name);
        target.setFullName(fullName);

        target.setSid(sid);

        // XXX - Should remove this later.
        String sidTypeId = sidType.getId();
        if (sidTypeId != null && sidTypeId.isEmpty())
            sidTypeId = null;
        sidType.setId(sidTypeId);
        merge(target, "sidType", sidType);

        target.setBirthday(birthday);
        target.setInterests(interests);

        target.setBank(bank);
        target.setBankAccount(bankAccount);

        target.setMemo(memo);

        mergeSet(target, "tags", tags);

        if (selection.contains(CONTACTS))
            mergeList(target, "contacts", contacts);

        if (selection.contains(RECORDS))
            mergeList(target, "records", records);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getName() {
        return getLabel();
    }

    public void setName(String name) {
        if (name == null)
            throw new NullPointerException("name");
        setLabel(name);
    }

    @NLength(max = Party.FULLNAME_LENGTH)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDisplayName() {
        if (fullName != null && !fullName.isEmpty())
            return fullName;
        if (nickName != null && !nickName.isEmpty())
            return nickName;
        return name;
    }

    public PartySidTypeDto getSidType() {
        return sidType;
    }

    public void setSidType(PartySidTypeDto sidType) {
        if (sidType == null)
            throw new NullPointerException("sidType");
        this.sidType = sidType;
    }

    @NLength(max = Party.SID_LENGTH)
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        if (sid != null && sid.isEmpty())
            sid = null;
        this.sid = sid;
    }

    public String getXid() {
        if (sidType == null || sid == null || sid.isEmpty())
            return null;
        return sidType.getId() + ":" + sid;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Min(15)
    @Max(120)
    public Integer getAge() {
        if (birthday == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(this.birthday);
        int birthYear = cal.get(Calendar.YEAR);

        cal.setTimeInMillis(System.currentTimeMillis());
        int currentYear = cal.get(Calendar.YEAR);

        return currentYear - birthYear;
    }

    public Integer getBirthdayRemains() {
        if (birthday == null)
            return null;

        Calendar cal = Calendar.getInstance();
        int birthDOY = cal.get(Calendar.DAY_OF_YEAR);

        cal.setTimeInMillis(System.currentTimeMillis());
        int currentDOY = cal.get(Calendar.DAY_OF_YEAR);

        int diff = currentDOY - birthDOY;
        if (diff < 0)
            diff += 365;

        return diff;
    }

    @NLength(max = Party.INTERESTS_LENGTH)
    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    @NLength(max = Party.BANK_LENGTH)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @NLength(max = Party.BANK_ACCOUNT_LENGTH)
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @NLength(max = Party.MEMO_LENGTH)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<PartyTagnameDto> getTags() {
        return tags;
    }

    public void setTags(List<PartyTagnameDto> tags) {
        this.tags = tags;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
    }

    public String getContactsString() {
        return PeopleUtil.getCombinedContacts(contacts);
    }

    public List<PartyRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<PartyRecordDto> records) {
        this.records = records;
    }

    @Override
    protected Serializable naturalId() {
        String xid = getXid();
        if (xid == null)
            return new DummyId(this);
        else
            return xid;
    }

}
