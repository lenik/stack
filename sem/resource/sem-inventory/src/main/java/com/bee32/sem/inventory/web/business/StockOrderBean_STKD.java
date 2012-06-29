package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StocktakingOrderDto;
import com.bee32.sem.inventory.dto.StocktakingOrderItemDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderItem;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.entity.StocktakingOrder;
import com.bee32.sem.inventory.service.StockQueryResult;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.sandbox.UIHelper;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "STKD"))
public class StockOrderBean_STKD
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    Date queryDate = new Date();
    boolean queryAllMaterials;
    List<MaterialDto> queryMaterials = new ArrayList<MaterialDto>();
    MaterialDto chosenMaterial;
    Long selectedMaterialId;

    public StockOrderBean_STKD() {
        subject = StockOrderSubject.STKD;
        setEntityType(StocktakingOrder.class);
        setEntityDtoType(StocktakingOrderDto.class);
    }

    @Override
    protected boolean configJobStepping(StockJobStepping stepping) {
        return false;
    }

    @Override
    protected Class<? extends StockOrderItemDto> getItemDtoClass() {
        return StocktakingOrderItemDto.class;
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

    public void copyResult(StockQueryResult result) {
        StocktakingOrderDto order = getOpenedObject();

        order.getItems().clear();
        long index = 0;
        for (StockOrderItem item : result.getItems()) {
            StocktakingOrderItemDto itemDto = new StocktakingOrderItemDto().create();
            itemDto.marshal(item);
            itemDto.setId(index++, true);
            itemDto.setExpectedQuantity(item.getQuantity());
            itemDto.setDiffQuantity(null);
            itemDto.setParent(order);
            order.addItem(itemDto);
        }
    }

}
