package com.bee32.sem.make.web;

import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.make.dto.QCSpecDto;
import com.bee32.sem.make.dto.QCSpecParameterDto;
import com.bee32.sem.make.entity.QCSpec;
import com.bee32.sem.make.entity.QCSpecParameter;
import com.bee32.sem.make.util.BomCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChooseQCSpecParameterDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseQCSpecParameterDialogBean.class);

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

    public void setQcSpecRestriction(Long qcSpecId) {
        if (qcSpecId != null && qcSpecId != -1) {
            setSearchFragment("qcSpec", "质检标准id为" + qcSpecId, //
                    BomCriteria.specParamsOf(qcSpecId));
        }
    }

}
