package com.bee32.sem.account.service;

import com.bee32.sem.people.dto.PartyDto;

public interface IAccountService {
    /**
     * 检查某个客户的应收初始化是否已经存在
     * @param party
     * @return
     */
    public boolean isReceivableInitExisted(PartyDto party);

    /**
     * 检查某个供应商的应付初始化是否已经存在
     * @param party
     * @return
     */
    public boolean isPayableInitExisted(PartyDto party);
}
