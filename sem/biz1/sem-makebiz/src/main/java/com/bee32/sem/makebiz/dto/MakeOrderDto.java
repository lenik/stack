package com.bee32.sem.makebiz.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeOrderItem;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.world.thing.AbstractItemListDto;

public class MakeOrderDto
        extends AbstractItemListDto<MakeOrder, MakeOrderItem, MakeOrderItemDto>
        implements DecimalConfig, IVerifiableDto {

    private static final long serialVersionUID = 1L;

    public static final int TASKS = 2;
    public static final int DELIVERY_NOTES = 4;
    public static final int PLANS = 8;

    public static final int NOT_ARRANGED_ITEMS = 16 | ITEMS;
    public static final int NOT_DELIVERIED_ITEMS = 32 | ITEMS;

    public static final int ITEM_ATTRIBUTES = 128;

    PartyDto customer;
    String status;
    ChanceDto chance;
    PersonDto salesman;
    boolean valid;

    List<MakeTaskDto> tasks;
    List<MaterialPlanDto> plans;
    List<DeliveryNoteDto> deliveryNotes;
    List<MakeOrderItemDto> notArrangedItems;
    List<MakeOrderItemDto> notDeliveriedItems;

    SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport;

    @Override
    protected Class<? extends MakeOrderItemDto> getItemDtoClass() {
        return MakeOrderItemDto.class;
    }

    @Override
    protected void attach(MakeOrderItemDto item) {
        item.setParent(this);
    }

    @Override
    protected void _copy() {
        super._copy();
        tasks = new ArrayList<MakeTaskDto>();
        plans = new ArrayList<MaterialPlanDto>();
        deliveryNotes = new ArrayList<DeliveryNoteDto>();
        notArrangedItems = new ArrayList<MakeOrderItemDto>();
        notDeliveriedItems = new ArrayList<MakeOrderItemDto>();
    }

    @Override
    protected void _marshal(MakeOrder source) {
        customer = mref(PartyDto.class, source.getCustomer());
        status = source.getStatus();
        chance = mref(ChanceDto.class, source.getChance());
        salesman = mref(PersonDto.class, source.getSalesman());
        valid = source.isValid();

        if (selection.contains(TASKS))
            tasks = marshalList(MakeTaskDto.class, source.getTasks());
        else
            tasks = Collections.emptyList();

        if (selection.contains(PLANS))
            plans = marshalList(MaterialPlanDto.class, source.getPlans());
        else
            plans = Collections.emptyList();

        if (selection.contains(DELIVERY_NOTES))
            deliveryNotes = marshalList(DeliveryNoteDto.class, source.getDeliveryNotes());
        else
            deliveryNotes = Collections.emptyList();

        if (selection.contains(NOT_ARRANGED_ITEMS))
            notArrangedItems = marshalList(MakeOrderItemDto.class, source.getNotArrangedItems());
        else
            notArrangedItems = Collections.emptyList();


        if (selection.contains(NOT_DELIVERIED_ITEMS))
            notDeliveriedItems = marshalList(MakeOrderItemDto.class, source.getNotDeliveriedItems());
        else
            notDeliveriedItems = Collections.emptyList();

        singleVerifierWithNumberSupport = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());
    }

    @Override
    public int getItemSelection() {
        return selection.translate(//
                ITEM_ATTRIBUTES, MakeOrderItemDto.MATERIAL_ATTRIBUTES) ;
    }

    @Override
    protected void _unmarshalTo(MakeOrder target) {
        merge(target, "customer", customer);
        target.setStatus(status);
        merge(target, "chance", chance);
        merge(target, "salesman", salesman);
        target.setValid(valid);

        if (selection.contains(TASKS))
            mergeList(target, "tasks", tasks);

        if (selection.contains(PLANS))
            mergeList(target, "plans", plans);

        if (selection.contains(DELIVERY_NOTES))
            mergeList(target, "deliveryNotes", deliveryNotes);

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
            throw new NullPointerException("customer");
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

    public PersonDto getSalesman() {
        return salesman;
    }

    public void setSalesman(PersonDto salesman) {
        this.salesman = salesman;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getValidString() {
        if (valid) {
            return "??????";
        }
        return "??????";
    }

    public List<MakeTaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<MakeTaskDto> tasks) {
        if (tasks == null)
            throw new NullPointerException("tasks");
        this.tasks = tasks;
    }

    public List<MaterialPlanDto> getPlans() {
        return plans;
    }

    public void setPlans(List<MaterialPlanDto> plans) {
        this.plans = plans;
    }

    public List<DeliveryNoteDto> getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(List<DeliveryNoteDto> deliveryNotes) {
        if (deliveryNotes == null)
            throw new NullPointerException("deliveryNotes");
        this.deliveryNotes = deliveryNotes;
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public List<MakeTaskItemDto> arrangeMakeTask() {
        List<MakeTaskItemDto> taskItems = new ArrayList<MakeTaskItemDto>();

        for (MakeOrderItemDto orderItem : notArrangedItems) {
            MakeTaskItemDto taskItem = new MakeTaskItemDto().create();
                taskItem.setMaterial(orderItem.getMaterial());
                taskItem.setQuantity(orderItem.getQuantity());
                taskItem.setDeadline(orderItem.getDeadline());
                taskItem.setDescription(orderItem.getDescription());

                taskItems.add(taskItem);

        }
        return taskItems;
    }

    /**
     * ????????????????????????????????????
     *
     * @return
     */
    public void arrangeMaterialPlan(MaterialPlanDto materialPlan) {
        List<MaterialPlanItemDto> planItems = new ArrayList<MaterialPlanItemDto>();

        for (MakeOrderItemDto orderItem : notArrangedItems) {
            MaterialPlanItemDto planItem = new MaterialPlanItemDto().create();
            planItem.setMaterialPlan(materialPlan);
            planItem.setMaterial(orderItem.getMaterial());
            planItem.setQuantity(orderItem.getQuantity());

            planItems.add(planItem);
        }

        if (planItems.isEmpty())
            throw new IllegalStateException("???????????????????????????????????????????????????????????????????????????!");

        materialPlan.setOrder(this);
        materialPlan.setItems(planItems);
        if (StringUtils.isEmpty(materialPlan.getLabel()))
            materialPlan.setLabel(this.getLabel());
    }

    /**
     * ???????????????
     *
     * @return
     */
    public List<DeliveryNoteItemDto> arrangeDeliveryNote() {
        List<DeliveryNoteItemDto> deliveryNoteItems = new ArrayList<DeliveryNoteItemDto>();

        for (MakeOrderItemDto orderItem : notDeliveriedItems) {
            DeliveryNoteItemDto deliveryNoteItem = new DeliveryNoteItemDto().create();

            //????????????????????????MakeOrderItem????????????
            deliveryNoteItem.setQuantity(orderItem.getQuantity());

            //???notDeliveriedItems????????????MakeOrderItem??????????????????????????????????????????????????????????????????MakeOrderItem
            MakeOrderItem originalOrderItem = ctx.data.access(MakeOrderItem.class).get(orderItem.getId());
            deliveryNoteItem.setOrderItem(DTOs.marshal(MakeOrderItemDto.class, originalOrderItem));
            deliveryNoteItem.setMaterial(orderItem.getMaterial());
            deliveryNoteItem.setPrice(orderItem.getPrice());

            deliveryNoteItems.add(deliveryNoteItem);
        }
        return deliveryNoteItems;
    }

    @Override
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
    }

}
