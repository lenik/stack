package com.bee32.sem.chance.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceCategoryDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.dto.ChanceQuotationDto;
import com.bee32.sem.chance.dto.ChanceQuotationItemDto;
import com.bee32.sem.chance.dto.ChanceSourceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceQuotation;
import com.bee32.sem.chance.entity.ChanceQuotationItem;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.MCValue;

@Component
@Scope("view")
public class ChanceBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    public static final int TAB_INDEX = 0;
    public static final int TAB_FORM = 1;

    static final String FIELD_ACTIONHISTORY = "chanceForm:actionHistoryField";
    static final String FIELD_QUOTATION = "chanceForm:quotationField";

    static final String BUTTON_CHANCE_NEW = "chanceForm:chanceNewButton";
    static final String BUTTON_CHANCE_EDIT = "chanceForm:chanceEditButton";
    static final String BUTTON_CHANCE_DELETE = "chanceForm:chanceDeleteButton";
    static final String BUTTON_CHANCE_ADD = "chanceForm:chanceAddButtton";
    static final String BUTTON_CHANCE_RESET = "chanceForm:chanceResetButton";
    static final String BUTTON_CHANCE_DETAIL = "chanceForm:detailButton";

    // 判断是不是在查找状态
    private boolean isSearching;

    private boolean edable;
    private boolean relating = true;
    private boolean unRelating = true;
    private boolean actionDetail = true;
    private boolean editable;
    private boolean chancePartyEdit;
    private boolean roleRendered;
    private boolean isChancePartyEditing;

    private String subjectPattern;
    private String partyPattern;
    private Date searchBeginTime;
    private Date searchEndTime;
    private LazyDataModel<ChanceDto> chances;
    private ChanceDto selectedChance;
    private ChanceDto activeChance = new ChanceDto().create();
    private List<ChanceActionDto> actions;
    private ChanceActionDto[] selectedActions;
    private ChanceActionDto selectedAction;
    private ChanceActionDto tempAction = new ChanceActionDto();
    private ChancePartyDto selectedChanceParty;
    private PartyDto selectedParty;
    private List<PartyDto> parties;

    // quotation fields

    private boolean quotationOptionable;
    private boolean quotationEdit;
    private boolean quotationAdd;
    private boolean quotationDetailable = true;
    private boolean isPriceEditing;
    private boolean isQuantityEditing;

    private List<ChanceQuotationDto> quotations;
    private ChanceQuotationDto selectedQuotation;
    private ChanceQuotationDto quotation;
    private List<String> materials;
    private String selectedMaterial;
    private String materialPattern;
    private ChanceQuotationItemDto selectedQuotationItem;
    private MCValue temPrice = new MCValue();
    private BigDecimal temQuantity = new BigDecimal(0);
    private boolean quotationItemPriceRendered;
    private boolean quotationItemNumberRendered;

    // quotation methods

// void initMaterial() {
// materials = new ArrayList<String>();
// materials.add("尼康D200");
// materials.add("松下GF3");
// materials.add("佳能D300");
// materials.add("宾得XR");
// materials.add("猪肉");
// }

    public void findMaterial() {
        // XXX
// if (!materialPattern.isEmpty()) {
// List<String> temp = new ArrayList<String>();
// initMaterial();
// for (String s : materials) {
// if (s.contains(materialPattern))
// temp.add(s);
// }
// setMaterials(temp);
// } else {
// initMaterial();
// }
    }

    public void viewQuotationDetail() {
        if (selectedQuotation != null)
            quotation = selectedQuotation;
        quotationEdit = true;
    }

    public void viewActionDetail() {
        if (selectedAction != null)
            tempAction = selectedAction;
    }

    public void editQuotation() {
        if (selectedQuotation != null)
            quotation = selectedQuotation;
        quotationEdit = false;
    }

    public void chooseMaterial() {
        // XXX
// String sm = selectedMaterial;
// MaterialPrice currentPrice = serviceFor(MaterialPrice.class).list(//
// Order.desc("createdDate"), //
// PriceCriteria.listBasePriceByMaterial(sm)).get(0);
// ChanceQuotationItemDto qi = new ChanceQuotationItemDto().create();
// qi.setQuotation(quotation);
//
// qi.setBasePrice(DTOs.mref(MaterialPriceDto.class, currentPrice));
//
// qi.setMaterial(sm);
// quotation.addItem(qi);
    }

    void listQuotationByChance(ChanceDto chance) {
// List<ChanceQuotation> quotationList = serviceFor(ChanceQuotation.class).list(//
// Order.desc("createdDate"), //
// PriceCriteria.listQuotationByChance(chance.getId()));
// quotations = DTOs.marshalList(ChanceQuotationDto.class, quotationList);
    }

    public void calculatePriceChange() {
        // XXX
// double amount = selectedQuotationItem.getPrice() * selectedQuotationItem.getNumber();
// selectedQuotationItem.setAmount(amount);
// quotationItemPriceRendered = !quotationItemPriceRendered;
// isPriceEditing = false;
    }

    public void calculateNumberChange() {
        // XXX
// double amount = selectedQuotationItem.getPrice() * selectedQuotationItem.getNumber();
// selectedQuotationItem.setAmount(amount);
// quotationItemNumberRendered = !quotationItemNumberRendered;
// isQuantityEditing = false;
    }

    public void editPrice() {
        quotationItemPriceRendered = !quotationItemPriceRendered;
        temPrice = selectedQuotationItem.getPrice();
        isPriceEditing = true;
    }

// public void uneditPrice() {
// quotationItemPriceRendered = !quotationItemPriceRendered;
// selectedQuotationItem.setPrice(temPrice);
// }

// public void uneditQuantity() {
// quotationItemNumberRendered = !quotationItemNumberRendered;
// selectedQuotationItem.setNumber(temQuantity);
// }

    public void editQuantity() {
        quotationItemNumberRendered = !quotationItemNumberRendered;
        temQuantity = selectedQuotationItem.getQuantity();
        isQuantityEditing = true;
    }

    public void calculateQuotaionAmount() {
        BigDecimal total = new BigDecimal(0);
        BigDecimal itemQuantity = isQuantityEditing == true ? temQuantity : selectedQuotationItem.getQuantity();
        MCValue itemPrice = isPriceEditing == true ? temPrice : selectedQuotationItem.getPrice();
        if (isPriceEditing)
            selectedQuotationItem.setPrice(itemPrice);
        if (isQuantityEditing)
            selectedQuotationItem.setQuantity(itemQuantity);
        // quotation.getTotal();
    }

    @SuppressWarnings("unchecked")
    public void saveQuotation() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ChanceQuotation quotationEntity = quotation.unmarshal();
// if(quotationEntity.getChance().getId() == null)
// serviceFor(Chance)
            @SuppressWarnings("rawtypes")
            List<Entity> entityList = new ArrayList<Entity>();
            if (!quotations.contains(quotation))
                quotations.add(quotation);
            for (ChanceQuotationItem tempItem : quotationEntity.getItems()) {
                entityList.add(tempItem);
            }
            entityList.add(quotationEntity);
            serviceFor(Entity.class).saveOrUpdateAll(entityList);
            if (selectedQuotation == null)
                quotationOptionable = quotationDetailable = true;
            context.addMessage(null, new FacesMessage("提示", "保存报价单成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误提示", "保存报价单错误:" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void dropQuotation() {
        FacesContext context = FacesContext.getCurrentInstance();
        ChanceQuotation quo = selectedQuotation.unmarshal();
        try {
            serviceFor(ChanceQuotation.class).delete(quo);
            quotations.remove(selectedQuotation);
            quotationOptionable = quotationDetailable = true;
            context.addMessage(null, new FacesMessage("提示:", "成功删除报价单"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误提示:", "删除报价单失败" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void dropQuotationItem() {
        quotation.removeItem(selectedQuotationItem);
    }

    public void createQuotationForm() {
        quotation = new ChanceQuotationDto().create();
        quotation.setChance(activeChance);
    }

    public void onQuotationRowSelect() {
        if (editable != true)
            quotationOptionable = false;
        quotationDetailable = false;
    }

    public void onQuotationRowUnselect() {
        if (editable != true)
            quotationOptionable = true;
        quotationDetailable = true;
    }

    // chanceBean methods

    public void initList() {
        EntityDataModelOptions<Chance, ChanceDto> edmo = new EntityDataModelOptions<Chance, ChanceDto>(Chance.class,
                ChanceDto.class);
        edmo.setSelection(-1);
        edmo.setCriteriaElements(//
                Order.desc("createdDate"), //
                ChanceCriteria.ownedByCurrentUser());
        chances = UIHelper.buildLazyDataModel(edmo);
        isSearching = false;
        refreshChanceCount(isSearching);
        quotationOptionable = true;
    }

    public void initToolbar() {
        edable = false;
        boolean temp = selectedChance == null ? true : false;
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(temp);
    }

    @PostConstruct
    public void initialization() {
        initList();
        initToolbar();
        editable = false;
        activeChance = new ChanceDto();
        quotations = new ArrayList<ChanceQuotationDto>();
        relating = false;
        unRelating = false;
    }

    void refreshChanceCount(boolean forSearch) {
        int count = serviceFor(Chance.class).count(ChanceCriteria.ownedByCurrentUser(),//
                forSearch ? ChanceCriteria.subjectLike(subjectPattern) : null);
        chances.setRowCount(count);
    }

    public void onRowSelect() {
        findComponentEx(BUTTON_CHANCE_DETAIL).setEnabled(true);
    }

    public void onRowUnselect() {
        findComponentEx(BUTTON_CHANCE_DETAIL).setEnabled(false);
    }

    public void onActionRowSelect() {
        if (editable != true)
            unRelating = false;
        actionDetail = false;
    }

    public void onActionRowUnselect() {
        if (editable != true)
            unRelating = true;
        actionDetail = true;
    }

    public List<SelectItem> getCategory() {
        List<ChanceCategory> chanceCategoryList = serviceFor(ChanceCategory.class).list();
        List<ChanceCategoryDto> categoryDtoList = DTOs.marshalList(ChanceCategoryDto.class, chanceCategoryList);
        return UIHelper.selectItemsFromDict(categoryDtoList);
    }

    public List<SelectItem> getSource() {
        List<ChanceSourceType> sourceTypeList = serviceFor(ChanceSourceType.class).list();
        List<ChanceSourceDto> chanceSourceDtoList = DTOs.marshalList(ChanceSourceDto.class, sourceTypeList);
        return UIHelper.selectItemsFromDict(chanceSourceDtoList);
    }

    public List<SelectItem> getStage() {
        List<ChanceStage> chanceStageList = serviceFor(ChanceStage.class).list();
        List<ChanceStageDto> chanceStageDtoList = DTOs.marshalList(ChanceStageDto.class, chanceStageList);
        return UIHelper.selectItemsFromDict(chanceStageDtoList);
    }

    public void findParty() {
        if (!partyPattern.isEmpty()) {
            List<Party> _parties = serviceFor(Party.class).list(//
                    PeopleCriteria.ownedByCurrentUser(), //
                    PeopleCriteria.namedLike(partyPattern));
            parties = DTOs.marshalList(PartyDto.class, _parties);
        } else {
            List<Party> lp = serviceFor(Party.class).list(PeopleCriteria.ownedByCurrentUser());
            parties = DTOs.marshalList(PartyDto.class, lp);
        }
    }

    public void searchAction() {
        if (searchBeginTime != null && searchEndTime != null) {
            List<ChanceAction> _actions = serviceFor(ChanceAction.class).list(//
                    Order.desc("createdDate"), //
                    ChanceCriteria.actedByCurrentUser(), //
                    ChanceCriteria.beganWithin(searchBeginTime, searchEndTime), //
                    ChanceCriteria.danglingChance());
            actions = DTOs.marshalList(ChanceActionDto.class, _actions);
        } else {
            List<ChanceAction> lca = serviceFor(ChanceAction.class).list(//
                    Order.desc("createdDate"), //
                    ChanceCriteria.actedByCurrentUser(), //
                    ChanceCriteria.danglingChance());
            actions = DTOs.marshalList(ChanceActionDto.class, lca);
        }
    }

    public void searchChance() {
        if (subjectPattern != null) {
            EntityDataModelOptions<Chance, ChanceDto> edmo = new EntityDataModelOptions<Chance, ChanceDto>(
                    Chance.class, ChanceDto.class);
            edmo.setSelection(-1);
            edmo.setCriteriaElements(//
                    Order.desc("createdDate"), //
                    ChanceCriteria.ownedByCurrentUser(), //
                    ChanceCriteria.subjectLike(subjectPattern));
            chances = UIHelper.buildLazyDataModel(edmo);
            isSearching = true;
            refreshChanceCount(isSearching);
        } else {
            initList();
        }
        initToolbar();
        selectedChance = null;
        setActiveTab(TAB_INDEX);
    }

    public void doAttachActions() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedActions.length == 0) {
            return;
        }
        activeChance.addActions(selectedActions);
        Chance _chance = activeChance.unmarshal();
        try {
            for (ChanceAction _action : _chance.getActions()) {
                if (_action.getChance() == null)
                    _action.setChance(_chance);
                if (_action.getStage() == null)
                    _action.setStage(ChanceStage.INIT);
            }

            serviceFor(ChanceAction.class).saveOrUpdateAll(_chance.getActions());
            context.addMessage(null, new FacesMessage("提示", "关联成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误提示", "关联失败:" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void addChanceParty() {
        if (selectedParty == null)
            return;
        ChancePartyDto chancePartyDto = new ChancePartyDto().create();
        chancePartyDto.setChance(activeChance);
        chancePartyDto.setParty(selectedParty);
        chancePartyDto.setRole("普通客户");
        activeChance.addChanceParty(chancePartyDto);
    }

    public void createForm() {
        activeChance = new ChanceDto().create();
        setActiveTab(TAB_FORM);
        edable = true;
        boolean tempBoolean = selectedChance == null ? true : false;
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(tempBoolean);
        quotations = new ArrayList<ChanceQuotationDto>();
        findComponent(FIELD_ACTIONHISTORY).setRendered(false);
        findComponent(FIELD_QUOTATION).setRendered(false);
        quotationAdd = false;
        editable = false;
        relating = true;
        unRelating = true;
    }

    public void editForm() {
        if (selectedChance == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("提示:", "请选择需要修改的销售机会!"));
            return;
        }
        activeChance = selectedChance;
        listQuotationByChance(activeChance);
        setActiveTab(TAB_FORM);
        edable = true;
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(true);
        relating = false;
        unRelating = true;
        editable = false;
        findComponent(FIELD_ACTIONHISTORY).setRendered(true);
        findComponent(FIELD_QUOTATION).setRendered(true);
        quotationAdd = false;
    }

    public void detailForm() {
        setActiveChance(selectedChance);
        listQuotationByChance(activeChance);
        setActiveTab(TAB_FORM);
        edable = false;
        boolean tempBoolean = selectedChance == null ? true : false;
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(tempBoolean);

        Fieldset actionHistoryField = (Fieldset) findComponent(FIELD_ACTIONHISTORY);
        actionHistoryField.setCollapsed(false);
        actionHistoryField.setRendered(true);
        Fieldset quotationField = (Fieldset) findComponent(FIELD_QUOTATION);
        quotationField.setRendered(true);
        quotationField.setCollapsed(false);

        quotationAdd = true;
        editable = true;
    }

    public void cancel() {
        initToolbar();
        setActiveTab(TAB_INDEX);
        editable = false;
        findComponent(FIELD_ACTIONHISTORY).setRendered(false);
        findComponent(FIELD_QUOTATION).setRendered(false);
        quotationAdd = false;
    }

    public void editCustomerRole() {
        roleRendered = !roleRendered;
    }

    public void dropCustomer() {
        activeChance.deleteChanceParty(selectedChanceParty);
    }

    public void saveChance() {
        String subject = activeChance.getSubject();
        if (subject.isEmpty()) {
            uiLogger.error("错误提示", "机会标题不能为空!");
            return;
        }

        if (activeChance.getParties().isEmpty()) {
            uiLogger.error("错误提示", "请选择机会对应的客户!");
            return;
        }
        String content = activeChance.getContent();
        if (content.isEmpty())
            activeChance.setContent("");

        String categoryId = activeChance.getCategory().getId();
        ChanceCategoryDto ccd = null;
        if (!categoryId.isEmpty())
            ccd = new ChanceCategoryDto().ref(categoryId);
        activeChance.setCategory(ccd);

        String sourceId = activeChance.getSource().getId();
        ChanceSourceDto csd = null;
        if (!sourceId.isEmpty())
            csd = new ChanceSourceDto().ref(sourceId);
        activeChance.setSource(csd);

        ChanceStageDto tempStage = null;
        String chanceStageId = activeChance.getStage().getId();
        if (chanceStageId.isEmpty())
            tempStage = new ChanceStageDto().ref(ChanceStage.INIT.getId());
        else
            tempStage = new ChanceStageDto().ref(chanceStageId);

        activeChance.setStage(tempStage);

        Chance _chance = activeChance.unmarshal();

        try {

// //TODO 在新建机会的时候直接关联机会,则启用以下代码
// for (ChanceAction _action : _chance.getActions()) {
// _action.setChance(_chance);
// _action.setStage(ChanceStage.INIT);
// }
//
// serviceFor(ChanceAction.class).saveOrUpdateAll(_chance.getActions());
            serviceFor(Chance.class).saveOrUpdate(_chance);

            setActiveTab(TAB_INDEX);
            initToolbar();
            editable = true;
            findComponent(FIELD_ACTIONHISTORY).setRendered(false);
            findComponent(FIELD_QUOTATION).setRendered(false);
            uiLogger.info("提示", "保存销售机会成功");
        } catch (Exception e) {
            uiLogger.error("保存销售机会失败: " + e.getMessage(), e);
        }
    }

    public void drop() {
        if (selectedChance == null) {
            uiLogger.error("请选择需要删除的销售机会!");
            return;
        }
        try {
            Chance chanceToDelete = serviceFor(Chance.class).getOrFail(selectedChance.getId());
            for (ChanceAction _action : chanceToDelete.getActions()) {
                _action.setChance(null);
                _action.setStage(null);
                serviceFor(ChanceAction.class).save(_action);
            }
            serviceFor(Chance.class).delete(chanceToDelete);
            refreshChanceCount(isSearching);
            initToolbar();
            uiLogger.info("成功删除行动记录");
        } catch (Exception e) {
            uiLogger.error("删除销售机会失败:" + e.getMessage(), e);
        }
    }

    public void unRelating() {
        try {
            ChanceActionDto actionDto = selectedAction;
            ChanceAction chanceAction = actionDto.unmarshal();
            activeChance.deleteAction(actionDto);
            chanceAction.setChance(null);
            chanceAction.setStage(null);
            serviceFor(ChanceAction.class).save(chanceAction);
            unRelating = true;
            uiLogger.info("反关联成功");
        } catch (Exception e) {
            uiLogger.error("反关联失败:" + e.getMessage(), e);
        }
    }

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean isSearching) {
        this.isSearching = isSearching;
    }

    public boolean isEdable() {
        return edable;
    }

    public void setEdable(boolean edable) {
        this.edable = edable;
    }

    public boolean isRelating() {
        return relating;
    }

    public void setRelating(boolean relating) {
        this.relating = relating;
    }

    public boolean isUnRelating() {
        return unRelating;
    }

    public void setUnRelating(boolean unRelating) {
        this.unRelating = unRelating;
    }

    public boolean isActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(boolean actionDetail) {
        this.actionDetail = actionDetail;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isChancePartyEdit() {
        return chancePartyEdit;
    }

    public void setChancePartyEdit(boolean chancePartyEdit) {
        this.chancePartyEdit = chancePartyEdit;
    }

    public boolean isRoleRendered() {
        return roleRendered;
    }

    public void setRoleRendered(boolean roleRendered) {
        this.roleRendered = roleRendered;
    }

    public boolean isChancePartyEditing() {
        return isChancePartyEditing;
    }

    public void setChancePartyEditing(boolean isChancePartyEditing) {
        this.isChancePartyEditing = isChancePartyEditing;
    }

    public String getSubjectPattern() {
        return subjectPattern;
    }

    public void setSubjectPattern(String subjectPattern) {
        this.subjectPattern = subjectPattern;
    }

    public String getPartyPattern() {
        return partyPattern;
    }

    public void setPartyPattern(String partyPattern) {
        this.partyPattern = partyPattern;
    }

    public Date getSearchBeginTime() {
        return searchBeginTime;
    }

    public void setSearchBeginTime(Date searchBeginTime) {
        this.searchBeginTime = searchBeginTime;
    }

    public Date getSearchEndTime() {
        return searchEndTime;
    }

    public void setSearchEndTime(Date searchEndTime) {
        this.searchEndTime = searchEndTime;
    }

    public LazyDataModel<ChanceDto> getChances() {
        return chances;
    }

    public void setChances(LazyDataModel<ChanceDto> chances) {
        this.chances = chances;
    }

    public ChanceDto getSelectedChance() {
        return selectedChance;
    }

    public void setSelectedChance(ChanceDto selectedChance) {
        this.selectedChance = selectedChance;
    }

    public ChanceDto getActiveChance() {
        return activeChance;
    }

    public void setActiveChance(ChanceDto chance) {
        this.activeChance = chance;
    }

    public List<ChanceActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ChanceActionDto> actions) {
        this.actions = actions;
    }

    public ChanceActionDto[] getSelectedActions() {
        return selectedActions;
    }

    public void setSelectedActions(ChanceActionDto[] selectedActions) {
        this.selectedActions = selectedActions;
    }

    public ChanceActionDto getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(ChanceActionDto selectedAction) {
        this.selectedAction = selectedAction;
    }

    public ChanceActionDto getTempAction() {
        return tempAction;
    }

    public void setTempAction(ChanceActionDto tempAction) {
        this.tempAction = tempAction;
    }

    public ChancePartyDto getSelectedChanceParty() {
        return selectedChanceParty;
    }

    public void setSelectedChanceParty(ChancePartyDto selectedChanceParty) {
        this.selectedChanceParty = selectedChanceParty;
    }

    public PartyDto getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(PartyDto selectedParty) {
        this.selectedParty = selectedParty;
    }

    public List<PartyDto> getParties() {
        return parties;
    }

    public void setParties(List<PartyDto> parties) {
        this.parties = parties;
    }

    public boolean isQuotationOptionable() {
        return quotationOptionable;
    }

    public void setQuotationOptionable(boolean quotationOptionable) {
        this.quotationOptionable = quotationOptionable;
    }

    public boolean isQuotationEdit() {
        return quotationEdit;
    }

    public void setQuotationEdit(boolean quotationEdit) {
        this.quotationEdit = quotationEdit;
    }

    public boolean isQuotationAdd() {
        return quotationAdd;
    }

    public void setQuotationAdd(boolean quotationAdd) {
        this.quotationAdd = quotationAdd;
    }

    public boolean isQuotationDetailable() {
        return quotationDetailable;
    }

    public void setQuotationDetailable(boolean quotationDetailable) {
        this.quotationDetailable = quotationDetailable;
    }

    public boolean isPriceEditing() {
        return isPriceEditing;
    }

    public void setPriceEditing(boolean isPriceEditing) {
        this.isPriceEditing = isPriceEditing;
    }

    public boolean isQuantityEditing() {
        return isQuantityEditing;
    }

    public void setQuantityEditing(boolean isQuantityEditing) {
        this.isQuantityEditing = isQuantityEditing;
    }

    public List<ChanceQuotationDto> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<ChanceQuotationDto> quotations) {
        this.quotations = quotations;
    }

    public ChanceQuotationDto getSelectedQuotation() {
        return selectedQuotation;
    }

    public void setSelectedQuotation(ChanceQuotationDto selectedQuotation) {
        this.selectedQuotation = selectedQuotation;
    }

    public ChanceQuotationDto getQuotation() {
        return quotation;
    }

    public void setQuotation(ChanceQuotationDto quotation) {
        this.quotation = quotation;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public String getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(String selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

    public String getMaterialPattern() {
        return materialPattern;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public ChanceQuotationItemDto getSelectedQuotationItem() {
        return selectedQuotationItem;
    }

    public void setSelectedQuotationItem(ChanceQuotationItemDto selectedQuotationItem) {
        this.selectedQuotationItem = selectedQuotationItem;
    }

    public MCValue getTemPrice() {
        return temPrice;
    }

    public BigDecimal getTemQuantity() {
        return temQuantity;
    }

    public void setTemQuantity(BigDecimal temQuantity) {
        this.temQuantity = temQuantity;
    }

    public boolean isQuotationItemPriceRendered() {
        return quotationItemPriceRendered;
    }

    public void setQuotationItemPriceRendered(boolean quotationItemPriceRendered) {
        this.quotationItemPriceRendered = quotationItemPriceRendered;
    }

    public boolean isQuotationItemNumberRendered() {
        return quotationItemNumberRendered;
    }

    public void setQuotationItemNumberRendered(boolean quotationItemNumberRendered) {
        this.quotationItemNumberRendered = quotationItemNumberRendered;
    }

}
