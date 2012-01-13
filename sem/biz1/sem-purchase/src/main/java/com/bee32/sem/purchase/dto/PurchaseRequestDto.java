package com.bee32.sem.purchase.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.purchase.entity.PurchaseRequest;

public class PurchaseRequestDto
        extends MomentIntervalDto<PurchaseRequest>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int PLANS = 2;

    List<PurchaseRequestItemDto> items;
    List<MaterialPlanDto> plans;

    List<OrderHolderDto> orderHolders;

    SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport;

    @Override
    protected void _marshal(PurchaseRequest source) {
        if (selection.contains(ITEMS))
            items = marshalList(PurchaseRequestItemDto.class, source.getItems());
        else
            items = new ArrayList<PurchaseRequestItemDto>();

        if (selection.contains(PLANS))
            plans = marshalList(MaterialPlanDto.class, source.getPlans());
        else
            plans = new ArrayList<MaterialPlanDto>();

        orderHolders = marshalList(OrderHolderDto.class, source.getOrderHolders());

        singleVerifierWithNumberSupport = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(PurchaseRequest target) {
        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        if (selection.contains(PLANS))
            mergeList(target, "plans", plans);

        mergeList(target, "orderHolders", orderHolders);

        merge(target, "verifyContext", singleVerifierWithNumberSupport);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public List<PurchaseRequestItemDto> getItems() {
        return items;
    }

    public void setItems(List<PurchaseRequestItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(PurchaseRequestItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(PurchaseRequestItemDto item) {
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


    public List<MaterialPlanDto> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlanDto> plans) {
        this.plans = plans;
    }

    public List<OrderHolderDto> getOrderHolders() {
        return orderHolders;
    }

    public void setOrderHolders(List<OrderHolderDto> orderHolders) {
        this.orderHolders = orderHolders;
    }

    public synchronized void addOrderHolder(OrderHolderDto orderHolder) {
        if (orderHolder == null)
            throw new NullPointerException("orderHolder");

        orderHolders.add(orderHolder);
    }

    public synchronized void removeOrderHolder(OrderHolderDto orderHolder) {
        if (orderHolder == null)
            throw new NullPointerException("orderHolder");

        int index = orderHolders.indexOf(orderHolder);
        if (index == -1)
            return /* false */;

        orderHolders.remove(index);
        // orderHolder.detach();
    }

    @Override
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
    }
}
