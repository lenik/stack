package com.bee32.plover.servlet.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.free.ClassLocal;
import javax.free.IVariantLookupMap;
import javax.free.IllegalUsageException;
import javax.free.Map2VariantLookupMap;

import org.springframework.web.servlet.View;

import com.bee32.plover.arch.util.res.ResourceBundleEx;

/**
 * Extension of model&view with meta-data and vocabulary support.
 *
 * It may be called Mavex in the controller implementations.
 *
 * Examples:
 *
 * <pre>
 *  ${meta.typeName}
 *  ${V.CREATE}
 * </pre>
 */
public abstract class ModelAndViewEx
        extends org.springframework.web.servlet.ModelAndView {

    protected final Class<?> hintClass;
    public final IVariantLookupMap<String> meta;
    public final Map<String, String> V;

    public ModelAndViewEx() {
        super();
    }

    public ModelAndViewEx(String viewName, Map<String, ?> model) {
        super(viewName, model);
    }

    public ModelAndViewEx(String viewName, String modelName, Object modelObject) {
        super(viewName, modelName, modelObject);
    }

    public ModelAndViewEx(String viewName) {
        super(viewName);
    }

    public ModelAndViewEx(View view, Map<String, ?> model) {
        super(view, model);
    }

    public ModelAndViewEx(View view, String modelName, Object modelObject) {
        super(view, modelName, modelObject);
    }

    public ModelAndViewEx(View view) {
        super(view);
    }

    {
        hintClass = getHintClass();

        ClassDataEx local = classLocalClassDataEx.get(hintClass);
        if (local == null) {
            local = new ClassDataEx();
            loadMetaData(local.metaData);
            loadVocab(local.vocab);
            classLocalClassDataEx.put(hintClass, local);
        }

        Map<String, Object> model = getModel();
        model.put("meta", meta = local.metaDataLookup);
        model.put("V", V = local.vocab);
    }

    /**
     * Get the hint class around this model/view object.
     *
     * @return Non-<code>null</code> hint class.
     */
    protected Class<?> getHintClass() {
        Class<?> enclosingClass = getClass().getEnclosingClass();
        if (enclosingClass == null)
            throw new IllegalUsageException("Should be used as inner class Mavex.");
        return enclosingClass;
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
