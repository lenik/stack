package com.bee32.sem.inventory.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseMaterialDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialDialogBean.class);

    String caption = "Please choose a material..."; // NLS: 选择用户或组

    public ChooseMaterialDialogBean() {
        super(Material.class, MaterialDto.class, 0);
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
