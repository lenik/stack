package com.bee32.sem.chance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;

import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.color.MomentIntervalCriteria;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.dto.ChanceActionStyleDto;
import com.bee32.sem.chance.dto.ChanceDto;
import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.util.ChanceCriteria;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.MultiTabEntityVdx;
import com.bee32.sem.sandbox.UIHelper;

@ForEntity(ChanceAction.class)
public class ChanceActionBean
        extends MultiTabEntityVdx {

    private static final long serialVersionUID = 1L;

    static final String BUTTON_NEWACTION = "chanceActionForm:newActionButton";
    static final String BUTTON_EDITACTION = "chanceActionForm:editActionButton";
    static final String BUTTON_DELETEACTION = "chanceActionForm:deleteActionButton";
    static final String BUTTON_SAVEACTION = "chanceActionForm:saveActionButton";
    static final String BUTTON_RESET = "chanceActionForm:resetButton";
    static final String BUTTON_VIEWACTION = "chanceActionForm:viewActionButton";

    static final String DETAIL_TAB = "chanceActionForm:mainTab:newAction";
    static final String DATATABLE_ACTIONS = "chanceActionForm:mainTab:actions";

    static final String DATATABLE_PARTIES = "customerForm:customers";
    static final String DATATABLE_PARTNERS = "partnerForm:partnersTable";

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
    private UserDto[] choosedPartners;

    public ChanceActionBean() {
        super(ChanceAction.class, ChanceActionDto.class, ChanceActionDto.PARTIES | ChanceActionDto.PARTNERS);
    }

    @PostConstruct
    public void init() {
        initList();
        action = null;
        editable = false;
    }

    public void search() {
        EntityDataModelOptions<ChanceAction, ChanceActionDto> edmo = new EntityDataModelOptions<ChanceAction, ChanceActionDto>(
                ChanceAction.class, ChanceActionDto.class, //
                ChanceActionDto.PARTIES | ChanceActionDto.PARTNERS);
        edmo.setCriteriaElements(//
                Order.desc("createdDate"), //
                ChanceCriteria.actedByCurrentUser(), //
                MomentIntervalCriteria.timeBetween(searchBeginTime, searchEndTime));
        actions = UIHelper.buildLazyDataModel(edmo);
        refreshActionCount();
        initToolbar();
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
        EntityDataModelOptions<ChanceAction, ChanceActionDto> emdo = new EntityDataModelOptions<ChanceAction, ChanceActionDto>(
                ChanceAction.class, ChanceActionDto.class, 0, //
                Order.desc("createdDate"), ChanceCriteria.actedByCurrentUser());
        actions = UIHelper.buildLazyDataModel(emdo);
        refreshActionCount();
        initToolbar();
    }

    void refreshActionCount() {
        int count = serviceFor(ChanceAction.class).count(//
                ChanceCriteria.actedByCurrentUser(), //
                MomentIntervalCriteria.timeBetween(searchBeginTime, searchEndTime));

        actions.setRowCount(count);
    }

    public void findChances() {
        List<Chance> _chances = serviceFor(Chance.class).list(//
                Order.desc("createdDate"), //
                ChanceCriteria.subjectLike(chancePattern));

        chances = DTOs.mrefList(ChanceDto.class, 0, _chances);
    }

    public void chooseCustomerForm() {
        //DataTable table = (DataTable) findComponent(DATATABLE_PARTIES);
        // XXX table.clearSelectedRowIndexes();
    }

    public void findCustomer() {
        List<Party> parties = serviceFor(Party.class).list(//
                PeopleCriteria.namedLike(customerPattern));

        customers = DTOs.mrefList(PartyDto.class, PartyDto.CONTACTS, parties);
    }

    public void choosePartnerForm() {
        //DataTable table = (DataTable) findComponent(DATATABLE_PARTNERS);
        // XXX table.clearSelectedRowIndexes();
    }

    public void findPartner() {
        List<User> _partners = serviceFor(User.class).list(ChanceCriteria.nameLike(partnerPattern));
        partners = DTOs.mrefList(UserDto.class, 0, _partners);
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
        ChanceAction _action = serviceFor(ChanceAction.class).getOrFail(selectedAction.getId());
        action = DTOs.marshal(ChanceActionDto.class, _action);

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
            refreshActionCount();

            findComponentEx(BUTTON_NEWACTION).setEnabled(true);
            findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
            findComponentEx(BUTTON_SAVEACTION).setEnabled(false);
            findComponentEx(BUTTON_RESET).setEnabled(false);

            //DataTable table = (DataTable) findComponent(DATATABLE_ACTIONS);
            // XXX table.clearSelectedRowIndexes();

            editable = false;
            uiLogger.info("提示", "成功删除行动记录!");

        } catch (Exception e) {
            uiLogger.error("删除行动记录错误", e);
        }

    }

    public void cancel() {
        setActiveTab(TAB_INDEX);
        //DataTable table = (DataTable) findComponent(DATATABLE_ACTIONS);
        // XXX table.clearSelectedRowIndexes();
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
        for (PartyDto party : selectedCustomers) {
            action.addParty(party);
        }

        selectedCustomers = new PartyDto[0];
    }

    public void addPartner() {
        for (UserDto partner : choosedPartners) {
            action.addPartner(partner);
        }

        choosedPartners = new UserDto[0];
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

        if (action.getMoreInfo() == null)
            action.setMoreInfo("");
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

        UserDto actor = new UserDto().ref(SessionUser.getInstance().getUser().getId());
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
            //DataTable table = (DataTable) findComponent(DATATABLE_ACTIONS);
            // XXX table.clearSelectedRowIndexes();

            findComponentEx(BUTTON_NEWACTION).setEnabled(true);
            findComponentEx(BUTTON_EDITACTION).setEnabled(true);
            findComponentEx(BUTTON_DELETEACTION).setEnabled(true);
            findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
            findComponentEx(BUTTON_RESET).setEnabled(false);
            findComponentEx(BUTTON_SAVEACTION).setEnabled(false);

            uiLogger.info("保存日志成功!");
        } catch (Exception e) {
            uiLogger.error("保存日志失败", e);
        }
    }

    public void onRowSelect(SelectEvent event) {
        findComponentEx(BUTTON_VIEWACTION).setEnabled(true);
    }

    public void onRowUnselect(UnselectEvent event) {
        findComponentEx(BUTTON_VIEWACTION).setEnabled(false);
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

    public UserDto[] getChoosedPartners() {
        return choosedPartners;
    }

    public void setChoosedPartners(UserDto[] choosedPartners) {
        this.choosedPartners = choosedPartners;
    }

}
