package com.bee32.sem.inventory.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.NotEquals;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseMaterialDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialDialogBean.class);

    String header = "Please choose a material..."; // NLS: 选择用户或组

    Boolean productLike;

    public ChooseMaterialDialogBean() {
        super(Material.class, MaterialDto.class, 0);
    }

    static Set<Character> productLikeClasses;
    static {
        productLikeClasses = new HashSet<Character>();
        productLikeClasses.add(Classification.PRODUCT.getValue());
        productLikeClasses.add(Classification.SEMI.getValue());
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (productLike != null) {
            if (productLike)
                elements.add(new InCollection("_classification", productLikeClasses));
            else
                elements.add(new NotEquals("_classification", Classification.PRODUCT.getValue()));
        }
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Values:
     * <ul>
     * <li>true = 成品/半成品
     * <li>false = not 成品
     * <li>null = 不限
     * </ul>
     */
    public Boolean getProductLike() {
        return productLike;
    }

    public void setProductLike(Boolean productLike) {
        this.productLike = productLike;
    }

}
