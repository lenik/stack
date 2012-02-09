package com.bee32.sem.inventory.web;

import javax.free.IllegalUsageException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseStockJobDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockJobDialogBean.class);

    String header = "Please choose a stock job..."; // NLS: 选择用户或组

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

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
