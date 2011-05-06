package com.bee32.sem.people.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.plover.orm.util.IUnmarshalContext;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonXP;

public class AbstractPersonDto<E extends Person>
        extends EntityExtDto<E, Integer, PersonXP> {

    private static final long serialVersionUID = 1L;

    public AbstractPersonDto() {
        super();
    }

    public AbstractPersonDto(E source) {
        super(source);
    }

    public AbstractPersonDto(int selection) {
        super(selection);
    }

    public AbstractPersonDto(int selection, E source) {
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

    List<ContactDto> contacts;
    List<PersonLogDto> logs;

    @Override
    protected void _marshal(E source) {
        name = source.getName();
        fullName = source.getFullName();
        nickName = source.getNickName();

        sex = source.getSex();

        birthday = source.getBirthday();

        interests = source.getInterests();

        censusRegister = source.getCensusRegister();
        sidType = source.getSidType();
        sid = source.getSid();

        contacts = marshalList(ContactDto.class, source.getContacts());
    }

    @Override
    protected void _unmarshalTo(IUnmarshalContext context, E target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

}
