package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.DeliveryNoteDto;
import com.bee32.sem.purchase.dto.DeliveryNoteItemDto;
import com.bee32.sem.purchase.dto.DeliveryNoteTakeOutDto;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.DeliveryNote;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.service.PurchaseService;

@ForEntity(DeliveryNote.class)
public class DeliveryNoteAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    int tabIndex;

    public DeliveryNoteAdminBean() {
        super(DeliveryNote.class, DeliveryNoteDto.class, 0);
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (DeliveryNote _note : uMap.<DeliveryNote> entitySet()) {
            MakeOrder order = _note.getOrder();
            if (!order.getDeliveryNotes().contains(_note)) {
                order.getDeliveryNotes().add(_note);
            }
            Map<Part, BigDecimal> overloadPartsOfDelivery = order.getOverloadPartsOfDelivery();
            if (!overloadPartsOfDelivery.isEmpty()) {
                StringBuilder message = new StringBuilder();
                for (Entry<Part, BigDecimal> entry : overloadPartsOfDelivery.entrySet()) {
                    message.append(entry.getKey().getTarget().getLabel());
                    message.append(" 超出 ");
                    message.append(entry.getValue());
                    message.append("; ");
                }
                uiLogger.error("送货数量超过订单中的数量: " + message);
                return false;
            }
        }
        return true;
    }

    public void setApplyMakeOrder(MakeOrderDto makeOrderRef) {
        DeliveryNoteDto deliveryNote = getOpenedObject();
        MakeOrderDto makeOrder = reload(makeOrderRef, MakeOrderDto.NOT_DELIVERIED_ITEMS);
        List<DeliveryNoteItemDto> deliveryNoteItems = makeOrder.arrangeDeliveryNote();
        if (deliveryNoteItems.isEmpty()) {
            uiLogger.error("此订单已经全部安排送货.");
            return;
        }
        deliveryNote.setOrder(makeOrderRef);
        deliveryNote.setCustomer(makeOrderRef.getCustomer());
        deliveryNote.setItems(deliveryNoteItems);
        if (StringUtils.isEmpty(deliveryNote.getLabel()))
            deliveryNote.setLabel(makeOrder.getLabel());
    }

    /**
     * 生成销售出库单
     */
    public void generateTakeOutStockOrders() {
        DeliveryNoteDto deliveryNote = getOpenedObject();
        for (DeliveryNoteItemDto item : deliveryNote.getItems()) {
            if (DTOs.isNull(item.getSourceWarehouse())) {
                uiLogger.error("所有送货单的明细都必须选择对应的出库仓库!");
                return;
            }
        }

        PurchaseService purchaseService = ctx.bean.getBean(PurchaseService.class);
        try {
            purchaseService.generateTakeOutStockOrders(deliveryNote);
            uiLogger.info("生成成功");
        } catch (Exception e) {
            uiLogger.error("错误", e);
            return;
        }
    }

    final ListMBean<DeliveryNoteItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", DeliveryNoteItemDto.class);
    final ListMBean<DeliveryNoteTakeOutDto> takeOutsMBean = ListMBean.fromEL(this, //
            "openedObject.takeOuts", DeliveryNoteTakeOutDto.class);
    final ListMBean<StockOrderItemDto> orderItemsMBean = ListMBean.fromEL(takeOutsMBean, //
            "openedObject.stockOrder.items", StockOrderItemDto.class);

    public ListMBean<DeliveryNoteItemDto> getItemsMBean() {
        return itemsMBean;
    }

    public ListMBean<DeliveryNoteTakeOutDto> getTakeOutsMBean() {
        return takeOutsMBean;
    }

    public ListMBean<StockOrderItemDto> getOrderItemsMBean() {
        return orderItemsMBean;
    }

}
