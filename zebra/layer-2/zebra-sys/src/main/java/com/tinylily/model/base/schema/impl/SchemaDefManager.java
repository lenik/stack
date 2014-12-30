package com.tinylily.model.base.schema.impl;

import net.bodz.bas.repr.path.PathToken;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.tk.sea.FooManager;
import com.tinylily.model.base.schema.SchemaDef;

/**
 * 用于定义特定用途的语境。
 * 
 * @label 模式
 */
@PathToken("schema")
public class SchemaDefManager
        extends FooManager {

    public SchemaDefManager(IQueryable context) {
        super(SchemaDef.class, context);
    }

}
