package com.bee32.sem.material.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.material.dto.StockLocationDto;
import com.bee32.sem.material.dto.StockWarehouseDto;
import com.bee32.sem.material.entity.MaterialPreferredLocation;
import com.bee32.sem.material.entity.StockLocation;
import com.bee32.sem.misc.ChooseTreeEntityDialogBean;
import com.bee32.sem.sandbox.TreeNodeSelectionHolder;

public class ChooseStockLocationDialogBean
        extends ChooseTreeEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockLocationDialogBean.class);

    int tabIndex;
    StockWarehouseDto warehouse;
    MaterialDto material;
    final TreeNodeSelectionHolder fullSelectionHolder = new TreeNodeSelectionHolder();
    List<StockLocationDto> preferredLocations;
    StockLocationDto selectedPreferredLocation;

    public ChooseStockLocationDialogBean() {
        super(StockLocation.class, StockLocationDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (warehouse != null && !warehouse.isNull())
            elements.add(new Equals("warehouse.id", warehouse.getId()));
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public StockWarehouseDto getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(StockWarehouseDto warehouse) {
        this.warehouse = warehouse;
        refreshTree();
    }

    public MaterialDto getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDto material) {
        this.material = material;
        preferredLocations = null;
    }

    public TreeNodeSelectionHolder getFullSelectionHolder() {
        return fullSelectionHolder;
    }

    public synchronized SelectableList<StockLocationDto> getPreferredLocations() {
        if (preferredLocations == null) {
            List<StockLocationDto> list = new ArrayList<StockLocationDto>();
            if (material != null && !material.isNull()) {
                for (MaterialPreferredLocation mpl : DATA(MaterialPreferredLocation.class).list(
                        new Equals("material.id", material.getId()))) {
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
        switch (tabIndex) {
        default:
        case 0:
            return fullSelectionHolder.getSelection();
        case 1:
            return listOfNonNulls(selectedPreferredLocation);
        }
    }

    @Override
    public void setSelection(List<?> selection) {
        logger.warn("Read-Only: " + getClass().getSimpleName() + ".selection");
    }

}
