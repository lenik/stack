package com.bee32.sem.purchase.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.persistence.Column;
import javax.persistence.Transient;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.base.tx.TxEntityDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

public class MakeOrderDto
        extends TxEntityDto<MakeOrder>
        implements DecimalConfig, IVerifiableDto {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int TASKS = 2;
    public static final int NOT_ARRANGED_ITEMS = 4;

    PartyDto customer;
    String status;
    ChanceDto chance;

    List<MakeOrderItemDto> items;

    List<MakeTaskDto> tasks;

    MCVector total;
    BigDecimal nativeTotal; // Redundant.

    List<MakeOrderItemDto> notArrangedItems;

    SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport;

    @Override
    protected void _marshal(MakeOrder source) {
        customer = mref(PartyDto.class, source.getCustomer());
        status = source.getStatus();
        chance = mref(ChanceDto.class, source.getChance());

        if (selection.contains(ITEMS))
            items = marshalList(MakeOrderItemDto.class, source.getItems());
        else
            items = Collections.emptyList();

        if (selection.contains(TASKS))
            tasks = marshalList(MakeTaskDto.class, source.getTasks());
        else
            tasks = Collections.emptyList();

        if (selection.contains(NOT_ARRANGED_ITEMS))
            notArrangedItems = marshalList(MakeOrderItemDto.class, source.getNotArrangedItems());
        else
            notArrangedItems = Collections.emptyList();

        singleVerifierWithNumberSupport = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(MakeOrder target) {
        merge(target, "customer", customer);
        target.setStatus(status);
        merge(target, "chance", chance);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        if (selection.contains(TASKS))
            mergeList(target, "tasks", tasks);

        merge(target, "verifyContext", singleVerifierWithNumberSupport);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        throw new NotImplementedException();
    }

    public PartyDto getCustomer() {
        return customer;
    }

    public void setCustomer(PartyDto customer) {
        if (customer == null)
            return; // throw new NullPointerException("customer");
        this.customer = customer;
    }

    @NLength(max = MakeOrder.STATUS_LENGTH)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = TextUtil.normalizeSpace(status);
    }

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
    }

    public List<MakeOrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<MakeOrderItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(MakeOrderItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
        invalidateTotal();
    }

    public synchronized void removeItem(MakeOrderItemDto item) {
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

        invalidateTotal();
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    public List<MakeTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<MakeTaskDto> tasks) {
        if (tasks == null)
            throw new NullPointerException("tasks");
        this.tasks = tasks;
    }

    /**
     * 多币种表示的金额。
     */
    @Transient
    public synchronized MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (MakeOrderItemDto item : items) {
                MCValue itemTotal = item.getTotal();
                total.add(itemTotal);
            }
        }
        return total;
    }

    /**
     * 【冗余】获取用本地货币表示的总金额。
     *
     * @throws FxrQueryException
     *             外汇查询异常。
     */
    @Redundant
    @Column(precision = MONEY_TOTAL_PRECISION, scale = MONEY_TOTAL_SCALE)
    public synchronized BigDecimal getNativeTotal()
            throws FxrQueryException {
        if (nativeTotal == null) {
            synchronized (this) {
                if (nativeTotal == null) {
                    BigDecimal sum = new BigDecimal(0L, MONEY_TOTAL_CONTEXT);
                    for (MakeOrderItemDto item : items) {
                        BigDecimal itemNativeTotal = item.getNativePrice();
                        sum = sum.add(itemNativeTotal);
                    }
                    nativeTotal = sum;
                }
            }
        }
        return nativeTotal;
    }

    public void setNativeTotal(BigDecimal nativeTotal) {
        this.nativeTotal = nativeTotal;
    }

    protected void invalidateTotal() {
        total = null;
        nativeTotal = null;
    }

    public List<MakeTaskItemDto> arrangeMakeTask() {
        List<MakeTaskItemDto> taskItems = new ArrayList<MakeTaskItemDto>();

        for (MakeOrderItemDto orderItem : notArrangedItems) {
            MakeTaskItemDto taskItem = new MakeTaskItemDto().create();
            taskItem.setPart(orderItem.getPart());
            taskItem.setQuantity(orderItem.quantity);

            taskItems.add(taskItem);
        }
        return taskItems;
    }

    @Override
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
    }

}
