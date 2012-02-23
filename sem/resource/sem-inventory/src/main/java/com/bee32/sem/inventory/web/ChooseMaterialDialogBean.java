package com.bee32.sem.inventory.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialType;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseMaterialDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialDialogBean.class);

    String header = "Please choose a material..."; // NLS: 选择用户或组

    Integer materialType = null;

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
            switch(materialType) {
            case 1:
                elements.add(MaterialCriteria.materialType(MaterialType.PRODUCT, MaterialType.SEMI));
                break;
            case 2:
                elements.add(MaterialCriteria.materialType(MaterialType.SEMI, MaterialType.RAW, MaterialType.OTHER));
                break;
            }
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

}
