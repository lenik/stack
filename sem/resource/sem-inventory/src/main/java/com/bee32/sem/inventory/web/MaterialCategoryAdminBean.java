package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(MaterialCategory.class)
public class MaterialCategoryAdminBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    public MaterialCategoryAdminBean() {
        super(MaterialCategory.class, MaterialCategoryDto.class, 0);
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap) throws Exception {
        super.postUpdate(uMap);
        ctx.bean.getBean(ChooseMaterialCategoryDialogBean.class).refreshTree();
    }


    public List<SelectItem> getMaterialTypes() {
        List<SelectItem> materialTypes = new ArrayList<SelectItem>();
        for (MaterialType c : MaterialType.values())
            materialTypes.add(new SelectItem(c.getValue(), c.getDisplayName()));
        return materialTypes;
    }

}
