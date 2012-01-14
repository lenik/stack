package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeOrderItemDto;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.MakeTask;

@ForEntity(MakeTask.class)
public class MakeTaskAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private Date limitDateFrom;
    private Date limitDateTo;

    private boolean editable = false;

    private int goNumber;
    private int count;

    protected MakeTaskDto makeTask = new MakeTaskDto().create();

    protected MakeTaskItemDto makeTaskItem = new MakeTaskItemDto().create();

    private String partPattern;
    private List<PartDto> parts;
    private PartDto selectedPart;

    private boolean newItemStatus = false;

    protected List<MakeTaskItemDto> itemsNeedToRemoveWhenModify = new ArrayList<MakeTaskItemDto>();

    private PartyDto customer;

    private Date limitDateFromForOrder;
    private Date limitDateToForOrder;

    private List<MakeOrderDto> orders;
    private MakeOrderDto selectedOrder;

    private String customerPattern;
    private List<PartyDto> customers;
    private PartyDto selectedCustomer;


    public MakeTaskAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();
        limitDateFromForOrder = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();
        limitDateToForOrder = c.getTime();

        goNumber = 1;
        loadMakeTask(goNumber);
    }

    public Date getLimitDateFrom() {
        return limitDateFrom;
    }

    public void setLimitDateFrom(Date limitDateFrom) {
        this.limitDateFrom = limitDateFrom;
    }

    public Date getLimitDateTo() {
        return limitDateTo;
    }

    public void setLimitDateTo(Date limitDateTo) {
        this.limitDateTo = limitDateTo;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getGoNumber() {
        return goNumber;
    }

    public void setGoNumber(int goNumber) {
        this.goNumber = goNumber;
    }


    public int getCount() {
        count = serviceFor(MakeTask.class).count(//
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
        return count;
    }

    public MakeTaskDto getMakeTask() {
        return makeTask;
    }

    public void setMakeTask(MakeTaskDto makeTask) {
        this.makeTask = makeTask;
    }

    public String getCreator() {
        if (makeTask == null)
            return "";
        else
            return makeTask.getOwnerDisplayName();
    }

    public List<MakeTaskItemDto> getItems() {
        if (makeTask == null)
            return null;
        return makeTask.getItems();
    }

    public MakeTaskItemDto getMakeTaskItem() {
        return makeTaskItem;
    }

    public void setMakeTaskItem(MakeTaskItemDto makeTaskItem) {
        this.makeTaskItem = makeTaskItem;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPartPattern() {
        return partPattern;
    }

    public void setPartPattern(String partPattern) {
        this.partPattern = partPattern;
    }

    public List<PartDto> getParts() {
        return parts;
    }

    public void setParts(List<PartDto> parts) {
        this.parts = parts;
    }

    public PartDto getSelectedPart() {
        return selectedPart;
    }

    public void setSelectedPart(PartDto selectedPart) {
        this.selectedPart = selectedPart;
    }

    public boolean isNewItemStatus() {
        return newItemStatus;
    }

    public void setNewItemStatus(boolean newItemStatus) {
        this.newItemStatus = newItemStatus;
    }

    public PartyDto getCustomer() {
        return customer;
    }

    public void setCustomer(PartyDto customer) {
        this.customer = customer;
    }

    public Date getLimitDateFromForOrder() {
        return limitDateFromForOrder;
    }

    public void setLimitDateFromForOrder(Date limitDateFromForOrder) {
        this.limitDateFromForOrder = limitDateFromForOrder;
    }

    public Date getLimitDateToForOrder() {
        return limitDateToForOrder;
    }

    public void setLimitDateToForOrder(Date limitDateToForOrder) {
        this.limitDateToForOrder = limitDateToForOrder;
    }

    public List<MakeOrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<MakeOrderDto> orders) {
        this.orders = orders;
    }

    public MakeOrderDto getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(MakeOrderDto selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public String getCustomerPattern() {
        return customerPattern;
    }

    public void setCustomerPattern(String customerPattern) {
        this.customerPattern = customerPattern;
    }

    public List<PartyDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<PartyDto> customers) {
        this.customers = customers;
    }

    public PartyDto getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(PartyDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public List<MakeOrderItemDto> getOrderItems() {
        if(selectedOrder != null) {
            return selectedOrder.getItems();
        }
        return null;
    }








    public void limit() {
        loadMakeTask(goNumber);
    }

    private void loadMakeTask(int position) {
        //刷新总记录数
        getCount();

        goNumber = position;

        if(position < 1) {
            goNumber = 1;
            position = 1;
        }
        if(goNumber > count) {
            goNumber = count;
            position = count;
        }

        makeTask = new MakeTaskDto().create();    //如果限定条件内没有找到makeTask,则创建一个

        MakeTask firstTask = serviceFor(MakeTask.class).getFirst( //
                new Offset(position - 1), //
                CommonCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
                Order.asc("id"));

        if (firstTask != null)
            makeTask = DTOs.marshal(MakeTaskDto.class, MakeTaskDto.ITEMS, firstTask);

    }

    public void new_() {
        makeTask = new MakeTaskDto().create();
        editable = true;
    }

    public void modify() {
        if(makeTask.getId() == null) {
            uiLogger.warn("当前没有对应的单据");
            return;
        }

        itemsNeedToRemoveWhenModify.clear();

        editable = true;
    }

    public void save() {
        if(makeTask.getId() == null) {
            //新增
            goNumber = count + 1;
        }

        try {
            MakeTask _task = makeTask.unmarshal();
            for(MakeTaskItemDto item : itemsNeedToRemoveWhenModify) {
                _task.removeItem(item.unmarshal());
            }

            MakeOrder order = _task.getOrder();
            if (!order.getTasks().contains(_task)) {
                order.getTasks().add(_task);
            }

            Map<Part, BigDecimal> mapQuantityOverloadParts = order.checkIfTaskQuantityFitOrder();
            Set<Part> setQuantityOverloadParts = mapQuantityOverloadParts.keySet();
            if(setQuantityOverloadParts.size() > 0){
                StringBuilder infoBuilder = new StringBuilder();
                for(Part part : setQuantityOverloadParts) {
                    infoBuilder.append(part.getTarget().getLabel());
                    infoBuilder.append("超过");
                    infoBuilder.append(mapQuantityOverloadParts.get(part));
                    infoBuilder.append(";");
                }

                uiLogger.info("对应订单的所有生产任务单数量总和超过订单中的数量!" + infoBuilder.toString());
            } else {
                serviceFor(MakeTask.class).save(_task);
                uiLogger.info("保存成功");
                loadMakeTask(goNumber);
                editable = false;
            }
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

    public void cancel() {
        loadMakeTask(goNumber);
        editable = false;
    }

    public void first() {
        goNumber = 1;
        loadMakeTask(goNumber);
    }

    public void previous() {
        goNumber--;
        if (goNumber < 1)
            goNumber = 1;
        loadMakeTask(goNumber);
    }

    public void go() {
        if (goNumber < 1) {
            goNumber = 1;
        } else if (goNumber > count) {
            goNumber = count;
        }
        loadMakeTask(goNumber);
    }

    public void next() {
        goNumber++;

        if (goNumber > count)
            goNumber = count;
        loadMakeTask(goNumber);
    }

    public void last() {
        goNumber = count + 1;
        loadMakeTask(goNumber);
    }

    public void newItem() {
        makeTaskItem = new MakeTaskItemDto().create();
        makeTaskItem.setTask(makeTask);

        newItemStatus = true;
    }

    public void modifyItem() {
        newItemStatus = false;
    }


    public void saveItem() {
        makeTaskItem.setTask(makeTask);
        if (newItemStatus) {
            makeTask.addItem(makeTaskItem);
        }
    }

    public void delete() {
        try {
            serviceFor(MakeTask.class).delete(makeTask.unmarshal());
            uiLogger.info("删除成功!");
            loadMakeTask(goNumber);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    public void deleteItem() {
        makeTask.removeItem(makeTaskItem);

        if (makeTaskItem.getId() != null) {
            itemsNeedToRemoveWhenModify.add(makeTaskItem);
        }
    }

    public void findPart() {
        if (partPattern != null && !partPattern.isEmpty()) {

            List<Part> _parts = serviceFor(Part.class).list(BomCriteria.findPartUseMaterialName(partPattern));

            parts = DTOs.mrefList(PartDto.class, _parts);
        }
    }

    public void choosePart() {
        makeTaskItem.setPart(selectedPart);

        selectedPart = null;
    }

    public void findOrder() {
        List<MakeOrder> _orders = serviceFor(MakeOrder.class).list( //
                new Equals("customer.id", customer.getId()), //
                new Equals("verifyEvalState", VerifyEvalState.VERIFIED.getValue()), //
                CommonCriteria.createdBetweenEx(limitDateFromForOrder, limitDateToForOrder));

        orders = DTOs.marshalList(MakeOrderDto.class, _orders);
    }

    public void chooseOrder() {
        selectedOrder = reload(selectedOrder);
        List<MakeTaskItemDto> makeTaskItems = selectedOrder.arrangeMakeTask(makeTask);
        if(makeTaskItems != null) {
            makeTask.setItems(makeTaskItems);
            makeTask.setOrder(selectedOrder);
        } else {
            uiLogger.error("此定单上的产品的生产任务已经全部安排完成");
        }
    }

    public void findCustomer() {
        if (customerPattern != null && !customerPattern.isEmpty()) {

            List<Party> _customers = serviceFor(Party.class).list( //
                    PeopleCriteria.customers(), //
                    new Or( //
                            new Like("name", "%" + customerPattern + "%"), //
                            new Like("fullName", "%" + customerPattern + "%")));

            customers = DTOs.marshalList(PartyDto.class, _customers);
        }
    }

    public void chooseCustomer() {
        customer = selectedCustomer;
    }
}
