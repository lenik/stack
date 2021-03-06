package com.bee32.sem.makebiz.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.free.IllegalUsageException;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.WantedProductAttributeDto;
import com.bee32.sem.chance.dto.WantedProductDto;
import com.bee32.sem.chance.dto.WantedProductQuotationDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.make.entity.MakeStepModel;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.entity.PartItem;
import com.bee32.sem.make.entity.QCResult;
import com.bee32.sem.make.entity.QCResultParameter;
import com.bee32.sem.make.entity.QCSpecParameter;
import com.bee32.sem.make.util.BomCriteria;
import com.bee32.sem.makebiz.dto.DeliveryNoteDto;
import com.bee32.sem.makebiz.dto.DeliveryNoteItemDto;
import com.bee32.sem.makebiz.dto.DeliveryNoteTakeOutDto;
import com.bee32.sem.makebiz.dto.MakeOrderDto;
import com.bee32.sem.makebiz.dto.MakeOrderItemDto;
import com.bee32.sem.makebiz.dto.MakeTaskDto;
import com.bee32.sem.makebiz.dto.MakeTaskItemDto;
import com.bee32.sem.makebiz.dto.MaterialPlanDto;
import com.bee32.sem.makebiz.dto.MaterialPlanItemDto;
import com.bee32.sem.makebiz.entity.MakeProcess;
import com.bee32.sem.makebiz.entity.MakeStep;
import com.bee32.sem.makebiz.entity.MakeTaskItem;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.material.dto.StockWarehouseDto;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.world.monetary.MutableMCValue;

public class MakebizService
        extends DataService {

    /**
     * ????????????????????????????????????,???????????????????????????
     */
    public void generateTakeOutStockOrders(DeliveryNoteDto deliveryNote) {
        // ??????deliveryNote????????????????????????????????????
        if (!deliveryNote.getTakeOuts().isEmpty())
            throw new IllegalStateException("???????????????????????????????????????.");

        // ??????map?????????????????????warehouseId??????????????????reload(purchaseRequest)???????????????warehouseIds??????
        Map<Long, Integer> itemWarehouseMap = new HashMap<Long, Integer>();
        for (DeliveryNoteItemDto item : deliveryNote.getItems()) {
            itemWarehouseMap.put(item.getId(), item.getSourceWarehouse().getId());
        }

        Map<Object, List<DeliveryNoteItemDto>> splitMap = new HashMap<>();

        // ???????????????????????????????????????????????????
        for (DeliveryNoteItemDto item : deliveryNote.getItems()) {
            PartyDto customer = deliveryNote.getCustomer();
            // ???warehouseId??????????????????id????????? x&y ?????????
            IdComposite key = new IdComposite(item.getSourceWarehouse().getId(), customer.getId());

            if (!splitMap.containsKey(key)) {
                List<DeliveryNoteItemDto> newItemList = new ArrayList<DeliveryNoteItemDto>();
                splitMap.put(key, newItemList);
            }

            List<DeliveryNoteItemDto> itemList = splitMap.get(key);
            itemList.add(item);
        }

        // 3.???????????????subject???takeOut???StockOrder->StockOrderItem*
        for (List<DeliveryNoteItemDto> itemList : splitMap.values()) {
            PartyDto customer = itemList.get(0).getParent().getCustomer();

            StockWarehouse warehouse = DATA(StockWarehouse.class).lazyLoad(
                    itemWarehouseMap.get(itemList.get(0).getId()));

            StockOrderDto takeOutOrder = new StockOrderDto().create();
            takeOutOrder.setOrg(customer);
            takeOutOrder.setWarehouse(DTOs.marshal(StockWarehouseDto.class, warehouse));
            takeOutOrder.setSubject(StockOrderSubject.TAKE_OUT);
            takeOutOrder.setLabel("????????????[" + deliveryNote.getLabel() + "]??????????????????");

            for (DeliveryNoteItemDto item : itemList) {
                StockOrderItemDto orderItem = new StockOrderItemDto().create();
                orderItem.setParent(takeOutOrder);

                orderItem.setMaterial(item.getMaterial());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getPrice());
                orderItem.setDescription("????????????[" + deliveryNote.getLabel() + "]???????????????????????????");

                takeOutOrder.addItem(orderItem);
            }

            DeliveryNoteTakeOutDto takeOut = new DeliveryNoteTakeOutDto().create();
            takeOutOrder.setJob(takeOut);
            takeOut.setDeliveryNote(deliveryNote);
            takeOut.setStockOrder(takeOutOrder);
            deliveryNote.setTakeOut(takeOut);
        }

    }

    public void chanceApplyToMakeOrder(ChanceDto chance, MakeOrderDto makeOrder) {
        chance = reload(chance, ChanceDto.PRODUCTS_MORE);
        makeOrder.setChance(chance);

        List<MakeOrderItemDto> items = new ArrayList<MakeOrderItemDto>();
        for (WantedProductDto product : chance.getProducts()) {
            MakeOrderItemDto item = new MakeOrderItemDto();
            item.setParent(makeOrder);

            item.setExternalProductName(product.getLabel());
            item.setExternalModelSpec(product.getModelSpec());

            StringBuilder descBuilder = new StringBuilder();
            for(WantedProductAttributeDto attr : product.getAttributes()) {
		descBuilder.append(attr.getName());
		descBuilder.append(":");
		descBuilder.append(attr.getValue());
		descBuilder.append(";");
            }
            item.setDescription(descBuilder.toString());
            item.setMaterial(product.getDecidedMaterial());

            WantedProductQuotationDto lastQuotation = product.getLastQuotation();
            if (lastQuotation != null) {
                item.setPrice(lastQuotation.getPrice().multiply(lastQuotation.getDiscountReal()).toMutable());
                item.setQuantity(lastQuotation.getQuantity());
            } else {
                item.setPrice(new MutableMCValue(0));
                item.setQuantity(BigDecimal.ZERO);
            }
            items.add(item);
        }
        makeOrder.setItems(items);
    }

    /**
     * ???????????????????????????bom?????????????????????
     */
    public void calcMaterialPlanFromBom(MaterialPlanDto plan, MakeTaskDto task) {
        MakeTaskDto makeTask = reload(task, MakeTaskDto.ITEMS | MakeTaskDto.PLANS);

        if (!makeTask.getPlans().isEmpty())
            throw new IllegalStateException("????????????????????????????????????????????????!");

        plan.setTask(task);
        if (StringUtils.isEmpty(plan.getLabel()))
            plan.setLabel(makeTask.getLabel());
        plan.getItems().clear();

        for (MakeTaskItemDto taskItem : makeTask.getItems()) {
            Part _part = DATA(Part.class).getFirst(
                    BomCriteria.findPartByMaterial(taskItem.getMaterial().getId()));
            if (_part == null) {
                MaterialPlanItemDto planItem = new MaterialPlanItemDto().create();

                planItem.setId(-plan.getItems().size() - 1L, true);
                planItem.setMaterialPlan(plan);
                planItem.setMaterial(taskItem.getMaterial());
                planItem.setQuantity(taskItem.getQuantity()); // ?????????????????????????????????
                plan.addItem(planItem);
            } else {
                PartDto part = DTOs.marshal(PartDto.class, PartDto.MATERIAL_CONSUMPTION , _part);
                BigDecimal quantity = taskItem.getQuantity();

                Map<MaterialDto, BigDecimal> allMaterial = part.getMaterialConsumption().dtoMap();
                for (Entry<MaterialDto, BigDecimal> ent : allMaterial.entrySet()) {
                    MaterialPlanItemDto planItem = new MaterialPlanItemDto().create();
                    planItem.setId(-plan.getItems().size() - 1L, true);
                    planItem.setMaterialPlan(plan);
                    planItem.setMaterial(ent.getKey());
                    planItem.setQuantity(quantity.multiply(ent.getValue())); // ?????????????????????????????????
                    plan.addItem(planItem);
                }
            }
        }
        // ?????????????????????
        plan.getPlanOrders().clear();
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    @Transactional
    public void generateProcess(MakeTaskItemDto taskItem, List<SplitToProcessHolder> holders) {
	if(taskItem == null || DTOs.isNull(taskItem))
		throw new NullPointerException("????????????????????????");

	taskItem = ctx.data.reload(taskItem, MakeTaskItemDto.PROCESSES);

        MakeTaskItem _taskItem = DATA(MakeTaskItem.class).getOrFail(taskItem.getId());

        if (_taskItem.getProcesses() != null && _taskItem.getProcesses().size() > 0) {
            throw new IllegalUsageException("???????????????????????????????????????.");
        }

        Part _part = DATA(Part.class).getFirst(
                BomCriteria.findPartByMaterial(_taskItem.getMaterial().getId()));
        if (_part == null) {
            new FacesUILogger(false).error(
                    "??????" + _taskItem.getMaterial().getDisplayName() + "??????BOM,????????????!",
                    _taskItem.getMaterial());
        } else {
		BigDecimal count = new BigDecimal(0);
		for(SplitToProcessHolder holder : holders) {
		    count = count.add(holder.getQuantity());
		}
		if(count.compareTo(_taskItem.getQuantity()) != 0) {
		    throw new RuntimeException("??????????????????????????????????????????????????????!");
		}


		for(SplitToProcessHolder holder : holders) {
                MakeProcess process = new MakeProcess();
                process.setLabel(_taskItem.getTask().getLabel());
                process.setDescription(_taskItem.getTask().getDescription());
                process.setTaskItemEven(_taskItem);
                process.setPart(_part);
                process.setBatchNumber(holder.getBatchNumber());
                process.setQuantity(holder.getQuantity());

                //??????bom??????????????????????????????MakeStep
                claimTree(process, _part, process.getQuantity());
                DATA(MakeProcess.class).save(process);

            }
        }
    }

    private void claimTree(MakeProcess process, Part part, BigDecimal quantity) {
	for (PartItem partItem : part.getChildren()) {
		if (partItem.getPart() != null) {
			//???????????????????????????
			claimTree(process, partItem.getPart(), partItem.getQuantity().multiply(process.getQuantity() ));
		}
	}

	for (MakeStepModel stepModel : part.getSteps()) {
		MakeStep step = new MakeStep();

		step.setParent(process);
		step.setModel(stepModel);
            step.setLabel(process.getLabel());
		//step.setPlanQuantity(quantity);

		if(stepModel.isQualityControlled()) {
		    //????????????????????????
		    QCResult qcResult = new QCResult();
		    //step.setQcResult(qcResult);
		    for(QCSpecParameter specPara : stepModel.getQcSpec().getParameters()) {
		        QCResultParameter resultPara = new QCResultParameter();
		        resultPara.setParent(qcResult);
		        resultPara.setKey(specPara);

		        qcResult.getParameters().add(resultPara);
		    }
		}

		process.addStep(step);
	}
    }

}
