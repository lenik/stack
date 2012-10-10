package com.bee32.sem.asset.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.asset.service.IAccountTicketSourceProvider;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.world.monetary.FxrQueryException;

public class AccountTicketDto
        extends ProcessEntityDto<AccountTicket>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 0x10000;

    List<AccountTicketItemDto> items;

    SingleVerifierWithNumberSupportDto verifyContext;

    IAccountTicketSource ticketSource;

    @Override
    protected void _copy() {
        items = CopyUtils.copyList(items, this);
    }

    @Override
    protected void _marshal(AccountTicket source) {
        if (selection.contains(ITEMS))
            items = marshalList(AccountTicketItemDto.class, source.getItems());
        else
            items = Collections.emptyList();
        verifyContext = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(AccountTicket target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);
        merge(target, "verifyContext", verifyContext);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public List<AccountTicketItemDto> getItems() {
        return items;
    }

    public void setItems(List<AccountTicketItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(AccountTicketItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(AccountTicketItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        // item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    /**
     * 凭证是否借贷平衡
     *
     * 判断借贷是否相等
     */
    public boolean isDebitCreditEqual()
            throws FxrQueryException {
        BigDecimal debitTotal = BigDecimal.ZERO;
        BigDecimal creditTotal = BigDecimal.ZERO;
        for (AccountTicketItemDto item : items) {
            BigDecimal itemNativeValue = item.getValue().getNativeValue(getCreatedDate());
            if (item.isDebitSide())
                debitTotal = debitTotal.add(itemNativeValue.abs());
            else
                creditTotal = creditTotal.add(itemNativeValue.abs());
        }
        return debitTotal.compareTo(creditTotal)==0;
    }

    @Override
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto verifySupport) {
        this.verifyContext = verifySupport;
    }

    public IAccountTicketSource getTicketSource() {
        if (ticketSource == null) {
            ServiceLoader<IAccountTicketSourceProvider> providers = ServiceLoader.load(IAccountTicketSourceProvider.class);
            for(IAccountTicketSourceProvider provider : providers) {
                IAccountTicketSource source = provider.getSource(this.getId());
                if (source != null) {
                     return ticketSource = source;
                }
            }
        }

        return ticketSource;
    }

    public void setTicketSource(IAccountTicketSource ticketSource) {
        this.ticketSource = ticketSource;
    }
}
