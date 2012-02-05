package com.bee32.sem.misc;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.sandbox.ZLazyDataModel;

public class ScrollEntityViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    int rowNumber;
    int rowNumberInput;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    ScrollEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    protected void selectRow(int rowNumber) {
        int rowCount = dataModel.getRowCount();
        if (rowNumber < 1)
            rowNumber = 1;
        if (rowNumber > rowCount)
            rowNumber = rowCount;

        EntityDto<?, ?> dto = null;
        if (rowNumber > 0) {
            int rowIndex = rowNumber - 1;
            ZLazyDataModel<?, ?> dataModel = (ZLazyDataModel<?, ?>) getDataModel();
            dto = dataModel.load(rowIndex);
        }
        setSingleSelection(dto);
        this.rowNumber = rowNumber;
        this.rowNumberInput = rowNumber;
        openSelection(); // fmask override here.
    }

    public boolean isFirst() {
        return rowNumber == 1;
    }

    public boolean isLast() {
        return rowNumber == getDataModel().getRowCount();
    }

    public void gotoFirst() {
        if (rowNumber != 1)
            selectRow(1);
    }

    public void gotoPrevious() {
        if (rowNumber > 1)
            selectRow(rowNumber - 1);
    }

    public void gotoNext() {
        if (rowNumber < getDataModel().getRowCount())
            selectRow(rowNumber + 1);
    }

    public void gotoLast() {
        if (rowNumber != getDataModel().getRowCount())
            selectRow(getDataModel().getRowCount());
    }

    public void gotoInput() {
        if (rowNumber != rowNumberInput)
            selectRow(rowNumberInput);
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getRowNumberInput() {
        return rowNumberInput;
    }

    public void setRowNumberInput(int rowNumberInput) {
        this.rowNumberInput = rowNumberInput;
    }

}
