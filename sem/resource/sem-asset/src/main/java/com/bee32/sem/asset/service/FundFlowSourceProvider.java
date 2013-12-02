package com.bee32.sem.asset.service;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.asset.util.AssetCriteria;

/**
 * 资金流原始单据提供者
 */
public class FundFlowSourceProvider
        extends DataService
        implements IAccountTicketSourceProvider {

    /**
     * 取得原始单据
     *
     * 根据凭证取得对应的资金流原始单据。
     *
     * @param ticket
     * @return
     */
    @Override
    public IAccountTicketSource getSource(long ticketId) {

        FundFlow fundFlow = DATA(FundFlow.class).getUnique(//
                AssetCriteria.correspondingTicket(ticketId));

        return (IAccountTicketSource) fundFlow;
    }

}
