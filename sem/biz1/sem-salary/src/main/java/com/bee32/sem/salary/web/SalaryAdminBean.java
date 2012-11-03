package com.bee32.sem.salary.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.Pair;
import javax.free.TypeMatrix_BigDecimal;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.attendance.util.DefCriteria;
import com.bee32.sem.attendance.util.SalaryCriteria;
import com.bee32.sem.frame.ui.ELListMBean;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.web.EmployeeInfoCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.entity.MonthSalary;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;
import com.bee32.sem.salary.util.ChineseCodec;
import com.bee32.sem.salary.util.ColumnModel;
import com.bee32.sem.salary.util.SalaryDateUtil;
import com.bee32.sem.salary.util.SalaryTreeNode;
import com.bee32.sem.salary.util.TreeSalaryElementDefComparator;

public class SalaryAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    Date targetDate = new Date();

    SalaryElementDto selectedElement = new SalaryElementDto().create();

    List<ColumnModel> columns = new ArrayList<ColumnModel>();

    StreamedContent pdfFile;

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
                String expression = def.getExpr();
                StandardEvaluationContext context = new StandardEvaluationContext();
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

                SpelExpressionParser parser = new SpelExpressionParser();
                Expression expr;
                try {
                    expr = parser.parseExpression(ChineseCodec.encode(expression));
                } catch (ParseException e) {
                    uiLogger.error("表达式非法", e);
                    return false;
                }
                Object result;
                try {
                    result = (Number) expr.getValue(context);
                    System.out.println(result);
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

    /**
     * 更新月工资记录
     *
     * 效率比较低
     */
    public void updateMonthSalary() {
        int yearMonth = getYearMonth();
        BigDecimal total = BigDecimal.ZERO;
        List<Salary> salaries = DATA(Salary.class).list(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
        MonthSalary ms = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));

        if (salaries.size() == 0) {
            if (ms != null)
                DATA(MonthSalary.class).delete(ms);
        } else {
            for (Salary s : salaries) {
                total = total.add(s.getTotal());
            }
            if (ms == null) {
                ms = new MonthSalary();
                ms.setYearMonth(yearMonth);
            }
            ms.setValue(total);
            DATA(MonthSalary.class).saveOrUpdate(ms);
        }

    }

    public void exportToPdf() {

        openSelection();
        SalaryDto salary = getOpenedObject();
        if (salary == null) {
            System.out.println("openedObject is null");
            return;
        }

        Pair<Date, Date> datePair = SalaryDateUtil.toMonthRange(targetDate);
// List<EventBonusDto> events = mrefList(EventBonus.class, EventBonusDto.class, 0,
// EventBonusCriteria.listEvents(salary.getId(), datePair.getFirst(), datePair.getSecond()));
// IReportRowModel model = new IReportRowModel(salary.getElements(), events);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(salary.getElements());

        ClassLoader ccl = getClass().getClassLoader(); // Thread.currentThread().getContextClassLoader();
        InputStream reportStream = ccl.getResourceAsStream("resources/3/15/6/4/salary/report1.jrxml");

        try {
            BigDecimal tax = salary.getTax().setScale(4, BigDecimal.ROUND_HALF_UP);
            BigDecimal realsalary = salary.getTax().subtract(salary.getTax());
            JasperReport report = JasperCompileManager.compileReport(reportStream);
            Map<String, Object> reportParams = new HashMap<String, Object>();
            reportParams.put("title", "工资单");
            reportParams.put("employeeName", salary.getEmployee().getPersonName());
            reportParams.put("yearMonthString", salary.getYear() + "年" + salary.getMonth() + "月份");
            reportParams.put("total", salary.getTotal());
            reportParams.put("tax", tax);
            reportParams.put("salary", realsalary);
            System.out.println(reportParams);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportParams, beanCollectionDataSource);
            byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);

            InputStream stream = new ByteArrayInputStream(pdfByteArray);
            pdfFile = new DefaultStreamedContent(stream, "application/pdf", "test.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        SalaryDto dto = getOpenedObject();

        int yearMonth = getYearMonth();
        MonthSalary ms = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
        if (ms == null) {
            ms = new MonthSalary();
            ms.setYearMonth(yearMonth);
        }
        ms.setValue(dto.getTotal());
        DATA(MonthSalary.class).saveOrUpdate(ms);

        super.postUpdate(uMap);
    }

    @Override
    protected void postDelete(UnmarshalMap uMap)
            throws Exception {
        int yearMonth = getYearMonth();
        SalaryDto dto = getOpenedObject();

        MonthSalary monthSalary = DATA(MonthSalary.class).getUnique(SalaryCriteria.listSalaryByYearAndMonth(yearMonth));
        BigDecimal total = monthSalary.getValue();
        total = total.subtract(dto.getTotal());
        if (total.equals(BigDecimal.ZERO))
            DATA(MonthSalary.class).delete(monthSalary);
        else {
            monthSalary.setValue(total);
            DATA(MonthSalary.class).save(monthSalary);
        }
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

    public StreamedContent getPdfFile() {
        return pdfFile;
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
