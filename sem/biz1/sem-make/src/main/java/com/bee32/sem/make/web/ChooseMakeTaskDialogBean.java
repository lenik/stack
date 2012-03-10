package com.bee32.sem.make.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.make.dto.MakeTaskDto;
import com.bee32.sem.make.entity.MakeTask;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseMakeTaskDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMakeTaskDialogBean.class);

    public ChooseMakeTaskDialogBean() {
        super(MakeTask.class, MakeTaskDto.class, 0);
    }

}
