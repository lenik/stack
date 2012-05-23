package com.bee32.sem.chance.web;

import java.util.List;

import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.Identities;
import com.bee32.plover.orm.util.RefsDiff;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.dto.ChanceSourceTypeDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.dto.WantedProductAttributeDto;
import com.bee32.sem.chance.dto.WantedProductDto;
import com.bee32.sem.chance.dto.WantedProductQuotationDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(Chance.class)
public class ChanceBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceStageDto searchStage = new ChanceStageDto().create();
    ChanceSourceTypeDto searchSource = new ChanceSourceTypeDto().create();

    public ChanceBean() {
        super(Chance.class, ChanceDto.class, 0);
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/
    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, Or.of(//
                CommonCriteria.labelledWith(searchPattern, true), //
                ChanceCriteria.subjectLike(searchPattern, true)));
        searchPattern = null;
    }

    public void addStageRestricion(){
        ChanceStage chanceStage = ctx.data.access(ChanceStage.class).lazyLoad(searchStage.getId());
        setSearchFragment("stage", "阶段为 " + chanceStage.getLabel(),//
                ChanceCriteria.stageOf(searchStage.getId()));
    }

    public void addSourceRestricion(){
        ChanceSourceType cst = ctx.data.access(ChanceSourceType.class).lazyLoad(searchSource.getId());
        setSearchFragment("source", "来源为 " + cst.getLabel(),//
                ChanceCriteria.sourceTypeOf(searchSource.getId()));
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<ChancePartyDto> partiesMBean = ListMBean.fromEL(this,//
            "openedObject.parties", ChancePartyDto.class);
    final ListMBean<ChanceActionDto> actionsMBean = ListMBean.fromEL(this,//
            "openedObject.actions", ChanceActionDto.class);
    final ListMBean<WantedProductDto> productsMBean = ListMBean.fromEL(this, //
            "openedObject.products", WantedProductDto.class);
    final ListMBean<WantedProductAttributeDto> productAttributesMBean = ListMBean.fromEL(productsMBean,
            "openedObject.attributes", WantedProductAttributeDto.class);
    final ListMBean<WantedProductQuotationDto> productQuotationsMBean = ListMBean.fromEL(productsMBean,
            "openedObject.quotations", WantedProductQuotationDto.class);

    public ListMBean<ChancePartyDto> getPartiesMBean() {
        return partiesMBean;
    }

    public ListMBean<ChanceActionDto> getActionsMBean() {
        return actionsMBean;
    }

    public ListMBean<WantedProductDto> getProductsMBean() {
        return productsMBean;
    }

    public ListMBean<WantedProductAttributeDto> getProductAttributesMBean() {
        return productAttributesMBean;
    }

    public ListMBean<WantedProductQuotationDto> getProductQuotationsMBean() {
        return productQuotationsMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean postValidate(List<?> dtos) {
        for (Object dto : dtos) {
            ChanceDto chance = (ChanceDto) dto;
            if (chance.getParties().isEmpty()) {
                uiLogger.error("错误提示", "请选择机会对应的客户!");
                return false;
            }
        }
        return true;
    }

    @Override
    protected Integer getFmaskOverride(int saveFlags) {
        return Fmask.F_MORE & ~ChanceDto.ACTIONS;
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        UnmarshalMap dActions = uMap.deltaMap("actions");
        dActions.setLabel("关联行动");
        dActions.setEntityClass(ChanceAction.class);

        for (Chance _chance : uMap.<Chance> entitySet()) {
            ChanceDto chance = uMap.getSourceDto(_chance);
            RefsDiff diff = Identities.compare(_chance.getActions(), chance.getActions());
            for (ChanceAction _detached : diff.<ChanceAction> leftOnly()) {
                _detached.setChance(null);
                dActions.put(_detached, null);
            }
            for (ChanceActionDto attached : diff.<ChanceActionDto> rightOnly()) {
                ChanceAction _attached = attached.unmarshal();
                _attached.setChance(_chance);
                dActions.put(_attached, attached);
            }
        }
        return true;
    }

    public ChanceStageDto getSearchStage() {
        return searchStage;
    }

    public void setSearchStage(ChanceStageDto searchStage) {
        this.searchStage = searchStage;
    }

    public ChanceSourceTypeDto getSearchSource() {
        return searchSource;
    }

    public void setSearchSource(ChanceSourceTypeDto searchSource) {
        this.searchSource = searchSource;
    }

}
