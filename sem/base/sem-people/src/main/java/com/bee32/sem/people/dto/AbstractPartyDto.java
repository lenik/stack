package com.bee32.sem.people.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
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

    UserDto owner;

    String name;
    String fullName;
    String nickName;

    Date birthday;
    String interests;
    String sid;
    String memo;

    List<PartyTagDto> tags;
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
        owner = mref(UserDto.class, 0, source.getOwner());
        tags = marshalList(PartyTagDto.class, source.getTags(), true);

        name = source.getName();
        fullName = source.getFullName();
        nickName = source.getNickName();

        birthday = source.getBirthday();

        interests = source.getInterests();

        memo = source.getMemo();

        sid = source.getSid();

        if (selection.contains(CONTACTS))
            contacts = marshalList(ContactDto.class, source.getContacts(), true);

        if (selection.contains(RECORDS))
            records = marshalList(PartyRecordDto.class, source.getRecords(), true);
    }

    @Override
    protected void _unmarshalTo(E target) {
        merge(target, "owner", owner);

        target.setName(name);
        target.setFullName(fullName);
        target.setNickName(nickName);

        target.setBirthday(birthday);
        target.setInterests(interests);
        target.setSid(sid);
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

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<PartyTagDto> getTags() {
        return tags;
    }

    public void setTags(List<PartyTagDto> tags) {
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

}
