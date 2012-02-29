package com.bee32.sem.purchase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.orm.entity.IdUtils;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.WantedProductDto;
import com.bee32.sem.chance.dto.WantedProductQuotationDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.inventory.service.StockQueryResult;
import com.bee32.sem.inventory.util.ConsumptionMap;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.purchase.dto.MakeOrderDto;
import com.bee32.sem.purchase.dto.MakeOrderItemDto;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.PurchaseTakeIn;
import com.bee32.sem.world.monetary.MutableMCValue;

public class PurchaseService
        extends DataService {

    @Inject
    IStockQuery stockQuery;

    /**
     *
     * 根据传入的物料计划，结合当前库存和物料的安全库存，计算所需要采购的量
     *
     * 注：如果同一物料分布在多个仓库，此处目前数量合并计算
     */
    public List<PurchaseRequestItemDto> calcMaterialRequirement(List<MaterialPlanDto> plans) {
        // 计算需求
        ConsumptionMap cmap = new ConsumptionMap();
        for (MaterialPlanDto materialPlan : plans)
            materialPlan.declareConsumption(cmap);

        // 用当前库存余量（不考虑锁定）去抵消需求
        Collection<MaterialDto> materials = cmap.getDtoIndex().values();
        if (materials.isEmpty())
            return Collections.emptyList();

        List<Long> materialIds = IdUtils.getDtoIdList(materials);
        StockQueryOptions queryOptions = new StockQueryOptions(new Date(), true);
        StockQueryResult queryResult = stockQuery.getPhysicalStock(materialIds, queryOptions);
        queryResult.declareSupply(cmap);

        // 抵消后的数量为采购需求数量，再考虑安全库存：
        ConsumptionMap safetyMap = new ConsumptionMap();
        List<MaterialWarehouseOption> _mwopts = ctx.data.access(MaterialWarehouseOption.class).list(
                new InCollection("material.id", materialIds));
        for (MaterialWarehouseOption _mwopt : _mwopts)
            safetyMap.consume(_mwopt.getMaterial(), _mwopt.getSafetyStock());

        // 生成列表
        List<PurchaseRequestItemDto> requestItems = new ArrayList<>();
        for (MaterialDto material : materials) {
            BigDecimal needQuantity = cmap.getConsumption(material);

            // 采购 至少要满足安全库存
            BigDecimal safetyStock = safetyMap.getConsumption(material);
            needQuantity = needQuantity.max(safetyStock);

            // 计算需要采购的数量
            BigDecimal requestQuantity = needQuantity;
            if (requestQuantity != null) {
                PurchaseRequestItemDto requestItem = new PurchaseRequestItemDto().create();
                requestItem.setMaterial(material);

                // 从物料计划计算采购请求量时，把计划采购量首先设置为和计算量相同
                requestItem.setRequiredQuantity(requestQuantity);
                requestItem.setQuantity(requestQuantity);

                requestItems.add(requestItem);
            }
        }
        return requestItems;
    }

    /**
     * 根据采购请求和所选择的仓库id,自动生成采购入库单
     */
    @Transactional
    public void generateTakeInStockOrders(PurchaseRequestDto purchaseRequest)
            throws NoPurchaseAdviceException, AdviceHaveNotVerifiedException, TakeInStockOrderAlreadyGeneratedException {

        // 先用map保存用户输入的warehouseId，因为后面的reload(purchaseRequest)会导致这些warehouseIds丢失
        Map<Long, Integer> itemWarehouseMap = new HashMap<Long, Integer>();
        for (PurchaseRequestItemDto requestItem : purchaseRequest.getItems()) {
            itemWarehouseMap.put(requestItem.getId(), requestItem.getDestWarehouse().getId());
        }

        purchaseRequest = reload(purchaseRequest);

        // 检测purchaseReqeust是否已经生成过采购入库单
        if (!purchaseRequest.getTakeIns().isEmpty()) {
            throw new TakeInStockOrderAlreadyGeneratedException();
        }

        // 检测有没有询价和审核采购建议
        for (PurchaseRequestItemDto requestItem : purchaseRequest.getItems()) {
            if (requestItem.getAcceptedInquiry() == null || requestItem.getAcceptedInquiry().getId() == null) {
                throw new NoPurchaseAdviceException();
            }

        }

        Map<Object, List<PurchaseRequestItemDto>> splitMap //
        = new HashMap<Object, List<PurchaseRequestItemDto>>();

        // 按仓库和供应商区分要生成的采购入库单
        for (PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            OrgDto preferredSupplier = item.getAcceptedInquiry().getSupplier();
            // 以warehouseId和对应供应商的id组合成 x&y 的形式
            IdComposite key = new IdComposite(item.getDestWarehouse().getId(), preferredSupplier.getId());

            if (!splitMap.containsKey(key)) {
                List<PurchaseRequestItemDto> newItemList = new ArrayList<PurchaseRequestItemDto>();
                splitMap.put(key, newItemList);
            }

            List<PurchaseRequestItemDto> itemList = splitMap.get(key);
            itemList.add(item);
        }

        // 3.按仓库生成subject为takeIn的StockOrder->StockOrderItem*
        for (List<PurchaseRequestItemDto> itemList : splitMap.values()) {
            OrgDto preferredSupplier = itemList.get(0).getAcceptedInquiry().getSupplier();

            StockOrder _takeInOrder = new StockOrder();
            _takeInOrder.setSubject(StockOrderSubject.TAKE_IN);
            _takeInOrder.setLabel("从采购请求生成的入库单");
            _takeInOrder.setOrg((Org) preferredSupplier.unmarshal());

            for (PurchaseRequestItemDto item : itemList) {
                StockOrderItem _item = new StockOrderItem();

                _item.setParent(_takeInOrder);
                _item.setMaterial(item.getMaterial().unmarshal());
                _item.setQuantity(item.getQuantity());
                _item.setPrice(item.getAcceptedInquiry().getPrice());
                _item.setDescription("由采购请求生成的入库明细项目");

                _takeInOrder.addItem(_item);

                // 保存以上StockOrder*
                StockWarehouse warehouse = ctx.data.access(StockWarehouse.class).lazyLoad(
                        itemWarehouseMap.get(item.getId()));
                _takeInOrder.setWarehouse(warehouse);
                ctx.data.access(StockOrder.class).saveOrUpdate(_takeInOrder);

                // 填充并保存OrderHolder,以在PurchaseRequest和StockOrder之间建立关联
                PurchaseTakeIn _takeIn = new PurchaseTakeIn();
                _takeIn.setPurchaseRequest(purchaseRequest.unmarshal());
                _takeIn.setStockOrder(_takeInOrder); // 重新marshal，以便dto中包含id

                ctx.data.access(PurchaseTakeIn.class).saveOrUpdate(_takeIn);
            }
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

            Part _part = ctx.data.access(Part.class).getFirst(
                    BomCriteria.findPartByMaterial(product.getDecidedMaterial().getId()));
            item.setPart(DTOs.mref(PartDto.class, _part));

            WantedProductQuotationDto lastQuotation = product.getLastQuotation();
            if (lastQuotation != null) {
                item.setPrice(lastQuotation.getPrice().multiply(lastQuotation.getDiscountRate()).toMutable());
                item.setQuantity(lastQuotation.getQuantity());
            } else {
                item.setPrice(new MutableMCValue(0));
                item.setQuantity(BigDecimal.ZERO);
            }
            items.add(item);
        }
        makeOrder.setItems(items);
    }

}
