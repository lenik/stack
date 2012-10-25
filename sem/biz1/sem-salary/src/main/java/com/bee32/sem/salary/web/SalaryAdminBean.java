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
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.attendance.util.DefCriteria;
import com.bee32.sem.attendance.util.EventBonusCriteria;
import com.bee32.sem.attendance.util.SalaryCriteria;
import com.bee32.sem.frame.ui.ELListMBean;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.EventBonusDto;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.entity.EventBonus;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;
import com.bee32.sem.salary.util.ChineseCodec;
import com.bee32.sem.salary.util.ColumnModel;
import com.bee32.sem.salary.util.IReportRowModel;
import com.bee32.sem.salary.util.SalaryDateUtil;
import com.bee32.sem.salary.util.SalaryTreeNode;
import com.bee32.sem.salary.util.TreeSalaryElementDefComparator;

public class SalaryAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    Date targetDate = new Date();
    int targetYear;
    int targetMonth;
    SalaryElementDto selectedElement = new SalaryElementDto().create();

    List<ColumnModel> columns = new ArrayList<ColumnModel>();

    StreamedContent pdfFile;

    public SalaryAdminBean() {
        super(Salary.class, SalaryDto.class, SalaryDto.ELEMENTS, SalaryCriteria.listByDate(new Date()));

        changeTargetDate();
        generateColumns();
    }

    public void addDateRestriction() {
        changeTargetDate();

        setSearchFragment("yearmonth", "限定工资为" + targetYear + "年" + targetMonth + "月",
                SalaryCriteria.listSalaryByYearAndMonth(targetYear, targetMonth));
    }

    List<Salary> listSalary(int year, int month) {
        return DATA(Salary.class).list(SalaryCriteria.listSalaryByYearAndMonth(year, month));
    }

    boolean salaryExisting(int year, int month) {
        List<Salary> salaries = listSalary(year, month);
        if (salaries.size() == 0 || salaries == null)
            return false;
        else
            return true;
    }

    public boolean generateSalary() {

        boolean existing = salaryExisting(targetYear, targetMonth);

        if (existing) {
            uiLogger.info(targetYear + "年" + targetMonth + "月工资已经存在");
            return false;
        }

        List<EmployeeInfo> employeeList = DATA(EmployeeInfo.class).list();
        List<SalaryElementDef> efficaciousDef = DATA(SalaryElementDef.class).list(
                DefCriteria.listEffectiveDef(targetDate));

        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Salary> salarys = new ArrayList<Salary>();

        Pair<Date, Date> rangePair = SalaryDateUtil.toMonthRange(targetDate);

        for (EmployeeInfo employee : employeeList) {
            List<SalaryElement> elements = new ArrayList<SalaryElement>();
            Salary salary = new Salary();
            salary.setYear(targetYear);
            salary.setMonth(targetMonth);

            salary.setEmployee(employee);

            map.clear();
            map.put(ISalaryVariableProvider.ARG_EMPLOYEE, employee);
            map.put(ISalaryVariableProvider.ARG_BEGIN_DATE, rangePair.getFirst());
            map.put(ISalaryVariableProvider.ARG_END_DATE, rangePair.getSecond());
            map.put(ISalaryVariableProvider.ARG_YEARMONTH, targetYear*100 + targetMonth);
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
                } catch (EvaluationException e) {
                    uiLogger.error("无法对表达式求值", e);
                    return false;
                }
                BigDecimal value = TypeMatrix_BigDecimal.fromObject(result);

                SalaryElement element = new SalaryElement();
                element.setParent(salary);
                element.setDef(def);
                element.setBonus(value);
                elements.add(element);
                salary.setElements(elements);
            }

            salarys.add(salary);
        }
        DATA(Salary.class).saveAll(salarys);
        uiLogger.info("创建工资记录成功");
        return true;
    }

    public boolean deleteSalary() {
        try {
            List<Salary> salaries = listSalary(targetYear, targetMonth);
            if (salaries == null || salaries.size() == 0) {
                uiLogger.error("没有[" + targetYear + "年" + targetMonth + "月] 工资记录存在!");
                return false;
            }
            DATA(Salary.class).deleteAll(salaries);
            uiLogger.info("删除[" + targetYear + "年" + targetMonth + "月] 工资成功!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            uiLogger.error("删除[" + targetYear + "年" + targetMonth + "月] 工资失败!", e);
            return false;
        }

    }

    void changeTargetDate() {

        Pair<Integer, Integer> yearAndMonth = SalaryDateUtil.getYearAndMonth(targetDate);
        targetYear = yearAndMonth.getFirst();
        targetMonth = yearAndMonth.getSecond();
    }

    void generateColumns() {

        List<SalaryElementDef> effectiveDefs = DATA(SalaryElementDef.class).list(
                DefCriteria.listEffectiveDef(targetDate));

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

    public void exportToPdf() {

        showContent();
        SalaryDto salary = this.getOpenedObject();
        if (salary == null) {
            System.out.println("openedObject is null");
            return;
        }

        Pair<Date, Date> datePair = SalaryDateUtil.toMonthRange(targetDate);
        List<EventBonusDto> events = mrefList(EventBonus.class, EventBonusDto.class, 0,
                EventBonusCriteria.listEvents(salary.getId(), datePair.getFirst(), datePair.getSecond()));

        IReportRowModel model = new IReportRowModel(salary.getElements(), events);

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
            reportParams.put("year", salary.getYear());
            reportParams.put("month", salary.getMonth());
            reportParams.put("total", salary.getTotal());
            reportParams.put("tax", tax);
            reportParams.put("salary", realsalary);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, reportParams, beanCollectionDataSource);
            byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);

            InputStream stream = new ByteArrayInputStream(pdfByteArray);
            pdfFile = new DefaultStreamedContent(stream, "application/pdf", salary.getEmployee().getPersonName() + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public int getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(int targetYear) {
        this.targetYear = targetYear;
    }

    public int getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(int targetMonth) {
        this.targetMonth = targetMonth;
    }

    public SalaryElementDto getSelectedElement() {
        return selectedElement;
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
