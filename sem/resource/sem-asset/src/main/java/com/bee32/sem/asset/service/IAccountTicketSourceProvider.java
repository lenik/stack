package com.bee32.sem.asset.service;

import com.bee32.sem.asset.entity.IAccountTicketSource;

/**
 * 凭证源提供者
 *
 * 取得凭证对应的原始单据。
 *
 * @author jack
 *
 */
public interface IAccountTicketSourceProvider {
    public IAccountTicketSource getSource(Long ticketId);
}
