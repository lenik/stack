package com.bee32.sem.salary.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryElementDefDto;
import com.bee32.sem.salary.entity.SalaryElementDef;
import com.bee32.sem.salary.expr.ChineseCodec;
import com.bee32.sem.salary.salary.SalaryDefPreview;

public class SalaryDefAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<SalaryDefPreview> previewData;
    Date generateDate;

    public SalaryDefAdminBean() {
        super(SalaryElementDef.class, SalaryElementDefDto.class, 0);
    }

    public List<SalaryDefPreview> getPreviewData() {
        List<SalaryDefPreview> list = previewData;
        if (list == null)
            list = Collections.emptyList();
        return SelectableList.decorate(list);
    }

    public boolean updatePreviewDate() {
        SalaryElementDefDto def = (SalaryElementDefDto) getOpenedObject();

        previewData = new ArrayList<SalaryDefPreview>();
        List<EmployeeInfo> employeeList = DATA(EmployeeInfo.class).list();

        String expression = def.getExpr();
        String encode = ChineseCodec.encode(expression);

        for (EmployeeInfo employee : employeeList) {

            Map<String, Object> map = new HashMap<String, Object>();
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
            Expression expr = null;
            Number result = null;

            try {
                expr = parser.parseExpression(encode);
            } catch (ParseException e) {
                uiLogger.error("表达式非法", e);
                return false;
            }

            try {
                result = (Number) expr.getValue(context);
            } catch (EvaluationException e) {
                uiLogger.error("无法对表达式求值", e);
                return false;
            }

            String label = employee.getPerson().getLabel();
            previewData.add(new SalaryDefPreview(label, result));
        }

        return true;
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
