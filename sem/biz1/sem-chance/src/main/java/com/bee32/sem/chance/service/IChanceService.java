package com.bee32.sem.chance.service;

import java.util.List;

import com.bee32.sem.people.entity.Party;

public interface IChanceService {

    int getAllPartyCount();

    int getPartyCountByKeyword(String keyword);

    List<Party> findLimitedParties(int displayStart, int displayLength);

    List<Party> findPartyByKeywords(String keyword, int displayStart, int displayLength);
}
