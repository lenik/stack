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
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockTakingDto;
import com.bee32.sem.inventory.tx.dto.StockTakingItemDto;
import com.bee32.sem.inventory.tx.entity.StockTaking;
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
    public void setOpenedObjects(List<?> openedObjects) {
        super.setOpenedObjects(openedObjects);
        stockTaking = null;
    }

    public StockTakingDto getStockTaking() {
        if (stockTaking == null) {
            StockOrderDto diffOrder = getOpenedObject();
            StockTaking _stockTaking = asFor(StockTaking.class).getUnique(new Equals("diff.id", diffOrder.getId()));
            stockTaking = new StockTakingDto(-1).marshal(_stockTaking);
        }
        return stockTaking;
    }

    ListMBean<StockTakingItemDto> joinedItemsMBean = ListMBean.fromEL(this, "stockTaking.items",
            StockTakingItemDto.class);

    @Override
    public Object getEnclosingObject() {
        return stockTaking;
    }

    @Override
    public ListMBean<StockOrderItemDto> getItemsMBean() {
        return super.getItemsMBean();
    }

}
