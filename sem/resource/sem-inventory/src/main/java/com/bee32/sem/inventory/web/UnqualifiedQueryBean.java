package com.bee32.sem.inventory.web;

import java.util.Date;

import javax.inject.Inject;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.inventory.dto.StockWarehouseDto;
import com.bee32.sem.inventory.entity.StockInventory;
import com.bee32.sem.inventory.service.StockQueryResult;
import com.bee32.sem.inventory.web.business.StockDictsBean;

@ForEntity(StockInventory.class)
public class UnqualifiedQueryBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    @Inject
    StockDictsBean dicts;

    Integer selectedWarehouseId = -1;
    Date queryDateFrom = new Date();
    Date queryDateTo = new Date();


    public int getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(int selectedWarehouseId) {
        if (this.selectedWarehouseId != selectedWarehouseId) {
            this.selectedWarehouseId = selectedWarehouseId;
        }
    }

    public StockWarehouseDto getSelectedWarehouse() {
        if (selectedWarehouseId == -1)
            return null;
        else
            return dicts.getWarehouse(selectedWarehouseId);
    }

    public Date getQueryDateFrom() {
	return queryDateFrom;
    }

	public void setQueryDateFrom(Date queryDateFrom) {
	this.queryDateFrom = queryDateFrom;
    }

	public Date getQueryDateTo() {
	return queryDateTo;
    }

	public void setQueryDateTo(Date queryDateTo) {
	this.queryDateTo = queryDateTo;
    }

	public void query(StockQueryResult result) {
		StringBuilder sb = new StringBuilder();

		sb.append("");

		sb.append("select ");
		sb.append("	date_trunc('day', created_date) as d_day, ");
		sb.append("	state, ");
		sb.append("	sum(quantity) as quantity ");
		sb.append("from stock_order_item ");
		sb.append("where ");
		sb.append("	warehouse=22 ");
		sb.append("	and created_date between ? and ? ");
		sb.append("group by ");
		sb.append("	date_trunc('day', created_date), ");
		sb.append("	state ");
		sb.append("order by date_trunc('day', created_date) ");

    }


}
