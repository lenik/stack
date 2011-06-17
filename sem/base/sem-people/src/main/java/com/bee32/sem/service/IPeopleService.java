package com.bee32.sem.service;

import java.util.List;

import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.PersonDto;

public interface IPeopleService {
	List<PersonDto> listPersonByCurrentUser(Integer start, Integer count);
	long listPersonByCurrentUserCount();
    List<OrgDto> listOrgByCurrentUser(Integer start, Integer count);
    long listOrgByCurrentUserCount();
    public List<PersonDto> listPersonByCurrentUser(String keyword);
}
