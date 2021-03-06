package com.bee32.sem.material.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.sem.material.dto.MaterialDto;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialCategory;
import com.bee32.sem.material.entity.MaterialType;
import com.bee32.sem.material.util.MaterialCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseMaterialDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialDialogBean.class);

    Integer materialType = null;
    Integer categoryId;

    public ChooseMaterialDialogBean() {
        super(Material.class, MaterialDto.class, 0);
    }

    static Set<MaterialType> productLikeClasses;
    static {
        productLikeClasses = new HashSet<MaterialType>();
        productLikeClasses.add(MaterialType.PRODUCT);
        productLikeClasses.add(MaterialType.SEMI);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (materialType != null) {
            switch (materialType) {
            case 1:
                elements.add(MaterialCriteria.materialType(MaterialType.PRODUCT, MaterialType.SEMI));
                break;
            case 2:
                elements.add(MaterialCriteria.materialType(MaterialType.SEMI, MaterialType.RAW, MaterialType.OTHER));
                break;
            }
        }
        if (categoryId != null && categoryId != -1)
            elements.add(new Equals("category.id", categoryId));
    }

    /**
     * Values:
     * <ul>
     * <li>1 = ?????????????????????,??????????????????
     * <li>2 = ????????????????????????,?????????,???????????????
     * <li>null = ????????????
     * </ul>
     */
    public Integer getMaterialType() {
        return materialType;
    }

    /**
     * ?????????????????????f:setPropertyActionListener???????????????
     */
    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
        this.refreshRowCount();
    }

    /**
     * ??????????????????
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer materialCategoryId) {
        this.categoryId = materialCategoryId;
        this.refreshRowCount();
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    public void addModelSpecRestriction() {
        setSearchFragment("model-spec", "?????????????????? " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                MaterialCriteria.modelSpecLike(searchPattern, true));
        searchPattern = null;
    }

    public void setCategoryRestriction(Integer categoryId) {
        if (categoryId != null && categoryId != -1) {
            MaterialCategory category = DATA(MaterialCategory.class).get(categoryId);
            setSearchFragment("category", "?????????" + category.getLabel(), //
                    MaterialCriteria.categoryOf(categoryId));
        }
    }

    public void setPattern(String pattern) {
        if (StringUtils.isEmpty(pattern))
            removeSearchFragmentGroup("pattern");
        else
            setSearchFragment("pattern", "???????????????????????????" + pattern,
                Or.of(
                        new Like("label", pattern, MatchMode.ANYWHERE),
                        new Like("keyword", pattern, MatchMode.ANYWHERE)
                        )
                );
    }

}
