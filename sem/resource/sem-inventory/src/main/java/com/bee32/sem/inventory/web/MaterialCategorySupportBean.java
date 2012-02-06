package com.bee32.sem.inventory.web;

import java.io.Serializable;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class MaterialCategorySupportBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected MaterialCategoryTreeModel categoryTree = new MaterialCategoryTreeModel();

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> MaterialCategorySupportBean(
            Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    public MaterialCategoryTreeModel getCategoryTree() {
        return categoryTree;
    }

    protected void onCreateMaterial(MaterialDto material) {
        Integer categoryId = material.getCategory().getId();
        MaterialCategoryDto category = categoryTree.getIndex().get(categoryId);
        if (category != null)
            category.setMaterialCount(category.getMaterialCount() + 1);
    }

    protected void onDeleteMaterial(MaterialDto material) {
        Integer categoryId = material.getCategory().getId();
        MaterialCategoryDto category = categoryTree.getIndex().get(categoryId);
        if (category != null)
            category.setMaterialCount(category.getMaterialCount() - 1);
    }

}
