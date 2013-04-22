package com.bee32.sem.makebiz.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.make.entity.MakeStepModel;
import com.bee32.sem.make.entity.MakeStepName;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.entity.MakeOrder;
import com.bee32.sem.makebiz.entity.MakeProcess;
import com.bee32.sem.makebiz.entity.MakeStep;
import com.bee32.sem.makebiz.entity.MakeStepItem;
import com.bee32.sem.makebiz.entity.MakeTaskItem;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.material.entity.Material;

public class ProductionSchedule
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceDto project;
    TreeNode root = new DefaultTreeNode("root", null);

    public void buildProjectTree() {

        System.out.println("test");

        // TODO list MakeOrder buy chanceid;
        // TODO list MakeTasks by makeorder;
        // TODO makeprocesses
        Map<Long, MaterialDto> cache = new HashMap<Long, MaterialDto>();
        Long chanceId = project.getId();
        if (null == chanceId)
            throw new NullPointerException("target project id is null");
        MakeOrder order = DATA(MakeOrder.class).getUnique(MakebizCriteria.getMakeOrderByChanceId(project.getId()));
        if (null == order)
            uiLogger.warn("没有该销售机会对应的生产订单");
        else {
            MakeOrderDto orderDto = DTOs.marshal(MakeOrderDto.class, MakeOrderDto.ITEMS, order);
            List<MakeOrderItemDto> items = orderDto.getItems();

            for (MakeOrderItemDto item : items) {
                MaterialDto material = item.getMaterial();
                cache.put(material.getId(), material);
                String dataModel = material.getLabel() + "(" + item.getQuantity().intValue() + ")";
                TreeNode node = new DefaultTreeNode(dataModel, root);
            }
        }

        List<MakeTaskItem> taskItems = DATA(MakeTaskItem.class).list(
                MakebizCriteria.listMakeTaskItemsByChance(project.getId()));
        for (MakeTaskItem taskItem : taskItems) {
            Material material = taskItem.getMaterial();

            List<MakeProcess> processes = taskItem.getProcesses();

            for (MakeProcess process : processes) {
                Part part = process.getPart();
                List<MakeStep> steps = process.getSteps();
                for (MakeStep step : steps) {
                    MakeStepModel stepModel = step.getModel();
                    MakeStepName stepName = stepModel.getStepName();

                    List<MakeStepItem> stepItems = step.getItems();
                    int stepQuantity = 0;
                    for (MakeStepItem stepItem : stepItems) {
                        int actualQuantity = stepItem.getActualQuantity().intValue();
                        stepQuantity = stepQuantity + actualQuantity;
                    }
                }
            }

        }

// List<MakeTaskItemDto> taskItemDtos = DTOs.marshalList(MakeTaskItemDto.class,
// MakeTaskItemDto.PROCESSES,
// taskItems);
//
// Map<Long, TestModel> map = new HashMap<Long, TestModel>();

// Map<String>

// for (MakeTaskItemDto item : taskItemDtos) {
// MaterialDto material = item.getMaterial();
//
// TestModel testModel = map.get(material.getId());
// if (testModel == null) {
// testModel = new TestModel(material, item.getQuantity());
// } else {
// BigDecimal tmp = testModel.getQuantity();
// tmp = tmp.add(item.getQuantity());
// testModel.setQuantity(tmp);
// }
//
// List<MakeProcessDto> processes = item.getProcesses();
// for(MakeProcessDto process : processes){
// List<MakeStepDto> steps = process.getSteps();
// for(MakeStepDto step : steps){
// MakeStepModelDto model = step.getModel();
// PartDto output = model.getOutput();
// if(output.getTarget().getId() == material.getId());
//
// }
// }
//
// map.put(material.getId(), testModel);
// }
//
//
// for (MakeTaskItemDto taskItem : taskItemDtos) {
// TreeNodeDataModel data = new TreeNodeDataModel(taskItem.getMaterial().getLabel(),
// taskItem.getQuantity(),
// BigDecimal.ZERO);
// DefaultTreeNode taskItemNode = new DefaultTreeNode();
// taskItemNode.setParent(root);
// taskItemNode.setData(data);
//
// List<MakeProcessDto> itemProcesses = taskItem.getProcesses();
// for (MakeProcessDto pro : itemProcesses) {
//
// PartDto part = pro.getPart();
// part.getTarget();
//
//
//
//
//
// List<MakeStepDto> steps = pro.getSteps();
// for (MakeStepDto step : steps) {
//
// BigDecimal quantity = BigDecimal.ZERO;
// List<MakeStepItemDto> items = step.getItems();
// for (MakeStepItemDto item : items) {
// quantity = quantity.add(item.getVerifiedQuantity());
// }
//
// MakeStepModelDto model = step.getModel();
// PartDto output = model.getOutput();
// MakeStepNameDto stepName = model.getStepName();
// String label = stepName.getLabel();
//
// DefaultTreeNode child = new DefaultTreeNode();
// TreeNodeDataModel childDataModel = new TreeNodeDataModel(output.getTarget().getLabel(), quantity,
// taskItem.getQuantity());
// child.setParent(taskItemNode);
// child.setData(childDataModel);
// }
// }
//
// }

    }

    public ChanceDto getProject() {
        return project;
    }

    public void setProject(ChanceDto project) {
        this.project = project;
    }

    public TreeNode getRoot() {
        return root;
    }
}