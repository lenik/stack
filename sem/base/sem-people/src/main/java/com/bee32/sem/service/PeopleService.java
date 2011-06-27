package com.bee32.sem.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.dao.PartyDao;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.user.util.SessionLoginInfo;

public class PeopleService
        extends EnterpriseService
        implements IPeopleService {

    @Inject
    private PartyDao partyDao;

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> listPersonByCurrentUser(String keyword) {
        IUserPrincipal currentUser = SessionLoginInfo.getCurrentUser();

        List<Person> personList = partyDao.limitedKeywordList(Person.class, currentUser, keyword);

        return DTOs.marshalList(PersonDto.class, PersonDto.CONTACTS | PersonDto.RECORDS, personList);
    }

}
