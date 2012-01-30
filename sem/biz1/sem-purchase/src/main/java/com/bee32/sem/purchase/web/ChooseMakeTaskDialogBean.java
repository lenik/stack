package com.bee32.sem.purchase.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.purchase.dto.MakeTaskDto;
import com.bee32.sem.purchase.entity.MakeTask;

public class ChooseMakeTaskDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMakeTaskDialogBean.class);

    String caption = "选择生产任务";

    public ChooseMakeTaskDialogBean() {
        super(MakeTask.class, MakeTaskDto.class, 0);
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
