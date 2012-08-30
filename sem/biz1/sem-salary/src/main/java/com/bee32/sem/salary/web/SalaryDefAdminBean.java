package com.bee32.sem.salary.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryElementDefDto;
import com.bee32.sem.salary.entity.SalaryElementDef;
import com.bee32.sem.salary.expr.ChineseCodec;
import com.bee32.sem.salary.salary.SalaryDefPreview;
import com.bee32.sem.salary.util.ColumnModel;

public class SalaryDefAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<EmployeeInfoDto> allEmp;
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    List<SalaryDefPreview> previewData;
    Date generateDate;

    public SalaryDefAdminBean() {
        super(SalaryElementDef.class, SalaryElementDefDto.class, 0);
    }

    public void updateColumns() {
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent("expr");
        table.setValueExpression("stortBy", null);
        createDynamicColumns();
    }

    public void createDynamicColumns() {
        String[] columnKeys = { "" };
        columns.clear();
        for (String key : columnKeys) {
            String columnKey = key.trim();
            columns.add(new ColumnModel(columnKey, key));
        }

    }

    public Object getPreviewData() {

        previewData = new ArrayList<SalaryDefPreview>();
        allEmp = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);

        SalaryElementDefDto def = (SalaryElementDefDto) getOpenedObject();
        if (def != null) {
            String expression = def.getExpr();
            String encode = ChineseCodec.encode(expression);

            HashMap<String, Object> map = new HashMap<String, Object>();

            for (EmployeeInfoDto employee : allEmp) {
                map.put(ISalaryVariableProvider.ARG_EMPLOYEE, employee);
                final TextMap args = new TextMap(map);

                StandardEvaluationContext context = new StandardEvaluationContext();
                BeanResolver resolver = new BeanResolver() {

                    @Override
                    public Object resolve(EvaluationContext context, String beanName)
                            throws AccessException {

                        beanName = ChineseCodec.decode(beanName);
                        for (ISalaryVariableProvider provider : SalaryVariableProviders.getProviders()) {
                            BigDecimal bonus = provider.evaluate(args, beanName);
// System.out.printf("%s: %s -> %s\n", beanName, provider, bonus);
                            if (bonus != null)
                                return bonus.doubleValue();
                        }
                        return 0.0;
                    }
                };
                context.setBeanResolver(resolver);

                SpelExpressionParser parser = new SpelExpressionParser();
                Expression expr = parser.parseExpression(encode);
                Object result = expr.getValue(context);

                previewData.add(new SalaryDefPreview(employee, result));
            }
        }
        return previewData;
    }

    public void generateSalary() {
        allEmp = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        List<SalaryElementDefDto> efficacious = mrefList(SalaryElementDef.class, SalaryElementDefDto.class, 0,
                AttendanceCriteria.listEfficious(generateDate));
        for (SalaryElementDefDto def : efficacious) {
            // TODO
        }

    }

    public List<ISalaryVariableProvider> getProviders() {
        return SalaryVariableProviders.getProviders();
    }

    // all expression paramas for dynamic columns
    List<String> getAllParams() {
        List<String> keys = new ArrayList<String>();
        for (ISalaryVariableProvider provider : getProviders()) {
            for (String string : provider.getVariableNames())
                keys.add(string);
        }
        return keys;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

}
