package com.bee32.sem.makebiz.service;

import com.bee32.plover.arch.DataService;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.asset.service.IAccountTicketSourceProvider;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.makebiz.util.MakebizCriteria;

/**
 * 送货单原始单据提供者
 */
public class DeliveryNoteSourceProvider
        extends DataService
        implements IAccountTicketSourceProvider {

    /**
     * 取得原始单据
     *
     * 根据凭证取得对应的送货单原始单据。
     *
     * @param ticket
     * @return
     */
    @Override
    public IAccountTicketSource getSource(long ticketId) {

        DeliveryNote deliveryNote = DATA(DeliveryNote.class).getUnique(//
                MakebizCriteria.correspondingTicket(ticketId));

        return deliveryNote;
    }

}
