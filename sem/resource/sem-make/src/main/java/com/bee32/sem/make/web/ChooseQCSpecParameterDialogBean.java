package com.bee32.sem.make.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.make.dto.QCSpecParameterDto;
import com.bee32.sem.make.entity.QCSpecParameter;
import com.bee32.sem.make.util.BomCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseQCSpecParameterDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseQCSpecParameterDialogBean.class);

    public ChooseQCSpecParameterDialogBean() {
        super(QCSpecParameter.class, QCSpecParameterDto.class, 0);
    }

    public void addQcSpecRestriction(Long qcSpecId) {
        if (qcSpecId != null && qcSpecId != -1) {
            setSearchFragment("qcSpec", "质检标准id为" + qcSpecId, //
                    BomCriteria.specParamsOf(qcSpecId));
        }
    }

}
