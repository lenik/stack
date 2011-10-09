package com.bee32.sem.people.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.Size;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.plover.ox1.xp.EntityExtDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyXP;

public class AbstractPartyDto<E extends Party>
        extends EntityExtDto<E, Integer, PartyXP> {

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
    String memo;

    List<PartyTagnameDto> tags;
    List<ContactDto> contacts;
    List<PartyRecordDto> records;

    public AbstractPartyDto() {
        super();
    }

    public AbstractPartyDto(int selection) {
        super(selection);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.bee32.plover.arch.util.dto.BaseDto_Skel#_marshal(java.lang.Object)
     */
    @Override
    protected void _marshal(E source) {

        name = source.getName();
        fullName = source.getFullName();
        nickName = source.getNickName();

        sidType = mref(PartySidTypeDto.class, source.getSidType());
        sid = source.getSid();

        birthday = source.getBirthday();
        interests = source.getInterests();

        memo = source.getMemo();

        tags = mrefList(PartyTagnameDto.class, source.getTags());

        if (selection.contains(CONTACTS))
            contacts = mrefList(ContactDto.class, source.getContacts());

        if (selection.contains(RECORDS))
            records = mrefList(PartyRecordDto.class, source.getRecords());
    }

    @Override
    protected void _unmarshalTo(E target) {
        target.setName(name);
        target.setFullName(fullName);
        target.setNickName(nickName);

        target.setSid(sid);

        // XXX - Should remove this later.
        String sidTypeId = sidType.getId();
        if (sidTypeId != null && sidTypeId.isEmpty())
            sidTypeId = null;
        sidType.setId(sidTypeId);
        merge(target, "sidType", sidType);

        target.setBirthday(birthday);
        target.setInterests(interests);
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

    @Size(min = 2, max = Party.NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(max = Party.FULLNAME_LENGTH)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Size(max = Party.NICKNAME_LENGTH)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    @Size(max = Party.SID_LENGTH)
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

    @Size(max = Party.INTERESTS_LENGTH)
    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    @Size(max = Party.MEMO_LENGTH)
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

    public List<PartyRecordDto> getRecords() {
        return records;
    }

    public void setRecords(List<PartyRecordDto> records) {
        this.records = records;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
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
