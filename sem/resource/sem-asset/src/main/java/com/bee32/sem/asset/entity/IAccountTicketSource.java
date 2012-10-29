package com.bee32.sem.asset.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bee32.sem.world.monetary.FxrQueryException;

/**
 * 凭证源
 *
 * 作为原始单据的接口。使不同类型的原始单据可以统一处理。
 *
 * @author jack
 *
 */
public interface IAccountTicketSource {
    public AccountTicket getTicket();
    public void setTicket(AccountTicket ticket);

    public Serializable getTicketSrcId();
    public String getTicketSrcType();
    public String getTicketSrcLabel();
    public BigDecimal getTicketSrcValue() throws FxrQueryException;

}
