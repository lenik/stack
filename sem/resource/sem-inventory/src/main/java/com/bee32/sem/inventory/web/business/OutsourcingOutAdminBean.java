package com.bee32.sem.inventory.web.business;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.annotation.TypeParameter;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.tx.dto.StockOutsourcingDto;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.util.StockJobStepping;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(value = StockOrder.class, parameters = @TypeParameter(name = "_subject", value = "OSPO"))
public class OutsourcingOutAdminBean
        extends AbstractStockOrderBean {

    private static final long serialVersionUID = 1L;

    private StockOutsourcingDto stockOutsourcing = new StockOutsourcingDto().create();

    public OutsourcingOutAdminBean() {
        this.subject = StockOrderSubject.OSP_OUT;
    }

    @Override
    protected void configJobStepping(StockJobStepping stepping) {
        stepping.setJobClass(StockOutsourcing.class);
        stepping.setJobDtoClass(StockOutsourcingDto.class);
        stepping.setInitiatorProperty("output");
        stepping.setInitiatorColumn("s1");
        stepping.setBindingProperty("output");
        stepping.setBindingColumn("s1");
    }

    public StockOutsourcingDto getStockOutsourcing() {
        return stockOutsourcing;
    }

    public void setStockOutsourcing(StockOutsourcingDto stockOutsourcing) {
        this.stockOutsourcing = stockOutsourcing;
    }

    @Override
    protected StockOrderDto create() {
        StockOrderDto stockOrder = super.create();
        stockOutsourcing = new StockOutsourcingDto().create();
        return stockOrder;
    }

    @Override
    protected void openSelection() {
        super.openSelection();
        StockOrderDto stockOrder = getOpenedObject();
        if (stockOrder == null)
            stockOutsourcing = null;
        else {
            StockOutsourcing _outsourcing = serviceFor(StockOutsourcing.class).getUnique(
                    new Equals("output.id", stockOrder.getId()));
            if (_outsourcing == null) // unexpected.
                stockOutsourcing = new StockOutsourcingDto().create();
            else
                stockOutsourcing = DTOs.marshal(StockOutsourcingDto.class, -1, _outsourcing);
            stockOutsourcing.setOutput(stockOrder); // this is an optim.
        }
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (StockOrderDto stockOrder : uMap.<StockOrderDto> dtos()) {
            try {
                serviceFor(StockOutsourcing.class).findAndDelete(new Equals("output.id", stockOrder.getId()));
            } catch (Exception e) {
                uiLogger.error("Can't delete stock-outsourcing", e);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap) {
        for (StockOrder _stockOrder : uMap.<StockOrder> entitySet()) {
            StockOutsourcing _stockOursourcing = stockOutsourcing.unmarshal();
            _stockOursourcing.setOutput(_stockOrder);
            asFor(StockOutsourcing.class).saveOrUpdate(_stockOursourcing);
            break;
        }
    }

}
