package com.bee32.sems.bom.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.jsf.FacesContextUtils;

import com.bee32.sems.bom.dto.MaterialPriceStrategyDTO;
import com.bee32.sems.bom.entity.Strategy;
import com.bee32.sems.bom.service.BomServiceFacade;

@Component
@Scope("view")
public class MaterialPriceStrategyBean implements Serializable {
    private MaterialPriceStrategyDTO materialPriceStrategy;

    @PostConstruct
    public void init() {
        BomServiceFacade bomServiceFacade
                = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");
        materialPriceStrategy = bomServiceFacade.getMaterialPriceStragegy();
        if(materialPriceStrategy == null) {
            materialPriceStrategy = new MaterialPriceStrategyDTO();
        }
    }

    public MaterialPriceStrategyDTO getMaterialPriceStrategy() {
        return materialPriceStrategy;
    }

    public void setMaterialPriceStrategy(MaterialPriceStrategyDTO materialPriceStrategy) {
        this.materialPriceStrategy = materialPriceStrategy;
    }

    public List<SelectItem> getStrategies() {
        List strategies = new ArrayList();
        for(Strategy s : Strategy.values()) {
            strategies.add(new SelectItem(s.value(), s.text()));
        }
        return strategies;
    }




    public void save() {
        BomServiceFacade bomServiceFacade
                = (BomServiceFacade) FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance()).getBean("bomServiceFacadeImpl");
        bomServiceFacade.saveMaterialPriceStrategy(materialPriceStrategy);

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("提示", "物料价格获取策略设置成功!"));
    }
}
