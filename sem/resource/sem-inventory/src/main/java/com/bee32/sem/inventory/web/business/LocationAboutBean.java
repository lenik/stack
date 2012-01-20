package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.inventory.dto.MaterialPreferredLocationDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockOrderItemDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockLocation;

@ForEntity(StockLocation.class)
public abstract class LocationAboutBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private TreeNode locationRoot;
    private TreeNode selectedStockLocationNode;
    private StockLocationDto selectedPreferredStockLocation;

    public TreeNode getLocationRoot() {
        if (locationRoot == null) {
            locationRoot = new DefaultTreeNode("", null);
        }
        return locationRoot;
    }

    public TreeNode getSelectedStockLocationNode() {
        return selectedStockLocationNode;
    }

    public void setSelectedStockLocationNode(TreeNode selectedStockLocationNode) {
        this.selectedStockLocationNode = selectedStockLocationNode;
    }

    public List<StockLocationDto> getPreferredLocations() {
        List<StockLocationDto> stockLocations = new ArrayList<StockLocationDto>();

        if (getOrderItem_() != null && getOrderItem_().getMaterial() != null) {
            List<MaterialPreferredLocationDto> preferredLocations = getOrderItem_().getMaterial()
                    .getPreferredLocations();
            if (preferredLocations != null) {
                for (MaterialPreferredLocationDto preferredLocation : preferredLocations) {
                    stockLocations.add(preferredLocation.getLocation());
                }
            }
        }

        return stockLocations;
    }

    public StockLocationDto getSelectedPreferredStockLocation() {
        return selectedPreferredStockLocation;
    }

    public void setSelectedPreferredStockLocation(StockLocationDto selectedPreferredStockLocation) {
        this.selectedPreferredStockLocation = selectedPreferredStockLocation;
    }

    protected void loadStockLocationTree() {
        if (getSelectedWarehouse_() != null) {
            locationRoot = new DefaultTreeNode(getSelectedWarehouse_(), null);

            List<StockLocation> topLocations = serviceFor(StockLocation.class).list(//
                    TreeCriteria.root(), //
                    new Equals("warehouse.id", getSelectedWarehouse_().getId()));
            List<StockLocationDto> topLocationDtos = DTOs.mrefList(StockLocationDto.class, -1, topLocations);

            for (StockLocationDto stockLocationDto : topLocationDtos) {
                loadStockLocationRecursive(stockLocationDto, locationRoot);
            }
        }
    }

    private void loadStockLocationRecursive(StockLocationDto stockLocationDto, TreeNode parentTreeNode) {
        TreeNode stockLocationNode = new DefaultTreeNode(stockLocationDto, parentTreeNode);

        for (StockLocationDto subStockLocation : stockLocationDto.getChildren()) {
            loadStockLocationRecursive(subStockLocation, stockLocationNode);
        }
    }

    public void updateLocations() {

    }

    public void doSelectStockLocation() {
        getOrderItem_().setLocation((StockLocationDto) selectedStockLocationNode.getData());
    }

    public void doSelectPreferredStockLocation() {
        getOrderItem_().setLocation(selectedPreferredStockLocation);
    }

    public abstract StockOrderItemDto getOrderItem_();

    public abstract StockWarehouseDto getSelectedWarehouse_();

}
