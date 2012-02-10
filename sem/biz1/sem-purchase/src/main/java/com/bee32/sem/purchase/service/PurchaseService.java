package com.bee32.sem.purchase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.DataService;
import com.bee32.plover.arch.util.IdComposite;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialWarehouseOption;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.inventory.service.IStockQuery;
import com.bee32.sem.inventory.service.StockQueryOptions;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.dto.StockPlanOrderDto;
import com.bee32.sem.purchase.dto.PurchaseAdviceDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.PurchaseTakeIn;

public class PurchaseService
        extends DataService {

    @Inject
    IStockQuery stockQuery;

    /**
     * 根据传入的物料计划，结合当前库存和物料的安全库存，计算所需要采购的量
     *
     * 注：如果同一物料分布在多个仓库，此处目前数量合并计算
     */
    public List<PurchaseRequestItemDto> calcMaterialRequirement(PurchaseRequestDto purchaseRequest,
            List<MaterialPlanDto> plans) {
        Map<MaterialDto, BigDecimal> need = new HashMap<MaterialDto, BigDecimal>();
        Map<MaterialDto, BigDecimal> alreadyHave = new HashMap<MaterialDto, BigDecimal>();

        for (MaterialPlanDto p : plans) {

            for (MaterialPlanItemDto item : p.getItems()) {
                BigDecimal needQuantity = need.get(item.getMaterial());
                if (needQuantity != null) {
                    need.put(item.getMaterial(), item.getQuantity().add(needQuantity));
                } else {
                    need.put(item.getMaterial(), item.getQuantity());
                }
            }

            List<StockPlanOrderDto> orders = p.getPlanOrders();
            if (orders != null) {
                for (StockPlanOrderDto order : orders) {
                    for (StockOrderItemDto item : order.getItems()) {
                        BigDecimal alreadyHaveQuantity = alreadyHave.get(item.getMaterial());
                        if (alreadyHaveQuantity != null) {
                            alreadyHave.put(item.getMaterial(), item.getQuantity().add(alreadyHaveQuantity));
                        } else {
                            alreadyHave.put(item.getMaterial(), item.getQuantity());
                        }
                    }

                }
            }
        }

        List<PurchaseRequestItemDto> requestItems = new ArrayList<PurchaseRequestItemDto>();

        Set<MaterialDto> materialSet = need.keySet();
        for (MaterialDto material : materialSet) {
            // BigDecimal alreadyHaveQuantity = alreadyHave.get(m);
            BigDecimal needQuantity = need.get(material);

            // 取得物料的安全库存
            Material _m = asFor(Material.class).get(material.getId());
            BigDecimal safetyQuantity = new BigDecimal(0);
            for (MaterialWarehouseOption option : _m.getOptions())
                safetyQuantity = safetyQuantity.add(option.getSafetyStock());

            StockQueryOptions opts = new StockQueryOptions(new Date(), true);
            opts.setCBatch(null, true);
            opts.setLocation(null, true);
            opts.setWarehouse(null, true);

            StockOrder list = stockQuery.getActualSummary(Arrays.asList(material.getId()), opts);
            BigDecimal actualQuantity = new BigDecimal(0);
            for (StockOrderItem item : list.getItems())
                actualQuantity = actualQuantity.add(item.getQuantity());

            // 计算需要采购的数量
            BigDecimal requestQuantity = new BigDecimal(0);
            if (needQuantity.compareTo(actualQuantity.subtract(safetyQuantity)) <= 0) {
                // 不用采购
                requestQuantity = null;
            } else {
                requestQuantity = needQuantity.subtract(actualQuantity.subtract(safetyQuantity));
            }

            if (requestQuantity != null) {
                PurchaseRequestItemDto requestItem = new PurchaseRequestItemDto().create();
                requestItem.setMaterial(material);
                requestItem.setPurchaseRequest(purchaseRequest);
                requestItem.setQuantity(requestQuantity);
                requestItem.setPlanQuantity(requestQuantity); // 从物料计划计算采购请求量时，把计划采购量首先设置为和计算量相同

                requestItems.add(requestItem);
            }
        }

        if (requestItems.size() > 0)
            return requestItems;
        return null;
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
            itemWarehouseMap.put(requestItem.getId(), requestItem.getWarehouseId());
        }

        purchaseRequest = reload(purchaseRequest);

        // 检测purchaseReqeust是否已经生成过采购入库单
        if (!purchaseRequest.getTakeIns().isEmpty()) {
            throw new TakeInStockOrderAlreadyGeneratedException();
        }

        // 检测有没有询价和审核采购建议
        for (PurchaseRequestItemDto requestItem : purchaseRequest.getItems()) {
            PurchaseAdviceDto advice = requestItem.getPurchaseAdvice();
            if (advice == null || advice.getId() == null) {
                throw new NoPurchaseAdviceException();
            }

            // TODO verify check
            // if(!advice.isVerified()) {
            // throw new AdviceHaveNotVerifiedException();
            // }
        }

        Map<Object, List<PurchaseRequestItemDto>> splitMap //
        = new HashMap<Object, List<PurchaseRequestItemDto>>();

        // 按仓库和供应商区分要生成的采购入库单
        for (PurchaseRequestItemDto item : purchaseRequest.getItems()) {
            OrgDto preferredSupplier = item.getPurchaseAdvice().getPreferredInquiry().getSupplier();
            // 以warehouseId和对应供应商的id组合成 x&y 的形式
            IdComposite key = new IdComposite(item.getWarehouseId(), preferredSupplier.getId());

            if (!splitMap.containsKey(key)) {
                List<PurchaseRequestItemDto> newItemList = new ArrayList<PurchaseRequestItemDto>();
                splitMap.put(key, newItemList);
            }

            List<PurchaseRequestItemDto> itemList = splitMap.get(key);
            itemList.add(item);
        }

        // 3.按仓库生成subject为takeIn的StockOrder->StockOrderItem*
        for (List<PurchaseRequestItemDto> itemList : splitMap.values()) {
            OrgDto preferredSupplier = itemList.get(0).getPurchaseAdvice().getPreferredInquiry().getSupplier();

            StockOrder _takeInOrder = new StockOrder();
            _takeInOrder.setSubject(StockOrderSubject.TAKE_IN);
            _takeInOrder.setLabel("从采购请求生成的入库单");
            _takeInOrder.setOrg((Org) preferredSupplier.unmarshal());

            for (PurchaseRequestItemDto item : itemList) {
                StockOrderItem _item = new StockOrderItem();

                _item.setParent(_takeInOrder);
                _item.setMaterial(item.getMaterial().unmarshal());
                _item.setQuantity(item.getPlanQuantity());
                _item.setPrice(item.getPurchaseAdvice().getPreferredInquiry().getPrice());
                _item.setDescription("由采购请求生成的入库明细项目");

                _takeInOrder.addItem(_item);

                // 保存以上StockOrder*
                StockWarehouse warehouse = asFor(StockWarehouse.class).lazyLoad(itemWarehouseMap.get(item.getId()));
                _takeInOrder.setWarehouse(warehouse);
                asFor(StockOrder.class).saveOrUpdate(_takeInOrder);

                // 填充并保存OrderHolder,以在PurchaseRequest和StockOrder之间建立关联
                PurchaseTakeIn _takeIn = new PurchaseTakeIn();
                _takeIn.setPurchaseRequest(purchaseRequest.unmarshal());
                _takeIn.setStockOrder(_takeInOrder); // 重新marshal，以便dto中包含id

                asFor(PurchaseTakeIn.class).saveOrUpdate(_takeIn);
            }
        }
    }

}
