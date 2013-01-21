package com.bee32.sem.make.web;

import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.make.dto.QCSpecParameterDto;
import com.bee32.sem.make.entity.QCSpecParameter;
import com.bee32.sem.make.util.BomCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChooseQCSpecParameterDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseQCSpecParameterDialogBean.class);

    String mode;

    public ChooseQCSpecParameterDialogBean() {
        super(QCSpecParameter.class, QCSpecParameterDto.class, 0);
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
