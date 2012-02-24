package com.bee32.sem.inventory.web;

import java.util.List;

import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.tx.dto.StockOutsourcingDto;
import com.bee32.sem.inventory.tx.dto.StockTransferDto;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseStockJobDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockJobDialogBean.class);

    // 用于查询拨出单
    int destWarehouseId = -1;
    boolean nullDest = false;

    // 用于查询委外单
    boolean nullInput = false;

    @SuppressWarnings("unchecked")
    public ChooseStockJobDialogBean() {
        super(StockJob.class, StockJobDto.class, 0);
    }

    public String getType() {
        return entityClass.getName();
    }

    public void setType(String typeName) {
        // sessionFactory.getClassMetadata("typeName").getMappedClass(EntityMode.POJO);
        try {
            Class<? extends Entity<?>> type = (Class<? extends Entity<?>>) Class.forName(typeName);
            entityClass = type;
        } catch (ClassNotFoundException e) {
            throw new IllegalUsageException("Bad type name: " + typeName, e);
        }
        Class<? extends EntityDto<?, ?>> dtoType = (Class<? extends EntityDto<?, ?>>) EntityUtil
                .getDtoType(entityClass);
        dtoClass = dtoType;
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);

        if (StockTransferDto.class.isAssignableFrom(entityClass) && destWarehouseId != -1) {
            elements.add(new Equals("destWarehouse.id", destWarehouseId));
        }

        if (StockTransferDto.class.isAssignableFrom(entityClass) && nullDest) {
            elements.add(new IsNull("dest"));
        }

        if (StockOutsourcingDto.class.isAssignableFrom(entityClass) && nullInput) {
            elements.add(new IsNull("input"));
        }
    }

    // 限定StockTransfer的拨入仓库
    public int getDestWarehouseId() {
        return destWarehouseId;
    }

    public void setDestWarehouseId(int destWarehouseId) {
        this.destWarehouseId = destWarehouseId;
        refreshRowCount();
    }

    // 限定StockTransfer的dest为null,即还没有对应拨入单的拨出单
    public boolean isNullDest() {
        return nullDest;
    }

    public void setNullDest(boolean nullDest) {
        this.nullDest = nullDest;
    }

    // 限定StockOutsourcing的input为null,即还没有对应委外入库的委外出库单
    public boolean isNullInput() {
        return nullInput;
    }

    public void setNullInput(boolean nullInput) {
        this.nullInput = nullInput;
    }

}
