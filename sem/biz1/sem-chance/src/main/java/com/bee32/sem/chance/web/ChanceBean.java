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
import com.bee32.plover.orm.ext.tree.TreeCriteria;
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
import com.bee32.sem.frame.ui.SelectionAdapter;
import com.bee32.sem.frame.ui.SelectionEvent;
import com.bee32.sem.inventory.dto.MaterialCategoryDto;
import com.bee32.sem.inventory.dto.MaterialDto;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.util.MaterialCriteria;
import com.bee32.sem.inventory.web.dialogs.MaterialCategoryTreeModel;
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
    static final String BUTTON_CHANCE_ADD = "chanceForm:chanceAddButton";
    static final String BUTTON_CHANCE_RESET = "chanceForm:chanceResetButton";
    static final String BUTTON_CHANCE_DETAIL = "chanceForm:chanceDetailButton";
    static final String BUTTON_CHANCE_RELATEACTION = "chanceForm:relateActionButton";
    static final String BUTTON_CHANCE_UNRELATEACTION = "chanceForm:unrelateActionButton";
    static final String BUTTON_CHANCE_VIEWACTION = "chanceForm:viewActionDetailButton";

    static final String BUTTON_QUOTATION_NEW = "chanceForm:chanceNewQuotationButton";
    static final String BUTTON_QUOTATION_EDIT = "chanceForm:chanceEditQuotationButton";
    static final String BUTTON_QUOTATION_DELETE = "chanceForm:chanceDeleteQuotationButton";
    static final String BUTTON_QUOTATION_VIEW = "chanceForm:chanceViewQuotationButton";

    // 判断是不是在查找状态
    boolean isSearching;

    boolean editable;
    boolean chancePartyEdit;
    boolean roleRendered;
    boolean isChancePartyEditing;

    String subjectPattern;
    String partyPattern;
    Date searchBeginTime;
    Date searchEndTime;
    LazyDataModel<ChanceDto> chances;
    ChanceDto selectedChance;
    ChanceDto activeChance = new ChanceDto().create();
    List<ChanceActionDto> actions;
    ChanceActionDto[] selectedActions;
    ChanceActionDto selectedAction;
    ChanceActionDto activeAction = new ChanceActionDto().create();
    ChancePartyDto selectedChanceParty;
    PartyDto selectedParty;
    List<PartyDto> parties;

    // quotation fields

    boolean quotationEdit;
    boolean isPriceEditing;
    boolean isQuantityEditing;

    List<ChanceQuotationDto> quotations;
    ChanceQuotationDto selectedQuotation;
    ChanceQuotationDto activeQuotation;
    String materialPattern;
    ChanceQuotationItemDto selectedQuotationItem;
    MCValue temPrice = new MCValue();
    BigDecimal temQuantity = new BigDecimal(0);
    boolean quotationItemPriceRendered;
    boolean quotationItemNumberRendered;

    MaterialCategoryTreeModel materialTree;
    List<MaterialDto> materialList;
    MaterialDto selectedMaterial;

    ChanceBean() {
        CommandButton button_new = (CommandButton) findComponent(BUTTON_CHANCE_NEW);
        button_new.setDisabled(false);
        CommandButton button_edit = (CommandButton) findComponent(BUTTON_CHANCE_EDIT);
        button_edit.setDisabled(false);
        CommandButton button_delete = (CommandButton) findComponent(BUTTON_CHANCE_DELETE);
        button_delete.setDisabled(false);
        CommandButton button_add = (CommandButton) findComponent(BUTTON_CHANCE_ADD);
        button_add.setDisabled(true);
        CommandButton button_reset = (CommandButton) findComponent(BUTTON_CHANCE_RESET);
        button_reset.setDisabled(true);
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(true);

        CommandButton button_relating = (CommandButton) findComponent(BUTTON_CHANCE_RELATEACTION);
        button_relating.setDisabled(true);
        CommandButton button_unrelating = (CommandButton) findComponent(BUTTON_CHANCE_UNRELATEACTION);
        button_unrelating.setDisabled(true);
        CommandButton button_viewAction = (CommandButton) findComponent(BUTTON_CHANCE_VIEWACTION);
        button_viewAction.setDisabled(true);

        initMaterialCategoryTree();
    }

    @PostConstruct
    public void initialization() {
        initList();
        editable = false;
        quotations = new ArrayList<ChanceQuotationDto>();
// relating = false;
// unRelating = false;
    }

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
            activeQuotation = selectedQuotation;
        quotationEdit = true;
    }

    public void viewActionDetail() {
        if (selectedAction != null)
            activeAction = selectedAction;
    }

    public void editQuotation() {
        if (selectedQuotation != null)
            activeQuotation = selectedQuotation;
        quotationEdit = false;
    }

    public void chooseMaterial() {
        MaterialDto mdto = null;
        if (selectedMaterial != null)
            mdto = selectedMaterial;
        ChanceQuotationItemDto item = new ChanceQuotationItemDto().create();
        item.setParent(activeQuotation);
        item.setMaterial(mdto);
        item.setDiscount(1f);
        activeQuotation.addItem(item);
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
        selectedQuotationItem.setPriceValue(new BigDecimal(19));
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
        ChanceQuotation quotationEntity = activeQuotation.unmarshal();
        if (quotationEntity.getItems() == null || quotationEntity.getItems().size() == 0) {
            uiLogger.error("错误提示:", "请添加明细");
            return;
        }
        @SuppressWarnings("rawtypes")
        List<Entity> entityList = new ArrayList<Entity>();
        if (!quotations.contains(activeQuotation))
            quotations.add(activeQuotation);
        for (ChanceQuotationItem tempItem : quotationEntity.getItems()) {
            entityList.add(tempItem);
        }
        entityList.add(quotationEntity);

        try {
// if(quotationEntity.getChance().getId() == null)
// serviceFor(Chance)
            serviceFor(Entity.class).saveOrUpdateAll(entityList);
            if (selectedQuotation == null) {
                CommandButton button_quotation_edit = (CommandButton) findComponent(BUTTON_QUOTATION_EDIT);
                button_quotation_edit.setDisabled(true);
                CommandButton button_quotation_delete = (CommandButton) findComponent(BUTTON_QUOTATION_DELETE);
                button_quotation_delete.setDisabled(true);
                CommandButton button_quotation_view = (CommandButton) findComponent(BUTTON_QUOTATION_VIEW);
                button_quotation_view.setDisabled(true);
            }
            uiLogger.info("提示", "保存报价单成功");
        } catch (Exception e) {
            uiLogger.error("保存报价单错误:" + e.getMessage(), e);
        }
    }

    public void dropQuotation() {
        ChanceQuotation quo = selectedQuotation.unmarshal();
        try {
            serviceFor(ChanceQuotation.class).delete(quo);
            quotations.remove(selectedQuotation);
            CommandButton button_quotation_edit = (CommandButton) findComponent(BUTTON_QUOTATION_EDIT);
            button_quotation_edit.setDisabled(true);
            CommandButton button_quotation_delete = (CommandButton) findComponent(BUTTON_QUOTATION_DELETE);
            button_quotation_delete.setDisabled(true);
            CommandButton button_quotation_viwe = (CommandButton) findComponent(BUTTON_QUOTATION_VIEW);
            button_quotation_viwe.setDisabled(true);
            uiLogger.info("提示:", "成功删除报价单");
        } catch (Exception e) {
            uiLogger.error("删除报价单失败" + e.getMessage(), e);
        }
    }

    public void dropQuotationItem() {
        activeQuotation.removeItem(selectedQuotationItem);
    }

    public void createQuotationForm() {
        activeQuotation = new ChanceQuotationDto().create();
        activeQuotation.setChance(activeChance);
    }

    public void onQuotationRowSelect() {
        if (editable != true) {
            CommandButton button_edit = (CommandButton) findComponent(BUTTON_QUOTATION_EDIT);
            button_edit.setDisabled(false);
            CommandButton button_delete = (CommandButton) findComponent(BUTTON_QUOTATION_DELETE);
            button_delete.setDisabled(false);
        }
        CommandButton button_quotation_view = (CommandButton) findComponent(BUTTON_QUOTATION_VIEW);
        button_quotation_view.setDisabled(false);
    }

    public void onQuotationRowUnselect() {
        if (editable != true) {
            CommandButton button_edit = (CommandButton) findComponent(BUTTON_QUOTATION_EDIT);
            button_edit.setDisabled(true);
            CommandButton button_delete = (CommandButton) findComponent(BUTTON_QUOTATION_DELETE);
            button_delete.setDisabled(true);
        }
        CommandButton button_quotation_view = (CommandButton) findComponent(BUTTON_QUOTATION_VIEW);
        button_quotation_view.setDisabled(true);
    }

    /** 生成物料树 */
    public void initMaterialCategoryTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.marshalList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories);

        materialTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialTree.addListener(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();
                List<Material> _materials = serviceFor(Material.class).list(//
                        // Order.asc("name"),
                        MaterialCriteria.categoryOf(materialCategoryDto.getId()));
                materialList = DTOs.marshalList(MaterialDto.class, _materials, true);
            }
        });
    }

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
        CommandButton button_quotation_edit = (CommandButton) findComponent(BUTTON_QUOTATION_EDIT);
        button_quotation_edit.setDisabled(true);
        CommandButton button_quotation_delete = (CommandButton) findComponent(BUTTON_QUOTATION_DELETE);
        button_quotation_delete.setDisabled(true);
    }

    public void initToolbar() {
        CommandButton button_new = (CommandButton) findComponent(BUTTON_CHANCE_NEW);
        button_new.setDisabled(false);
        CommandButton button_edit = (CommandButton) findComponent(BUTTON_CHANCE_EDIT);
        button_edit.setDisabled(false);
        CommandButton button_delete = (CommandButton) findComponent(BUTTON_CHANCE_DELETE);
        button_delete.setDisabled(false);
        CommandButton button_add = (CommandButton) findComponent(BUTTON_CHANCE_ADD);
        button_add.setDisabled(true);
        CommandButton button_reset = (CommandButton) findComponent(BUTTON_CHANCE_RESET);
        button_reset.setDisabled(true);
        boolean temp = selectedChance == null ? true : false;
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(temp);
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
        if (editable != true) {
            CommandButton button_unrelate = (CommandButton) findComponent(BUTTON_CHANCE_UNRELATEACTION);
            button_unrelate.setDisabled(false);
        }

        CommandButton button_viewAction = (CommandButton) findComponent(BUTTON_CHANCE_VIEWACTION);
        button_viewAction.setDisabled(false);
    }

    public void onActionRowUnselect() {
        if (editable != true) {
            CommandButton button_unrelate = (CommandButton) findComponent(BUTTON_CHANCE_UNRELATEACTION);
            button_unrelate.setDisabled(true);
        }

        CommandButton button_viewAction = (CommandButton) findComponent(BUTTON_CHANCE_VIEWACTION);
        button_viewAction.setDisabled(true);
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
        editable = false;
        activeChance = new ChanceDto().create();
        quotations = new ArrayList<ChanceQuotationDto>();
        setActiveTab(TAB_FORM);

        CommandButton button_quotation_add = (CommandButton) findComponent(BUTTON_QUOTATION_NEW);
        button_quotation_add.setDisabled(false);
        CommandButton button_quotation_view = (CommandButton) findComponent(BUTTON_QUOTATION_VIEW);
        button_quotation_view.setDisabled(true);

        CommandButton button_new = (CommandButton) findComponent(BUTTON_CHANCE_NEW);
        button_new.setDisabled(true);
        CommandButton button_edit = (CommandButton) findComponent(BUTTON_CHANCE_EDIT);
        button_edit.setDisabled(true);
        CommandButton button_delete = (CommandButton) findComponent(BUTTON_CHANCE_DELETE);
        button_delete.setDisabled(true);
        CommandButton button_add = (CommandButton) findComponent(BUTTON_CHANCE_ADD);
        button_add.setDisabled(false);
        CommandButton button_reset = (CommandButton) findComponent(BUTTON_CHANCE_RESET);
        button_reset.setDisabled(false);
// boolean tempBoolean = selectedChance == null ? true : false;
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(true);
        findComponent(FIELD_ACTIONHISTORY).setRendered(false);
        findComponent(FIELD_QUOTATION).setRendered(false);

// CommandButton button_relate = (CommandButton) findComponent(BUTTON_CHANCE_RELATEACTION);
// button_relate.setDisabled(true);
// CommandButton button_unrelate = (CommandButton) findComponent(BUTTON_CHANCE_UNRELATEACTION);
// button_unrelate.setDisabled(true);
    }

    public void editForm() {
        if (selectedChance == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("提示:", "请选择需要修改的销售机会!"));
            return;
        }
        setActiveChance(selectedChance);
        listQuotationByChance(activeChance);
        setActiveTab(TAB_FORM);

        CommandButton button_new = (CommandButton) findComponent(BUTTON_CHANCE_NEW);
        button_new.setDisabled(true);
        CommandButton button_edit = (CommandButton) findComponent(BUTTON_CHANCE_EDIT);
        button_edit.setDisabled(true);
        CommandButton button_delete = (CommandButton) findComponent(BUTTON_CHANCE_DELETE);
        button_delete.setDisabled(true);
        CommandButton button_add = (CommandButton) findComponent(BUTTON_CHANCE_ADD);
        button_add.setDisabled(false);
        CommandButton button_reset = (CommandButton) findComponent(BUTTON_CHANCE_RESET);
        button_reset.setDisabled(false);

        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(true);

        CommandButton button_relate = (CommandButton) findComponent(BUTTON_CHANCE_RELATEACTION);
        button_relate.setDisabled(false);
        CommandButton button_unrelate = (CommandButton) findComponent(BUTTON_CHANCE_UNRELATEACTION);
        button_unrelate.setDisabled(true);

        editable = false;
        findComponent(FIELD_ACTIONHISTORY).setRendered(true);
        findComponent(FIELD_QUOTATION).setRendered(true);

        CommandButton button_quotation_new = (CommandButton) findComponent(BUTTON_QUOTATION_NEW);
        button_quotation_new.setDisabled(false);
        CommandButton button_quotation_view = (CommandButton) findComponent(BUTTON_QUOTATION_VIEW);
        button_quotation_view.setDisabled(true);
    }

    public void detailForm() {
        setActiveChance(selectedChance);
        listQuotationByChance(activeChance);
        setActiveTab(TAB_FORM);

        CommandButton button_new = (CommandButton) findComponent(BUTTON_CHANCE_NEW);
        button_new.setDisabled(false);
        CommandButton button_edit = (CommandButton) findComponent(BUTTON_CHANCE_EDIT);
        button_edit.setDisabled(false);
        CommandButton button_delete = (CommandButton) findComponent(BUTTON_CHANCE_DELETE);
        button_delete.setDisabled(false);
        CommandButton button_add = (CommandButton) findComponent(BUTTON_CHANCE_ADD);
        button_add.setDisabled(true);
        CommandButton button_reset = (CommandButton) findComponent(BUTTON_CHANCE_RESET);
        button_reset.setDisabled(true);

        boolean tempBoolean = selectedChance == null ? true : false;
        CommandButton button_detail = (CommandButton) findComponent(BUTTON_CHANCE_DETAIL);
        button_detail.setDisabled(tempBoolean);

        Fieldset actionHistoryField = (Fieldset) findComponent(FIELD_ACTIONHISTORY);
        actionHistoryField.setCollapsed(false);
        actionHistoryField.setRendered(true);
        Fieldset quotationField = (Fieldset) findComponent(FIELD_QUOTATION);
        quotationField.setRendered(true);
        quotationField.setCollapsed(false);

        CommandButton button_relate = (CommandButton) findComponent(BUTTON_CHANCE_RELATEACTION);
        button_relate.setDisabled(true);
        CommandButton button_unrelate = (CommandButton) findComponent(BUTTON_CHANCE_UNRELATEACTION);
        button_unrelate.setDisabled(true);

        CommandButton button_quotation_view = (CommandButton) findComponent(BUTTON_QUOTATION_VIEW);
        button_quotation_view.setDisabled(true);
        CommandButton button_quotation_new = (CommandButton) findComponent(BUTTON_QUOTATION_NEW);
        button_quotation_new.setDisabled(true);
        editable = true;
    }

    public void cancel() {
        initToolbar();
        setActiveTab(TAB_INDEX);
        editable = false;
        findComponent(FIELD_ACTIONHISTORY).setRendered(false);
        findComponent(FIELD_QUOTATION).setRendered(false);
        CommandButton button_quotation_new = (CommandButton) findComponent(BUTTON_QUOTATION_NEW);
        button_quotation_new.setDisabled(false);
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
            CommandButton button_unrelate = (CommandButton) findComponent(BUTTON_CHANCE_UNRELATEACTION);
            button_unrelate.setDisabled(true);
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

    public ChanceActionDto getActiveAction() {
        return activeAction;
    }

    public void setActiveAction(ChanceActionDto activeAction) {
        this.activeAction = activeAction;
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

    public boolean isQuotationEdit() {
        return quotationEdit;
    }

    public void setQuotationEdit(boolean quotationEdit) {
        this.quotationEdit = quotationEdit;
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

    public ChanceQuotationDto getActiveQuotation() {
        return activeQuotation;
    }

    public void setActiveQuotation(ChanceQuotationDto activeQuotation) {
        this.activeQuotation = activeQuotation;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
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

    public MaterialCategoryTreeModel getMaterialTree() {
        return materialTree;
    }

    public List<MaterialDto> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<MaterialDto> materialList) {
        this.materialList = materialList;
    }

}
