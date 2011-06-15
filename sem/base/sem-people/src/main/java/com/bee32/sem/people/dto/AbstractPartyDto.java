package com.bee32.sem.people.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyXP;

public class AbstractPartyDto<E extends Party>
        extends EntityExtDto<E, Integer, PartyXP> {

    private static final long serialVersionUID = 1L;

    public AbstractPartyDto() {
        super();
    }

    public AbstractPartyDto(E source) {
        super(source);
    }

    public AbstractPartyDto(int selection) {
        super(selection);
    }

    public AbstractPartyDto(int selection, E source) {
        super(selection, source);
    }

    User owner;

    List<PartyTagDto> tags;

    String name;
    String fullName;
    String nickName;

    Date birthday;

    String interests;

    String sid;

    List<PartyRecordDto> logs;

    @Override
    protected void _marshal(E source) {

        owner = source.getOwner();
        tags = DTOs.marshalList(PartyTagDto.class, source.getTags());

        name = source.getName();
        fullName = source.getFullName();
        nickName = source.getNickName();

        birthday = source.getBirthday();

        interests = source.getInterests();

        sid = source.getSid();

        logs = DTOs.marshalList(PartyLogDto.class, source.getLogs());
    }

    @Override
    protected void _unmarshalTo(E target) {
        target.setOwner(owner);
        DTOs.mergeSet(target, "tags", tags);

        target.setName(name);
        target.setFullName(fullName);
        target.setNickName(nickName);

        target.setBirthday(birthday);

        target.setInterests(interests);

        target.setSid(sid);

        DTOs.mergeList(target, "logs", logs);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<PartyTagDto> getTags() {
        return tags;
    }

    public void setTags(List<PartyTagDto> tags) {
        this.tags = tags;
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

    public List<PartyRecordDto> getLogs() {
        return logs;
    }

    public void setLogs(List<PartyRecordDto> logs) {
        this.logs = logs;
    }




}
