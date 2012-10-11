package com.bee32.sem.inventory.service;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.asset.service.IAccountTicketSourceProvider;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.util.StockCriteria;

/**
 * 仓库原始单据提供者
 *
 * @author jack
 *
 */
public class StockOrderSourceProvider
    extends DataService
    implements IAccountTicketSourceProvider {

    /**
     * 取得原始单据
     *
     * 根据凭证取得对应的仓库原始单据。
     *
     * @param ticket
     * @return
     */
    @Override
    public IAccountTicketSource getSource(Long ticketId) {

        StockOrder stockOrder = DATA(StockOrder.class).getUnique(StockCriteria.correspondingTicket(ticketId));

        return (IAccountTicketSource)stockOrder;
    }

}
