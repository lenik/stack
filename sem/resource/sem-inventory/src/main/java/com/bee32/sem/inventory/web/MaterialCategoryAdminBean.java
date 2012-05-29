package com.bee32.sem.inventory.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.misc.TreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(MaterialCategory.class)
public class MaterialCategoryAdminBean
        extends TreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected String stringFragment;

    protected List<MaterialType> types; // = new ArrayList<MaterialType>();

    public MaterialCategoryAdminBean() {
        super(MaterialCategory.class, MaterialCategoryDto.class, 0);
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        super.postUpdate(uMap);
        ctx.bean.getBean(ChooseMaterialCategoryDialogBean.class).refreshTree();
    }

    public void addCfinishedRestriction() {
        setSearchFragment("type", "成品", //
                MaterialCriteria.categoryType(Arrays.asList(MaterialType.PRODUCT)));
    }

    public void addSemiRestriction() {
        setSearchFragment("type", "半成品", //
                MaterialCriteria.categoryType(Arrays.asList(MaterialType.SEMI)));
    }

    public void addRawRestriction() {
        setSearchFragment("type", "原材料", //
                MaterialCriteria.categoryType(Arrays.asList(MaterialType.RAW)));
    }

    public void addOtherRestriction() {
        setSearchFragment("type", "其他", //
                MaterialCriteria.categoryType(Arrays.asList(MaterialType.OTHER)));
    }

    public List<SelectItem> getMaterialTypes() {
        List<SelectItem> materialTypes = new ArrayList<SelectItem>();
        for (MaterialType c : MaterialType.values())
            materialTypes.add(new SelectItem(c.getValue(), c.getDisplayName()));
        return materialTypes;
    }

    public String getStringFragment() {
        return stringFragment;
    }

    public void setStringFragment(String stringFragement) {
        this.stringFragment = stringFragement;
    }

}
