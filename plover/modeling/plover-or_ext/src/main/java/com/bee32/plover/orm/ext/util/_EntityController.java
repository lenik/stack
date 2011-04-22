package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.free.NotImplementedException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.servlet.mvc.ModelAndViewEx;

public abstract class _EntityController<E extends EntityBean<K>, K extends Serializable> {

    @Inject
    protected CommonDataManager dataManager;

    private final String _PREFIX;
    protected final Class<E> entityType;

    public _EntityController() {
        entityType = ClassUtil.infer1(getClass(), _EntityController.class, 0);

        try {
            Field prefixField = getClass().getDeclaredField("PREFIX");

            int modifiers = prefixField.getModifiers();
            if (!Modifier.isStatic(modifiers))
                throw new Error(prefixField + " must be static");

            _PREFIX = (String) prefixField.get(null);
        } catch (Exception e) {
            throw new Error("PREFIX isn't defined in " + getClass());
        }
    }

    protected class ViewData
            extends ModelAndViewEx {

        public ViewData() {
            super();
        }

        public ViewData(String viewName, Map<String, ?> model) {
            super(viewName, model);
        }

        public ViewData(String viewName, String modelName, Object modelObject) {
            super(viewName, modelName, modelObject);
        }

        public ViewData(String viewName) {
            super(viewName);
        }

        public ViewData(View view, Map<String, ?> model) {
            super(view, model);
        }

        public ViewData(View view, String modelName, Object modelObject) {
            super(view, modelName, modelObject);
        }

        public ViewData(View view) {
            super(view);
        }

        @Override
        protected void loadMetaData(Map<String, Object> metaData) {
            super.loadMetaData(metaData);
            _EntityController.this.loadMetaData(metaData);
        }

        @Override
        protected void loadVocab(Map<String, String> vocab) {
            super.loadVocab(vocab);
            _EntityController.this.loadVocab(vocab);
        }

        public boolean isMethod(String method) {
            Object _method = getModelMap().get("method");
            if (method == null)
                return _method == null;
            else
                return method.equals(_method);
        }

    }

    /**
     * Build all meta data.
     *
     * Implicit build the user meta data, too.
     *
     * @see #preamble(Map)
     */
    protected void loadMetaData(Map<String, Object> metaData) {
        String entityTypeName = ClassUtil.getDisplayName(entityType);
        metaData.put("name", entityTypeName);
    }

    /**
     * Load vocabulary for this controller.
     */
    protected void loadVocab(Map<String, String> vocab) {
    }

    protected String viewOf(String localView) {
        return _PREFIX + localView;
    }

    protected ModelAndView _index(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ViewData view = new ViewData();

        // Index by data-table:
        // List<AllowListDto> list = AllowListDto.marshalList(0, allowListDao.list());
        // mm.put("list", list);

        return view;
    }

    protected ModelAndView _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

    /**
     * Should construct a JSON response.
     */
    protected ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

    protected ModelAndView _createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewData view = new ViewData();
        view.setViewName(viewOf("form"));
        view.put("method", "create");
        view.put("METHOD", view.V.get("create"));

        return _createOrEditForm(view, req, resp);
    }

    protected ModelAndView _editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewData view = new ViewData();
        view.setViewName(viewOf("form"));
        view.put("method", "edit");
        view.put("METHOD", view.V.get("edit"));

        return _createOrEditForm(view, req, resp);
    }

    protected abstract ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    protected ModelAndView _save(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewData view = new ViewData();
        view.setViewName(viewOf("index"));
        view.put("method", "save");
        view.put("METHOD", view.V.get("save"));

        return _saveOrUpdate(view, req, resp);
    }

    protected ModelAndView _update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewData view = new ViewData();
        view.setViewName(viewOf("index"));
        view.put("method", "update");
        view.put("METHOD", view.V.get("update"));

        return _saveOrUpdate(view, req, resp);
    }

    /**
     * Use <code>req.getAttribute("create"): Boolean</code> to distinguish create and update.
     */
    protected abstract ModelAndView _saveOrUpdate(ViewData data, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    protected ModelAndView _delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idString = req.getParameter("id");
        K id = EntityUtil.parseId(entityType, idString);

        ViewData view = new ViewData(viewOf("index"));

        E entity = dataManager.load(entityType, id);
        if (entity != null)
            try {
                dataManager.delete(entity);
            } catch (DataIntegrityViolationException e) {
                resp.setCharacterEncoding("utf-8");
                String entityName = view.meta.getString("typeName") + " " + entity.getName();
                String message = entityName + " 正在被其它对象使用中，删除失败。";
                Javascripts.alertAndBack(message).dump(req, resp);

                return null;
            }

        return view;
    }

    // Utils...
    protected ViewData it(Object it) {
        ViewData view = new ViewData();
        view.put("it", it);
        return view;
    }

}
