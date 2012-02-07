package com.bee32.sem.inventory.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseStockOrderDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockOrderDialogBean.class);

    String header = "Please choose a stock order..."; // NLS: 选择用户或组
    StockOrderSubject subject;

    public ChooseStockOrderDialogBean() {
        super(StockOrder.class, StockOrderDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (subject != null)
            elements.add(StockCriteria.subjectOf(subject));
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        if (this.subject != subject) {
            this.subject = subject;
            refreshRowCount();
        }
        if (subject != null) {
            header = "请选择 " + subject.getDisplayName() + " 单...";
        } else {
            header = "请选择库存单据...";
        }
    }

    public String get_subject() {
        if (subject == null)
            return null;
        else
            return subject.getValue();
    }

    public void set_subject(String _subject) {
        if (_subject == null)
            setSubject(null);
        else
            setSubject(StockOrderSubject.valueOf(_subject));
    }

}
