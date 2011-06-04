package com.bee32.sem.people.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.sem.people.Gender;
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

    String name;
    String fullName;
    String nickName;

    Gender sex;

    Date birthday;

    String interests;

    String censusRegister;
    String sidType;
    String sid;

    List<PartyLogDto> logs;

    @Override
    protected void _marshal(E source) {
        name = source.getName();
        fullName = source.getFullName();
        nickName = source.getNickName();

        birthday = source.getBirthday();

        sid = source.getSid();
    }

    @Override
    protected void _unmarshalTo(E target) {
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

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
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

    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    public String getSidType() {
        return sidType;
    }

    public void setSidType(String sidType) {
        this.sidType = sidType;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public List<PartyLogDto> getLogs() {
        return logs;
    }

    public void setLogs(List<PartyLogDto> logs) {
        this.logs = logs;
    }

}
