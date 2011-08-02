package com.bee32.sem.people.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyXP;

public class AbstractPartyDto<E extends Party>
        extends EntityExtDto<E, Integer, PartyXP> {

    private static final long serialVersionUID = 1L;

    public static final int CONTACTS = 1;
    public static final int RECORDS = 2;
    public static final int ROLES = 4;

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

    @Override
    protected void _marshal(E source) {
        tags = marshalList(PartyTagnameDto.class, source.getTags(), true);

        name = source.getName();
        fullName = source.getFullName();
        nickName = source.getNickName();

        sidType = mref(PartySidTypeDto.class, source.getSidType());
        sid = source.getSid();

        birthday = source.getBirthday();
        interests = source.getInterests();

        memo = source.getMemo();

        if (selection.contains(CONTACTS))
            contacts = marshalList(ContactDto.class, source.getContacts(), true);

        if (selection.contains(RECORDS))
            records = marshalList(PartyRecordDto.class, source.getRecords(), true);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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
        this.sidType = sidType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        if (sid != null && sid.isEmpty())
            sid = null;
        this.sid = sid;

        if (sid == null)
            sidType = null;
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

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

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
