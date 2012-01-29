package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.List;

import javax.free.Nullables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;

public class ChooseStockLocationDialogBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockLocationDialogBean.class);

    String caption = "Please choose a stock location..."; // NLS: 选择用户或组
    Integer warehouseId;
    Long materialId;
    List<StockLocationDto> preferredLocations;

    public ChooseStockLocationDialogBean() {
        super(StockLocation.class, StockLocationDto.class, 0);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        if (warehouseId != null)
            elements.add(new Equals("warehouse.id", warehouseId));
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        if (!Nullables.equals(this.materialId, materialId)) {
            this.materialId = materialId;
            this.preferredLocations = null;
        }
    }

    public synchronized List<StockLocationDto> getPreferredLocations() {
        if (preferredLocations == null) {
            preferredLocations = new ArrayList<StockLocationDto>();
            if (materialId != null) {
                for (MaterialPreferredLocation mpl : asFor(MaterialPreferredLocation.class).list(
                        new Equals("material.id", materialId))) {
                    StockLocation _location = mpl.getLocation();
                    StockLocationDto location = DTOs.mref(StockLocationDto.class, _location);
                    preferredLocations.add(location);
                }
            }
        }
        return preferredLocations;
    }

}
