package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

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
import com.bee32.sem.sandbox.SelectableList;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.sandbox.ZLazyDataModel;
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

    ZLazyDataModel<Chance, ChanceDto> chances;
    ChanceDto chanceCopy = new ChanceDto().create();

    SelectableList<ChanceActionDto> actions;
    ChanceActionDto[] selectedActions;
    ChanceActionDto actionCopy = new ChanceActionDto().create();

    ChancePartyDto selectedChanceParty;
    SelectableList<PartyDto> parties;

    // quotation fields

    SelectableList<ChanceQuotationDto> quotations;
    ChanceQuotationDto quotationCopy;

    String materialPattern;
    ChanceQuotationItemDto selectedQuotationItem;

    MaterialCategoryTreeModel materialTree;

    SelectableList<MaterialDto> materialList;

    ChanceBean() {
        initMaterialCategoryTree();
    }

    @PostConstruct
    public void initialization() {
        initList();
        quotations = UIHelper.selectable(new ArrayList<ChanceQuotationDto>());
    }

    public void findMaterial() {
        List<Material> _materials = serviceFor(Material.class).list(MaterialCriteria.namedLike(materialPattern));
        materialList = UIHelper.selectable(DTOs.marshalList(MaterialDto.class, 0, _materials, true));
    }

    public void viewActionDetail() {
        if (actions.isSelected()) {
            uiLogger.error("提示:", "请选择行动记录");
            return;
        }
        actionCopy = actions.getSelection();
        state = State.VIEW_ACTION;
    }

    public void viewQuotationDetail() {
        quotationCopy = quotations.getSelection();
        state = State.VIEW_Q;
    }

    public void editQuotation() {
        quotationCopy = quotations.getSelection();
        state = State.EDIT_Q;
    }

    public void chooseMaterial() {
        MaterialDto mdto = null;
        if (materialList.getSelection() != null)
            mdto = materialList.getSelection();
        ChanceQuotationItemDto item = new ChanceQuotationItemDto().create();
        item.setParent(quotationCopy);
        item.setMaterial(mdto);
        item.setDiscount(1f);
        quotationCopy.addItem(item);
    }

    void listQuotationByChance(ChanceDto chance) {
        List<ChanceQuotation> quotationList = serviceFor(ChanceQuotation.class).list(
                ChanceCriteria.chanceEquals(chanceCopy));
        quotations = UIHelper.selectable(DTOs.marshalList(ChanceQuotationDto.class, 0, quotationList, true));
    }

    public void calculatePriceChange() {
        MCValue _price = new MCValue(selectedQuotationItem.getPriceCurrency(), selectedQuotationItem.getViewPrice());
        selectedQuotationItem.setPrice(_price);
    }

    public List<SelectItem> getChanceActionStyles() {
        List<ChanceActionStyle> styleList = serviceFor(ChanceActionStyle.class).list();
        List<ChanceActionStyleDto> styleDtoList = DTOs.marshalList(ChanceActionStyleDto.class, styleList, true);
        return UIHelper.selectItemsFromDict(styleDtoList);
    }

    public void uneditQuotationItem() {
        state = State.EDIT;
    }

    public void saveQuotation() {
        if (quotationCopy.getItems() == null || quotationCopy.getItems().size() == 0) {
            uiLogger.error("错误提示:", "请添加明细");
            return;
        }

        if (Strings.isEmpty(quotationCopy.getSubject())) {
            uiLogger.error("错误提示:", "请添加报价单标题!");
            return;
        }
        ChanceQuotation quotationEntity = quotationCopy.unmarshal();

        try {
            serviceFor(ChanceQuotation.class).saveOrUpdate(quotationEntity);

            // if (quotationCopy.getId() == null)
            // quotations.add(DTOs.marshal(ChanceQuotationDto.class, quotationEntity));
            listQuotationByChance(chanceCopy);

            uiLogger.info("提示", "保存报价单成功");

            state = State.EDIT;
        } catch (Exception e) {
            uiLogger.error("保存报价单错误:" + e.getMessage(), e);
        }
    }

    public void dropQuotation() {
        ChanceQuotation quo = quotations.getSelection().unmarshal();
        try {
            serviceFor(ChanceQuotation.class).delete(quo);
            quotations.remove(quotations.getSelection());
            quotations.deselect();
            uiLogger.info("提示:", "成功删除报价单");
        } catch (Exception e) {
            uiLogger.error("删除报价单失败" + e.getMessage(), e);
        }
    }

    public void dropQuotationItem() {
        quotationCopy.removeItem(selectedQuotationItem);
    }

    public void createQuotationForm() {
        quotationCopy = new ChanceQuotationDto().create();
        quotationCopy.setChance(chanceCopy);
    }

    public void onQuotationRowSelect() {
    }

    public void onQuotationRowUnselect() {
    }

    /** 生成物料树 */
    public void initMaterialCategoryTree() {
        List<MaterialCategory> rootCategories = serviceFor(MaterialCategory.class).list(TreeCriteria.root());
        List<MaterialCategoryDto> rootCategoryDtos = DTOs.marshalList(MaterialCategoryDto.class,
                ~MaterialCategoryDto.MATERIALS, rootCategories, true);

        materialTree = new MaterialCategoryTreeModel(rootCategoryDtos);
        materialTree.addListener(new SelectionAdapter() {
            private static final long serialVersionUID = 1L;

            @Override
            public void itemSelected(SelectionEvent event) {
                MaterialCategoryDto materialCategoryDto = (MaterialCategoryDto) event.getSelection();
                List<Material> _materials = serviceFor(Material.class).list(//
                        // Order.asc("name"),
                        MaterialCriteria.categoryOf(materialCategoryDto.getId()));
                materialList = UIHelper.selectable(DTOs.marshalList(MaterialDto.class, _materials, true));
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
    }

    public void initToolbar() {
    }

    void refreshChanceCount(boolean forSearch) {
        int count = serviceFor(Chance.class).count(//
                EntityCriteria.ownedByCurrentUser(),//
                forSearch ? ChanceCriteria.subjectLike(subjectPattern) : null);
        chances.setRowCount(count);
    }

    public void onRowSelect() {
    }

    public void onRowUnselect() {
    }

    public void onActionRowSelect() {
    }

    public void onActionRowUnselect() {
    }

    public List<SelectItem> getCategory() {
        List<ChanceCategory> chanceCategoryList = serviceFor(ChanceCategory.class).list();
        List<ChanceCategoryDto> categoryDtoList = DTOs.marshalList(ChanceCategoryDto.class, chanceCategoryList, true);
        return UIHelper.selectItemsFromDict(categoryDtoList);
    }

    public List<SelectItem> getSource() {
        List<ChanceSourceType> sourceTypeList = serviceFor(ChanceSourceType.class).list();
        List<ChanceSourceDto> chanceSourceDtoList = DTOs.marshalList(ChanceSourceDto.class, sourceTypeList, true);
        return UIHelper.selectItemsFromDict(chanceSourceDtoList);
    }

    public List<SelectItem> getStage() {
        List<ChanceStage> chanceStageList = serviceFor(ChanceStage.class).list();
        List<ChanceStageDto> chanceStageDtoList = DTOs.marshalList(ChanceStageDto.class, chanceStageList, true);
        return UIHelper.selectItemsFromDict(chanceStageDtoList);
    }

    public void findParty() {
        List<Party> _parties;
        if (!partyPattern.isEmpty())
            _parties = serviceFor(Party.class).list(//
                    EntityCriteria.ownedByCurrentUser(), //
                    PeopleCriteria.namedLike(partyPattern));
        else
            _parties = serviceFor(Party.class).list(EntityCriteria.ownedByCurrentUser());
        parties = UIHelper.selectable(DTOs.marshalList(PartyDto.class, 0, _parties, true));
    }

    public void searchAction() {
        List<ChanceAction> _actions;
        if (searchBeginTime != null && searchEndTime != null) {
            _actions = serviceFor(ChanceAction.class).list(//
                    Order.desc("createdDate"), //
                    ChanceCriteria.actedByCurrentUser(), //
                    ChanceCriteria.beganWithin(searchBeginTime, searchEndTime), //
                    ChanceCriteria.danglingChance());
        } else {
            _actions = serviceFor(ChanceAction.class).list(//
                    Order.desc("createdDate"), //
                    ChanceCriteria.actedByCurrentUser(), //
                    ChanceCriteria.danglingChance());
        }
        actions = UIHelper.selectable(DTOs.marshalList(ChanceActionDto.class, _actions));
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
        chances.deselect();
        setActiveTab(TAB_INDEX);
    }

    public void doAttachActions() {
        if (selectedActions.length == 0)
            return;

        for (ChanceActionDto action : selectedActions)
            chanceCopy.addAction(action);

        Chance _chance = chanceCopy.unmarshal();
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
        if (parties.isSelected())
            return;
        ChancePartyDto chancePartyDto = new ChancePartyDto().create();
        chancePartyDto.setChance(chanceCopy);
        chancePartyDto.setParty(parties.getSelection());
        chancePartyDto.setRole("普通客户");
        chanceCopy.addParty(chancePartyDto);
    }

    public void createForm() {
        chanceCopy = new ChanceDto().create();
        quotations = UIHelper.selectable(new ArrayList<ChanceQuotationDto>());
        setActiveTab(TAB_FORM);

        state = State.EDIT;
    }

    public void editForm() {
        if (chances.isSelected()) {
            uiLogger.error("请选择需要修改的销售机会!");
            return;
        }
        setChanceCopy(chances.getSelection());
        listQuotationByChance(chanceCopy);
        setActiveTab(TAB_FORM);

        state = State.EDIT;
    }

    public void detailForm() {
        setChanceCopy(chances.getSelection());
        listQuotationByChance(chanceCopy);
        setActiveTab(TAB_FORM);

        state = State.VIEW;
    }

    public void cancel() {
        initToolbar();
        setActiveTab(TAB_INDEX);

        state = State.INDEX;
    }

    public void editCustomerRole() {
        roleRendered = !roleRendered;
    }

    public void dropCustomer() {
        chanceCopy.removeParty(selectedChanceParty);
    }

    public void initActions() {
        actions = UIHelper.selectable(new ArrayList<ChanceActionDto>());
    }

    public void saveChance() {
        String subject = chanceCopy.getSubject();
        if (subject.isEmpty()) {
            uiLogger.error("错误提示", "机会标题不能为空!");
            return;
        }

        if (chanceCopy.getParties().isEmpty()) {
            uiLogger.error("错误提示", "请选择机会对应的客户!");
            return;
        }
        String content = chanceCopy.getContent();
        if (content.isEmpty())
            chanceCopy.setContent("");

        String categoryId = chanceCopy.getCategory().getId();
        ChanceCategoryDto ccd = null;
        if (!categoryId.isEmpty())
            ccd = new ChanceCategoryDto().ref(categoryId);
        chanceCopy.setCategory(ccd);

        String sourceId = chanceCopy.getSource().getId();
        ChanceSourceDto csd = null;
        if (!sourceId.isEmpty())
            csd = new ChanceSourceDto().ref(sourceId);
        chanceCopy.setSource(csd);

        ChanceStageDto tempStage = null;
        String chanceStageId = chanceCopy.getStage().getId();
        if (chanceStageId.isEmpty())
            tempStage = new ChanceStageDto().ref(ChanceStage.INIT.getId());
        else
            tempStage = new ChanceStageDto().ref(chanceStageId);

        chanceCopy.setStage(tempStage);

        Chance _chance = chanceCopy.unmarshal();

        try {

            serviceFor(Chance.class).saveOrUpdate(_chance);

            setActiveTab(TAB_INDEX);
            initList();
            initToolbar();

            uiLogger.info("提示", "保存销售机会成功");

            state = State.INDEX;
        } catch (Exception e) {
            uiLogger.error("保存销售机会失败: " + e.getMessage(), e);
        }
    }

    public void drop() {
        if (chances.isSelected()) {
            uiLogger.error("请选择需要删除的销售机会!");
            return;
        }
        try {
            Chance chanceToDelete = serviceFor(Chance.class).getOrFail(chances.getSelection().getId());
            for (ChanceAction _action : chanceToDelete.getActions()) {
                _action.setChance(null);
                _action.setStage(null);
                serviceFor(ChanceAction.class).save(_action);
            }
            serviceFor(Chance.class).delete(chanceToDelete);
            initList();
            initToolbar();
            uiLogger.info("成功删除行动记录");
        } catch (Exception e) {
            uiLogger.error("删除销售机会失败:" + e.getMessage(), e);
        }
    }

    public void unRelating() {
        if (actions.isSelected()) {
            uiLogger.error("错误提示:", "请选择行动记录!");
            return;
        }
        ChanceAction chanceAction = actions.getSelection().unmarshal();
        chanceAction.setChance(null);
        chanceAction.setStage(null);
        try {
            serviceFor(ChanceAction.class).save(chanceAction);
            chanceCopy.deleteAction(actions.getSelection());
            actions.deselect();
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

    public ChanceDto getChanceCopy() {
        return chanceCopy;
    }

    public List<ChanceActionDto> getActions() {
        return actions;
    }

    public ChanceActionDto[] getselectedActions() {
        return selectedActions;
    }

    public ChanceActionDto getActionCopy() {
        return actionCopy;
    }

    public ChancePartyDto getSelectedChanceParty() {
        return selectedChanceParty;
    }

    public List<PartyDto> getParties() {
        return parties;
    }

    public List<ChanceQuotationDto> getQuotations() {
        return quotations;
    }

    public ChanceQuotationDto getQuotationCopy() {
        return quotationCopy;
    }

    public String getMaterialPattern() {
        return materialPattern;
    }

    public ChanceQuotationItemDto getSelectedQuotationItem() {
        return selectedQuotationItem;
    }

    public MaterialCategoryTreeModel getMaterialTree() {
        return materialTree;
    }

    public List<MaterialDto> getMaterialList() {
        return materialList;
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

    public void setChanceCopy(ChanceDto chanceCopy) {
        this.chanceCopy = chanceCopy;
    }

    public void setSelectActions(ChanceActionDto[] selectedActions) {
        this.selectedActions = selectedActions;
    }

    public void setActionCopy(ChanceActionDto actionCopy) {
        this.actionCopy = actionCopy;
    }

    public void setSelectedChanceParty(ChancePartyDto selectedChanceParty) {
        this.selectedChanceParty = selectedChanceParty;
    }

    public void setQuotationCopy(ChanceQuotationDto quotationCopy) {
        this.quotationCopy = quotationCopy;
    }

    public void setMaterialPattern(String materialPattern) {
        this.materialPattern = materialPattern;
    }

    public void setSelectedQuotationItem(ChanceQuotationItemDto selectedQuotationItem) {
        this.selectedQuotationItem = selectedQuotationItem;
    }

    public void setMaterialTree(MaterialCategoryTreeModel materialTree) {
        this.materialTree = materialTree;
    }

}
