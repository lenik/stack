package com.bee32.sem.chance.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.lang.Strings;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.tree.TreeCriteria;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceCategoryDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.dto.ChanceQuotationDto;
import com.bee32.sem.chance.dto.ChanceQuotationItemDto;
import com.bee32.sem.chance.dto.ChanceSourceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceQuotation;
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
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.world.monetary.MCValue;

@Component
@Scope("view")
public class ChanceBean
        extends ChanceBeanVdx {

    private static final long serialVersionUID = 1L;

    // 判断是不是在查找状态

    String subjectPattern;
    String partyPattern;
    Date searchBeginTime;
    Date searchEndTime;
    LazyDataModel<ChanceDto> chances;
    ChanceDto selectedChance;
    ChanceDto activeChance = new ChanceDto().create();
    List<ChanceActionDto> activeActions;
    ChanceActionDto[] selectedActions;
    ChanceActionDto selectedAction;
    ChanceActionDto activeAction = new ChanceActionDto().create();
    ChancePartyDto selectedChanceParty;
    PartyDto selectedParty;
    List<PartyDto> parties;

    // quotation fields

    List<ChanceQuotationDto> quotations;
    ChanceQuotationDto selectedQuotation;
    ChanceQuotationDto activeQuotation;
    String materialPattern;
    ChanceQuotationItemDto selectedQuotationItem;
    MCValue temPrice = new MCValue();
    BigDecimal temQuantity = new BigDecimal(0);

    MaterialCategoryTreeModel materialTree;
    List<MaterialDto> materialList;
    MaterialDto selectedMaterial;

    ChanceBean() {
        findComponentEx(BUTTON_CHANCE_NEW).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_EDIT).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_DELETE).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_ADD).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_RESET).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_DETAIL).setEnabled(false);

        findComponentEx(BUTTON_CHANCE_RELATEACTION).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_UNRELATEACTION).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_VIEWACTION).setEnabled(false);

        findComponent(OUTPUT_DISCOUNT).setRendered(true);
        findComponent(INPUT_DISCOUNT).setRendered(false);
        findComponent(COMMANDLINK_EDITDISCOUNT).setRendered(true);
        findComponent(COMMANDLINK_SUREDISCOUNT).setRendered(false);

        findComponent(OUTPUT_PRICE).setRendered(true);
        findComponent(INPUT_PRICE).setRendered(false);
        findComponent(COMMANDLINK_EDITPRICE).setRendered(true);
        findComponent(COMMANDLINK_SUREPIRCE).setRendered(false);

        findComponent(OUTPUT_QUANTITY).setRendered(true);
        findComponent(INPUT_QUANTITY).setRendered(false);
        findComponent(COMMANDLINK_EDITQUANTITY).setRendered(true);
        findComponent(COMMANDLINK_SUREQUANTITY).setRendered(false);

        initMaterialCategoryTree();
    }

    @PostConstruct
    public void initialization() {
        initList();
        editable = false;
        quotations = new ArrayList<ChanceQuotationDto>();
    }

    public void findMaterial() {
        List<Material> _materials = serviceFor(Material.class).list(MaterialCriteria.namedLike(materialPattern));
        materialList = DTOs.marshalList(MaterialDto.class, _materials);
    }

    public void viewQuotationDetail() {
        if (selectedQuotation != null)
            activeQuotation = selectedQuotation;
        quotationEdit = true;
    }

    public void viewActionDetail() {
        if (selectedAction == null) {
            uiLogger.error("提示:", "请选择行动记录");
            return;
        }
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
        List<ChanceQuotation> quotationList = serviceFor(ChanceQuotation.class).list(
                ChanceCriteria.chanceEquals(activeChance));
        quotations = DTOs.marshalList(ChanceQuotationDto.class, quotationList);
    }

    public void calculatePriceChange() {
        MCValue _price = new MCValue(selectedQuotationItem.getPriceCurrency(), selectedQuotationItem.getViewPrice());
        selectedQuotationItem.setPrice(_price);
        findComponent(OUTPUT_PRICE).setRendered(true);
        findComponent(INPUT_PRICE).setRendered(false);
        findComponent(COMMANDLINK_EDITPRICE).setRendered(true);
        findComponent(COMMANDLINK_SUREPIRCE).setRendered(false);
        isPriceEditing = false;
    }

    public void calculateNumberChange() {
        findComponent(OUTPUT_QUANTITY).setRendered(true);
        findComponent(INPUT_QUANTITY).setRendered(false);
        findComponent(COMMANDLINK_EDITQUANTITY).setRendered(true);
        findComponent(COMMANDLINK_SUREQUANTITY).setRendered(false);
        isQuantityEditing = false;
    }

    public void editPrice() {
        findComponent(OUTPUT_PRICE).setRendered(false);
        findComponent(INPUT_PRICE).setRendered(true);
        findComponent(COMMANDLINK_EDITPRICE).setRendered(false);
        findComponent(COMMANDLINK_SUREPIRCE).setRendered(true);
        temPrice = selectedQuotationItem.getPrice();
        isPriceEditing = true;
    }

    public void editDiscount() {
        findComponent(OUTPUT_DISCOUNT).setRendered(false);
        findComponent(INPUT_DISCOUNT).setRendered(true);
        findComponent(COMMANDLINK_EDITDISCOUNT).setRendered(false);
        findComponent(COMMANDLINK_SUREDISCOUNT).setRendered(true);
        isDiscountEdting = true;
    }

    public void sureDiscount() {
        findComponent(OUTPUT_DISCOUNT).setRendered(true);
        findComponent(INPUT_DISCOUNT).setRendered(false);
        findComponent(COMMANDLINK_EDITDISCOUNT).setRendered(true);
        findComponent(COMMANDLINK_SUREDISCOUNT).setRendered(false);
        isDiscountEdting = false;
    }

    public List<SelectItem> getChanceActionStyles() {
        List<ChanceActionStyle> chanceActionStyleList = serviceFor(ChanceActionStyle.class).list();
        List<ChanceActionStyleDto> chanceActionStyleDtoList = DTOs.marshalList(ChanceActionStyleDto.class,
                chanceActionStyleList);
        return UIHelper.selectItemsFromDict(chanceActionStyleDtoList);
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
        findComponent(OUTPUT_QUANTITY).setRendered(false);
        findComponent(INPUT_QUANTITY).setRendered(true);
        findComponent(COMMANDLINK_EDITQUANTITY).setRendered(false);
        findComponent(COMMANDLINK_SUREQUANTITY).setRendered(true);
        temQuantity = selectedQuotationItem.getQuantity();
        isQuantityEditing = true;
    }

    public void editQuotationItem() {
        BigDecimal itemQuantity = isQuantityEditing == true ? temQuantity : selectedQuotationItem.getQuantity();
        MCValue itemPrice = isPriceEditing == true ? temPrice : selectedQuotationItem.getPrice();
        if (isPriceEditing)
            selectedQuotationItem.setPrice(itemPrice);
        if (isQuantityEditing)
            selectedQuotationItem.setQuantity(itemQuantity);
        activeQuotation.editItem(selectedQuotationItem);
    }

    public void uneditQuotationItem() {
        selectedQuotationItem.setQuantity(temQuantity);
        selectedQuotationItem.setPrice(temPrice);
        findComponent(OUTPUT_DISCOUNT).setRendered(true);
        findComponent(OUTPUT_PRICE).setRendered(true);
        findComponent(OUTPUT_QUANTITY).setRendered(true);

        findComponent(INPUT_DISCOUNT).setRendered(false);
        findComponent(INPUT_PRICE).setRendered(false);
        findComponent(INPUT_QUANTITY).setRendered(false);

        findComponent(COMMANDLINK_EDITDISCOUNT).setRendered(true);
        findComponent(COMMANDLINK_EDITPRICE).setRendered(true);
        findComponent(COMMANDLINK_EDITQUANTITY).setRendered(true);

        findComponent(COMMANDLINK_SUREDISCOUNT).setRendered(false);
        findComponent(COMMANDLINK_SUREPIRCE).setRendered(false);
        findComponent(COMMANDLINK_EDITQUANTITY).setRendered(false);
    }

    public void saveQuotation() {

        if (activeQuotation.getItems() == null || activeQuotation.getItems().size() == 0) {
            uiLogger.error("错误提示:", "请添加明细");
            return;
        }

        if (Strings.isEmpty(activeQuotation.getSubject())) {
            uiLogger.error("错误提示:", "请添加报价单标题!");
            return;
        }
        ChanceQuotation quotationEntity = activeQuotation.unmarshal();

        try {
            serviceFor(ChanceQuotation.class).saveOrUpdate(quotationEntity);

// if (activeQuotation.getId() == null)
// quotations.add(DTOs.marshal(ChanceQuotationDto.class, quotationEntity));
            listQuotationByChance(activeChance);

            DataTable quotationItemTable = (DataTable) findComponent(DATATABLE_QUOTATIONS);
            quotationItemTable.clearSelectedRowIndexes();

            findComponentEx(BUTTON_QUOTATION_EDIT).setEnabled(false);
            findComponentEx(BUTTON_QUOTATION_DELETE).setEnabled(false);
            findComponentEx(BUTTON_QUOTATION_VIEW).setEnabled(false);

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
            findComponentEx(BUTTON_QUOTATION_EDIT).setEnabled(true);
            findComponentEx(BUTTON_QUOTATION_DELETE).setEnabled(true);
        }
        findComponentEx(BUTTON_QUOTATION_VIEW).setEnabled(true);
    }

    public void onQuotationRowUnselect() {
        if (editable != true) {
            findComponentEx(BUTTON_QUOTATION_EDIT).setEnabled(false);
            findComponentEx(BUTTON_QUOTATION_DELETE).setEnabled(false);
        }
        findComponentEx(BUTTON_QUOTATION_VIEW).setEnabled(false);
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
        if (isSearching) {
            EntityDataModelOptions<Chance, ChanceDto> edmo = new EntityDataModelOptions<Chance, ChanceDto>(
                    Chance.class, ChanceDto.class);
            edmo.setSelection(-1);
            edmo.setCriteriaElements(Order.desc("createdDate"),//
                    EntityCriteria.ownedByCurrentUser(),//
                    ChanceCriteria.nameLike(materialPattern));
            chances = UIHelper.buildLazyDataModel(edmo);
        } else {
            EntityDataModelOptions<Chance, ChanceDto> edmo = new EntityDataModelOptions<Chance, ChanceDto>(
                    Chance.class, ChanceDto.class);
            edmo.setSelection(-1);
            edmo.setCriteriaElements(//
                    Order.desc("createdDate"), //
                    EntityCriteria.ownedByCurrentUser());
            chances = UIHelper.buildLazyDataModel(edmo);
        }
        refreshChanceCount(isSearching);
        findComponentEx(BUTTON_QUOTATION_EDIT).setEnabled(false);
        findComponentEx(BUTTON_QUOTATION_DELETE).setEnabled(false);
    }

    public void initToolbar() {
        findComponentEx(BUTTON_CHANCE_NEW).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_EDIT).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_DELETE).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_ADD).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_RESET).setEnabled(false);
        boolean temp = selectedChance == null ? false : true;
        findComponentEx(BUTTON_CHANCE_DETAIL).setEnabled(temp);
    }

    void refreshChanceCount(boolean forSearch) {
        int count = serviceFor(Chance.class).count(EntityCriteria.ownedByCurrentUser(),//
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
            findComponentEx(BUTTON_CHANCE_UNRELATEACTION).setEnabled(true);
        }

        findComponentEx(BUTTON_CHANCE_VIEWACTION).setEnabled(true);
    }

    public void onActionRowUnselect() {
        if (editable != true) {
            findComponentEx(BUTTON_CHANCE_UNRELATEACTION).setEnabled(false);
        }

        findComponentEx(BUTTON_CHANCE_VIEWACTION).setEnabled(false);
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
                    EntityCriteria.ownedByCurrentUser(), //
                    PeopleCriteria.namedLike(partyPattern));
            parties = DTOs.marshalList(PartyDto.class, _parties);
        } else {
            List<Party> lp = serviceFor(Party.class).list(EntityCriteria.ownedByCurrentUser());
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
            activeActions = DTOs.marshalList(ChanceActionDto.class, _actions);
        } else {
            List<ChanceAction> lca = serviceFor(ChanceAction.class).list(//
                    Order.desc("createdDate"), //
                    ChanceCriteria.actedByCurrentUser(), //
                    ChanceCriteria.danglingChance());
            activeActions = DTOs.marshalList(ChanceActionDto.class, lca);
        }
    }

    public void searchChance() {
        if (subjectPattern != null) {
            isSearching = true;
            initList();
        } else {
            isSearching = false;
            initList();
        }
        initToolbar();
        selectedChance = null;
        setActiveTab(TAB_INDEX);
    }

    public void doAttachActions() {
        if (selectedActions.length == 0)
            return;

        for (ChanceActionDto action : selectedActions)
            activeChance.addAction(action);

        Chance _chance = activeChance.unmarshal();
        try {
            for (ChanceAction _action : _chance.getActions()) {
                if (_action.getChance() == null)
                    _action.setChance(_chance);
                if (_action.getStage() == null)
                    _action.setStage(ChanceStage.INIT);
            }

            serviceFor(ChanceAction.class).saveOrUpdateAll(_chance.getActions());
            uiLogger.info("提示", "关联成功");
        } catch (Exception e) {
            uiLogger.error("错误提示", "关联失败:" + e.getMessage(), e);
        }
    }

    public void addChanceParty() {
        if (selectedParty == null)
            return;
        ChancePartyDto chancePartyDto = new ChancePartyDto().create();
        chancePartyDto.setChance(activeChance);
        chancePartyDto.setParty(selectedParty);
        chancePartyDto.setRole("普通客户");
        activeChance.addParty(chancePartyDto);
    }

    public void createForm() {
        editable = false;
        activeChance = new ChanceDto().create();
        quotations = new ArrayList<ChanceQuotationDto>();
        setActiveTab(TAB_FORM);

        findComponentEx(BUTTON_QUOTATION_NEW).setEnabled(true);
        findComponentEx(BUTTON_QUOTATION_VIEW).setEnabled(false);

        findComponentEx(BUTTON_CHANCE_NEW).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_EDIT).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_DELETE).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_ADD).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_RESET).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_DETAIL).setEnabled(false);
        findComponent(FIELD_ACTIONHISTORY).setRendered(false);
        findComponent(FIELD_QUOTATION).setRendered(false);

    }

    public void editForm() {
        if (selectedChance == null) {
            uiLogger.error("请选择需要修改的销售机会!");
            return;
        }
        setActiveChance(selectedChance);
        listQuotationByChance(activeChance);
        setActiveTab(TAB_FORM);

        findComponentEx(BUTTON_CHANCE_NEW).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_EDIT).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_DELETE).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_ADD).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_RESET).setEnabled(true);

        findComponentEx(BUTTON_CHANCE_DETAIL).setEnabled(false);

        findComponentEx(BUTTON_CHANCE_RELATEACTION).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_UNRELATEACTION).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_VIEWACTION).setEnabled(false);

        editable = false;
        findComponent(FIELD_ACTIONHISTORY).setRendered(true);
        findComponent(FIELD_QUOTATION).setRendered(true);

        findComponentEx(BUTTON_QUOTATION_NEW).setEnabled(true);
        findComponentEx(BUTTON_QUOTATION_VIEW).setEnabled(false);
    }

    public void detailForm() {
        setActiveChance(selectedChance);
        listQuotationByChance(activeChance);
        setActiveTab(TAB_FORM);

        findComponentEx(BUTTON_CHANCE_NEW).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_EDIT).setEnabled(true);
        ;
        findComponentEx(BUTTON_CHANCE_DELETE).setEnabled(true);
        findComponentEx(BUTTON_CHANCE_ADD).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_RESET).setEnabled(false);

        boolean tempBoolean = selectedChance == null ? false : true;
        findComponentEx(BUTTON_CHANCE_DETAIL).setEnabled(tempBoolean);

        Fieldset actionHistoryField = (Fieldset) findComponent(FIELD_ACTIONHISTORY);
        actionHistoryField.setCollapsed(false);
        actionHistoryField.setRendered(true);
        Fieldset quotationField = (Fieldset) findComponent(FIELD_QUOTATION);
        quotationField.setRendered(true);
        quotationField.setCollapsed(false);

        findComponentEx(BUTTON_CHANCE_RELATEACTION).setEnabled(false);
        findComponentEx(BUTTON_CHANCE_UNRELATEACTION).setEnabled(false);

        findComponentEx(BUTTON_QUOTATION_VIEW).setEnabled(false);
        findComponentEx(BUTTON_QUOTATION_NEW).setEnabled(false);
        editable = true;
    }

    public void cancel() {
        initToolbar();
        setActiveTab(TAB_INDEX);
        editable = false;
        findComponent(FIELD_ACTIONHISTORY).setRendered(false);
        findComponent(FIELD_QUOTATION).setRendered(false);
        findComponentEx(BUTTON_QUOTATION_NEW).setEnabled(true);
    }

    public void editCustomerRole() {
        roleRendered = !roleRendered;
    }

    public void dropCustomer() {
        activeChance.removeParty(selectedChanceParty);
    }

    public void initActiveActions() {
        activeActions = new ArrayList<ChanceActionDto>();
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

            serviceFor(Chance.class).saveOrUpdate(_chance);

            setActiveTab(TAB_INDEX);
            DataTable table = (DataTable) findComponent(DATATABLE_CHANCES);
            table.clearSelectedRowIndexes();
            initList();
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
            DataTable table = (DataTable) findComponent(DATATABLE_CHANCES);
            table.clearSelectedRowIndexes();
            initList();
            initToolbar();
            uiLogger.info("成功删除行动记录");
        } catch (Exception e) {
            uiLogger.error("删除销售机会失败:" + e.getMessage(), e);
        }
    }

    public void unRelating() {
        if (selectedAction == null) {
            uiLogger.error("错误提示:", "请选择行动记录!");
            return;
        }
        ChanceAction chanceAction = selectedAction.unmarshal();
        chanceAction.setChance(null);
        chanceAction.setStage(null);
        try {
            serviceFor(ChanceAction.class).save(chanceAction);
            activeChance.deleteAction(selectedAction);
            findComponentEx(BUTTON_CHANCE_UNRELATEACTION).setEnabled(false);
            findComponentEx(BUTTON_CHANCE_VIEWACTION).setEnabled(false);
            uiLogger.info("反关联成功");
        } catch (Exception e) {
            uiLogger.error("反关联失败:" + e.getMessage(), e);
        }
    }

    public String getSubjectPattern() {
        return subjectPattern;
    }

    public String getPartyPattern() {
        return partyPattern;
    }

    public Date getSearchBeginTime() {
        return searchBeginTime;
    }

    public Date getSearchEndTime() {
        return searchEndTime;
    }

    public LazyDataModel<ChanceDto> getChances() {
        return chances;
    }

    public ChanceDto getSelectedChance() {
        return selectedChance;
    }

    public ChanceDto getActiveChance() {
        return activeChance;
    }

    public List<ChanceActionDto> getActiveActions() {
        return activeActions;
    }

    public ChanceActionDto[] getSelectedActions() {
        return selectedActions;
    }

    public ChanceActionDto getSelectedAction() {
        return selectedAction;
    }

    public ChanceActionDto getActiveAction() {
        return activeAction;
    }

    public ChancePartyDto getSelectedChanceParty() {
        return selectedChanceParty;
    }

    public PartyDto getSelectedParty() {
        return selectedParty;
    }

    public List<PartyDto> getParties() {
        return parties;
    }

    public List<ChanceQuotationDto> getQuotations() {
        return quotations;
    }

    public ChanceQuotationDto getSelectedQuotation() {
        return selectedQuotation;
    }

    public ChanceQuotationDto getActiveQuotation() {
        return activeQuotation;
    }

    public String getMaterialPattern() {
        return materialPattern;
    }

    public ChanceQuotationItemDto getSelectedQuotationItem() {
        return selectedQuotationItem;
    }

    public MCValue getTemPrice() {
        return temPrice;
    }

    public BigDecimal getTemQuantity() {
        return temQuantity;
    }

    public MaterialCategoryTreeModel getMaterialTree() {
        return materialTree;
    }

    public List<MaterialDto> getMaterialList() {
        return materialList;
    }

    public MaterialDto getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSubjectPattern(String subjectPattern) {
        this.subjectPattern = subjectPattern;
    }

    public void setPartyPattern(String partyPattern) {
        this.partyPattern = partyPattern;
    }

    public void setSearchBeginTime(Date searchBeginTime) {
        this.searchBeginTime = searchBeginTime;
    }

    public void setSearchEndTime(Date searchEndTime) {
        this.searchEndTime = searchEndTime;
    }

    public void setChances(LazyDataModel<ChanceDto> chances) {
        this.chances = chances;
    }

    public void setSelectedChance(ChanceDto selectedChance) {
        this.selectedChance = selectedChance;
    }

    public void setActiveChance(ChanceDto activeChance) {
        this.activeChance = activeChance;
    }

    public void setActiveActions(List<ChanceActionDto> activeActions) {
        this.activeActions = activeActions;
    }

    public void setSelectedActions(ChanceActionDto[] selectedActions) {
        this.selectedActions = selectedActions;
    }

    public void setSelectedAction(ChanceActionDto selectedAction) {
        this.selectedAction = selectedAction;
    }

    public void setActiveAction(ChanceActionDto activeAction) {
        this.activeAction = activeAction;
    }

    public void setSelectedChanceParty(ChancePartyDto selectedChanceParty) {
        this.selectedChanceParty = selectedChanceParty;
    }

    public void setSelectedParty(PartyDto selectedParty) {
        this.selectedParty = selectedParty;
    }

    public void setParties(List<PartyDto> parties) {
        this.parties = parties;
    }

    public void setQuotations(List<ChanceQuotationDto> quotations) {
        this.quotations = quotations;
    }

    public void setSelectedQuotation(ChanceQuotationDto selectedQuotation) {
        this.selectedQuotation = selectedQuotation;
    }

    public void setActiveQuotation(ChanceQuotationDto activeQuotation) {
        this.activeQuotation = activeQuotation;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public void setSelectedQuotationItem(ChanceQuotationItemDto selectedQuotationItem) {
        this.selectedQuotationItem = selectedQuotationItem;
    }

    public void setTemPrice(MCValue temPrice) {
        this.temPrice = temPrice;
    }

    public void setTemQuantity(BigDecimal temQuantity) {
        this.temQuantity = temQuantity;
    }

    public void setMaterialTree(MaterialCategoryTreeModel materialTree) {
        this.materialTree = materialTree;
    }

    public void setMaterialList(List<MaterialDto> materialList) {
        this.materialList = materialList;
    }

    public void setSelectedMaterial(MaterialDto selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }

}
