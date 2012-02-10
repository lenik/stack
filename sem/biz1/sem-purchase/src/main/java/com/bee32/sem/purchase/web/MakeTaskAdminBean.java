package com.bee32.sem.purchase.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.MakeTask;

@ForEntity(MakeTask.class)
public class MakeTaskAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MakeTaskAdminBean() {
        super(MakeTask.class, MakeTaskDto.class, 0);
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (MakeTask _task : uMap.<MakeTask> entitySet()) {
            MakeOrder order = _task.getOrder();
            if (!order.getTasks().contains(_task)) {
                order.getTasks().add(_task);
            }
            Map<Part, BigDecimal> overloadParts = order.getOverloadParts();
            if (!overloadParts.isEmpty()) {
                StringBuilder message = new StringBuilder();
                for (Entry<Part, BigDecimal> entry : overloadParts.entrySet()) {
                    message.append(entry.getKey().getTarget().getLabel());
                    message.append(" 超出 ");
                    message.append(entry.getValue());
                    message.append("; ");
                }
                uiLogger.error("生产数量超过订单中的数量: " + message);
                return false;
            }
        }
        return true;
    }

    PartyDto customer;

    @LeftHand(MakeOrder.class)
    public CriteriaElement getCustomerRestriction() {
        return new Equals("customer.id", customer.getId());
    }

    public void chooseOrder() {
        MakeTaskDto makeTask = getOpenedObject();
        MakeOrderDto selectedOrder = makeTask.getOrder();
        selectedOrder = reload(selectedOrder);
        List<MakeTaskItemDto> makeTaskItems = selectedOrder.arrangeMakeTask();
        if (makeTaskItems.isEmpty()) {
            uiLogger.error("此定单上的产品的生产任务已经全部安排完成");
            return;
        }

        makeTask.setItems(makeTaskItems);
        makeTask.setOrder(selectedOrder);
    }

}
