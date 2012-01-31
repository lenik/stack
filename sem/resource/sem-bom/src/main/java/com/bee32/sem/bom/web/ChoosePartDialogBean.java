package com.bee32.sem.bom.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChoosePartDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePartDialogBean.class);

    String header = "Please choose a component part..."; // NLS: 选择用户或组

    public ChoosePartDialogBean() {
        super(Part.class, PartDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
