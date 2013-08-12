package com.bee32.sem.makebiz.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.dto.MakeTaskItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeTask;
import com.bee32.sem.makebiz.service.MakebizService;
import com.bee32.sem.makebiz.service.SplitToProcessHolder;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(MakeTask.class)
public class MakeTaskAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<SplitToProcessHolder> splitToProcessHolders;
    SplitToProcessHolder holder;

    public MakeTaskAdminBean() {
        super(MakeTask.class, MakeTaskDto.class, 0);
    }

    public List<SplitToProcessHolder> getSplitToProcessHolders() {
        if (splitToProcessHolders == null) {
            splitToProcessHolders = new ArrayList<SplitToProcessHolder>();
        }
        return splitToProcessHolders;
    }

    public void setSplitToProcessHolders(List<SplitToProcessHolder> splitToProcessHolders) {
        this.splitToProcessHolders = splitToProcessHolders;
    }

    public SplitToProcessHolder getHolder() {
        if (holder == null) {
            holder = new SplitToProcessHolder();
        }
        return holder;
    }

    public void setHolder(SplitToProcessHolder holder) {
        this.holder = holder;
    }

    public void setApplyMakeOrder(MakeOrderDto makeOrderRef) {
        MakeTaskDto makeTask = getOpenedObject();
        MakeOrderDto makeOrder = reload(makeOrderRef, MakeOrderDto.NOT_ARRANGED_ITEMS | MakeOrderDto.ITEM_ATTRIBUTES);

        for (MakeOrderItemDto item : makeOrder.getItems()) {
            if (item.getMaterial().isNull()) {
                uiLogger.error("订单明细没有指定物料.");
                return;
            }
        }

        List<MakeTaskItemDto> taskItems = makeOrder.arrangeMakeTask();
        if (taskItems.isEmpty()) {
            uiLogger.error("此订单上的产品已经全部安排为[生产任务]或[外购物料计划]或[没有BOM而不能按排生产]!");
            return;
        }
        makeTask.setOrder(makeOrderRef);
        makeTask.setItems(taskItems);
        if (StringUtils.isEmpty(makeTask.getLabel()))
            makeTask.setLabel(makeOrder.getLabel());
    }

    public void generateProcess() {

        MakeTaskItemDto item = itemsMBean.getLastSelection();

        MakebizService service = BEAN(MakebizService.class);
        try {
            service.generateProcess(item, splitToProcessHolders);
            item = reload(item);
            uiLogger.info("生成成功，进入\"工世流转单\"功能进行下一步操作.");
        } catch (Exception e) {
            uiLogger.error("生成失败!", e);
        }
    }

    public void setMbeanSelection(MakeTaskItemDto entry) {
        itemsMBean.setLastSelection(entry);
        newHolderList();
    }

    public void newHolderList() {
        splitToProcessHolders = new ArrayList<SplitToProcessHolder>();
    }

    public void newHolder() {
        holder = new SplitToProcessHolder();
    }

    public void removeHolder(SplitToProcessHolder holder) {
        splitToProcessHolders.remove(holder);
    }

    public void addHolder() {
        for (SplitToProcessHolder _holder : splitToProcessHolders) {
            if (holder.getBatchNumber().equals(_holder.getBatchNumber())) {
                uiLogger.error("批号已经存在!");

                return;
            }
        }
        splitToProcessHolders.add(holder);
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<MakeTaskItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", MakeTaskItemDto.class);

    public ListMBean<MakeTaskItemDto> getItemsMBean() {
        return itemsMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        for (MakeTask _task : uMap.<MakeTask> entitySet()) {
            MakeOrder order = _task.getOrder();
            if (!order.getTasks().contains(_task)) {
                order.getTasks().add(_task);
            }
            Map<Material, BigDecimal> overloadParts = order.getOverloadParts();
            if (!overloadParts.isEmpty()) {
                StringBuilder message = new StringBuilder();
                for (Entry<Material, BigDecimal> entry : overloadParts.entrySet()) {
                    message.append(entry.getKey().getLabel());
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

}
