package com.bee32.sem.salary.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.free.Pair;
import javax.free.TypeMatrix_BigDecimal;
import javax.free.UnexpectedException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.util.ChineseCodec;
import com.bee32.plover.util.Mime;
import com.bee32.plover.util.Mimes;
import com.bee32.plover.util.PloverEvaluationContext;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.attendance.util.DefCriteria;
import com.bee32.sem.attendance.util.EventBonusCriteria;
import com.bee32.sem.attendance.util.SalaryCriteria;
import com.bee32.sem.frame.ui.ELListMBean;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.web.EmployeeInfoCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.salary.dto.EventBonusDto;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.entity.EventBonus;
import com.bee32.sem.salary.entity.MonthSalary;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;
import com.bee32.sem.salary.util.ColumnModel;
import com.bee32.sem.salary.util.CrosstabModel;
import com.bee32.sem.salary.util.PersonSalaryReportModel;
import com.bee32.sem.salary.util.SalaryDateUtil;
import com.bee32.sem.salary.util.SalaryTreeNode;
import com.bee32.sem.salary.util.TreeSalaryElementDefComparator;

public class SalaryAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;
    private static final Mime PDF = Mimes.application_pdf;
    private static final Mime CSV = Mimes.text_csv;

    Date targetDate = new Date();
    SalaryElementDto selectedElement = new SalaryElementDto().create();
    List<ColumnModel> columns = new ArrayList<ColumnModel>();

    public SalaryAdminBean() {
        super(Salary.class, SalaryDto.class, SalaryDto.ELEMENTS);

        generateColumns();
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        elements.add(SalaryCriteria.listSalaryByYearAndMonth(getYearMonth()));
    }

    List<Salary> listSalary(int yearMonth) {
        return DATA(Salary.class).list(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
    }

    boolean salaryExisting(int yearMonth) {
        List<Salary> salaries = listSalary(yearMonth);
        if (salaries.size() == 0 || salaries == null)
            return false;
        else
            return true;
    }

    public boolean generateSalary() {

        int yearMonth = getYearMonth();
        boolean existing = salaryExisting(yearMonth);
        if (existing) {
            uiLogger.info(yearMonth / 100 + "年" + yearMonth % 100 + "月工资已经存在");
            return false;
        }

        BigDecimal total = BigDecimal.ZERO;
        Pair<Date, Date> rangePair = SalaryDateUtil.toMonthRange(targetDate);
        List<EmployeeInfo> employeeList = DATA(EmployeeInfo.class).list(
                EmployeeInfoCriteria.listInfo(rangePair.getFirst()));
        List<SalaryElementDef> efficaciousDef = DATA(SalaryElementDef.class).list(
                DefCriteria.listEffectiveDef(rangePair.getFirst(), rangePair.getSecond()));

        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Salary> salarys = new ArrayList<Salary>();

        for (EmployeeInfo employee : employeeList) {
            Salary salary = new Salary();
            salary.setYearMonth(yearMonth);
            salary.setEmployee(employee);
            List<SalaryElement> elements = new ArrayList<SalaryElement>();

            map.clear();
            map.put(ISalaryVariableProvider.ARG_EMPLOYEE, employee);
            map.put(ISalaryVariableProvider.ARG_BEGIN_DATE, rangePair.getFirst());
            map.put(ISalaryVariableProvider.ARG_END_DATE, rangePair.getSecond());
            map.put(ISalaryVariableProvider.ARG_YEARMONTH, yearMonth);
            // TODO add other args

            final TextMap args = new TextMap(map);

            for (SalaryElementDef def : efficaciousDef) {
                PloverEvaluationContext context = new PloverEvaluationContext();
                BeanResolver resolver = new BeanResolver() {
                    @Override
                    public Double resolve(EvaluationContext context, String beanName)
                            throws AccessException {

                        beanName = ChineseCodec.decode(beanName);
                        for (ISalaryVariableProvider provider : SalaryVariableProviders.getProviders()) {
                            BigDecimal bonus = provider.evaluate(args, beanName);
                            if (bonus != null)
                                return bonus.doubleValue();
                        }
                        return 0.0;
                    }
                };
                context.setBeanResolver(resolver);

                Number result;
                try {
                    result = (Number) context.evaluateZh(def.getExpr());
                } catch (ParseException e) {
                    uiLogger.error("表达式非法", e);
                    return false;
                } catch (EvaluationException e) {
                    uiLogger.error("无法对表达式求值", e);
                    return false;
                }
                BigDecimal value = TypeMatrix_BigDecimal.fromObject(result);

                SalaryElement element = new SalaryElement();
                element.setParent(salary);
                element.setDef(def);
                element.setBonus(value);
                total = total.add(value);
                elements.add(element);
            }
            salary.setElements(elements);
            salarys.add(salary);
        }
        DATA(Salary.class).saveAll(salarys);
        uiLogger.info("创建工资记录成功");

        MonthSalary ms = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
        if (ms == null) {
            ms = new MonthSalary();
            ms.setYearMonth(yearMonth);
        }
        ms.setValue(total);

        DATA(MonthSalary.class).saveOrUpdate(ms);
        uiLogger.info("更新月度工资冗余表");

        refreshData();
        return true;
    }

    public boolean deleteSalary() {
        int yearMonth = getYearMonth();
        try {
            List<Salary> salaries = listSalary(yearMonth);
            if (salaries == null || salaries.size() == 0) {
                uiLogger.error("没有[" + yearMonth / 100 + "年" + yearMonth % 100 + "月] 工资记录存在!");
                return false;
            }
            DATA(Salary.class).deleteAll(salaries);
            uiLogger.info("删除[" + yearMonth / 100 + "年" + yearMonth % 100 + "月] 工资成功!");
            MonthSalary unique = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
            DATA(MonthSalary.class).delete(unique);
            refreshData();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            uiLogger.error("删除[" + yearMonth / 100 + "年" + yearMonth % 100 + "月] 工资失败!", e);
            return false;
        }

    }

    void generateColumns() {
        Pair<Date, Date> rangePair = SalaryDateUtil.toMonthRange(targetDate);
        List<SalaryElementDef> effectiveDefs = DATA(SalaryElementDef.class).list(
                DefCriteria.listEffectiveDef(rangePair.getFirst(), rangePair.getSecond()));

        SalaryTreeNode root = createSalaryNodeTree();
        TreeSalaryElementDefComparator comparator = new TreeSalaryElementDefComparator(root);
        Collections.sort(effectiveDefs, comparator);

        for (SalaryElementDef def : effectiveDefs) {
            int index = columns.size();
            columns.add(new ColumnModel(def.getLabel() == null ? def.getCategory() : def.getLabel(), index, def.getId()));
        }
    }

    SalaryTreeNode createSalaryNodeTree() {
        SalaryTreeNode root = new SalaryTreeNode();
        root.setLabel("root");

        List<SalaryElementDef> defs = DATA(SalaryElementDef.class).list();
        for (SalaryElementDef def : defs) {
            String path = def.getPath();
            SalaryTreeNode node = root.resolve(path);
            node.setOrder(def.getOrder());
        }

        root.updateOrder();
        return root;
    }

    public List<SalaryElementDto> sortElements(SalaryDto salary) {
        List<SalaryElementDto> elements = salary.getElements();
        List<SalaryElementDto> sorted = new ArrayList<SalaryElementDto>(columns.size());
        for (ColumnModel column : columns) {
            long defId = column.getDefId();
            SalaryElementDto found = null;
            for (SalaryElementDto element : elements) {
                if (element.getDef().getId() == defId) {
                    found = element;
                    break;
                }
            }
            if (found == null)
                found = new SalaryElementDto();
            sorted.add(found);
        }
        return sorted;
    }

    public void exportPersonalReport() {

        openSelection();
        SalaryDto salary = getOpenedObject();
        if (salary == null) {
            System.out.println("openedObject is null");
            return;
        } else {
            // initialization
            FacesContext context = FacesContext.getCurrentInstance();
            InputStream reportTemplate = getClass().getClassLoader().getResourceAsStream(
                    "resources/3/15/6/4/salary/report1.jrxml");
            Pair<Date, Date> datePair = SalaryDateUtil.toMonthRange(targetDate);
            List<EventBonusDto> events = mrefList(EventBonus.class, EventBonusDto.class, 0,
                    EventBonusCriteria.listEvents(salary.getId(), datePair.getFirst(), datePair.getSecond()));
            PersonSalaryReportModel personalSalaryModel = new PersonSalaryReportModel();
            List<PersonSalaryReportModel> sourceList = new ArrayList<PersonSalaryReportModel>();
            Map<String, Object> reportParams = new HashMap<String, Object>();

            // set
            personalSalaryModel.setElements(salary.getElements());
            personalSalaryModel.setEvents(events);
            sourceList.add(personalSalaryModel);
            JRBeanCollectionDataSource beanSource = new JRBeanCollectionDataSource(sourceList);
            reportParams.put("title", "工资单");
            reportParams.put("employeeName", salary.getEmployee().getPersonName());
            reportParams.put("yearMonthString", salary.getYear() + "年" + salary.getMonth() + "月份");
            reportParams.put("total", salary.getTotal());
            reportParams.put("tax", salary.getTax().setScale(4, BigDecimal.ROUND_HALF_UP));
            reportParams.put("salary", salary.getTax().subtract(salary.getTax()));

            // do export
            doExportToPdf(context, reportTemplate, reportParams, beanSource, "测试", PDF);
        }

    }

    public void exportMonthReport() {

        String directory = "resources/3/15/6/4/salary/report2.jrxml";
        String fileName = getYear() + "年" + getMonth() + "月 工资表";
        FacesContext context = FacesContext.getCurrentInstance();
        InputStream reportTemplate;
        List<CrosstabModel> crosstabCollection;
// List<SalaryDto> monthList;
        JRBeanCollectionDataSource datasource;

        reportTemplate = getClass().getClassLoader().getResourceAsStream(directory);
        crosstabCollection = new ArrayList<CrosstabModel>();
// monthList = mrefList(Salary.class, SalaryDto.class, SalaryDto.ELEMENTS,
// SalaryCriteria.listSalaryByYearAndMonth(getYearMonth()));

        for (int i = 0; i < 3; i++) {
            CrosstabModel model = new CrosstabModel();
            model.setPersonName("test");
            model.setElementDef("test");
            model.setBonus(BigDecimal.ZERO);
            crosstabCollection.add(model);
        }
// for (SalaryDto salary : monthList) {
// String personName = salary.getEmployee().getPersonName();
// for (SalaryElementDto element : salary.getElements()) {
// CrosstabModel model = new CrosstabModel();
// model.setPersonName(personName);
// model.setElementDef(element.getDef().getTitle());
// model.setBonus(element.getBonus());
// crosstabCollection.add(model);
// }
// }
        datasource = new JRBeanCollectionDataSource(crosstabCollection);

        doExportToPdf(context, reportTemplate, null, datasource, fileName, PDF);
    }

    static void doExportToPdf(FacesContext context, InputStream template, Map<String, Object> params,
            JRDataSource datasource, String fileName, Mime contentType) {
        byte[] pdf;
        JasperReport report;
        ServletOutputStream outputStream;
        HttpServletResponse response;

        response = (HttpServletResponse) context.getExternalContext().getResponse();

        /**
         * @see RFC 5987 2.3
         */
        String encodedFilename;
        try {
            encodedFilename = URLEncoder.encode(fileName, "utf-8");
            System.out.println(encodedFilename);
        } catch (UnsupportedEncodingException e) {
            throw new UnexpectedException(e);
        }
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename + ".pdf");
        response.setCharacterEncoding("UTF-8");

        response.setContentType(contentType.getContentType());

        try {
            report = JasperCompileManager.compileReport(template);
            pdf = JasperRunManager.runReportToPdf(report, params, datasource);
            outputStream = response.getOutputStream();
            outputStream.write(pdf);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ioexception) {
            throw new RuntimeException(ioexception.getMessage(), ioexception);
        } catch (JRException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        context.responseComplete();
    }

    /**
     * 更新月工资记录
     */
    public boolean updateMonthSalary() {
        int yearMonth = getYearMonth();
        BigDecimal total = BigDecimal.ZERO;
        List<Salary> salaries = DATA(Salary.class).list(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
        MonthSalary ms = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));

        if (salaries.size() == 0) {
            if (ms != null)
                DATA(MonthSalary.class).delete(ms);
        } else {
            if (ms == null) {
                ms = new MonthSalary();
                ms.setYearMonth(yearMonth);
            }
            for (Salary s : salaries) {
                total = total.add(s.getTotal());
            }
            ms.setValue(total);
            try {
                DATA(MonthSalary.class).saveOrUpdate(ms);
            } catch (Exception e) {
                uiLogger.error(e);
                return false;
            }
        }
        return true;

    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        int yearMonth = getYearMonth();
        MonthSalary ms = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
        if (ms == null) {
            ms = new MonthSalary();
            ms.setYearMonth(yearMonth);
        }
        if (ms.isLocked()) {
            uiLogger.error("月工资表被锁定，此工资记录改动不能被保存");
            return false;
        }
        return super.preUpdate(uMap);
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        updateMonthSalary();
        super.postUpdate(uMap);
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        int yearMonth = getYearMonth();
        MonthSalary monthSalary = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
        if (monthSalary.isLocked()) {
            uiLogger.error("月工资表被锁定，此工资记录改动不能被保存");
            return false;
        }
        return super.preDelete(uMap);
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        updateMonthSalary();
        super.postDelete(uMap);
    }

    @Override
    protected Object create() {
        int yearMonth = getYearMonth();
        SalaryDto create = (SalaryDto) super.create();
        create.setYear(yearMonth / 100);
        create.setMonth(yearMonth % 100);
        return create;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public int getYear() {
        return getYearMonth() / 100;
    }

    public int getMonth() {
        return getYearMonth() % 100;
    }

    public SalaryElementDto getSelectedElement() {
        return selectedElement;
    }

    public int getYearMonth() {
        return SalaryDateUtil.convertToYearMonth(targetDate);
    }

    public void setSelectedElement(SalaryElementDto selectedElement) {
        this.selectedElement = selectedElement;
    }

    final ListMBean<SalaryElementDto> salaryElementMBean = ELListMBean.fromEL(this,//
            "openedObject.elements", SalaryElementDto.class);

    public ListMBean<SalaryElementDto> getSalaryElementMBean() {
        return salaryElementMBean;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

}
