package com.bee32.sem.salary.expr;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.test.FeaturePlayer;
import com.bee32.plover.test.ICoordinator;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryVariableProviders;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.JobPost;
import com.bee32.sem.salary.SEMSalaryUnit;

@Using(SEMSalaryUnit.class)
public class SimpleTest
        // extends WiredDaoFeat<SimpleTest> {
        extends FeaturePlayer<SimpleTest> {

    public void f1() {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expr = parser.parseExpression("1+2");
        Object val = expr.getValue();
        System.out.println("Result: " + val);
    }

    public void f2() {
        EmployeeInfo emp = new EmployeeInfo();
        emp.setBaseSalary(new BigDecimal(100));

        JobPost post = new JobPost("发发", "xx", new BigDecimal(200));
        emp.setRole(post);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(ISalaryVariableProvider.ARG_EMPLOYEE, emp);
        final TextMap args = new TextMap(map);

        StandardEvaluationContext context = new StandardEvaluationContext();
        BeanResolver myResolver = new BeanResolver() {
            @Override
            public Object resolve(EvaluationContext context, String beanName)
                    throws AccessException {
                beanName = ChineseCodec.decode(beanName);
                for (ISalaryVariableProvider provider : SalaryVariableProviders.getProviders()) {
                    BigDecimal bonus = provider.evaluate(args, beanName);
                    System.out.printf("%s: %s -> %s\n", beanName, provider, bonus);
                    if (bonus != null)
                        return bonus;
                }
                return BigDecimal.ZERO;
            }
        };
        context.setBeanResolver(myResolver);

        SpelExpressionParser parser = new SpelExpressionParser();
        String exprStr = "@岗位补贴";
        Expression expr = parser.parseExpression(ChineseCodec.encode(exprStr));
        Object result = expr.getValue(context);
        System.out.println(result);
    }

    public void f3() {
    }

    public void f4() {
    }

    public static void main(String[] args)
            throws IOException {
        new SimpleTest().mainLoop(new ICoordinator<SimpleTest>() {
            @Override
            public void main(SimpleTest feat)
                    throws Exception {
// feat.f1();
                feat.f2();
            }
        });
    }

}
