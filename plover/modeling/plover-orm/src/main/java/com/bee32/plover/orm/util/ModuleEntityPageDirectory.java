package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.bee32.plover.arch.Module;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.restful.resource.ModuleObjectPageDirectory;
import com.bee32.plover.restful.resource.StandardOperations;
import com.bee32.plover.restful.resource.StandardViews;

public class ModuleEntityPageDirectory
        extends ModuleObjectPageDirectory {

    static Map<String, String> predefinedViews = new HashMap<String, String>();
    static Map<String, String> predefinedOperations = new HashMap<String, String>();
    static {
        predefinedViews.put(StandardViews.LIST, "index-rich.jsf");
        predefinedViews.put(StandardViews.CONTENT_FORM, "contentForm.do");
        predefinedViews.put(StandardViews.EDIT_FORM, "editForm.do");
        predefinedOperations.put(StandardOperations.CREATE, "create.do");
        predefinedOperations.put(StandardOperations.UPDATE, "edit.do");
        predefinedOperations.put(StandardOperations.DELETE, "delete.do");
    }

    public ModuleEntityPageDirectory(Module module, Class<? extends Entity<?>> entity, String prefix) {
        super(module);
        for (Entry<String, String> predefinedView : predefinedViews.entrySet())
            addLocalPageForView(predefinedView.getKey(), prefix + predefinedView.getValue());
        for (Entry<String, String> predefinedOperation : predefinedOperations.entrySet())
            addLocalPageForOperation(predefinedOperation.getKey(), prefix + predefinedOperation.getValue());
    }

}
