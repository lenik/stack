package com.bee32.sem.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.plover.arch.EnterpriseService;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.people.dao.PartyDao;
import com.bee32.sem.people.dao.PersonContactDao;
import com.bee32.sem.people.dto.PersonContactDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.user.util.SessionLoginInfo;

public class PeopleService extends EnterpriseService implements IPeopleService {

	@Inject
	private PartyDao partyDao;

	@Inject
	private ContactDao contactDao;

	@Override
	@Transactional(readOnly = true)
	public List<PersonDto> listPersonByCurrentUser(Integer start, Integer count) {
		HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = (IUserPrincipal) SessionLoginInfo.getCurrentUser(session);

        List<Person> personList = partyDao.limitedList(Person.class, currUser, start, count);

        return DTOs.marshalList(PersonDto.class, personList);
	}



	@Override
	@Transactional(readOnly = true)
	public long listPersonByCurrentUserCount() {
		HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = (IUserPrincipal) SessionLoginInfo.getCurrentUser(session);

        return partyDao.limitedListCount(Person.class, currUser);
	}



    @Override
    public List<PersonContactDto> listContactByPerson(PersonDto personDto) {
        contactDao.listByPerson(Person person);
        return null;
    }


}
