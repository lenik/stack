package com.bee32.sem.misc;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class ScrollEntityViewBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    int rowNumber;
    int rowNumberInput;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    ScrollEntityViewBean(Class<E> entityClass, Class<D> dtoClass, int selection, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, selection, criteriaElements);
    }

    public void gotoFirst() {
        if (rowNumber != 1)
            loadRow(1);
    }

    public void gotoPrevious() {
        if (rowNumber > 1)
            loadRow(rowNumber - 1);
    }

    public void gotoNext() {
        if (rowNumber < getDataModel().getRowCount())
            loadRow(rowNumber + 1);
    }

    public void gotoLast() {
        if (rowNumber != getDataModel().getRowCount())
            loadRow(getDataModel().getRowCount());
    }

    public void gotoInput() {
        if (rowNumber != rowNumberInput)
            loadRow(rowNumberInput);
    }

    protected void loadRow(int rowNumber) {
        int rowCount = dataModel.getRowCount();
        if (rowNumber < 1)
            rowNumber = 1;
        if (rowNumber > rowCount)
            rowNumber = rowCount;

        int rowIndex = rowNumber - 1;
        EntityDto<?, ?> dto = getDataModel().load(rowIndex);
        if (dto == null)
            rowNumber = -1;
        else {
            loadForm(dto);
        }

        this.rowNumber = rowNumber;
        this.rowNumberInput = rowNumber;
    }

    public void cancel() {
        setActiveObject(null);
        loadRow(rowNumber);
    }

    protected void loadForm(EntityDto<?, ?> dto) {
        setActiveObject(dto);
    }

}
