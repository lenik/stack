package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.DeliveryNoteDto;
import com.bee32.sem.purchase.dto.DeliveryNoteItemDto;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.entity.DeliveryNote;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.MakeTask;

@ForEntity(DeliveryNote.class)
public class DeliveryNoteAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public DeliveryNoteAdminBean() {
        super(DeliveryNote.class, DeliveryNoteDto.class, 0);
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
            uiLogger.error("此订单上已经全部安排送货.");
            return;
        }
        deliveryNote.setOrder(makeOrderRef);
        deliveryNote.setItems(deliveryNoteItems);
        if (StringUtils.isEmpty(deliveryNote.getLabel()))
            deliveryNote.setLabel(makeOrder.getLabel());
    }

    ListMBean<DeliveryNoteItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", DeliveryNoteItemDto.class);

    public ListMBean<DeliveryNoteItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
