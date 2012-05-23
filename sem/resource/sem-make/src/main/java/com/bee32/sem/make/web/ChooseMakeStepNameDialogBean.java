package com.bee32.sem.make.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.make.dto.MakeStepNameDto;
import com.bee32.sem.make.entity.MakeStepName;
import com.bee32.sem.make.util.BomCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseMakeStepNameDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMakeStepNameDialogBean.class);

    String mode;

    public ChooseMakeStepNameDialogBean() {
        super(MakeStepName.class, MakeStepNameDto.class, 0);
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, Or.of(//
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
