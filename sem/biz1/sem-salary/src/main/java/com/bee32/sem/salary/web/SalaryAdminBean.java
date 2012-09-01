package com.bee32.sem.salary.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.free.Pair;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.attendance.util.SalaryCriteria;
import com.bee32.sem.frame.ui.ELListMBean;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;
import com.bee32.sem.salary.expr.ChineseCodec;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class SalaryAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    Date targetDate = new Date();
    int targetYear;
    int targetMonth;
    SalaryElementDto selectedElement = new SalaryElementDto().create();

    List<ColumnModel> columns = new ArrayList<ColumnModel>();

    public SalaryAdminBean() {
        super(Salary.class, SalaryDto.class, SalaryDto.ELEMENTS);

        changeTargetDate();

        genericColumns();
    }

    public void addDateRestriction() {
        // setSearchFragment("date", "限定工资为" + SalaryDateUtil.getMonNum(targetDate) + "月份",
        // AttendanceCriteria.getMonthList(targetDate));
        changeTargetDate();
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
        List<SalaryElementDef> efficaciousDef = DATA(SalaryElementDef.class).list();

        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Salary> salarys = new ArrayList<Salary>();

        for (EmployeeInfo employee : employeeList) {
            List<SalaryElement> elements = new ArrayList<SalaryElement>();
            Salary salary = new Salary();
            salary.setYear(targetYear);
            salary.setMonth(targetMonth);

            salary.setEmployee(employee);

            map.clear();
            map.put(ISalaryVariableProvider.ARG_EMPLOYEE, employee);
            // TODO add other args

            final TextMap args = new TextMap(map);

            for (SalaryElementDef def : efficaciousDef) {
                // TODO
                String expression = def.getExpr();

                StandardEvaluationContext context = new StandardEvaluationContext();

                BeanResolver resolver = new BeanResolver() {

                    @Override
                    public Object resolve(EvaluationContext context, String beanName)
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
                Expression expr = parser.parseExpression(ChineseCodec.encode(expression));
                double result = (Double) expr.getValue(context);

                SalaryElement element = new SalaryElement();
                element.setParent(salary);
                element.setDef(def);
                element.setBonus(new BigDecimal(result));
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

    void genericColumns() {
        List<SalaryElementDef> effectiveDefs = DATA(SalaryElementDef.class).list();
        Collections.sort(effectiveDefs);
        for (SalaryElementDef def : effectiveDefs) {
            columns.add(new ColumnModel(def.getLabel() == null ? def.getCategory() : def.getLabel(), def.getOrder()));
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

    final ListMBean<SalaryElementDto> salaryElementMBean = ELListMBean.fromEL(this,//
            "openedObject.elements", SalaryElementDto.class);

    public ListMBean<SalaryElementDto> getSalaryElementMBean() {
        return salaryElementMBean;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public static class ColumnModel
            implements Serializable {

        private static final long serialVersionUID = 1L;

        String header;
        int order;

        public ColumnModel(String header, int order) {
            this.header = header;
            this.order = order;
        }

        public String getHeader() {
            return header;
        }

        public int getOrder() {
            return order;
        }

    }
}
