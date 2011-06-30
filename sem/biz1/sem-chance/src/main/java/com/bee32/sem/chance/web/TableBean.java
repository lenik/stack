package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.orm.util.EntityViewBean;

@Component
@Scope("view")
public class TableBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    TableBean() {
        init();
    }

    private List<String> material;
    private String selectedMaterial;

    void init() {
        material = new ArrayList<String>();
        material.add("尼康D200");
        material.add("松下GF3");
        material.add("佳能D300");
        material.add("宾得XR");
    }

    public void findMaterial() {
        if (!PriceViewBean.materialPattern.isEmpty()) {
            String pattern = PriceViewBean.materialPattern;
            List<String> temp = new ArrayList<String>();
            for (String s : material) {
                if (s.contains(pattern))
                    temp.add(s);
            }
            setMaterial(temp);
        } else {
            init();
        }
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
        this.selectedMaterial = selectedMaterial;
    }
}
