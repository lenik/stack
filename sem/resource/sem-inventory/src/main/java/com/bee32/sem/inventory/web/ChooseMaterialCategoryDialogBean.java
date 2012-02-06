package com.bee32.sem.inventory.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;

public class ChooseMaterialCategoryDialogBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialCategoryDialogBean.class);

    String header = "Please choose a material category..."; // NLS: 选择用户或组

    public ChooseMaterialCategoryDialogBean() {
        super(MaterialCategory.class, MaterialCategoryDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
