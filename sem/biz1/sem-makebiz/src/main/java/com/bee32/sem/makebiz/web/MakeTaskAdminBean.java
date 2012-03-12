package com.bee32.sem.makebiz.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.dto.MakeTaskItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

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

    public void setApplyMakeOrder(MakeOrderDto makeOrderRef) {
        MakeTaskDto makeTask = getOpenedObject();
        MakeOrderDto makeOrder = reload(makeOrderRef, MakeOrderDto.NOT_ARRANGED_ITEMS);
        List<MakeTaskItemDto> taskItems = makeOrder.arrangeMakeTask();
        if (taskItems.isEmpty()) {
            uiLogger.error("此订单上的产品的生产任务已经全部安排完成");
            return;
        }
        makeTask.setOrder(makeOrderRef);
        makeTask.setItems(taskItems);
        if (StringUtils.isEmpty(makeTask.getLabel()))
            makeTask.setLabel(makeOrder.getLabel());
    }

    ListMBean<MakeTaskItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", MakeTaskItemDto.class);

    public ListMBean<MakeTaskItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
