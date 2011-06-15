package com.bee32.sem.chance.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.jsf.FacesContextUtils;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.facade.ChanceServiceFacade;
import com.bee32.sem.chance.service.ChanceService;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;

@Component
@Scope("view")
public class ChanceActionBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private int currentTab;
    private boolean editable;
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
                ChanceServiceFacade chanceService = (ChanceServiceFacade) FacesContextUtils.getWebApplicationContext(
                        FacesContext.getCurrentInstance()).getBean("chanceServiceFacade");

                return chanceService.limitedChanceAction(first, pageSize);
            }
        };

        ChanceServiceFacade chanceService = (ChanceServiceFacade) FacesContextUtils.getWebApplicationContext(
                FacesContext.getCurrentInstance()).getBean("chanceServiceFacade");

        actions.setRowCount(chanceService.getChanceActionCount());
    }

    public void findChances() {
        if (chancePartten != null && !chancePartten.isEmpty()) {
            ChanceServiceFacade chanceService = (ChanceServiceFacade) FacesContextUtils.getWebApplicationContext(
                    FacesContext.getCurrentInstance()).getBean("chanceServiceFacade");
            chances = chanceService.chanceSearch(chancePartten);
        }
    }

    public void findCustomer() {
        if (customerPartten != null && customerPartten.length() > 0) {
            ChanceService chanceService = (ChanceService) FacesContextUtils.getWebApplicationContext(
                    FacesContext.getCurrentInstance()).getBean("chanceService");
            List<Party> partyList = chanceService.listAll();
            customers = DTOs.marshalList(PartyDto.class, partyList);
        }
    }

    public void createForm() {
        currentTab = 1;
        editable = true;
        newAction();
    }

    public void newAction() {
        ChanceStageDto chanceStageDto = new ChanceStageDto();
        ChanceActionStyleDto chanceActionStyleDto = new ChanceActionStyleDto();
        List<PartyDto> partyDtoList = Arrays.asList(new PartyDto(0));
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
        System.out.println("===========================");
        System.out.println(selectedCustomer.getName());
        System.out.println("===========================");

        List<PartyDto> parties = chanceAction.getParties();
        if (selectedCustomer != null && ! parties.contains(selectedCustomer))
            parties.add(selectedCustomer);

        System.out.println("===========================");
        for(PartyDto pdto : parties){
            System.out.println(pdto.getName());
        }
        System.out.println("===========================");

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
        if(selectedCustomer == null)
            selectedCustomer = new PartyDto();
        this.selectedCustomer = selectedCustomer;
    }

}
