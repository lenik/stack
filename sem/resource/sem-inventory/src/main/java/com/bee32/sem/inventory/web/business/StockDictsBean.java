package com.bee32.sem.inventory.web.business;

import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.StockLocationDto;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.StockLocation;
import com.bee32.sem.inventory.entity.StockWarehouse;

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

}
