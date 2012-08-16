package com.bee32.sem.makebiz.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.makebiz.dto.DeliveryNoteDto;
import com.bee32.sem.makebiz.dto.DeliveryNoteItemDto;
import com.bee32.sem.makebiz.dto.DeliveryNoteTakeOutDto;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.entity.DeliveryNote;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeOrderItem;
import com.bee32.sem.makebiz.service.MakebizService;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

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


    public void setApplyMakeOrder(MakeOrderDto makeOrderRef) {
        DeliveryNoteDto deliveryNote = getOpenedObject();
        MakeOrderDto makeOrder = reload(makeOrderRef, MakeOrderDto.NOT_DELIVERIED_ITEMS);

//        for (MakeOrderItemDto item : makeOrder.getItems()) {
//            if (item.getMaterial().isNull()) {
//                uiLogger.error("定单明细没有指定物料.");
//                return;
//            }
//        }

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
                uiLogger.error("所有送货单的明细都必须选择对应的出库${tr.inventory.warehouse}!");
                return;
            }
        }

        if (!deliveryNote.getTakeOuts().isEmpty()) {
            uiLogger.error("送货单已经生成过销售出库单.");
            return;
        }

        MakebizService service = BEAN(MakebizService.class);
        try {
            service.generateTakeOutStockOrders(deliveryNote);
            uiLogger.info("生成成功");
        } catch (Exception e) {
            uiLogger.error("错误", e);
            return;
        }
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
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

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (DeliveryNote _note : uMap.<DeliveryNote> entitySet()) {
            MakeOrder order = _note.getOrder();
            if (!order.getDeliveryNotes().contains(_note)) {
                order.getDeliveryNotes().add(_note);
            }
            Map<MakeOrderItem, BigDecimal> overloadPartsOfDelivery = order.getOverloadPartsOfDelivery();
            if (!overloadPartsOfDelivery.isEmpty()) {
                StringBuilder message = new StringBuilder();
                for (Entry<MakeOrderItem, BigDecimal> entry : overloadPartsOfDelivery.entrySet()) {
                    if(entry.getKey().getMaterial() != null)
                        message.append(entry.getKey().getMaterial().getLabel());
                    message.append("[");
                    message.append(entry.getKey().getExternalProductName());
                    message.append("]");
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

}
