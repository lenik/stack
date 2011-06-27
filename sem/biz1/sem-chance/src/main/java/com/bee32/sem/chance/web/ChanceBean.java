package com.bee32.sem.chance.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.servlet.util.ThreadHttpContext;
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
import com.bee32.sem.chance.service.ChanceService;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.user.util.SessionLoginInfo;

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

    private String subjectPartten;
    private String partyPartten;
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
        chances = new LazyDataModel<ChanceDto>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<ChanceDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                    Map<String, String> filters) {
                ChanceService chanceService = getBean(ChanceService.class);
                List<Chance> chanceList = chanceService.limitedChanceList(first, pageSize);
                List<ChanceDto> chanceDtoList = DTOs.marshalList(ChanceDto.class, chanceList);
// return DTOs.marshalList(ChanceDto.class, 3, chanceList);
                return chanceDtoList;
            }

        };

        ChanceService chanceService = getBean(ChanceService.class);
        chances.setRowCount(chanceService.getChanceCount());
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
        chance = new ChanceDto();
        relating = false;
        unRelating = false;
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
        unRelating = false;
    }

    public void onActionRowUnselect() {
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
        if (!partyPartten.isEmpty()) {
            ChanceService chanceService = getBean(ChanceService.class);
            List<Party> keywordPartyList = chanceService.keywordPartyList(partyPartten);
            parties = DTOs.marshalList(PartyDto.class, keywordPartyList);
        }
    }

    public void searchAction() {
        if (searchBeginTime != null && searchEndTime != null) {
            ChanceService chanceService = getBean(ChanceService.class);
            List<ChanceAction> dateRangeActionList = chanceService.dateRangeActionList(searchBeginTime, searchEndTime);
            actions = DTOs.marshalList(ChanceActionDto.class, dateRangeActionList);
        }
    }

    public void doSelectActions() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (selectedActions.length == 0) {
            context.addMessage(null, new FacesMessage("提示", "至少请选择一个客户"));
            return;
        }
        chance.addActions(selectedActions);
        try {
            Chance _chance = chance.unmarshal();
// for (ChanceAction _action : _chance.getActions()) {
// ChanceAction __action = _action;
// if (__action.getChance().getId() == null)
// __action.setChance(_chance);
// if (__action.getStage() == null)
// __action.setStage(ChanceStage.INIT);
//
// getDataManager().save(_action);
// }
            serviceFor(Chance.class).save(_chance);
            context.addMessage(null, new FacesMessage("提示", "关联行动记录成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误提示", "关联行动记录错误" + e.getMessage()));
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
        setActiveTab(TAB_FORM);
        addable = true;
        edable = true;
        detailable = true;
        saveable = false;
        backable = false;
        searchable = false;
        relating = true;
        unRelating = true;
    }

    public void editForm() {
        chance = selectedChance;
        setActiveTab(TAB_FORM);
        addable = true;
        edable = true;
        detailable = true;
        saveable = false;
        backable = false;
        searchable = false;
        relating = false;
        unRelating = true;
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
    }

    public void cancel() {
        initToolbar();
        setActiveTab(TAB_INDEX);
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

        HttpSession session = ThreadHttpContext.requireSession();
        UserDto owner = new UserDto().ref(SessionLoginInfo.requireCurrentUser(session).getId());
        chance.setOwner(owner);

        // TODO 如果行动记录是空的,但是机会的阶段不是空
        // if(chance.getActions().isEmpty() && !chance.getStage().getId().isEmpty());
        int maxOrder = 0;
        ChanceStageDto temp = null;
        String chanceStageId = chance.getStage().getId();
        if (!chanceStageId.isEmpty()) {
            ChanceStage _stage = serviceFor(ChanceStage.class).load(chanceStageId);
            temp = DTOs.mref(ChanceStageDto.class, _stage);
            maxOrder = temp.getOrder();
        }
        for (ChanceActionDto cad : chance.getActions()) {
            if (cad.getStage().getOrder() > maxOrder) {
                maxOrder = cad.getStage().getOrder();
                chanceStageId = cad.getStage().getId();
            }
        }
        ChanceStage _chanceStage = serviceFor(ChanceStage.class).load(chanceStageId);
        temp = DTOs.mref(ChanceStageDto.class, _chanceStage);
        chance.setStage(temp);

        Chance _chance = chance.unmarshal();

        try {
            serviceFor(Chance.class).save(_chance);

            for (ChanceAction _action : _chance.getActions()) {
                _action.setChance(_chance);
                serviceFor(ChanceAction.class).save(_action);
            }

            context.addMessage(null, new FacesMessage("提示", "成功添加销售机会"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误", "添加销售机会失败: " + e.getMessage()));
            e.printStackTrace();
        }

        setActiveTab(TAB_INDEX);
        initToolbar();
    }

    public void drop() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Chance tempChance = selectedChance.unmarshal();
            for (ChanceAction _action : tempChance.getActions()) {
                _action.setChance(null);
                _action.setStage(null);
                serviceFor(ChanceAction.class).save(_action);
            }
            serviceFor(Chance.class).delete(tempChance);
            ChanceService chanceService = getBean(ChanceService.class);
            chances.setRowCount(chanceService.getChanceCount());
            context.addMessage(null, new FacesMessage("提示", "成功删除行动记录"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("错误提示", "删除销售机会失败:" + e.getMessage()));
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

    public String getSubjectPartten() {
        return subjectPartten;
    }

    public void setSubjectPartten(String subjectPartten) {
        this.subjectPartten = subjectPartten;
    }

    public String getPartyPartten() {
        return partyPartten;
    }

    public void setPartyPartten(String partyPartten) {
        this.partyPartten = partyPartten;
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
