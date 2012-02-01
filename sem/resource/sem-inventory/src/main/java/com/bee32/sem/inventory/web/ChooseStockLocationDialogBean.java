package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.List;

import javax.free.Nullables;
import javax.free.ReadOnlyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.entity.MaterialPreferredLocation;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;
import com.bee32.sem.sandbox.TreeNodeSelectionHolder;

public class ChooseStockLocationDialogBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockLocationDialogBean.class);

    String header = "Please choose a stock location..."; // NLS: 选择用户或组
    Integer warehouseId;
    Long materialId;
    final TreeNodeSelectionHolder fullSelectionHolder = new TreeNodeSelectionHolder();
    List<StockLocationDto> preferredLocations;
    StockLocationDto selectedPreferredLocation;

    public ChooseStockLocationDialogBean() {
        super(StockLocation.class, StockLocationDto.class, 0);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        if (warehouseId != null)
            elements.add(new Equals("warehouse.id", warehouseId));
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public TreeNodeSelectionHolder getFullSelectionHolder() {
        return fullSelectionHolder;
    }

    public synchronized SelectableList<StockLocationDto> getPreferredLocations() {
        if (preferredLocations == null) {
            List<StockLocationDto> list = new ArrayList<StockLocationDto>();
            if (materialId != null) {
                for (MaterialPreferredLocation mpl : asFor(MaterialPreferredLocation.class).list(
                        new Equals("material.id", materialId))) {
                    StockLocation _location = mpl.getLocation();
                    StockLocationDto location = DTOs.mref(StockLocationDto.class, _location);
                    list.add(location);
                }
            }
            preferredLocations = list;
        }
        return SelectableList.decorate(preferredLocations);
    }

    public StockLocationDto getSelectedPreferredLocation() {
        return selectedPreferredLocation;
    }

    public void setSelectedPreferredLocation(StockLocationDto selectedPreferredLocation) {
        this.selectedPreferredLocation = selectedPreferredLocation;
    }

    @Override
    public List<?> getSelection() {
        switch (getTabIndex()) {
        default:
        case 0:
            return fullSelectionHolder.getSelection();
        case 1:
            return listOfNonNulls(selectedPreferredLocation);
        }
    }

    @Override
    public void setSelection(List<?> selection) {
        throw new ReadOnlyException();
    }

}
