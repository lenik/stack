package com.bee32.sem.purchase.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.criteria.hibernate.Offset;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.dto.MakeTaskItemDto;
import com.bee32.sem.purchase.entity.MakeOrder;
import com.bee32.sem.purchase.entity.MakeTask;
import com.bee32.sems.bom.dto.PartDto;
import com.bee32.sems.bom.entity.Part;
import com.bee32.sems.bom.util.BomCriteria;

@Component
@Scope("view")
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

    public MakeTaskAdminBean() {
        Calendar c = Calendar.getInstance();
        // 取这个月的第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        limitDateFrom = c.getTime();

        // 最这个月的最后一天
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        limitDateTo = c.getTime();

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
        count = serviceFor(MakeOrder.class).count(//
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo));
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
                EntityCriteria.createdBetweenEx(limitDateFrom, limitDateTo), //
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

            serviceFor(MakeTask.class).save(_task);
            uiLogger.info("保存成功");
            loadMakeTask(goNumber);
            editable = false;
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

}
