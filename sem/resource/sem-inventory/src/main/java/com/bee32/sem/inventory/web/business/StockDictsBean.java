package com.bee32.sem.inventory.web.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;
import com.bee32.sem.misc.LazyDTOMap;

public class StockDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<StockWarehouseDto> warehouses;
    List<MaterialCategoryDto> categories;
    List<StockLocationDto> locations;

    public SelectableList<StockWarehouseDto> getWarehouses() {
        if (warehouses == null) {
            synchronized (this) {
                if (warehouses == null) {
                    warehouses = mrefList(StockWarehouse.class, StockWarehouseDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(warehouses);
    }

    public SelectableList<MaterialCategoryDto> getCategories() {
        if (categories == null) {
            synchronized (this) {
                if (categories == null) {
                    categories = mrefList(MaterialCategory.class, MaterialCategoryDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(categories);
    }

    public List<StockLocationDto> getLocations() {
        if (locations == null) {
            synchronized (this) {
                if (locations == null) {
                    locations = mrefList(StockLocation.class, StockLocationDto.class, 0);
                }
            }
        }
        return SelectableList.decorate(locations);
    }

    /**
     * TODO - This is a temporary solution for the absence of fr:select.
     */
    Map<Integer, StockWarehouseDto> warehouseMap = new LazyDTOMap<Integer, StockWarehouseDto>(StockWarehouse.class,
            StockWarehouseDto.class, 0);

    public StockWarehouseDto getWarehouse(int id) {
        StockWarehouseDto warehouse = warehouseMap.get(id);
        if (warehouse == null)
            warehouse = new StockWarehouseDto().ref();
        return warehouse;
    }

    public List<SelectItem> getWarehouseSelectItems() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (StockWarehouseDto warehouse : getWarehouses()) {
            Integer id = warehouse.getId();
            String name = warehouse.getLabel();
            selectItems.add(new SelectItem(id, name));
        }
        return selectItems;
    }

}
