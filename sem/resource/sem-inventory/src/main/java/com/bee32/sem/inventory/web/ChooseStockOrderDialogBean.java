package com.bee32.sem.inventory.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockOrderSubject;
import com.bee32.sem.inventory.util.StockCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseStockOrderDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseStockOrderDialogBean.class);

    StockOrderSubject subject;

    StockOrderSubject searchSubject;

    Boolean haveNoCorrespondingTicket = false;

    public ChooseStockOrderDialogBean() {
        super(StockOrder.class, StockOrderDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (subject != null)
            elements.add(StockCriteria.subjectOf(subject));
        if (haveNoCorrespondingTicket) {
            elements.add(new IsNull("ticket"));
        }
    }

    @Override
    public String getObjectTypeName() {
        if (subject != null)
            return subject.getDisplayName() + " 单...";
        else
            return "库存单据";
    }

    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        if (this.subject != subject) {
            this.subject = subject;
            refreshRowCount();
        }
    }

    public StockOrderSubject getSearchSubject() {
        return searchSubject;
    }

    public void setSearchSubject(StockOrderSubject searchSubject) {
        this.searchSubject = searchSubject;
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
            setSubject(StockOrderSubject.forValue(_subject));
    }

    public String get_searchSubject() {
        if (searchSubject == null)
            return null;
        else
            return searchSubject.getValue();
    }

    public void set_searchSubject(String _searchSubject) {
        if (_searchSubject == null)
            setSearchSubject(null);
        else
            setSearchSubject(StockOrderSubject.forValue(_searchSubject));
    }

    public Boolean getHaveNoCorrespondingTicket() {
        return haveNoCorrespondingTicket;
    }

    public void setHaveNoCorrespondingTicket(Boolean haveNoCorrespondingTicket) {
        this.haveNoCorrespondingTicket = haveNoCorrespondingTicket;
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    public void addSearchSubjectRestricion() {
        setSearchFragment("searchSubject", "单据类型为 " + searchSubject.getDisplayName(),//
                StockCriteria.subjectOf(searchSubject));
    }

}
