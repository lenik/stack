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

}
