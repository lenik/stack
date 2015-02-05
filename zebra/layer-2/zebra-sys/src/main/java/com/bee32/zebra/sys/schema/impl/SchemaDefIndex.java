package com.bee32.zebra.sys.schema.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.tinylily.model.base.schema.SchemaDef;

/**
 * 用于定义特定用途的语境。
 * 
 * @label 模式
 */
@ObjectType(SchemaDef.class)
public class SchemaDefIndex
        extends QuickIndex {

    public SchemaDefIndex(IQueryable context) {
        super(context);
    }

}
