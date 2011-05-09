package com.bee32.plover.orm.ext.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.free.ParseException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.JavascriptChunk;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityUtil;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.orm.util.IEntityMarshalContext;
import com.bee32.plover.servlet.mvc.ModelAndViewEx;

public abstract class _EntityController<E extends Entity<K>, K extends Serializable, Dto extends EntityDto<E, K>>
        extends Component
        implements IEntityMarshalContext {

    @Inject
    protected CommonDataManager dataManager;

    protected final String prefix;

    public _EntityController() {
        this(null);
    }

    public _EntityController(String prefix) {
        if (prefix == null)
            try {
                Field prefixField = getClass().getDeclaredField("PREFIX");

                int modifiers = prefixField.getModifiers();
                if (!Modifier.isStatic(modifiers))
                    throw new Error(prefixField + " must be static");

                prefix = (String) prefixField.get(null);
            } catch (Exception e) {
                throw new Error("PREFIX isn't defined in " + getClass());
            }
        this.prefix = prefix;
    }

    /**
     * Return the persistent instance of the given entity class with the given identifier, throwing
     * an exception if not found.
     *
     * @param entityClass
     *            a persistent class
     * @param id
     *            the identifier of the persistent instance
     * @return the persistent instance
     * @throws org.springframework.orm.ObjectRetrievalFailureException
     *             if not found
     * @throws org.springframework.dao.DataAccessException
     *             in case of Hibernate errors
     */
    @Override
    public <_E extends Entity<_K>, _K extends Serializable> _E loadEntity(Class<_E> entityType, _K id)
            throws DataAccessException {
        return dataManager.fetch(entityType, id);
    }

    protected abstract Class<? extends K> getKeyType();

    protected abstract Class<? extends E> getEntityType();

    protected abstract Class<? extends Dto> getTransferType();

    protected class ViewData
            extends ModelAndViewEx {

        public E entity;
        public Dto dto;

        public ViewData() {
            super(_EntityController.this);
        }

        public ViewData(String viewName, Map<String, ?> model) {
            super(_EntityController.this, viewName, model);
        }

        public ViewData(String viewName) {
            super(_EntityController.this, viewName);
        }

        public ViewData(View view, Map<String, ?> model) {
            super(_EntityController.this, view, model);
        }

        public ViewData(View view) {
            super(_EntityController.this, view);
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
        String entityTypeName = ClassUtil.getDisplayName(getEntityType());
        metaData.put("name", entityTypeName);
    }

    /**
     * Load vocabulary for this controller.
     */
    protected void loadVocab(Map<String, String> vocab) {
    }

    protected String viewOf(String localView) {
        return prefix + localView;
    }

    protected ModelAndView _index(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ViewData view = new ViewData();

        // Index by data-table:
        // List<AllowListDto> list = AllowListDto.marshalList(0, allowListDao.list());
        // mm.put("list", list);

        Map<String, String> parameterMap = new HashMap<String, String>();
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = req.getParameter(paramName);
            parameterMap.put(paramName, paramValue);
        }
        String parameterMapJson = JsonUtil.dump(parameterMap);

        view.put("parameterMap", parameterMapJson);

        return view;
    }

    protected abstract ModelAndView _content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    /**
     * Should construct a JSON response.
     */
    protected abstract ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

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

    protected ModelAndView _create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewData view = new ViewData();
        view.put("method", "create");
        view.put("METHOD", view.V.get("create"));

        _createOrEdit(view, req, resp);

        resp.sendRedirect("index.htm");
        return null;
    }

    protected ModelAndView _edit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ViewData view = new ViewData();
        view.setViewName(viewOf("index"));
        view.put("method", "edit");
        view.put("METHOD", view.V.get("edit"));

        _createOrEdit(view, req, resp);

        resp.sendRedirect("index.htm");
        return null;
    }

    /**
     * Use <code>req.getAttribute("create"): Boolean</code> to distinguish create and update.
     */
    protected abstract ModelAndView _createOrEdit(ViewData data, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    protected ModelAndView _delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idString = req.getParameter("id");
        K id;
        try {
            id = parseRequiredId(idString);
        } catch (Exception e) {
            return Javascripts.alertAndBack("非法对象标识: " + idString + ": " + e.getMessage()).dump(req, resp);
        }

        E entity = dataManager.fetch(getEntityType(), id);
        if (entity != null)
            try {
                dataManager.delete(entity);
            } catch (DataIntegrityViolationException e) {
                return Javascripts.alertAndBack("不能删除正在使用中的对象。" + hint(id)).dump(req, resp);
            }

        resp.sendRedirect("index.htm");
        return null;
    }

    // Utility pages

    protected ModelAndView notApplicable(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        JavascriptChunk chunk = Javascripts.alertAndBack("Not applicable");
        chunk.dump(req, resp);
        return null;
    }

    // Utilities...

    protected static class WithContext {

        final HttpServletRequest req;
        final HttpServletResponse resp;

        public WithContext(HttpServletRequest req, HttpServletResponse resp) {
            if (req == null)
                throw new NullPointerException("req");

            if (resp == null)
                throw new NullPointerException("resp");

            this.req = req;
            this.resp = resp;
        }

        protected <T> T errorAlert(String message)
                throws IOException {
            Javascripts.alertAndBack(message).dump(req, resp);
            return null;
        }

    }

    /**
     * Parse the id string in the format of corresponding key type.
     *
     * @return <code>null</code> If the given id string is <code>null</code>.
     */
    protected K parseId(String idString)
            throws ServletException {
        K id;
        try {
            id = EntityUtil.parseId(getKeyType(), idString);
        } catch (ParseException e) {
            throw new ServletException("无效的标识：" + idString, e);
        }
        return id;
    }

    protected K parseRequiredId(String idString)
            throws ServletException {
        if (idString == null)
            throw new ServletException("没有指定标识。");
        return parseId(idString);
    }

    protected String hint(K id) {
        String entityTypeName = ClassUtil.getDisplayName(getEntityType());
        return entityTypeName + " [" + id + "]";
    }

    protected String hint(Entity<?> entity) {
        return ClassUtil.getDisplayName(entity.getClass()) + " [" + entity.getId() + "]";
    }

    protected E newEntity()
            throws ServletException {
        try {
            return getEntityType().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    protected Dto newDto()
            throws ServletException {
        return newDto(null);
    }

    protected Dto newDto(Integer selection)
            throws ServletException {

        Class<? extends Dto> dtoType = getTransferType();
        Dto dto;

        try {
            if (selection == null)
                dto = dtoType.newInstance();
            else {
                Constructor<? extends Dto> selectionCtor = dtoType.getConstructor(int.class);
                dto = selectionCtor.newInstance(selection.intValue());
            }
        } catch (ReflectiveOperationException e) {
            throw new ServletException("Failed to create DTO of " + dtoType, e);
        }

        Class<? extends E> entityType = getEntityType();
        dto.initSourceType(entityType);
        return dto;
    }

    protected ViewData it(Object it) {
        ViewData view = new ViewData();
        view.put("it", it);
        return view;
    }

}
