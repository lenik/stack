package com.bee32.zebra.oa.calendar;

import java.util.Date;

import net.bodz.bas.err.IllegalUsageException;
import net.bodz.lily.model.base.CoObject;
import net.bodz.lily.model.base.schema.CategoryDef;
import net.bodz.lily.model.base.schema.FormDef;
import net.bodz.lily.model.base.security.User;

import com.bee32.zebra.tk.sea.TableMetadata;
import com.bee32.zebra.tk.sea.TableMetadataRegistry;

public class LogEntry
        extends CoObject {

    private static final long serialVersionUID = 1L;

    TableMetadata metadata;
    Long id;
    User op;
    FormDef form;
    CategoryDef category;
    String message;
    Date date;
    Date creation;

    public TableMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(TableMetadata sourceMetadata) {
        this.metadata = sourceMetadata;
    }

    public String getSource() {
        if (metadata == null)
            return null;
        else
            return metadata.getName();
    }

    public void setSource(String src) {
        TableMetadataRegistry registry = TableMetadataRegistry.getInstance();
        TableMetadata metadata = registry.get(src);
        if (metadata == null)
            throw new IllegalUsageException("Bad source: " + src);
        this.metadata = metadata;
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
