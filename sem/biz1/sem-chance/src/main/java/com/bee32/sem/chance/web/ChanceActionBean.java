package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.service.ChanceService;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.user.util.SessionLoginInfo;

/**
 *
 */
@Component
@Scope("view")
public class ChanceActionBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    static final int TAB_INDEX = 0;
    static final int TAB_FORM = 1;

    // 查找日志
    private Date searchBeginTime;
    private Date searchEndTime;
    private boolean add;
    private boolean edable;
    private boolean detail;
    private boolean back;
    private boolean saveable;
    private boolean searchable;

    // 日志列表
    private LazyDataModel<ChanceActionDto> actions;

    // 选中的日志,编辑,新增,详细
    private ChanceActionDto selectedAction;

    private ChanceActionDto action;

    // 查找机会
    private String chancePartten;
    // 机会列表
    private List<ChanceDto> chances;
    // 选中的机会
    private ChanceDto selectedChance;

    // 查找客户
    private String customerPartten;
    // 客户列表
    private List<PartyDto> customers;
    // 选中的客户
    private PartyDto selectedCustomer;

    @PostConstruct
    public void init() {
        initList();
        action = new ChanceActionDto();
        searchable = false;
    }

    class ChanceActionSearchDataModel
            extends LazyDataModel<ChanceActionDto> {

        private static final long serialVersionUID = 1L;

        @Override
        public List<ChanceActionDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {
            ChanceService chanceService = getBean(ChanceService.class);
            List<ChanceAction> chanceActionList = chanceService.limitedChanceActionRangeList(searchBeginTime,
                    searchEndTime, first, pageSize);
            return DTOs.marshalList(ChanceActionDto.class, chanceActionList);
        }

    };

    public void search() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (searchBeginTime == null) {
            context.addMessage(null, new FacesMessage("错误提示", "请选择搜索的开始时间"));
            return;
        }

        if (searchEndTime == null) {
            context.addMessage(null, new FacesMessage("错误提示", "请选择搜索的结束时间"));
            return;
        }

        actions = new ChanceActionSearchDataModel();
        ChanceService chanceService = getBean(ChanceService.class);
        actions.setRowCount(chanceService.limitedChanceActionRangeListCount(searchBeginTime, searchEndTime));

        initToolbar();
        searchable = true;

    }

    void initToolbar() {
        setActiveTab(TAB_INDEX);
        add = false;
        edable = true;
        detail = true;
        back = true;
        saveable = true;
    }

    class ChanceActionDataModel
            extends LazyDataModel<ChanceActionDto> {

        private static final long serialVersionUID = 1L;

        @Override
        public List<ChanceActionDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                Map<String, String> filters) {

            ChanceService chanceService = getBean(ChanceService.class);
            List<ChanceAction> actionList = chanceService.limitedChanceActionList(first, pageSize);
            return DTOs.marshalList(ChanceActionDto.class, actionList);
        }

    };

    public void initList() {
        actions = new ChanceActionDataModel();

        ChanceService chanceService = getBean(ChanceService.class);
        actions.setRowCount(chanceService.getChanceActionCount());

        initToolbar();
        searchable = false;
    }

    public void reInitSearch() {
        initList();
        searchBeginTime = null;
        searchEndTime = null;
    }

    public void findChances() {
        if (chancePartten != null && !chancePartten.isEmpty()) {
            ChanceService chanceService = getBean(ChanceService.class);
            List<Chance> keywordList = chanceService.keywordChanceList(chancePartten);
            chances = DTOs.marshalList(ChanceDto.class, keywordList);
        }
    }

    public void findCustomer() {
        if (customerPartten != null && !customerPartten.isEmpty()) {
            ChanceService chanceService = getBean(ChanceService.class);
            List<Party> partyList = chanceService.keywordPartyList(customerPartten);
            customers = DTOs.marshalList(PartyDto.class, partyList);
        }
    }

    public void createForm() {
        action = new ChanceActionDto();
        setActiveTab(TAB_FORM);
        add = true;
        edable = true;
        detail = true;
        saveable = false;
        back = false;
    }

    public void editForm() {
        action = selectedAction;
        setActiveTab(TAB_FORM);
        add = true;
        edable = true;
        detail = true;
        saveable = false;
        back = false;
    }

    public void detailForm() {
        action = selectedAction;
        setActiveTab(TAB_FORM);
        add = false;
        edable = true;
        detail = true;
        back = false;
        saveable = true;
    }

    public void drop() {
        FacesContext context = FacesContext.getCurrentInstance();
        getDataManager().delete(selectedAction.unmarshal());
        ChanceService chanceService = getBean(ChanceService.class);
        actions.setRowCount(chanceService.getChanceActionCount());
        context.addMessage(null, new FacesMessage("提示", "成功删除行动记录"));
    }

    public void cancel() {
        setActiveTab(TAB_INDEX);
        add = false;
        edable = true;
        detail = true;
        saveable = true;
        back = true;
    }

    public void chooseChance() {
        action.setChance(selectedChance);
    }

    public void addCustomer() {
        action.addParty(selectedCustomer);
    }

    public void deleteCustomer() {
        action.deleteParty(selectedCustomer);
    }

    public List<SelectItem> getChanceStages() {
        List<ChanceStage> chanceStageList = serviceFor(ChanceStage.class).list();
        List<ChanceStageDto> stageDtoList = DTOs.marshalList(ChanceStageDto.class, chanceStageList);

        List<SelectItem> items = new ArrayList<SelectItem>();

        for (ChanceStageDto entry : stageDtoList) {
            SelectItem item = new SelectItem(entry.getIdX(), entry.getLabel());
            items.add(item);
        }

        return items;
    }

    public List<SelectItem> getChanceActionStyles() {
        List<ChanceActionStyle> chanceActionStyleList = serviceFor(ChanceActionStyle.class).list();
        List<ChanceActionStyleDto> chanceActionStyleDtoList = DTOs.marshalList(ChanceActionStyleDto.class,
                chanceActionStyleList);
        return UIHelper.selectItemsFromDict(chanceActionStyleDtoList);
    }

// public List<SelectItem> getActors() {
// List<User> actorList = getDataManager().loadAll(User.class);
// List<UserDto> actorDtoList = DTOs.marshalList(UserDto.class, actorList);
// return UIHelper.selectItemsFromUser(actorDtoList);
// }

    public void saveAction() {

        FacesContext context = FacesContext.getCurrentInstance();

        Date begin = action.getBeginTime();
        if (begin == null) {
            context.addMessage(null, new FacesMessage("错误提示", "开始时间不能为空"));
            return;
        }

        Date end = action.getEndTime();
        if (end == null) {
            context.addMessage(null, new FacesMessage("错误提示", "结束时间不能为空"));
            return;
        }

        if (action.getParties().size() == 0) {
            context.addMessage(null, new FacesMessage("错误提示", "请选择拜访客户"));
            return;
        }

        if (action.getContent().isEmpty())
            action.setContent("");
        if (action.getSpending().isEmpty())
            action.setSpending("");

        String stageId = action.getStage().getId();
        Long chanceId = action.getChance().getId();

        if (chanceId == null && !stageId.isEmpty()) {
            context.addMessage(null, new FacesMessage("错误提示", "必须先选择机会,才能设置机会阶段"));
            return;
        }

        if (stageId.isEmpty()) {
            action.setStage(null);
        } else {
            // XXX ref 应该改为mref(order有用) 没有只有id的mref
            ChanceStage _stage = loadEntity(ChanceStage.class, stageId);
            ChanceStageDto stage = DTOs.mref(ChanceStageDto.class, _stage);
            action.pushStage(stage);
        }

        ChanceDto chance;
        if (chanceId == null)
            chance = null;
        else{
            Chance __chance = getDataManager().fetch(Chance.class, chanceId);
            chance = DTOs.mref(ChanceDto.class, __chance);
        }
        action.setChance(chance);

        String styleId = action.getStyle().getId();
        if (styleId.isEmpty()) {
            context.addMessage(null, new FacesMessage("提示", "请选择洽谈方式"));
            return;
        }
        ChanceActionStyleDto style = new ChanceActionStyleDto().ref(styleId);
        action.setStyle(style);

        HttpSession session = ThreadHttpContext.requireSession();
        UserDto actor = new UserDto().ref(SessionLoginInfo.requireCurrentUser(session).getId());
        action.setActor(actor);

        ChanceAction _action = action.unmarshal();

        Chance _chance = _action.getChance();
        if (_chance != null) {
            _chance.pushStage(_action.getStage());
            getDataManager().save(_chance);
        }

        getDataManager().save(_action);

        action = new ChanceActionDto();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("提示", "保存销售机会行动记录成功!"));
        setActiveTab(TAB_INDEX);
        add = false;
        edable = true;
        detail = true;
        back = true;
        saveable = true;

    }

    public void onRowSelect(SelectEvent event) {
        add = false;
        edable = false;
        detail = false;
        saveable = true;
        back = true;
    }

    public void onRowUnselect(UnselectEvent event) {
        add = false;
        edable = true;
        detail = true;
        saveable = true;
        back = true;
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

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isEdable() {
        return edable;
    }

    public void setEdable(boolean edable) {
        this.edable = edable;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
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
        if (selectedAction == null)
            selectedAction = new ChanceActionDto();
        this.selectedAction = selectedAction;
    }

    public ChanceActionDto getAction() {
        if (action == null)
            action = new ChanceActionDto();
        return action;
    }

    public void setAction(ChanceActionDto action) {
        if (action == null)
            action = new ChanceActionDto();
        this.action = action;
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
