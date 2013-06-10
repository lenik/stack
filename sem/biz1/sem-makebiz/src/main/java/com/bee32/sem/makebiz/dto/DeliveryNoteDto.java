package com.bee32.sem.makebiz.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.asset.dto.AccountTicketDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

public class DeliveryNoteDto extends ProcessEntityDto<DeliveryNote> implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int ITEMS = 1;
    public static final int TAKEOUT_ITEMS = 2;

    MakeOrderDto order;
    PartyDto customer;
    Date arrivalDate;
    List<DeliveryNoteItemDto> items;
    DeliveryNoteTakeOutDto takeOut;

    AccountTicketDto ticket;

    MCVector total;
    BigDecimal nativeTotal;

    UserDto approveUser;
    boolean approved;
    String approveMessage = "";

    @Override
    protected void _marshal(DeliveryNote source) {
        order = mref(MakeOrderDto.class, source.getOrder());
        customer = mref(PartyDto.class, source.getCustomer());

        arrivalDate = source.getArrivalDate();

        if (selection.contains(ITEMS))
            items = marshalList(DeliveryNoteItemDto.class, source.getItems());
        else
            items = new ArrayList<DeliveryNoteItemDto>();

        takeOut = marshal(DeliveryNoteTakeOutDto.class,
                selection.translate(TAKEOUT_ITEMS, DeliveryNoteTakeOutDto.ORDER_ITEMS), source.getTakeOut());

        ticket = mref(AccountTicketDto.class, source.getTicket());
        approveUser = mref(UserDto.class, source.getApproveUser());
        approved = source.isApproved();
        approveMessage = source.getApproveMessage();
    }

    @Override
    protected void _unmarshalTo(DeliveryNote target) {
        merge(target, "order", order);
        merge(target, "customer", customer);

        target.setArrivalDate(arrivalDate);

        if (selection.contains(ITEMS))
            mergeList(target, "items", items);

        merge(target, "takeOut", takeOut);
        merge(target, "ticket", ticket);
        merge(target, "approveUser", approveUser);
        target.setApproved(approved);
        target.setApproveMessage(approveMessage);
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        throw new NotImplementedException();
    }

    public MakeOrderDto getOrder() {
        return order;
    }

    public void setOrder(MakeOrderDto order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    @NotNull
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public List<DeliveryNoteItemDto> getItems() {
        return items;
    }

    public void setItems(List<DeliveryNoteItemDto> items) {
        if (items == null)
            throw new NullPointerException("items");
        for (DeliveryNoteItemDto item : items)
            item.setParent(this);
        this.items = items;
    }

    public synchronized void addItem(DeliveryNoteItemDto item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        item.setParent(this);
        items.add(item);
    }

    public synchronized void removeItem(DeliveryNoteItemDto item) {
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

    public MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (DeliveryNoteItemDto item : items) {
                MCValue itemTotal = item.getTotal();
                total.add(itemTotal);
            }
        }
        return total;
    }

    public void setTotal(MCVector total) {
        if (total == null)
            throw new NullPointerException("total");
        this.total = total;
    }

    public BigDecimal getNativeTotal() throws FxrQueryException {
        if (nativeTotal == null) {
            synchronized (this) {
                if (nativeTotal == null) {
                    BigDecimal sum = new BigDecimal(0L, MONEY_TOTAL_CONTEXT);
                    for (DeliveryNoteItemDto item : items) {

                        BigDecimal itemNativeTotal = item.getNativePrice();
                        double d = itemNativeTotal.doubleValue();
                        double q = item.getQuantity().doubleValue();
                        double itemTotal = d * q;
                        BigDecimal bit = new BigDecimal(itemTotal, MONEY_TOTAL_CONTEXT);
                        sum = sum.add(bit);
                        // sum = sum.add(itemNativeTotal);
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

    public void invalidateTotal() {
        total = null;
        nativeTotal = null;
    }

    public PartyDto getCustomer() {
        return customer;
    }

    public void setCustomer(PartyDto customer) {
        this.customer = customer;
    }

    public DeliveryNoteTakeOutDto getTakeOut() {
        return takeOut;
    }

    public void setTakeOut(DeliveryNoteTakeOutDto takeOut) {
        this.takeOut = takeOut;
    }

    public List<DeliveryNoteTakeOutDto> getTakeOuts() {
        if (takeOut.isNull())
            return new ArrayList<DeliveryNoteTakeOutDto>();
        return Arrays.asList(takeOut);
    }

    public AccountTicketDto getTicket() {
        return ticket;
    }

    public void setTicket(AccountTicketDto ticket) {
        this.ticket = ticket;
    }
    public UserDto getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(UserDto approveUser) {
        this.approveUser = approveUser;
    }

    public void approveByMe() {
        UserDto loginUser = SessionUser.getInstance().getUser();
        setApproveUser(loginUser);
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @NLength(max = Chance.APPROVE_MESSAGE_LENGTH)
    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        this.approveMessage = TextUtil.normalizeSpace(approveMessage);
    }

}
