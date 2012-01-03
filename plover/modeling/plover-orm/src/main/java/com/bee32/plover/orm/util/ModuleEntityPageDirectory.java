package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.restful.resource.IObjectURLFragmentsProvider;
import com.bee32.plover.restful.resource.ModuleObjectPageDirectory;
import com.bee32.plover.restful.resource.ObjectURLFragmentType;
import com.bee32.plover.restful.resource.StandardOperations;
import com.bee32.plover.restful.resource.StandardViews;

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

    String baseHref;

    public ModuleEntityPageDirectory(IModule module, String baseHref) {
        super(module);
        this.baseHref = baseHref;
    }

    @Override
    protected String getBaseHref() {
        return baseHref;
    }

    public void setBaseHref(String baseHref) {
        this.baseHref = baseHref;
    }

    @Override
    protected String getLocalPageForView(String viewName, Map<String, ?> parameters) {
        String override = super.getLocalPageForView(viewName, parameters);
        if (override != null)
            return override;
        String localPage = predefinedViews.get(viewName);
        return formatHref(localPage, parameters);
    }

    @Override
    protected String getLocalPageForOperation(String operationName, Map<String, ?> parameters) {
        String override = super.getLocalPageForOperation(operationName, parameters);
        if (override != null)
            return override;
        String localPage = predefinedOperations.get(operationName);
        return formatHref(localPage, parameters);
    }

    public static class Completion
            extends ModuleEntityPageDirectory {

        IObjectURLFragmentsProvider fragmentsProvider;
        Object instance;
        Map<String, String> extraParameters;

        public Completion(IModule module, IObjectURLFragmentsProvider fragmentsProvider, Object instance) {
            super(module, null);
            if (fragmentsProvider == null)
                throw new NullPointerException("fragmentsProvider");
            if (instance == null)
                throw new NullPointerException("instance");
            this.fragmentsProvider = fragmentsProvider;
            this.instance = instance;

            String baseHref = (String) fragmentsProvider.getURLFragment(instance,
                    ObjectURLFragmentType.baseHrefToModule);
            setBaseHref(baseHref);

            extraParameters = (Map<String, String>) fragmentsProvider.getURLFragment(instance,
                    ObjectURLFragmentType.extraParameters);
        }

        static Map<String, Object> mergeMap(Map<String, ?> map, Map<String, ?> overrides) {
            Map<String, Object> all = new LinkedHashMap<String, Object>();
            if (map != null)
                all.putAll(map);
            if (overrides != null)
                all.putAll(overrides);
            return all;
        }

        final Map<String, ?> getOverridedParameters(Map<String, ?> parameters) {
            if (extraParameters != null)
                parameters = mergeMap(parameters, extraParameters);
            return parameters;
        }

        @Override
        protected String getLocalPageForView(String viewName, Map<String, ?> parameters) {
            parameters = getOverridedParameters(parameters);
            String href = super.getLocalPageForView(viewName, parameters);
            return href;
        }

        @Override
        protected String getLocalPageForOperation(String operationName, Map<String, ?> parameters) {
            parameters = getOverridedParameters(parameters);
            String href = super.getLocalPageForOperation(operationName, parameters);
            return href;
        }

    }

}
