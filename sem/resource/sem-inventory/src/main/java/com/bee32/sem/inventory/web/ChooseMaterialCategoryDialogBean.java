package com.bee32.sem.inventory.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.misc.ChooseTreeEntityDialogBean;

public class ChooseMaterialCategoryDialogBean
        extends ChooseTreeEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialCategoryDialogBean.class);

    public ChooseMaterialCategoryDialogBean() {
        super(MaterialCategory.class, MaterialCategoryDto.class, 0);
    }

}
