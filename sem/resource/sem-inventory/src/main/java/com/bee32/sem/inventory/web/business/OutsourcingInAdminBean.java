package com.bee32.sem.inventory.web.business;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockOutsourcingDto;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "OSPI"))
public class OutsourcingInAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    public OutsourcingInAdminBean() {
        this.subject = StockOrderSubject.OSP_IN;
    }

    @Override
    protected void configJobStepping(StockJobStepping stepping) {
        stepping.setJobClass(StockOutsourcing.class);
        stepping.setJobDtoClass(StockOutsourcingDto.class);
        stepping.setInitiatorProperty("output");
        stepping.setInitiatorColumn("s1");
        stepping.setBindingProperty("input");
        stepping.setBindingColumn("s2");
    }

    @Transactional
    public void preDelete() {
        StockOrderDto stockOrder = getOpenedObject();
        List<StockOutsourcing> jobs = serviceFor(StockOutsourcing.class).list(
                new Equals("input.id", stockOrder.getId()));
        for (StockOutsourcing job : jobs) {
            job.setInput(null);
        }
        try {
            serviceFor(StockOutsourcing.class).saveOrUpdateAll(jobs);
        } catch (Exception e) {
            uiLogger.warn("删除失败,错误信息:" + e.getMessage());
        }
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        StockOrderDto stockOrder = getOpenedObject();
        try {
// stockOutsourcing.setInput(stockOrder);
// StockOutsourcing _stockOutsourcing = stockOutsourcing.unmarshal();

            // 保存stockOutsourcing
// serviceFor(StockOutsourcing.class).saveOrUpdate(_stockOutsourcing);
        } catch (Exception e) {
            uiLogger.error("保存失败,错误信息:" + e.getMessage());
        }
        return true;
    }

    @LeftHand(StockOrder.class)
    public CriteriaElement getDanglingCriteria() {
        return StockCriteria.danglingOutsourcing();
    }

}
