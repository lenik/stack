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
     * 根据送货单和所选择的仓库,自动生成销售出库单
     */
    public void generateTakeOutStockOrders(DeliveryNoteDto deliveryNote) {
        // 检测deliveryNote是否已经生成过销售出库单
        if (!deliveryNote.getTakeOuts().isEmpty())
            throw new IllegalStateException("送货单已经生成过销售出库单.");

        // 先用map保存用户输入的warehouseId，因为后面的reload(purchaseRequest)会导致这些warehouseIds丢失
        Map<Long, Integer> itemWarehouseMap = new HashMap<Long, Integer>();
        for (DeliveryNoteItemDto item : deliveryNote.getItems()) {
            itemWarehouseMap.put(item.getId(), item.getSourceWarehouse().getId());
        }

        Map<Object, List<DeliveryNoteItemDto>> splitMap = new HashMap<>();

        // 按仓库和客户区分要生成的销售出库单
        for (DeliveryNoteItemDto item : deliveryNote.getItems()) {
            PartyDto customer = deliveryNote.getCustomer();
            // 以warehouseId和对应客户的id组合成 x&y 的形式
            IdComposite key = new IdComposite(item.getSourceWarehouse().getId(), customer.getId());

            if (!splitMap.containsKey(key)) {
                List<DeliveryNoteItemDto> newItemList = new ArrayList<DeliveryNoteItemDto>();
                splitMap.put(key, newItemList);
            }

            List<DeliveryNoteItemDto> itemList = splitMap.get(key);
            itemList.add(item);
        }

        // 3.按仓库生成subject为takeOut的StockOrder->StockOrderItem*
        for (List<DeliveryNoteItemDto> itemList : splitMap.values()) {
            PartyDto customer = itemList.get(0).getParent().getCustomer();

            StockWarehouse warehouse = DATA(StockWarehouse.class).lazyLoad(
                    itemWarehouseMap.get(itemList.get(0).getId()));

            StockOrderDto takeOutOrder = new StockOrderDto().create();
            takeOutOrder.setOrg(customer);
            takeOutOrder.setWarehouse(DTOs.marshal(StockWarehouseDto.class, warehouse));
            takeOutOrder.setSubject(StockOrderSubject.TAKE_OUT);
            takeOutOrder.setLabel("从送货单[" + deliveryNote.getLabel() + "]生成的出库单");

            for (DeliveryNoteItemDto item : itemList) {
                StockOrderItemDto orderItem = new StockOrderItemDto().create();
                orderItem.setParent(takeOutOrder);

                orderItem.setMaterial(item.getMaterial());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setPrice(item.getPrice());
                orderItem.setDescription("由送货单[" + deliveryNote.getLabel() + "]生成的出库明细项目");

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
     * 由生产任务单，根据bom表生成物料计划
     */
    public void calcMaterialPlanFromBom(MaterialPlanDto plan, MakeTaskDto task) {
        MakeTaskDto makeTask = reload(task, MakeTaskDto.ITEMS | MakeTaskDto.PLANS);

        if (!makeTask.getPlans().isEmpty())
            throw new IllegalStateException("此生产任务单已经有对应的物料计划!");

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
                planItem.setQuantity(taskItem.getQuantity()); // 产品数量乘以原物料数量
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
                    planItem.setQuantity(quantity.multiply(ent.getValue())); // 产品数量乘以原物料数量
                    plan.addItem(planItem);
                }
            }
        }
        // 清空物料锁定。
        plan.getPlanOrders().clear();
    }

    /**
     * 根据生产任务明细条目，生成工艺流转单
     */
    @Transactional
    public void generateProcess(MakeTaskItemDto taskItem, List<SplitToProcessHolder> holders) {
	if(taskItem == null || DTOs.isNull(taskItem))
		throw new NullPointerException("生产任务明细为空");

	taskItem = ctx.data.reload(taskItem, MakeTaskItemDto.PROCESSES);

        MakeTaskItem _taskItem = DATA(MakeTaskItem.class).getOrFail(taskItem.getId());

        if (_taskItem.getProcesses() != null && _taskItem.getProcesses().size() > 0) {
            throw new IllegalUsageException("此生产任务已经有工艺流转单.");
        }

        Part _part = DATA(Part.class).getFirst(
                BomCriteria.findPartByMaterial(_taskItem.getMaterial().getId()));
        if (_part == null) {
            new FacesUILogger(false).error(
                    "物料" + _taskItem.getMaterial().getDisplayName() + "没有BOM,不能生产!",
                    _taskItem.getMaterial());
        } else {
		BigDecimal count = new BigDecimal(0);
		for(SplitToProcessHolder holder : holders) {
		    count = count.add(holder.getQuantity());
		}
		if(count.compareTo(_taskItem.getQuantity()) != 0) {
		    throw new RuntimeException("工艺单数量合计和生产任务的数量不相等!");
		}


		for(SplitToProcessHolder holder : holders) {
                MakeProcess process = new MakeProcess();
                process.setTaskItemEven(_taskItem);
                process.setPart(_part);
                process.setBatchNumber(holder.getBatchNumber());
                process.setQuantity(holder.getQuantity());

                //根据bom表和工艺，生成所有的MakeStep
                claimTree(process, _part, process.getQuantity());
                DATA(MakeProcess.class).save(process);

            }
        }
    }

    private void claimTree(MakeProcess process, Part part, BigDecimal quantity) {
	for (PartItem partItem : part.getChildren()) {
		if (partItem.getPart() != null) {
			//说明子部件是半成品
			claimTree(process, partItem.getPart(), partItem.getQuantity().multiply(process.getQuantity() ));
		}
	}

	for (MakeStepModel stepModel : part.getSteps()) {
		MakeStep step = new MakeStep();

		step.setParent(process);
		step.setModel(stepModel);
		step.setPlanQuantity(quantity);

		if(stepModel.isQualityControlled()) {
		    //如果需要质量控制
		    QCResult qcResult = new QCResult();
		    step.setQcResult(qcResult);
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
