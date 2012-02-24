package com.bee32.sem.bom.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.bom.util.BomCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChoosePartDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePartDialogBean.class);

    String mode;

    public ChoosePartDialogBean() {
        super(Part.class, PartDto.class, 0);
    }

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, Or.of(//
                CommonCriteria.labelledWith(searchPattern, true), //
                BomCriteria.targetLabel(searchPattern, true)));
        searchPattern = null;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
