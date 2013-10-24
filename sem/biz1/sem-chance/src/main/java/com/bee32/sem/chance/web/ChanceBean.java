package com.bee32.sem.chance.web;

import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.InCollection;
import com.bee32.plover.criteria.hibernate.Not;
import com.bee32.plover.criteria.hibernate.Or;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.Identities;
import com.bee32.plover.orm.util.RefsDiff;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.sem.chance.dto.*;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.file.dto.UserFileDto;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;

@ForEntity(Chance.class)
public class ChanceBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ChanceStageDto searchStage = new ChanceStageDto().create();
    ChanceSourceTypeDto searchSource = new ChanceSourceTypeDto().create();
    Date anticipation_begin;
    Date anticipation_end;

    public ChanceBean() {
        super(Chance.class, ChanceDto.class, 0);
    }

    /**
     * 用户每选择一次userFile,都调用本方法。 由ChanceDto.addAttachment()来记录多次选择的结果
     */
    public void setAttachmentToAttach(UserFileDto attachment) {
        if (attachment == null)
            return;

        ChanceDto chance = getOpenedObject();

        chance.addAttachment(attachment);
    }

    public void setActionToAdd(ChanceActionDto action) {
        if (isCreating())
            save();
        actionsMBean.setAddition(action);
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    boolean showTerminated = false;

    public boolean isShowTerminated() {
        return showTerminated;
    }

    public void setShowTerminated(boolean showTerminated) {
        this.showTerminated = showTerminated;
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        List<ChanceStage> closedStages = DATA(ChanceStage.class).list(new Equals("closed", true));
        if (showTerminated) {
            elements.add(new InCollection("stage", closedStages));
        } else {
            elements.add(Not.of(new InCollection("stage", closedStages)));
        }
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, Or.of(//
                CommonCriteria.labelledWith(searchPattern, true), //
                ChanceCriteria.subjectLike(searchPattern, true)));
        searchPattern = null;
    }

    public void addStageRestricion() {
        ChanceStage chanceStage = DATA(ChanceStage.class).lazyLoad(searchStage.getId());
        setSearchFragment("stage", "阶段为 " + chanceStage.getLabel(),//
                ChanceCriteria.stageOf(searchStage.getId()));
    }

    public void addSourceRestricion() {
        ChanceSourceType cst = DATA(ChanceSourceType.class).lazyLoad(searchSource.getId());
        setSearchFragment("source", "来源为 " + cst.getLabel(),//
                ChanceCriteria.sourceTypeOf(searchSource.getId()));
    }

    public void addProcurementDateRestrictions() {
        setSearchFragment("procurement", "预期订货日期包含在" + anticipation_begin + "到" + anticipation_end + "之间",//
                ChanceCriteria.findByRange(anticipation_begin, anticipation_end));
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<ChancePartyDto> partiesMBean = ListMBean.fromEL(this,//
            "openedObject.parties", ChancePartyDto.class);
    final ListMBean<ChanceCompetitorDto> competitoriesMBean = ListMBean.fromEL(this,//
            "openedObject.competitories", ChanceCompetitorDto.class);
    final ListMBean<ChanceActionDto> actionsMBean = ListMBean.fromEL(this,//
            "openedObject.actions", ChanceActionDto.class);
// final ListMBean<ChanceCompetitorDto> competitories = ListMBean.fromEL(this,//
// "openedObject.competitories", ChanceCompetitorDto.class);
    final ListMBean<WantedProductDto> productsMBean = ListMBean.fromEL(this, //
            "openedObject.products", WantedProductDto.class);
    final ListMBean<WantedProductAttributeDto> productAttributesMBean = ListMBean.fromEL(productsMBean,
            "openedObject.attributes", WantedProductAttributeDto.class);
    final ListMBean<WantedProductQuotationDto> productQuotationsMBean = ListMBean.fromEL(productsMBean,
            "openedObject.quotations", WantedProductQuotationDto.class);
    final ListMBean<UserFileDto> attachmentsMBean = ListMBean.fromEL(this,//
            "openedObject.attachments", UserFileDto.class);

    public ListMBean<ChancePartyDto> getPartiesMBean() {
        return partiesMBean;
    }

    public ListMBean<ChanceActionDto> getActionsMBean() {
        return actionsMBean;
    }

    public ListMBean<ChanceCompetitorDto> getCompetitoriesMBean() {
        return competitoriesMBean;
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

    public ListMBean<UserFileDto> getAttachmentsMBean() {
        return attachmentsMBean;
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

    public Date getAnticipation_begin() {
        return anticipation_begin;
    }

    public void setAnticipation_begin(Date anticipation_begin) {
        this.anticipation_begin = anticipation_begin;
    }

    public Date getAnticipation_end() {
        return anticipation_end;
    }

    public void setAnticipation_end(Date anticipation_end) {
        this.anticipation_end = anticipation_end;
    }

}
