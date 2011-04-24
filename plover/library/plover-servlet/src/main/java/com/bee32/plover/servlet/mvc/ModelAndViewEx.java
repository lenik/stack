package com.bee32.plover.servlet.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.free.ClassLocal;
import javax.free.IVariantLookupMap;
import javax.free.IllegalUsageException;
import javax.free.Map2VariantLookupMap;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bee32.plover.arch.util.res.ResourceBundleEx;

/**
 * Extension of model&view with meta-data and vocabulary support.
 *
 * It may be called ViewData in the controller implementations.
 *
 * Examples:
 *
 * <pre>
 *  ${meta.typeName}
 *  ${V.CREATE}
 * </pre>
 */
public abstract class ModelAndViewEx
        extends ModelAndView {

    private Object controller;
    private Class<?> hintClass;

    public Map<String, Object> _meta;
    public IVariantLookupMap<String> meta;
    public Map<String, String> V;

    public ModelAndViewEx(Object controller) {
        super();
        initController(controller);
    }

    public ModelAndViewEx(Object controller, String viewName, Map<String, ?> model) {
        super(viewName, model);
        initController(controller);
    }

    public ModelAndViewEx(Object controller, String viewName) {
        super(viewName);
        initController(controller);
    }

    public ModelAndViewEx(Object controller, View view, Map<String, ?> model) {
        super(view, model);
        initController(controller);
    }

    public ModelAndViewEx(Object controller, View view) {
        super(view);
        initController(controller);
    }

    private void initController(Object controller) {
        this.controller = controller;
        this.hintClass = getHintClass();

        ClassDataEx local = classLocalClassDataEx.get(hintClass);
        if (local == null) {
            local = new ClassDataEx();
            loadMetaData(local.metaData);
            loadVocab(local.vocab);
            classLocalClassDataEx.put(hintClass, local);
        }

        _meta = local.metaData;
        meta = local.metaDataLookup;
        V = local.vocab;

        Map<String, Object> model = getModel();
        model.put("reflect", this);
        model.put("this", controller);
        model.put("meta", local.metaData);
        model.put("V", local.vocab);
    }

    public Set<String> getMetaKeys() {
        return _meta.keySet();
    }

    /**
     * Get the hint class around this model/view object.
     *
     * @return Non-<code>null</code> hint class.
     */
    protected Class<?> getHintClass() {
        if (controller != null) {
            return controller.getClass();
        } else {
            Class<?> enclosingClass = getClass().getEnclosingClass();
            if (enclosingClass == null)
                throw new IllegalUsageException("Should be used as inner class Mavex.");
            return enclosingClass;
        }
    }

    /**
     * Populate the default meta data.
     */
    protected void loadMetaData(Map<String, Object> metaData) {
    }

    /**
     * Load vocabularies.
     *
     * By default, load rb-inhertiable from the enclosing class.
     */
    protected void loadVocab(Map<String, String> vocab) {
        ResourceBundleEx.load(hintClass, null, vocab, "vocab");
    }

    /**
     * This is the same as
     *
     * <pre>
     * getModel().put(name, value);
     * </pre>
     */
    public void put(String name, Object value) {
        addObject(name, value);
    }

    @Override
    public String toString() {
        return "ToString called";
    }

    static class ClassDataEx {

        public Map<String, Object> metaData;
        public IVariantLookupMap<String> metaDataLookup;
        public Map<String, String> vocab;

        public ClassDataEx() {
            metaData = new HashMap<String, Object>();
            metaDataLookup = new Map2VariantLookupMap<String>(metaData);
            vocab = new HashMap<String, String>();
        }

    }

    static final ClassLocal<ClassDataEx> classLocalClassDataEx;
    static {
        classLocalClassDataEx = new ClassLocal<ClassDataEx>();
    }

}
