package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.chance.dto.BasePriceDto;
import com.bee32.sem.chance.entity.BasePrice;
import com.bee32.sem.chance.util.PriceCriteria;

@Component
@Scope("view")
public class BasePriceBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    BasePriceBean() {
        init();
    }

    private List<String> material;
    private String selectedMaterial;
    private double price;
    private String remark;

    private List<BasePriceDto> basePriceList;

    void init() {
        material = new ArrayList<String>();
        material.add("尼康D200");
        material.add("松下GF3");
        material.add("佳能D300");
        material.add("宾得XR");
        material.add("猪肉");
    }

    public void findMaterial() {
        if (!PriceViewBean.materialPattern.isEmpty()) {
            String pattern = PriceViewBean.materialPattern;
            List<String> temp = new ArrayList<String>();
            init();
            for (String s : material) {
                if (s.contains(pattern))
                    temp.add(s);
            }
            setMaterial(temp);
        } else {
            init();
        }
    }

    // 没用
    // public void listDetail() {
    // List<QuotationDetail> lqd = serviceFor(QuotationDetail.class).list(
    // PriceCriteria.listByMaterial(selectedMaterial));
    // detailList = DTOs.marshalList(QuotationDetailDto.class, lqd);
    // }
    List<BasePriceDto> listDetailByMaterial() {
        List<BasePrice> lqd = serviceFor(BasePrice.class).list(//
                Order.desc("createdDate"), PriceCriteria.listByMaterial(selectedMaterial));
        return DTOs.marshalList(BasePriceDto.class, lqd);
    }

    public void addQuotationDetail() {
        BasePrice basePrice = new BasePrice(selectedMaterial, price, remark);
        serviceFor(BasePrice.class).saveOrUpdate(basePrice);
        basePriceList = listDetailByMaterial();
    }

    public List<String> getMaterial() {
        return material;
    }

    public void setMaterial(List<String> material) {
        this.material = material;
    }

    public String getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(String selectedMaterial) {

        if (this.selectedMaterial != null && !selectedMaterial.isEmpty()) {
            if (!this.selectedMaterial.equals(selectedMaterial)) {
                List<BasePrice> lqd = serviceFor(BasePrice.class).list(//
                        Order.desc("createdDate"), //
                        PriceCriteria.listByMaterial(selectedMaterial));
                basePriceList = DTOs.marshalList(BasePriceDto.class, lqd);
            }
        } else if (this.selectedMaterial == null && !selectedMaterial.isEmpty()) {
            List<BasePrice> lqd = serviceFor(BasePrice.class).list(//
                    Order.desc("createdDate"), //
                    PriceCriteria.listByMaterial(selectedMaterial));
            basePriceList = DTOs.marshalList(BasePriceDto.class, lqd);
        }
        this.selectedMaterial = selectedMaterial;
    }

    public List<BasePriceDto> getBasePriceList() {
        return basePriceList;
    }

    public void setBasePriceList(List<BasePriceDto> basePriceList) {
        this.basePriceList = basePriceList;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
