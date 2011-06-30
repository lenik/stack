package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.criterion.Restrictions;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceCategoryDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChancePartyDto;
import com.bee32.sem.chance.dto.ChanceSourceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.chance.entity.ChanceSourceType;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;

@Component
@Scope("view")
public class ChanceBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    public static final int TAB_INDEX = 0;
    public static final int TAB_FORM = 1;

    private boolean addable;
    private boolean edable;
    private boolean detailable;
    private boolean backable;
    private boolean saveable;
    private boolean searchable;
    private boolean relating;
    private boolean unRelating;
    private boolean editable;

    private String subjectPattern;
    private String partyPattern;
    private Date searchBeginTime;
    private Date searchEndTime;
    private LazyDataModel<ChanceDto> chances;
    private ChanceDto selectedChance;
    private ChanceDto chance;
    private List<ChanceActionDto> actions;
    private ChanceActionDto[] selectedActions;
    private ChanceActionDto selectedAction;
    // XXX 有用没有?
    private ChancePartyDto selectedChanceParty;
    private PartyDto selectedParty;
    private List<PartyDto> parties;

    public void initList() {
        EntityDataModelOptions<Chance, ChanceDto> edmo = new EntityDataModelOptions<Chance, ChanceDto>(Chance.class,
                ChanceDto.class);
        edmo.setSelection(-1);
        edmo.setRestrictions(ChanceCriteria.ownedByCurrentUser());
        chances = UIHelper.buildLazyDataModel(edmo);
        refreshChanceCount();
    }

    public void initToolbar() {
        addable = false;
        edable = true;
        detailable = true;
        backable = true;
        saveable = true;
        searchable = false;
        relating = true;
        unRelating = true;
    }

    @PostConstruct
    public void initialization() {
        initList();
        initToolbar();
        editable = false;
        chance = new ChanceDto();
        relating = false;
        unRelating = false;
    }

    void refreshChanceCount() {
        int count = serviceFor(Chance.class).count(ChanceCriteria.ownedByCurrentUser());
        chances.setRowCount(count);
    }

    public void onRowSelect() {
        addable = false;
        edable = false;
        detailable = false;
        backable = true;
        saveable = true;
        searchable = false;
    }

    public void onRowUnselect() {
        addable = false;
        edable = true;
        detailable = true;
        backable = true;
        saveable = true;
        searchable = false;
    }

    public void onActionRowSelect() {
        if (editable != true)
            unRelating = false;
    }

    public void onActionRowUnselect() {
        if (editable != true)
            unRelating = true;
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
                    PeopleCriteria.nameLike(partyPattern));
            parties = DTOs.marshalList(PartyDto.class, _parties);
        } else {
            List<Party> lp = serviceFor(Party.class).list(PeopleCriteria.ownedByCurrentUser());
            parties = DTOs.marshalList(PartyDto.class, lp);
        }
    }

    public void searchAction() {
        if (searchBeginTime != null && searchEndTime != null) {
            List<ChanceAction> _actions = serviceFor(ChanceAction.class).list(//
                    ChanceCriteria.actedByCurrentUser(), //
                    ChanceCriteria.beginWithin(searchBeginTime, searchEndTime), Restrictions.isNull("chance"));
            actions = DTOs.marshalList(ChanceActionDto.class, _actions);
        } else {
            List<ChanceAction> lca = serviceFor(ChanceAction.class).list(ChanceCriteria.actedByCurrentUser(),
                    Restrictions.isNull("chance"));
            actions = DTOs.marshalList(ChanceActionDto.class, lca);
        }
    }

    public void searchChance() {
        if (subjectPattern != null) {
            EntityDataModelOptions<Chance, ChanceDto> edmo = new EntityDataModelOptions<Chance, ChanceDto>(
                    Chance.class, ChanceDto.class);
            edmo.setSelection(-1);
            edmo.setRestrictions(ChanceCriteria.ownedByCurrentUser());
            edmo.setRestrictions(ChanceCriteria.subjectLike(subjectPattern));
            chances = UIHelper.buildLazyDataModel(edmo);
            refreshChanceCount();
        } else {
            initList();
        }
        setActiveTab(TAB_INDEX);
    }

    @SuppressWarnings("unchecked")
    public void doAttachActions() {
        if (selectedActions.length == 0) {
            return;
        }
        chance.addActions(selectedActions);
        Chance _chance = chance.unmarshal();
        try {
            for (ChanceAction _action : _chance.getActions()) {
                _action.setChance(_chance);
                _action.setStage(ChanceStage.INIT);
            }

            List<Entity<?>> all = new ArrayList<Entity<?>>();
            all.add(_chance);
            all.addAll(_chance.getActions());
            serviceFor(Entity.class).saveOrUpdateAll(all);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addChanceParty() {
        if (selectedParty == null)
            return;
        ChancePartyDto chancePartyDto = new ChancePartyDto();
        chancePartyDto.setChance(chance);
        chancePartyDto.setParty(selectedParty);
        chancePartyDto.setRole("普通客户");
        chance.addChanceParty(chancePartyDto);
    }

    public void createForm() {
        chance = new ChanceDto();
        selectedChance = null;
        setActiveTab(TAB_FORM);
        addable = true;
        edable = true;
        detailable = true;
        saveable = false;
        backable = false;
        searchable = true;
        relating = true;
        unRelating = true;
        editable = false;
    }

    public void editForm() {
        chance = selectedChance;
        setActiveTab(TAB_FORM);
        addable = true;
        edable = true;
        detailable = true;
        saveable = false;
        backable = false;
        searchable = true;
        relating = false;
        unRelating = true;
        editable = false;
    }

    public void detailForm() {
        chance = selectedChance;
        setActiveTab(TAB_FORM);
        addable = true;
        edable = true;
        detailable = true;
        saveable = true;
        backable = false;
        searchable = false;
        relating = true;
        unRelating = true;
        editable = true;
    }

    public void cancel() {
        initToolbar();
        setActiveTab(TAB_INDEX);
        editable = false;
        selectedChance = null;
    }

    public void saveChance() {
        FacesContext context = FacesContext.getCurrentInstance();
        String subject = chance.getSubject();
        if (subject.isEmpty()) {
            context.addMessage(null, new FacesMessage("错误提示", "机会标题不能为空!"));
            return;
        }

        if (chance.getParties().isEmpty()) {
            context.addMessage(null, new FacesMessage("错误提示", "请选择机会对应的客户!"));
            return;
        }
        String content = chance.getContent();
        if (content.isEmpty())
            chance.setContent("");

        String categoryId = chance.getCategory().getId();
        ChanceCategoryDto ccd = null;
        if (!categoryId.isEmpty())
            ccd = new ChanceCategoryDto().ref(categoryId);
        chance.setCategory(ccd);

        String sourceId = chance.getSource().getId();
        ChanceSourceDto csd = null;
        if (!sourceId.isEmpty())
            csd = new ChanceSourceDto().ref(sourceId);
        chance.setSource(csd);

        UserDto owner = new UserDto().ref(SessionLoginInfo.requireCurrentUser().getId());
        chance.setOwner(owner);

        ChanceStageDto tempStage = null;
        String chanceStageId = chance.getStage().getId();
        if (chanceStageId.isEmpty())
            tempStage = new ChanceStageDto().ref(ChanceStage.INIT.getId());
        else
            tempStage = new ChanceStageDto().ref(chanceStageId);

        chance.setStage(tempStage);

        Chance _chance = chance.unmarshal();

        try {

            for (ChanceAction _action : _chance.getActions()) {
                _action.setChance(_chance);
                _action.setStage(ChanceStage.INIT);
            }

            serviceFor(ChanceAction.class).saveOrUpdateAll(_chance.getActions());
            serviceFor(Chance.class).saveOrUpdate(_chance);

            context.addMessage(null, new FacesMessage("提示", "保存销售机会成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误", "保存销售机会失败: " + e.getMessage()));
            e.printStackTrace();

        }

        setActiveTab(TAB_INDEX);
        initToolbar();
        editable = true;
        selectedChance = null;

    }

    public void drop() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Chance tempChance = selectedChance.unmarshal();
            serviceFor(Chance.class).delete(tempChance);
            refreshChanceCount();
            for (ChanceAction _action : tempChance.getActions()) {
                _action.setChance(null);
                _action.setStage(null);
                serviceFor(ChanceAction.class).save(_action);
            }
            context.addMessage(null, new FacesMessage("提示", "成功删除行动记录"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误", "删除销售机会失败:" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void unRelating() {
        // XXX 取消机会与行动关联
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ChanceActionDto actionDto = selectedAction;
            ChanceAction chanceAction = actionDto.unmarshal();
            chance.deleteAction(actionDto);
            chanceAction.setChance(null);
            chanceAction.setStage(null);
            serviceFor(ChanceAction.class).save(chanceAction);
            if (chance.getActions().size() == 0) {
                unRelating = true;
            }
            context.addMessage(null, new FacesMessage("提示", "取消关联成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "取消关联失败:" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public boolean isAddable() {
        return addable;
    }

    public void setAddable(boolean addable) {
        this.addable = addable;
    }

    public boolean isEdable() {
        return edable;
    }

    public void setEdable(boolean edable) {
        this.edable = edable;
    }

    public boolean isDetailable() {
        return detailable;
    }

    public void setDetailable(boolean detailable) {
        this.detailable = detailable;
    }

    public boolean isBackable() {
        return backable;
    }

    public void setBackable(boolean backable) {
        this.backable = backable;
    }

    public boolean isSaveable() {
        return saveable;
    }

    public void setSaveable(boolean saveable) {
        this.saveable = saveable;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
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

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
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

}
