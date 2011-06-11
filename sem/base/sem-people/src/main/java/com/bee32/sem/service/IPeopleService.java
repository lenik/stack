package com.bee32.sem.service;

import java.util.List;

import com.bee32.sem.people.dto.PersonDto;

public interface IPeopleService {
	List<PersonDto> list(Long userId, Integer start, Integer count);
}
