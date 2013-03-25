package com.bee32.sem.makebiz.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.makebiz.dto.MakeProcessDto;
import com.bee32.sem.makebiz.dto.MakeStepDto;
import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.dto.MakeTaskItemDto;
import com.bee32.sem.makebiz.entity.MakeTaskItem;
import com.bee32.sem.makebiz.util.MakebizCriteria;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ProductionSchedule
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceDto project;
    TreeNode root = new DefaultTreeNode("root", null);

    public void buildProjectTree() {
        System.out.println("test");

        // TODO list MakeOrder buy chanceid;
        // TODO list MakeTasks by makeorder;
        // TODO makeprocesses
        List<MakeTaskItem> taskItems = DATA(MakeTaskItem.class).list(MakebizCriteria.test(project.getId()));
        List<MakeTaskItemDto> taskItemDtos = DTOs.marshalList(MakeTaskItemDto.class, MakeTaskItemDto.PROCESSES,
                taskItems);

        Map<Long, TestModel> map = new HashMap<Long, TestModel>();


//        Map<String>

        for (MakeTaskItemDto item : taskItemDtos) {
            MaterialDto material = item.getMaterial();

            TestModel testModel = map.get(material.getId());
            if (testModel == null) {
                testModel = new TestModel(material, item.getQuantity());
            } else {
                BigDecimal tmp = testModel.getQuantity();
                tmp = tmp.add(item.getQuantity());
                testModel.setQuantity(tmp);
            }

            List<MakeProcessDto> processes = item.getProcesses();
            for(MakeProcessDto process : processes){
                List<MakeStepDto> steps = process.getSteps();
                for(MakeStepDto step : steps){
                    MakeStepModelDto model = step.getModel();
                    PartDto output = model.getOutput();
                    if(output.getTarget().getId() == material.getId());

                }
            }

            map.put(material.getId(), testModel);
        }


        for (MakeTaskItemDto taskItem : taskItemDtos) {
            TreeNodeDataModel data = new TreeNodeDataModel(taskItem.getMaterial().getLabel(), taskItem.getQuantity(),
                    BigDecimal.ZERO);
            DefaultTreeNode taskItemNode = new DefaultTreeNode();
            taskItemNode.setParent(root);
            taskItemNode.setData(data);

            List<MakeProcessDto> itemProcesses = taskItem.getProcesses();
            for (MakeProcessDto pro : itemProcesses) {

                PartDto part = pro.getPart();
                part.getTarget();





                List<MakeStepDto> steps = pro.getSteps();
                for (MakeStepDto step : steps) {

                    BigDecimal quantity = BigDecimal.ZERO;
                    List<MakeStepItemDto> items = step.getItems();
                    for (MakeStepItemDto item : items) {
                        quantity = quantity.add(item.getVerifiedQuantity());
                    }

                    MakeStepModelDto model = step.getModel();
                    PartDto output = model.getOutput();
                    MakeStepNameDto stepName = model.getStepName();
                    String label = stepName.getLabel();

                    DefaultTreeNode child = new DefaultTreeNode();
                    TreeNodeDataModel childDataModel = new TreeNodeDataModel(output.getTarget().getLabel(), quantity,
                            taskItem.getQuantity());
                    child.setParent(taskItemNode);
                    child.setData(childDataModel);
                }
            }

        }

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