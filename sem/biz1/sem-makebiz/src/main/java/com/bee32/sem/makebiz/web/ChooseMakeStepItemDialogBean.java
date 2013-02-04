package com.bee32.sem.makebiz.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.makebiz.dto.MakeStepItemDto;
import com.bee32.sem.makebiz.entity.MakeStepItem;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseMakeStepItemDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMakeStepItemDialogBean.class);

    public ChooseMakeStepItemDialogBean() {
        super(MakeStepItem.class, MakeStepItemDto.class, 0);
    }

}
