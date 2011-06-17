package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.SelectEvent;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.facade.ChanceServiceFacade;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.user.util.SessionLoginInfo;

@Component
@Scope("view")
public class ChanceActionBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private int currentTab;
    private boolean editable;
    private boolean detail;
    private LazyDataModel<ChanceActionDto> actions;
    private ChanceActionDto selectedAction;
    private ChanceActionDto chanceAction;
    private String chancePartten;
    private List<ChanceDto> chances;
    private ChanceDto selectedChance;
    private String customerPartten;
    private List<PartyDto> customers;
    private PartyDto selectedCustomer;

    @PostConstruct
    public void init() {

        actions = new LazyDataModel<ChanceActionDto>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<ChanceActionDto> load(int first, int pageSize, String sortField, boolean sortOrder,
                    Map<String, String> filters) {
                ChanceServiceFacade chanceService = getBean(ChanceServiceFacade.class);

                return chanceService.limitedChanceAction(first, pageSize);
            }
        };

        ChanceServiceFacade chanceService = getBean(ChanceServiceFacade.class);
        actions.setRowCount(chanceService.getChanceActionCount());
        currentTab = 0;
        editable = false;
        detail = true;
    }

    public void findChances() {
        if (chancePartten != null && !chancePartten.isEmpty()) {
            ChanceServiceFacade chanceService = getBean(ChanceServiceFacade.class);
            chances = chanceService.chanceSearch(chancePartten);
        }
    }

    public void findCustomer() {
        if (customerPartten != null && customerPartten.length() > 0) {
            List<Party> partyList = getDataManager().loadAll(Party.class);
            customers = DTOs.marshalList(PartyDto.class, partyList);
        }
    }

    public void createForm() {
        newAction();
        currentTab = 1;
        editable = true;
    }

    public void cancel() {
        currentTab = 0;
        editable = false;
    }

    public void newAction() {
        ChanceStageDto chanceStageDto = new ChanceStageDto();
        ChanceActionStyleDto chanceActionStyleDto = new ChanceActionStyleDto();
        List<PartyDto> partyDtoList = new ArrayList<PartyDto>();
        ChanceDto chanceDto = new ChanceDto();
        chanceAction = new ChanceActionDto();

        chanceAction.setChance(chanceDto);
        chanceAction.setStage(chanceStageDto);
        chanceAction.setStyle(chanceActionStyleDto);
        chanceAction.setParties(partyDtoList);
    }

    public void chooseChance() {
        chanceAction.setChance(selectedChance);
    }

    public void addCustomer() {
        chanceAction.addParties(selectedCustomer);
    }

    public void deleteCustomer() {
        chanceAction.deleteParties(selectedCustomer);
    }

    public List<SelectItem> getChanceStages() {
        List<ChanceStage> chanceStageList = getDataManager().loadAll(ChanceStage.class);
        List<ChanceStageDto> stageDtoList = DTOs.marshalList(ChanceStageDto.class, chanceStageList);
        return UIHelper.selectItemsFromDict(stageDtoList);
    }

    public List<SelectItem> getChanceActionStyles() {
        List<ChanceActionStyle> chanceActionStyleList = getDataManager().loadAll(ChanceActionStyle.class);
        List<ChanceActionStyleDto> chanceActionStyleDtoList = DTOs.marshalList(ChanceActionStyleDto.class,
                chanceActionStyleList);
        return UIHelper.selectItemsFromDict(chanceActionStyleDtoList);
    }

    public void saveAction() {

        FacesContext context = FacesContext.getCurrentInstance();

        String stageId = chanceAction.getStage().getId();
        Long chanceId = chanceAction.getChance().getId();

// 设置空的引用
// if (stageId.isEmpty())
// stageId = null;
// ChanceStageDto stage = new ChanceStageDto().ref(stageId);
// chanceAction.setStage(stage);

        ChanceStageDto stage;

        if (stageId.isEmpty())
            stage = null;
        else {
            stage = new ChanceStageDto().ref(stageId);
        }
        chanceAction.setStage(stage);

        ChanceDto chance;
        if (chanceId == null)
            chance = null;
        else
            chance = new ChanceDto().ref(chanceId);
        chanceAction.setChance(chance);

        String styleId = chanceAction.getStyle().getId();
        if (styleId.isEmpty()) {
            context.addMessage(null, new FacesMessage("提示", "请选择洽谈方式"));
            return;
        }
        ChanceActionStyleDto style = new ChanceActionStyleDto().ref(styleId);
        chanceAction.setStyle(style);

        HttpSession session = ThreadHttpContext.requireSession();
        UserDto actor = new UserDto().ref(SessionLoginInfo.requireCurrentUser(session).getId());
        chanceAction.setActor(actor);

        ChanceAction action = chanceAction.unmarshal();

        getDataManager().save(action);

        chanceAction.setStage(new ChanceStageDto());

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("提示", "保存销售机会行动记录成功!"));
        currentTab = 0;
        editable = false;

    }

    public void onRowSelect(SelectEvent event) {

    }

    public void onRowUnselect(UnselectEvent event) {

    }

    public int getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(int currentTab) {
        this.currentTab = currentTab;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public LazyDataModel<ChanceActionDto> getActions() {
        return actions;
    }

    public void setActions(LazyDataModel<ChanceActionDto> actions) {
        this.actions = actions;
    };

    public ChanceActionDto getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(ChanceActionDto selectedAction) {
        this.selectedAction = selectedAction;
    }

    public ChanceActionDto getChanceAction() {
        if (chanceAction == null)
            newAction();
        return chanceAction;
    }

    public void setChanceAction(ChanceActionDto chanceAction) {
        this.selectedAction = chanceAction;
    }

    public String getChancePartten() {
        return chancePartten;
    }

    public void setChancePartten(String chancePartten) {
        this.chancePartten = chancePartten;
    }

    public List<ChanceDto> getChances() {
        return chances;
    }

    public void setChances(List<ChanceDto> chances) {
        this.chances = chances;
    }

    public ChanceDto getSelectedChance() {
        return selectedChance;
    }

    public void setSelectedChance(ChanceDto selectedChance) {
        this.selectedChance = selectedChance;
    }

    public String getCustomerPartten() {
        return customerPartten;
    }

    public void setCustomerPartten(String customerPartten) {
        this.customerPartten = customerPartten;
    }

    public List<PartyDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<PartyDto> customers) {
        if (customers == null)
            customers = new ArrayList<PartyDto>();
        this.customers = customers;
    }

    public PartyDto getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(PartyDto selectedCustomer) {
// if (selectedCustomer == null)
// selectedCustomer = new PartyDto();
        this.selectedCustomer = selectedCustomer;
    }

}
