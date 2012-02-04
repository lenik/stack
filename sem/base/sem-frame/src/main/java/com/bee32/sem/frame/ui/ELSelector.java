package com.bee32.sem.frame.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.ILabelledEntry;
import com.bee32.plover.orm.util.EntityDto;
import com.sun.el.ExpressionFactoryImpl;

public class ELSelector {

    final List<?> items;
    final LocalELContext local;

    final ValueExpression valueExpr;
    final ValueExpression itemKeyExpr;
    final ValueExpression itemLabelExpr;

    transient Object currentItem;
    String nullId = "__NULL__";
    String nullLabel = "(Not Available)";

    public ELSelector(List<?> items, String valueExpr, String varName, String itemKeyExpr, String itemLabelExpr) {
        this(items, null, valueExpr, varName, itemKeyExpr, itemLabelExpr);
    }

    public ELSelector(List<?> items, Object context, String valueExpr, String varName, String itemKeyExpr,
            String itemLabelExpr) {
        if (items == null)
            throw new NullPointerException("items");
        if (valueExpr == null)
            throw new NullPointerException("valueExpr");
        this.items = items;

        ELContext parent = getELContext();
        ExpressionFactory factory = getExpressionFactory();

        if (varName != null || context != null)
            local = new LocalELContext(parent);
        else
            local = null;
        if (local != null) {
            VariableMapper localVars = local.getVariableMapper();
            if (context != null)
                localVars.setVariable("context", factory.createValueExpression(context, Object.class));
            localVars.setVariable("__self", factory.createValueExpression(this, Object.class));
            localVars.setVariable(varName, factory.createValueExpression(local, "${__self.currentItem}", Object.class));
        }

        this.valueExpr = factory.createValueExpression(local != null ? local : parent, valueExpr, Object.class);

        if (itemKeyExpr != null)
            this.itemKeyExpr = factory.createValueExpression(local, itemKeyExpr, Object.class);
        else
            this.itemKeyExpr = null;

        if (itemLabelExpr != null)
            this.itemLabelExpr = factory.createValueExpression(local, itemLabelExpr, Object.class);
        else
            this.itemLabelExpr = null;
    }

    protected ELContext getELContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            ELContext elContext = facesContext.getELContext();
            return elContext;
        } else {
            return DefaultELContext.getInstance();
        }
    }

    protected ExpressionFactory getExpressionFactory() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null)
            return facesContext.getApplication().getExpressionFactory();
        else
            return new ExpressionFactoryImpl();
    }

    public ValueExpression getValueExpr() {
        return valueExpr;
    }

    public Object getValue() {
        if (local != null)
            return valueExpr.getValue(local);
        else
            return valueExpr.getValue(getELContext());
    }

    public void setValue(Object value) {
        if (local != null)
            valueExpr.setValue(local, value);
        else
            valueExpr.setValue(getELContext(), value);
    }

    public Object getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Object currentItem) {
        if (currentItem instanceof String)
            throw new IllegalUsageException();
        this.currentItem = currentItem;
    }

    protected Object evaluateLocal(ValueExpression expr) {
        ELContext parent = getELContext();
        ELContext elContext;
        if (local == null)
            elContext = parent;
        else {
            if (local != parent)
                local.setParent(parent);
            elContext = local;
        }
        Object value = expr.getValue(elContext);
        return value;
    }

    public String getValueId() {
        Object value = getValue();
        if (value == null)
            return nullId;
        return formatId(getValueKey());
    }

    public void setValueId(String id) {
        if (nullId.equals(id)) {
            setValue(null);
            return;
        }
        Object item = findItem(id);
        setValue(item);
    }

    public Object getValueKey() {
        Object value = getValue();
        if (value == null)
            return null;
        setCurrentItem(value);
        return getItemKey();
    }

    public String getValueLabel() {
        Object value = getValue();
        if (value == null)
            return nullLabel;
        setCurrentItem(value);
        return getItemLabel();
    }

    public String getItemId() {
        // assert item != null.
        return formatId(getItemKey());
    }

    public Object getItemKey() {
        if (itemKeyExpr != null)
            return evaluateLocal(itemKeyExpr);

        Object item = getCurrentItem();
        if (item == null)
            return null;

        if (item instanceof EntityDto<?, ?>)
            return ((EntityDto<?, ?>) item).getId();

        if (item instanceof EnumAlt<?, ?>)
            return ((EnumAlt<?, ?>) item).getValue();

        if (item instanceof Enum<?>)
            return ((Enum<?>) item).name();

        if (item instanceof Entry<?, ?>)
            return ((Entry<?, ?>) item).getKey();

        // return System.identityHashCode(value);
        throw new IllegalUsageException("Couldn't determine the value of object " + item.getClass());
    }

    public String getItemLabel() {
        if (itemLabelExpr != null)
            return String.valueOf(evaluateLocal(itemLabelExpr));

        Object value = getCurrentItem();
        if (value instanceof ILabelledEntry)
            return ((ILabelledEntry) value).getEntryLabel();

        return String.valueOf(value);
    }

    public String getNullId() {
        return nullId;
    }

    public void setNullId(String nullId) {
        this.nullId = nullId;
    }

    public String getNullLabel() {
        return nullLabel;
    }

    public void setNullLabel(String nullLabel) {
        this.nullLabel = nullLabel;
    }

    protected String formatId(Object key) {
        return String.valueOf(key);
    }

    protected Object findItem(String id) {
        if (id == null)
            throw new NullPointerException("id");
        for (Object item : items) {
            setCurrentItem(item);
            if (id.equals(getItemId()))
                return item;
        }
        throw new IllegalUsageException("Bad item id (or itemValue): \"" + id + "\"");
        // return null;
    }

    public List<SelectItem> getSelectItems() {
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        for (Object item : items) {
            setCurrentItem(item);
            String value = getItemId();
            String label = getItemLabel();
            SelectItem selectItem = new SelectItem(value, label);
            selectItems.add(selectItem);
        }
        return selectItems;
    }

}
