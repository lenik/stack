package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeOrderItemDto;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.MakeTask;

@ForEntity(MakeTask.class)
public class MakeTaskAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected MakeTaskDto makeTask = new MakeTaskDto().create();

    protected MakeTaskItemDto makeTaskItem = new MakeTaskItemDto().create();

    private PartDto selectedPart;

    private boolean newItemStatus = false;

    protected List<MakeTaskItemDto> itemsNeedToRemoveWhenModify = new ArrayList<MakeTaskItemDto>();

    private PartyDto customer;
    private MakeOrderDto selectedOrder;
    private PartyDto selectedCustomer;

    public MakeTaskAdminBean() {
        super(MakeTask.class, MakeTaskDto.class, 0);
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

    public MakeOrderDto getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(MakeOrderDto selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public PartyDto getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(PartyDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public List<MakeOrderItemDto> getOrderItems() {
        if (selectedOrder != null) {
            return selectedOrder.getItems();
        }
        return null;
    }

    public void save1() {
        if (makeTask.getId() == null) {
            // 新增
// goNumber = count + 1;
        }

        try {
            MakeTask _task = makeTask.unmarshal();

            MakeOrder order = _task.getOrder();
            if (!order.getTasks().contains(_task)) {
                order.getTasks().add(_task);
            }

            Map<Part, BigDecimal> mapQuantityOverloadParts = order.checkIfTaskQuantityFitOrder();
            Set<Part> setQuantityOverloadParts = mapQuantityOverloadParts.keySet();
            if (setQuantityOverloadParts.size() > 0) {
                StringBuilder infoBuilder = new StringBuilder();
                for (Part part : setQuantityOverloadParts) {
                    infoBuilder.append(part.getTarget().getLabel());
                    infoBuilder.append("超过");
                    infoBuilder.append(mapQuantityOverloadParts.get(part));
                    infoBuilder.append(";");
                }

                uiLogger.info("对应订单的所有生产任务单数量总和超过订单中的数量!" + infoBuilder.toString());
            } else {
                serviceFor(MakeTask.class).save(_task);
                uiLogger.info("保存成功");
// loadMakeTask(goNumber);
// editable = false;
            }
        } catch (Exception e) {
            uiLogger.warn("保存失败,错误信息:" + e.getMessage());
        }
    }

    public void findPart() {
// BomCriteria.findPartUseMaterialName(partPattern));
    }

    public void choosePart() {
        makeTaskItem.setPart(selectedPart);
        selectedPart = null;
    }

    public void findOrder() {
// List<MakeOrder> _orders = serviceFor(MakeOrder.class).list( //
// new Equals("customer.id", customer.getId()), //
// VerifyCriteria.verified(), //
    }

    public void chooseOrder() {
        selectedOrder = reload(selectedOrder);
        List<MakeTaskItemDto> makeTaskItems = selectedOrder.arrangeMakeTask(makeTask);
        if (makeTaskItems != null) {
            makeTask.setItems(makeTaskItems);
            makeTask.setOrder(selectedOrder);
        } else {
            uiLogger.error("此定单上的产品的生产任务已经全部安排完成");
        }
    }

    public void chooseCustomer() {
        customer = selectedCustomer;
    }

}
