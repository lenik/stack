package com.bee32.sem.inventory.web.settings;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.dao.DataIntegrityViolationException;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.thing.Unit;
import com.bee32.sem.world.thing.UnitDto;

@ForEntity(StockLocation.class)
public class StockLocationAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    protected boolean editNewStatus; // true:新增状态;false:修改状态;

    private StockWarehouseDto selectedWarehouse = new StockWarehouseDto().ref();
    private StockLocationDto stockLocation;

    private TreeNode root;
    private TreeNode selectedNode;
    private TreeNode selectedParentStockLocationNode;

    public boolean isEditNewStatus() {
        return editNewStatus;
    }

    public void setEditNewStatus(boolean editNewStatus) {
        this.editNewStatus = editNewStatus;
    }

    public StockWarehouseDto getSelectedWarehouse() {
        return selectedWarehouse;
    }

    public void setSelectedWarehouse(StockWarehouseDto selectedWarehouse) {
        this.selectedWarehouse = selectedWarehouse;
    }

    public List<SelectItem> getStockWarehouses() {
        List<StockWarehouse> stockWarehouses = serviceFor(StockWarehouse.class).list();
        List<StockWarehouseDto> stockWarehouseDtos = DTOs.mrefList(StockWarehouseDto.class, stockWarehouses);

        List<SelectItem> items = new ArrayList<SelectItem>();

        for (StockWarehouseDto stockWarehouseDto : stockWarehouseDtos) {
            String label = stockWarehouseDto.getName();
            SelectItem item = new SelectItem(stockWarehouseDto.getId(), label);
            items.add(item);
        }

        return items;
    }

    public TreeNode getRoot() {
        if (root == null) {
            root = new DefaultTreeNode("", null);
        }
        return root;
    }

    public StockLocationDto getStockLocation() {
        if (stockLocation == null) {
            _newStockLocation();
        }
        return stockLocation;
    }

    public void setStockLocation(StockLocationDto stockLocation) {
        this.stockLocation = stockLocation;
    }

    public List<SelectItem> getUnits() {
        List<Unit> units = serviceFor(Unit.class).list();
        List<UnitDto> unitDtos = DTOs.mrefList(UnitDto.class, units);
        return UIHelper.selectItemsFromDict(unitDtos);
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public TreeNode getSelectedParentStockLocationNode() {
        return selectedParentStockLocationNode;
    }

    public void setSelectedParentStockLocationNode(TreeNode selectedParentStockLocationNode) {
        this.selectedParentStockLocationNode = selectedParentStockLocationNode;
    }

    public void onSwChange(AjaxBehaviorEvent e) {
        loadStockLocationTree();
    }

    private void loadStockLocationTree() {
        if (selectedWarehouse != null) {
            root = new DefaultTreeNode(selectedWarehouse, null);

            List<StockLocation> topLocations = serviceFor(StockLocation.class).list(//
                    TreeCriteria.root(), //
                    new Equals("warehouse.id", selectedWarehouse.getId()));
            List<StockLocationDto> topLocationDtos = DTOs.mrefList(StockLocationDto.class, -1, topLocations);

            for (StockLocationDto stockLocationDto : topLocationDtos) {
                loadStockLocationRecursive(stockLocationDto, root);
            }
        }
    }

    private void loadStockLocationRecursive(StockLocationDto stockLocationDto, TreeNode parentTreeNode) {
        TreeNode stockLocationNode = new DefaultTreeNode(stockLocationDto, parentTreeNode);

        for (StockLocationDto subStockLocation : stockLocationDto.getChildren()) {
            loadStockLocationRecursive(subStockLocation, stockLocationNode);
        }
    }

    private void _newStockLocation() {
        stockLocation = new StockLocationDto().create();
    }

    public void doNewStockLocation() {
        editNewStatus = true;
        _newStockLocation();
    }

    public void chooseStockLocation() {
        stockLocation = reload((StockLocationDto) selectedNode.getData());
    }

    public void doModifyStockLocation() {
        chooseStockLocation();
        editNewStatus = false;
    }

    public void doSave() {
        if (selectedWarehouse == null) {
            uiLogger.warn("请先选择需要添加库位的仓库");
            return;
        }

        stockLocation.setWarehouse(selectedWarehouse);

        if (stockLocation.getCapacityUnit().getId().isEmpty()) {
            UnitDto nullUnitDto = new UnitDto().ref();
            stockLocation.setCapacityUnit(nullUnitDto);
        }

        StockLocation l = stockLocation.unmarshal(this);

        try {
            serviceFor(StockLocation.class).saveOrUpdate(l);
            loadStockLocationTree();
            uiLogger.info("保存成功。");
        } catch (Exception e) {
            uiLogger.error("保存失败.错误消息:" + e.getMessage(), e);
        }
    }

    public void doDelete() {
        if (stockLocation.getChildren().size() > 0) {
            uiLogger.info("本库位有下属库位，请先删除下属库位!");
            return;
        }

        try {
            serviceFor(StockLocation.class).delete(stockLocation.unmarshal());
            loadStockLocationTree();
            uiLogger.info("删除成功!");
        } catch (DataIntegrityViolationException e) {
            uiLogger.error("删除失败,违反约束归则,可能你需要删除的库位在其它地方被使用到!");
        }
    }

    public void doSelectParentStockLocation() {
        stockLocation.setParent((StockLocationDto) selectedParentStockLocationNode.getData());
    }

}
