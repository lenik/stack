package com.bee32.sem.salary.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.util.ChineseCodec;
import com.bee32.plover.util.PloverEvaluationContext;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryElementDefDto;
import com.bee32.sem.salary.entity.SalaryElementDef;
import com.bee32.sem.salary.salary.SalaryDefPreview;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class SalaryDefAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<SalaryDefPreview> previewData;
    Date generateDate;
    TreeNode variableTree;

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

        for (EmployeeInfo employee : employeeList) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put(ISalaryVariableProvider.ARG_EMPLOYEE, employee);
            map.put(ISalaryVariableProvider.ARG_YEARMONTH, SalaryDateUtil.convertToYearMonth(new Date()));
            final TextMap args = new TextMap(map);

            PloverEvaluationContext context = new PloverEvaluationContext();
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

            Number result = null;

            try {
                result = (Number) context.evaluateZh(def.getExpr());
            } catch (ParseException e) {
                uiLogger.error("表达式非法", e);
                return false;
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

    @Override
    protected boolean save(int saveFlags, String hint) {

        SalaryElementDefDto def = (SalaryElementDefDto) getOpenedObject();
        String expression = def.getExpr();
        String encoded = ChineseCodec.encode(expression);

        PloverEvaluationContext context = new PloverEvaluationContext();
        try {
            context.checkSyntaxZh(encoded);
        } catch (ParseException e) {
            uiLogger.error("表达式非法", e);
            return false;
        }

        return super.save(saveFlags, hint);
    }

    public TreeNode getVariableTree() {
        if (variableTree == null) {
            variableTree = new DefaultTreeNode("root", null);
            for (ISalaryVariableProvider provider : SalaryVariableProviders.getProviders()) {
                String typeName = ClassUtil.getTypeName(provider.getClass());
                TreeNode node = new DefaultTreeNode(typeName, variableTree);
                for (String variable : provider.getVariableNames()) {
                    if (!variable.isEmpty() && variable != null)
                        new DefaultTreeNode(variable, node);
                }
            }
        }
        return variableTree;
    }

    public Date getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(Date generateDate) {
        this.generateDate = generateDate;
    }

}
