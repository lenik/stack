package com.bee32.plover.faces.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentAccessor;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectManyMenu;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Locker
        extends UIPreprocessor {

    static Logger logger = LoggerFactory.getLogger(Locker.class);

    public static final String COMPONENT_TYPE = "plover.faces.Locker";
    public static final String APPLIED_ATTRIBUTE = "locker.applied";

    static enum PropertyKeys {
        test
    }

    public boolean isTest() {
        return (Boolean) getStateHelper().eval(PropertyKeys.test, false);
    }

    public void setTest(boolean test) {
        getStateHelper().put(PropertyKeys.test, test);
    }

    @Override
    public void encodeBegin(FacesContext context)
            throws IOException {
        for (UIComponent child : getChildren())
            applyComponent(context, child);
        super.encodeBegin(context);
    }

    static Map<String, LockMethod> lockFamilies = new HashMap<String, LockMethod>();
    static Map<Class<?>, LockMethod> lockComponents = new HashMap<Class<?>, LockMethod>();
    static {
        lockFamilies.put("javax.faces.Command", LockMethod.disable);
        lockFamilies.put("javax.faces.Input", LockMethod.readonly);
        lockFamilies.put("javax.faces.SelectOne", LockMethod.readonly);
        lockFamilies.put("javax.faces.SelectMany", LockMethod.readonly);

        lockComponents.put(HtmlCommandButton.class, LockMethod.disable);
        lockComponents.put(HtmlCommandLink.class, LockMethod.disable);
        lockComponents.put(HtmlInputText.class, LockMethod.readonly);
        lockComponents.put(HtmlInputTextarea.class, LockMethod.readonly);
        lockComponents.put(HtmlSelectBooleanCheckbox.class, LockMethod.readonly);
        lockComponents.put(HtmlSelectOneMenu.class, LockMethod.readonly);
        lockComponents.put(HtmlSelectManyMenu.class, LockMethod.readonly);
        // lockComponents.put(SelectOneMenu.class, LockMethod.editable);
        // lockComponents.put(SelectManyMenu.class, LockMethod.editable);
    }

    static LockMethod getLockMethod(UIComponent uiComponent) {
        String family = uiComponent.getFamily();
        LockMethod method = lockFamilies.get(family);
        if (method == null) {
            Class<?> clazz = uiComponent.getClass();
            while (clazz != null) {
                method = lockComponents.get(clazz);
                if (method != null)
                    break;
                clazz = clazz.getSuperclass();
            }
        }
        return method;
    }

    void applyComponent(FacesContext context, UIComponent uiComponent) {
        for (UIComponent child : uiComponent.getChildren())
            applyComponent(context, child);

        // XXX Not work!!!
        StateHelper stateHelper = UIComponentAccessor.getStateHelper(uiComponent);
        Boolean applied = (Boolean) stateHelper.get(APPLIED_ATTRIBUTE);
        if (applied == Boolean.TRUE)
            return;

        ExpressionFactory exprFactory = context.getApplication().getExpressionFactory();
        ELContext testElContext = getFacesContext().getELContext();
        // FacesContext origContext = UIComponentAccessor.getFacesContext(uiComponent);
        // ELContext origElContext = origContext.getELContext();

        // ELContext testElContext = getFacesContext().getELContext();
        LockMethod lockMethod = getLockMethod(uiComponent);
        if (lockMethod == null)
            return;

        ValueExpression testExpr = getValueExpression("test");
        // Boolean testVal = (Boolean) testExpr.getValue(context.getELContext());
        // System.err.println("eval test = " + testVal);

        String lockAttr = lockMethod.getAttributeName();
        ValueExpression origExpr = uiComponent.getValueExpression(lockAttr);
        if (origExpr == null) {
            // System.err.println("Lock attribute: " + uiComponent.getClientId() + "." + lockAttr);
            uiComponent.setValueExpression(lockAttr, testExpr);
        } else {
            String test = getScript(testExpr);
            String orig = getScript(origExpr);
            if (orig.equals(test) || orig.startsWith("(" + test + ") or "))
                return;

            String conj = "${(" + test + ") or (" + orig + ")}";
            // System.err.println(uiComponent.getClientId() + "." + lockType + " := " + conj);
            @SuppressWarnings("unused")
            ValueExpression conjExpr = exprFactory.createValueExpression(testElContext, conj, Boolean.class);
            // uiComponent.setValueExpression(lockAttr, conjExpr);
        }
        stateHelper.put(APPLIED_ATTRIBUTE, true);
    }

    static String getScript(ValueExpression expr) {
        String script = expr.getExpressionString();
        if (script.startsWith("${") && script.endsWith("}")) {
            script = script.substring(2, script.length() - 1);
        } else if (script.startsWith("#{") && script.endsWith("}")) {
            script = script.substring(2, script.length() - 1);
        }
        script = script.trim();
        return script;
    }

}
