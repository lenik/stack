package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockItemUnion;
import com.bee32.sem.inventory.tx.dto.StockTakingDto;
import com.bee32.sem.inventory.tx.entity.StockTaking;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.sandbox.UIHelper;

@ForEntity(StockTaking.class)
public class StocktakingAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    Date queryDate = new Date();
    boolean queryAllMaterials;
    List<MaterialDto> queryMaterials = new ArrayList<MaterialDto>();
    MaterialDto chosenMaterial;
    Long selectedMaterialId;

    StockTakingDto stockTaking;

    public StocktakingAdminBean() {
        subject = StockOrderSubject.STKD;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public boolean isQueryAllMaterials() {
        return queryAllMaterials;
    }

    public void setQueryAllMaterials(boolean queryAllMaterials) {
        this.queryAllMaterials = queryAllMaterials;
    }

    public List<MaterialDto> getQueryMaterials() {
        return queryMaterials;
    }

    public List<SelectItem> getQueryMaterialSelectItems() {
        return UIHelper.selectItems(queryMaterials);
    }

    public MaterialDto getChosenMaterial() {
        return chosenMaterial;
    }

    public void setChosenMaterial(MaterialDto chosenMaterial) {
        this.chosenMaterial = chosenMaterial;
    }

    public void addQueryMaterial() {
        if (!queryMaterials.contains(chosenMaterial))
            queryMaterials.add(chosenMaterial);
    }

    public Long getSelectedMaterialId() {
        return selectedMaterialId;
    }

    public void setSelectedMaterialId(Long selectedMaterialId) {
        this.selectedMaterialId = selectedMaterialId;
    }

    public void removeQueryMaterial() {
        if (selectedMaterialId == null) // unexpected.
            return;
        Iterator<MaterialDto> it = queryMaterials.iterator();
        while (it.hasNext()) {
            MaterialDto material = it.next();
            if (selectedMaterialId.equals(material.getId())) {
                it.remove();
                return;
            }
        }
        // unexpected: not found
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        if (!super.preDelete(uMap))
            return false;
        try {
            for (StockOrderDto diffOrder : uMap.<StockOrderDto> dtos()) {
                asFor(StockTaking.class).findAndDelete(new Equals("diff.id", diffOrder.getId()));
            }
        } catch (Exception e) {
            uiLogger.error("无法删除盘点作业对象", e);
            return false;
        }
        return true;
    }

    /**
     * <pre>
     * 1. 准备盘点：
     *      1.1. 将查询的 StockItemList 形成帐面清单 (subject=CACHE)
     *      1.2. *优化：为了支持清单合并，需要逐个 stockTaking.addExpectedItem(帐面清单)
     *              而不直接 stockTaking.setExpectedOrder
     * 2. （内部）通过 unionItemsMBean 将隐含调用 stockTaking.getUnionItems
     *      2.1. （内部） stockTaking.getUnionItems 将自动为帐面清单形成相同大小的差值清单和综合清单
     * 3. 页面上的 listView/unionItems 弹出项目编辑对话框，输入盘点数量
     *      3.1. （内部）unionItems 中改变盘点数量，将自动计算数量差值，并保存到对应的（diffOrder.item）
     * 4. 用户保存，先保存 diffOrder，然后触发 postUpdate
     *      4.1. postUpdate 中附带保存 stockTaking，同时由 cascade 保存冗余的 expectedOrder。
     * 5. 考虑数量为 0 的情况，如果用户不输入数量，则相应的记录不保存：
     *      5.1. 帐面为 0，盘点为 0：这种情况不需要形成记录，故可以省去
     *      5.2. 帐面不为 0，盘点为 0：用户需要标记 item.important = true，说明盘点数量为 0 有重要意义
     *      5.3. unionItem.important 默认为 false 。
     * </pre>
     */
    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        super.postUpdate(uMap);
        if (stockTaking != null) {
            stockTaking.getDiffOrder();
        }
    }

    @Override
    public void setOpenedObjects(List<?> openedObjects) {
        super.setOpenedObjects(openedObjects);
        stockTaking = null;
    }

    public StockTakingDto getStockTaking() {
        if (stockTaking == null) {
            stockTaking = new StockTakingDto(-1);
            StockOrderDto diffOrder = getOpenedObject();
            StockTaking _stockTaking = asFor(StockTaking.class).getUnique(new Equals("diff.id", diffOrder.getId()));
            if (_stockTaking == null)
                stockTaking.create();
            else
                stockTaking.marshal(_stockTaking);
        }
        return stockTaking;
    }

    ListMBean<StockItemUnion> unionItemsMBean = ListMBean.fromEL(this, "stockTaking.unionOrder.items",
            StockItemUnion.class);

    @Override
    public ListMBean<StockItemUnion> getItemsMBean() {
        return unionItemsMBean;
    }

}
