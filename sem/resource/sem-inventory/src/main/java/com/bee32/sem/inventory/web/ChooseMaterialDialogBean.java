package com.bee32.sem.inventory.web;

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
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;
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
     * <li>1 = 所属分类为成品,半成品的物料
     * <li>2 = 所属分类为半成品,原材料,其他的物料
     * <li>null = 所有物料
     * </ul>
     */
    public Integer getMaterialType() {
        return materialType;
    }

    /**
     * 一般在页面上用f:setPropertyActionListener来进行设置
     */
    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
        this.refreshRowCount();
    }

    /**
     * 限制物料分类
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
        setSearchFragment("model-spec", "规格型号含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                MaterialCriteria.modelSpecLike(searchPattern, true));
        searchPattern = null;
    }

    public void setCategoryRestriction(Integer categoryId) {
        if (categoryId != null && categoryId != -1) {
            MaterialCategory category = ctx.data.access(MaterialCategory.class).get(categoryId);
            setSearchFragment("category", "分类为" + category.getLabel(), //
                    MaterialCriteria.categoryOf(categoryId));
        }
    }

    public void setPattern(String pattern) {
        if (StringUtils.isEmpty(pattern))
            removeSearchFragmentGroup("pattern");
        else
            setSearchFragment("pattern", "物料名称或代码包含" + pattern,
                Or.of(
                        new Like("label", pattern, MatchMode.ANYWHERE),
                        new Like("keyword", pattern, MatchMode.ANYWHERE)
                        )
                );
    }

}
