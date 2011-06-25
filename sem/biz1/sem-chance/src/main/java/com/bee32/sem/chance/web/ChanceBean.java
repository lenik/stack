package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.dto.UserDto;
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
import com.bee32.sem.chance.service.ChanceService;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;

@Component
@Scope("view")
public class ChanceBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    private boolean addable;
    private boolean edable;
    private boolean detailable;
    private boolean backable;
    private boolean saveable;
    private boolean searchable;

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
                return DTOs.marshalList(ChanceDto.class, 3, chanceList);
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
    }

    public ChanceDto newChanceDto() {
        ChanceDto chanceDto = new ChanceDto();
        UserDto owner = new UserDto();
        ChanceCategoryDto chanceCategoryDto = new ChanceCategoryDto();
        ChanceSourceDto chanceSourceDto = new ChanceSourceDto();
        List<ChancePartyDto> parties = new ArrayList<ChancePartyDto>();
        List<ChanceActionDto> actions = new ArrayList<ChanceActionDto>();
        ChanceStageDto chanceStageDto = new ChanceStageDto();

        chanceDto.setOwner(owner);
        chanceDto.setCategory(chanceCategoryDto);
        chanceDto.setSource(chanceSourceDto);
        chanceDto.setParties(parties);
        chanceDto.setActions(actions);
        chanceDto.setStage(chanceStageDto);

        return chanceDto;
    }

    @PostConstruct
    public void initialization() {
        initList();
        initToolbar();
        chance = newChanceDto();
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

    public List<SelectItem> getCategory() {
        List<ChanceCategory> chanceCategoryList = getDataManager().loadAll(ChanceCategory.class);
        List<ChanceCategoryDto> categoryDtoList = DTOs.marshalList(ChanceCategoryDto.class, chanceCategoryList);
        return UIHelper.selectItemsFromDict(categoryDtoList);
    }

    public List<SelectItem> getSource() {
        List<ChanceSourceType> sourceTypeList = getDataManager().loadAll(ChanceSourceType.class);
        List<ChanceSourceDto> chanceSourceDtoList = DTOs.marshalList(ChanceSourceDto.class, sourceTypeList);
        return UIHelper.selectItemsFromDict(chanceSourceDtoList);
    }

    public List<SelectItem> getStage() {
        List<ChanceStage> chanceStageList = getDataManager().loadAll(ChanceStage.class);
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
        if (selectedActions.length == 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "至少请选择一个客户"));
            return;
        }
        chance.addActions(selectedActions);
    }

    public void addChanceParty() {
        ChancePartyDto chancePartyDto = new ChancePartyDto();
        chancePartyDto.setChance(chance);
        chancePartyDto.setParty(selectedParty);
        chancePartyDto.setRole("普通客户");
        chance.addChanceParty(chancePartyDto);
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
