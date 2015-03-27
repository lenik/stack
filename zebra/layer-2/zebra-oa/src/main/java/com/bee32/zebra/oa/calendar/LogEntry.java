package com.bee32.zebra.oa.calendar;

import java.util.Date;

import com.tinylily.model.base.CoObject;
import com.tinylily.model.base.schema.CategoryDef;
import com.tinylily.model.base.schema.FormDef;
import com.tinylily.model.base.security.User;

public class LogEntry
        extends CoObject {

    private static final long serialVersionUID = 1L;

    Class<?> sourceClass;
    Long id;
    User op;
    FormDef form;
    CategoryDef category;
    String message;
    Date date;
    Date creation;

    public Class<?> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    public String getSource() {
        return sourceClass.getName();
    }

    public void setSource(String src) {

    }

    public Object getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOp() {
        return op;
    }

    public void setOp(User op) {
        this.op = op;
    }

    public FormDef getForm() {
        return form;
    }

    public void setForm(FormDef form) {
        this.form = form;
    }

    public CategoryDef getCategory() {
        return category;
    }

    public void setCategory(CategoryDef category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

}
