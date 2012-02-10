package com.bee32.sem.inventory.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.Not;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;
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

    static Set<MaterialType> productLikeClasses;
    static {
        productLikeClasses = new HashSet<MaterialType>();
        productLikeClasses.add(MaterialType.PRODUCT);
        productLikeClasses.add(MaterialType.SEMI);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if (productLike != null) {
            if (productLike)
                elements.add(MaterialCriteria.materialType(productLikeClasses));
            else
                elements.add(Not.of(MaterialCriteria.materialType(MaterialType.PRODUCT)));
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
