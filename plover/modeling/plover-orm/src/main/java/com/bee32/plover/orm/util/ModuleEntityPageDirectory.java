package com.bee32.plover.orm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.arch.IModule;
import com.bee32.plover.restful.resource.IObjectURLFragmentsProvider;
import com.bee32.plover.restful.resource.ModuleObjectPageDirectory;
import com.bee32.plover.restful.resource.ObjectURLFragmentType;
import com.bee32.plover.restful.resource.StandardOperations;
import com.bee32.plover.restful.resource.StandardViews;

public class ModuleEntityPageDirectory
        extends ModuleObjectPageDirectory {

    static final boolean indexRichCompatMode = false;

    static Map<String, String> predefinedViews = new HashMap<String, String>();
    static Map<String, String> predefinedOperations = new HashMap<String, String>();

    static {
        if (indexRichCompatMode) {
            predefinedViews.put(StandardViews.LIST, "index.do");
            predefinedViews.put(StandardViews.CONTENT, "content.do");
            predefinedViews.put(StandardViews.CREATE_FORM, "createForm.do");
            predefinedViews.put(StandardViews.EDIT_FORM, "editForm.do");
            predefinedOperations.put(StandardOperations.CREATE, "create.do");
            predefinedOperations.put(StandardOperations.UPDATE, "edit.do");
            predefinedOperations.put(StandardOperations.DELETE, "delete.do");
        } else {
            predefinedViews.put(StandardViews.LIST, "index-rich.jsf?MODE=index");
            predefinedViews.put(StandardViews.CONTENT, "index-rich.jsf?MODE=content");
            predefinedViews.put(StandardViews.CREATE_FORM, "index-rich.jsf?MODE=createForm");
            predefinedViews.put(StandardViews.EDIT_FORM, "index-rich.jsf?MODE=editForm");
            predefinedOperations.put(StandardOperations.CREATE, "index-rich.jsf?MODE=create");
            predefinedOperations.put(StandardOperations.UPDATE, "index-rich.jsf?MODE=edit");
            predefinedOperations.put(StandardOperations.DELETE, "index-rich.jsf?MODE=delete");
        }
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
    protected List<String> getLocalPagesForView(String viewName, Map<String, ?> parameters) {
        List<String> pages = super.getLocalPagesForView(viewName, parameters);
        if (!pages.isEmpty())
            return pages;
        String predefined = predefinedViews.get(viewName);
        if (predefined != null) {
            pages = new ArrayList<String>(pages); // make a copy.
            pages.add(predefined);
        }
        return formatHref(pages, parameters);
    }

    @Override
    protected List<String> getLocalPagesForOperation(String operationName, Map<String, ?> parameters) {
        List<String> pages = super.getLocalPagesForOperation(operationName, parameters);
        if (!pages.isEmpty())
            return pages;
        String predefined = predefinedOperations.get(operationName);
        if (predefined != null) {
            pages = new ArrayList<String>(pages); // make a copy.
            pages.add(predefined);
        }
        return formatHref(pages, parameters);
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
        protected List<String> getLocalPagesForView(String viewName, Map<String, ?> parameters) {
            parameters = getOverridedParameters(parameters);
            List<String> pages = super.getLocalPagesForView(viewName, parameters);
            return pages;
        }

        @Override
        protected List<String> getLocalPagesForOperation(String operationName, Map<String, ?> parameters) {
            parameters = getOverridedParameters(parameters);
            List<String> pages = super.getLocalPagesForOperation(operationName, parameters);
            return pages;
        }

    }

}
