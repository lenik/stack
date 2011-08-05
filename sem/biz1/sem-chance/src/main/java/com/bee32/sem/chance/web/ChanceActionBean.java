package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;

@Component
@Scope("view")
public class ChanceActionBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    static final int TAB_INDEX = 0;
    static final int TAB_FORM = 1;

    static final String BUTTON_NEWACTION = "chanceActionForm:newActionButton";
    static final String BUTTON_EDITACTION = "chanceActionForm:editActionButton";
    static final String BUTTON_DELETEACTION = "chanceActionForm:deleteActionButton";
    static final String BUTTON_SAVEACTION = "chanceActionForm:saveActionButton";
    static final String BUTTON_RESET = "chanceActionForm:resetButton";
    static final String BUTTON_VIEWACTION = "chanceActionForm:viewActionButton";
    static final String DETAIL_TAB = "chanceActionForm:newAction";
    static final String DATATABLE_ACTIONS = "chanceActionForm:actions";
    static final String DATATABLE_PARTIES = "customerForm:customers";
    static final String DATATABLE_PARTNERS = "partnerForm:partners";

    private boolean isSearching;

    // 查找日志
    private Date searchBeginTime;
    private Date searchEndTime;

    private boolean editable;

    // 日志列表
    private LazyDataModel<ChanceActionDto> actions;

    // 选中的日志,编辑,新增,详细
    private ChanceActionDto selectedAction;

    private ChanceActionDto action;

    // 查找机会
    private String chancePattern;
    // 机会列表
    private List<ChanceDto> chances;
    // 选中的机会
    private ChanceDto selectedChance;

    // 查找客户
    private String customerPattern;

    // 客户列表
    private List<PartyDto> customers;

    // 查找合作伙伴
    private String partnerPattern;
    // 伙伴列表
    private List<UserDto> partners;
    // 选中的客户
    private PartyDto[] selectedCustomers;
    private PartyDto selectedCustomer;
    private UserDto selectedPartner;
    private UserDto[] selectedPartners;

    @PostConstruct
    public void init() {
        initList();
        action = null;
        editable = false;
    }

    public void search() {

        if (searchBeginTime != null && searchEndTime != null) {
            isSearching = true;
            EntityDataModelOptions<ChanceAction, ChanceActionDto> edmo = new EntityDataModelOptions<ChanceAction, ChanceActionDto>(
                    ChanceAction.class, ChanceActionDto.class);
            edmo.setCriteriaElements(//
                    Order.desc("createdDate"), //
                    ChanceCriteria.actedByCurrentUser(), //
                    ChanceCriteria.beganWithin(searchBeginTime, searchEndTime));
            actions = UIHelper.buildLazyDataModel(edmo);
            refreshActionCount(isSearching);
        }
        if (searchBeginTime == null) {
            uiLogger.error("开始时间为空");
        }
        if (searchEndTime == null) {
            uiLogger.error("结束时间为空");
        }

        initToolbar();
        setActiveTab(TAB_INDEX);
    }

    void initToolbar() {
        boolean temp = selectedAction == null ? false : true;
        setActiveTab(TAB_INDEX);
        findComponentEx(BUTTON_NEWACTION).setEnabled(true);
        findComponentEx(BUTTON_EDITACTION).setEnabled(true);
        findComponentEx(BUTTON_DELETEACTION).setEnabled(true);
        findComponentEx(BUTTON_VIEWACTION).setEnabled(temp);
        findComponentEx(BUTTON_RESET).setEnabled(false);
        findComponentEx(BUTTON_SAVEACTION).setEnabled(false);
    }

    public void initList() {
        isSearching = false;
        EntityDataModelOptions<ChanceAction, ChanceActionDto> emdo = new EntityDataModelOptions<ChanceAction, ChanceActionDto>(
                ChanceAction.class, ChanceActionDto.class, 0, //
                Order.desc("createdDate"), ChanceCriteria.actedByCurrentUser());
        actions = UIHelper.buildLazyDataModel(emdo);
        refreshActionCount(isSearching);
        initToolbar();
    }

    void refreshActionCount(boolean forSearch) {
        int count = serviceFor(ChanceAction.class).count(//
                ChanceCriteria.actedByCurrentUser(), //
                forSearch ? ChanceCriteria.beganWithin(searchBeginTime, searchEndTime) : null);

        actions.setRowCount(count);
    }

    public void findChances() {
        List<Chance> _chances = serviceFor(Chance.class).list(//
                Order.desc("createdDate"), EntityCriteria.ownedByCurrentUser(), //
                ChanceCriteria.subjectLike(chancePattern));

        chances = DTOs.marshalList(ChanceDto.class, 0, _chances, true);
    }

    public void chooseCustomerForm() {
        DataTable table = (DataTable) findComponent(DATATABLE_PARTIES);
        table.clearSelectedRowIndexes();
    }

    public void findCustomer() {
        List<Party> parties = serviceFor(Party.class).list(//
                EntityCriteria.ownedByCurrentUser(), //
                PeopleCriteria.namedLike(customerPattern));

        customers = DTOs.marshalList(PartyDto.class, 0, parties, true);
    }

    public void choosePartnerForm() {
        DataTable table = (DataTable) findComponent(DATATABLE_PARTNERS);
        table.clearSelectedRowIndexes();
    }

    public void findPartner() {
        List<User> _partners = serviceFor(User.class).list(ChanceCriteria.nameLike(partnerPattern));
        partners = DTOs.marshalList(UserDto.class, 0, _partners, true);
    }

    public void createForm() {
        action = new ChanceActionDto().create();
        selectedAction = null;
        setActiveTab(TAB_FORM);

        findComponent(DETAIL_TAB).setRendered(true);

        findComponentEx(BUTTON_NEWACTION).setEnabled(false);
        findComponentEx(BUTTON_EDITACTION).setEnabled(false);
        findComponentEx(BUTTON_DELETEACTION).setEnabled(false);
        findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
        findComponentEx(BUTTON_SAVEACTION).setEnabled(true);
        findComponentEx(BUTTON_RESET).setEnabled(true);
        editable = false;
    }

    public void editForm() {
        if (selectedAction == null) {
            uiLogger.error("请选择行动记录");
            return;
        }
        action = selectedAction;
        setActiveTab(TAB_FORM);
        findComponent(DETAIL_TAB).setRendered(true);

        findComponentEx(BUTTON_NEWACTION).setEnabled(false);
        findComponentEx(BUTTON_EDITACTION).setEnabled(false);
        findComponentEx(BUTTON_DELETEACTION).setEnabled(false);
        findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
        findComponentEx(BUTTON_SAVEACTION).setEnabled(true);
        findComponentEx(BUTTON_RESET).setEnabled(true);

        editable = false;
    }

    public void doDetailForm() {
        action = selectedAction;
        setActiveTab(TAB_FORM);
        findComponentEx(DETAIL_TAB).setRendered(true);

        findComponentEx(BUTTON_NEWACTION).setEnabled(true);
        findComponentEx(BUTTON_EDITACTION).setEnabled(false);
        findComponentEx(BUTTON_DELETEACTION).setEnabled(false);
        findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
        findComponentEx(BUTTON_RESET).setEnabled(true);
        findComponentEx(BUTTON_SAVEACTION).setEnabled(false);
        editable = true;
    }

    public void drop() {

        if (selectedAction == null) {
            uiLogger.error("请选择行动记录");
            return;
        }
        try {
            serviceFor(ChanceAction.class).delete(selectedAction.unmarshal());
            refreshActionCount(isSearching);

            findComponentEx(BUTTON_NEWACTION).setEnabled(true);
            findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
            findComponentEx(BUTTON_SAVEACTION).setEnabled(false);
            findComponentEx(BUTTON_RESET).setEnabled(false);

            DataTable table = (DataTable) findComponent(DATATABLE_ACTIONS);
            table.clearSelectedRowIndexes();

            editable = false;
            uiLogger.info("提示", "成功删除行动记录!");

        } catch (Exception e) {
            uiLogger.error("删除行动记录错误:" + e.getMessage(), e);
        }

    }

    public void cancel() {
        setActiveTab(TAB_INDEX);
        DataTable table = (DataTable) findComponent(DATATABLE_ACTIONS);
        table.clearSelectedRowIndexes();
        findComponent(DETAIL_TAB).setRendered(false);
        findComponentEx(BUTTON_EDITACTION).setEnabled(true);
        findComponentEx(BUTTON_NEWACTION).setEnabled(true);
        findComponentEx(BUTTON_DELETEACTION).setEnabled(true);
        findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
        findComponentEx(BUTTON_SAVEACTION).setEnabled(false);
        findComponentEx(BUTTON_RESET).setEnabled(false);

        editable = false;
        selectedAction = null;
    }

    public void chooseChance() {
        action.setChance(selectedChance);
    }

    public void addCustomer() {
        action.addParty(selectedCustomers);
    }

    public void deleteCustomer() {
        action.deleteParty(selectedCustomer);
    }

    public void addPartner() {
        action.addPartners(selectedPartners);
    }

    public void deletePartner() {
        action.deletePartner(selectedPartner);
    }

    public List<SelectItem> getChanceStages() {
        List<ChanceStage> chanceStageList = serviceFor(ChanceStage.class).list();
        List<ChanceStageDto> stageDtoList = DTOs.marshalList(ChanceStageDto.class, chanceStageList);
        return UIHelper.selectItemsFromDict(stageDtoList);
    }

    public List<SelectItem> getChanceActionStyles() {
        List<ChanceActionStyle> _styles = serviceFor(ChanceActionStyle.class).list();
        List<ChanceActionStyleDto> styles = DTOs.marshalList(ChanceActionStyleDto.class, _styles);
        return UIHelper.selectItemsFromDict(styles);
    }

    public void saveAction() {

        Date begin = action.getBeginTime();
        if (begin == null) {
            uiLogger.error("错误提示", "开始时间不能为空");
            return;
        }

        Date end = action.getEndTime();
        if (end == null) {
            uiLogger.error("错误提示", "结束时间不能为空");
            return;
        }

        if (action.getDescription() == null)
            action.setDescription("");
        if (action.getSpending() == null)
            action.setSpending("");

        ChanceStageDto stage = action.getStage();
        Long chanceId = action.getChance().getId();

        if (chanceId == null && !stage.isNullRef()) {
            uiLogger.error("错误提示", "必须先选择机会,才能设置机会阶段");
            return;
        }

        if (!stage.isNullRef()) {
            ChanceStage tempStage = serviceFor(ChanceStage.class).getOrFail(stage.getId());
            stage = DTOs.mref(ChanceStageDto.class, tempStage);
        }
// stage = reload(stage);
        action.pushStage(stage);

        ChanceDto chance;
        if (chanceId == null)
            chance = null;
        else {
            Chance __chance = serviceFor(Chance.class).getOrFail(chanceId);
            chance = DTOs.mref(ChanceDto.class, __chance);
        }
        action.setChance(chance);

        String styleId = action.getStyle().getId();
        if (styleId.isEmpty()) {
            uiLogger.error("错误提示:", "请选择洽谈方式!");
            return;
        }
        ChanceActionStyleDto style = new ChanceActionStyleDto().ref(styleId);
        action.setStyle(style);

        UserDto actor = new UserDto().ref(SessionLoginInfo.getUser().getId());
        action.setActor(actor);

        try {
            ChanceAction _action = action.unmarshal();
            Chance _chance = _action.getChance();
            if (_chance != null) {
                _chance.addAction(_action);
                serviceFor(Chance.class).saveOrUpdate(_chance);
            }
            serviceFor(ChanceAction.class).saveOrUpdate(_action);

            init();
            setActiveTab(TAB_INDEX);
            findComponent(DETAIL_TAB).setRendered(false);
            DataTable table = (DataTable) findComponent(DATATABLE_ACTIONS);
            table.clearSelectedRowIndexes();

            findComponentEx(BUTTON_NEWACTION).setEnabled(true);
            findComponentEx(BUTTON_EDITACTION).setEnabled(true);
            findComponentEx(BUTTON_DELETEACTION).setEnabled(true);
            findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
            findComponentEx(BUTTON_RESET).setEnabled(false);
            findComponentEx(BUTTON_SAVEACTION).setEnabled(false);

            uiLogger.info("保存销售机会行动记录成功!");
        } catch (Exception e) {
            uiLogger.error("保存行动记录失败:" + e.getMessage(), e);
        }

    }

    public void onRowSelect(SelectEvent event) {
        findComponentEx(BUTTON_VIEWACTION).setEnabled(true);
    }

    public void onRowUnselect(UnselectEvent event) {
        findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
    }

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean isSearching) {
        this.isSearching = isSearching;
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

    public ChanceActionDto getAction() {
        return action;
    }

    public void setAction(ChanceActionDto action) {
        this.action = action;
    }

    public String getChancePattern() {
        return chancePattern;
    }

    public void setChancePattern(String chancePattern) {
        this.chancePattern = chancePattern;
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

    public String getCustomerPattern() {
        return customerPattern;
    }

    public void setCustomerPattern(String customerPattern) {
        this.customerPattern = customerPattern;
    }

    public List<PartyDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<PartyDto> customers) {
        if (customers == null)
            customers = new ArrayList<PartyDto>();
        this.customers = customers;
    }

    public String getPartnerPattern() {
        return partnerPattern;
    }

    public void setPartnerPattern(String partnerPattern) {
        this.partnerPattern = partnerPattern;
    }

    public List<UserDto> getPartners() {
        return partners;
    }

    public void setPartners(List<UserDto> partners) {
        this.partners = partners;
    }

    public PartyDto[] getSelectedCustomers() {
        return selectedCustomers;
    }

    public void setSelectedCustomers(PartyDto... selectedCustomers) {
        this.selectedCustomers = selectedCustomers;
    }

    public PartyDto getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(PartyDto selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public UserDto getSelectedPartner() {
        return selectedPartner;
    }

    public void setSelectedPartner(UserDto selectedPartner) {
        this.selectedPartner = selectedPartner;
    }

    public UserDto[] getSelectedPartners() {
        return selectedPartners;
    }

    public void setSelectedPartners(UserDto[] selectedPartners) {
        this.selectedPartners = selectedPartners;
    }

}
