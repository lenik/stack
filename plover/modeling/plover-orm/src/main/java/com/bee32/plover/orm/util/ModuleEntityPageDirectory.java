package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.Module;
import com.bee32.plover.restful.resource.ModuleObjectPageDirectory;
import com.bee32.plover.restful.resource.StandardOperations;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;

public class ModuleEntityPageDirectory
        extends ModuleObjectPageDirectory {

    static Map<String, String> predefinedViews = new HashMap<String, String>();
    static Map<String, String> predefinedOperations = new HashMap<String, String>();
    static {
        predefinedViews.put(StandardViews.LIST, "index.do");
        predefinedViews.put(StandardViews.CONTENT, "content.do");
        predefinedViews.put(StandardViews.CREATE_FORM, "createForm.do");
        predefinedViews.put(StandardViews.EDIT_FORM, "editForm.do");
        predefinedOperations.put(StandardOperations.CREATE, "create.do");
        predefinedOperations.put(StandardOperations.UPDATE, "edit.do");
        predefinedOperations.put(StandardOperations.DELETE, "delete.do");
    }

    Location entityLocation;

    public ModuleEntityPageDirectory(Module module, String prefix) {
        super(module);
        entityLocation = super.getBaseLocation().join(prefix);
    }

    @Override
    public Location getBaseLocation() {
        return entityLocation;
    }

    @Override
    protected String getLocalPageForView(String viewName, Map<String, ?> parameters) {
        String override = super.getLocalPageForView(viewName, parameters);
        if (override != null)
            return override;
        return predefinedViews.get(viewName);
    }

    @Override
    protected String getLocalPageForOperation(String operationName, Map<String, ?> parameters) {
        String override = super.getLocalPageForOperation(operationName, parameters);
        if (override != null)
            return override;
        return predefinedOperations.get(operationName);
    }

}
