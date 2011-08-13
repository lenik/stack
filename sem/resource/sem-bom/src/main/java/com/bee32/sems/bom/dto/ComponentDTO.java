package com.bee32.sems.bom.dto;

import java.io.Serializable;
import java.util.Date;

import javax.free.NotImplementedException;
import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sems.bom.entity.Component;
import com.bee32.sems.material.dto.MaterialDTO;

public class ComponentDTO extends EntityDto<Component, Long> {


    private static final long serialVersionUID = 1L;

    private MaterialDTO material;
    private Long quantity;
    private String desc;
    private Boolean valid;
    private Date validDateFrom;
    private Date validDateTo;
    private Long productId;

    public ComponentDTO() {
        super();
    }

    public ComponentDTO(Component source) {
        super(source);
    }

    @Override
    protected void _marshal(Component source) {
        material = new MaterialDTO(source.getMaterial());
        quantity = source.getQuantity();
        desc = source.getDesc();
        valid = source.getValid();
        validDateFrom = source.getValidDateFrom();
        validDateTo = source.getValidDateTo();
        productId = source.getProduct() == null ? null : source.getProduct().getId();
    }

    @Override
    protected void _unmarshalTo(Component target) {
        throw new NotImplementedException();
    }

    @Override
    protected void _parse(TextMap map) throws ParseException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Date getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Date validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public Date getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Date validDateTo) {
        this.validDateTo = validDateTo;
    }
}
