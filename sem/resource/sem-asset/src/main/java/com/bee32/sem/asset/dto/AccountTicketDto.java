package com.bee32.sem.asset.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class AccountTicketDto
        extends TxEntityDto<AccountTicket>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;

    List<AccountTicketItemDto> items;
    BudgetRequestDto request;

    SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport;

    @Override
    protected void _marshal(AccountTicket source) {
        if (selection.contains(ITEMS))
            items = marshalList(AccountTicketItemDto.class, source.getItems());
        else
            items = new ArrayList<AccountTicketItemDto>();

        request = mref(BudgetRequestDto.class, source.getRequest());
        singleVerifierWithNumberSupport = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(AccountTicket target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        merge(target, "request", request);
        merge(target, "verifyContext", singleVerifierWithNumberSupport);
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

    public BudgetRequestDto getRequest() {
        return request;
    }

    public void setRequest(BudgetRequestDto request) {
        this.request = request;
    }

    @Override
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
    }
}
