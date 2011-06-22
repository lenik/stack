package com.bee32.sem.chance.service;

import java.util.Date;
import java.util.List;

import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.people.entity.Party;

public interface IChanceService {

// int getPartyCount();
// List<Party> limitedPartyList(int displayStart, int displayLength);

// int limitedPartyKeywordListCount(String keyword);
// List<Party> limitedPartyKeywordList(String keyword, int displayStart, int displayLength);

    /**
     * return all ChanceAction number corresponding to the current User
     * 取得当前用户对应的所有行动记录个数
     */
    int getChanceActionCount();

    /**
     * pagination show the ChanceAction corresponding to the current User
     * 分页显示当前用户对应的行动记录
     */
    List<ChanceAction> limitedChanceActionList(int first, int pageSize);

    /**
     * get the number of records that within the (Date)begin and (Date)end time range, corresponding
     * to the current User
     * 取得时间段内,当前用户对应的行动记录个数
     */
    int limitedChanceActionRangeListCount(Date begin, Date end);

    /**
     * pagination show the ChanceAction records corresponding to the current User within time
     * range (begin to end)
     * 分页显示时间段内当前用户的行动记录
     */
    List<ChanceAction> limitedChanceActionRangeList(Date begin, Date end, int first, int pageSize);

    List<ChanceAction> dateRangeActionList(Date begin, Date end);

    /**
     * search records,unnecessary to pagination
     */
    List<Chance> keywordChanceList(String keyword);

    /**
     * search records,unnecessary to pagination
     */
    List<Party> keywordPartyList(String keyword);

    /**
     * 当前用户对应的机会个数
     */
    int getChanceCount();
    /**
     * pagenation chance records corresponding to the current user
     */
    List<Chance> limitedChanceList(int first, int pageSize);

    int searchedChanceCount();

    List<Chance> limitedSearchChanceList(String keyword, int first, int pageSize);

}
