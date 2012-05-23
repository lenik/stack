package com.bee32.sem.make.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.make.dto.PartDto;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.make.util.BomCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChoosePartDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePartDialogBean.class);

    String mode;

    public ChoosePartDialogBean() {
        super(Part.class, PartDto.class, 0);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, Or.of(//
                CommonCriteria.labelledWith(searchPattern, true), //
                BomCriteria.targetLabel(searchPattern, true)));
        searchPattern = null;
    }

    public void addModelSpecRestriction() {
        setSearchFragment("model-spec", "规格型号含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                BomCriteria.targetModelSpec(searchPattern, true));
        searchPattern = null;
    }

    public void addCategoryRestriction(Integer categoryId) {
        if (categoryId != null && categoryId != -1) {
            MaterialCategory category = ctx.data.access(MaterialCategory.class).get(categoryId);
            addSearchFragment("分类为" + category.getLabel(), //
                    BomCriteria.targetCategory(categoryId));
        }
    }

}
