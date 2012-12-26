package com.bee32.sem.make.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.make.dto.MakeStepInputDto;
import com.bee32.sem.make.dto.MakeStepModelDto;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.make.dto.PartItemDto;
import com.bee32.sem.make.dto.QCSpecDto;
import com.bee32.sem.make.dto.QCSpecParameterDto;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.material.entity.Material;

public class PartService
        extends DataService {

    /**
     * 批量更改PartItem中的material为part,即把PartItem从原材料改为半成品
     */
    @Transactional
    public void changePartItemFromMaterialToPart(Part part) {
        Material material = part.getTarget();
        // 保存前查找partItem为原材料的物料和当前part的target是否相同，
        // 如果相同，则把这些partItem中的material设为null,part设为当前part
        // 进行这项操作是为解决不能正向设置bom的问题
        List<PartItem> items = DATA(PartItem.class).list(new Equals("material.id", material.getId()));

        if (items != null && items.size() > 0) {
            for (PartItem item : items) {
                item.setMaterial(null);
                item.setPart(part);
                DATA(PartItem.class).saveOrUpdate(item);
            }
        }
    }

    public PartDto copyBom(PartDto part) {
        PartDto newPart = new PartDto().create();
        newPart.setValid(part.isValid());
        newPart.setValidDateFrom(part.getValidDateFrom());
        newPart.setValidDateTo(part.getValidDateTo());
        newPart.setWage(part.getWage());
        newPart.setOtherFee(part.getOtherFee());
        newPart.setElectricityFee(part.getElectricityFee());
        newPart.setEquipmentCost(part.getEquipmentCost());


        for(PartItemDto item : part.getChildren()) {
            PartItemDto newItem = new PartItemDto().create();
            newItem.setParent(newPart);

            if (DTOs.isNull(item.getMaterial()))
                newItem.setPart(item.getPart());
            else
                newItem.setMaterial(item.getMaterial());

            newItem.setQuantity(item.getQuantity());
            newItem.setValid(item.isValid());
            newItem.setValidDateFrom(item.getValidDateFrom());
            newItem.setValidDateTo(item.getValidDateTo());
            newItem.setLabel(item.getLabel());
            newItem.setDescription(item.getDescription());

            newPart.addChild(newItem);
        }

        for(MakeStepModelDto step : part.getSteps()) {
            MakeStepModelDto newStep = new MakeStepModelDto().create();

            newStep.setStepName(step.getStepName());
            newStep.setOrder(step.getOrder());
            newStep.setOutput(newPart);
            newStep.setQualityControlled(step.isQualityControlled());
            newStep.setConsumeTime(step.getConsumeTime());
            newStep.setOneHourWage(step.getOneHourWage());
            newStep.setOtherFee(step.getOtherFee());
            newStep.setElectricityFee(step.getElectricityFee());
            newStep.setEquipmentCost(step.getEquipmentCost());
            newStep.setValidateTime(step.getValidateTime());
            newStep.setEquipment(step.getEquipment());
            newStep.setOperation(step.getOperation());

            for(MakeStepInputDto input : step.getInputs()) {
                MakeStepInputDto newInput = new MakeStepInputDto().create();
                newInput.setStepModel(newStep);
                newInput.setMaterial(input.getMaterial());
                newInput.setQuantity(input.getQuantity());

                newStep.getInputs().add(newInput);
            }

            QCSpecDto qcSpec = step.getQcSpec();
            if(!DTOs.isNull(qcSpec)) {
                QCSpecDto newQcSpec = new QCSpecDto().create();
                newQcSpec.setText(qcSpec.getText());
                for(QCSpecParameterDto para : qcSpec.getParameters()) {
                    QCSpecParameterDto newPara = new QCSpecParameterDto().create();

                    newPara.setParent(newQcSpec);
                    newPara.setLabel(para.getLabel());
                    newPara.setDescription(para.getDescription());
                    newPara.setValue(para.getValue());
                    newPara.setRequired(para.isRequired());

                    newQcSpec.getParameters().add(newPara);
                }
                newStep.setQcSpec(newQcSpec);
            }

            newPart.addStep(newStep);
        }

        return newPart;
    }

}
