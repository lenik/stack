package com.bee32.plover.orm.util;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.free.ClassLocal;
import javax.free.IVariantLookupMap;
import javax.free.Map2VariantLookupMap;
import javax.free.NotImplementedException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityUtil;

@ComponentTemplate
@Lazy
public abstract class EntityController<E extends EntityBean<K>, K extends Serializable>
        extends MultiActionController {

    private final String _prefix;

    @Inject
    protected CommonDataManager dataManager;

    protected final Class<E> entityType;
    {
        entityType = ClassUtil.infer1(getClass(), EntityController.class, 0);
    }

    public EntityController() {
        try {
            Field prefixField = getClass().getDeclaredField("PREFIX");

            int modifiers = prefixField.getModifiers();
            if (!Modifier.isStatic(modifiers))
                throw new Error(prefixField + " must be static");

            _prefix = (String) prefixField.get(null);
        } catch (Exception e) {
            throw new Error("PREFIX isn't defined in " + getClass());
        }
    }

    static final ClassLocal<IVariantLookupMap<String>> controllerLocalMetaData;
    static {
        controllerLocalMetaData = new ClassLocal<IVariantLookupMap<String>>();
    }

    final IVariantLookupMap<String> metaData;
    {
        IVariantLookupMap<String> local = controllerLocalMetaData.get(getClass());
        if (local == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            _buildMetaData(map);
            local = new Map2VariantLookupMap<String>(map);
            controllerLocalMetaData.put(getClass(), local);
        }
        metaData = local;
    }

    protected static final String ENTITY_TYPE_NAME = "entity.name";

    protected static final String VERB_CREATE_EN = "v.create";
    protected static final String VERB_CREATE_LANG = "v.create.lang";
    protected static final String VERB_EDIT_EN = "v.update";
    protected static final String VERB_EDIT_LANG = "v.update.lang";

    /**
     * Build all meta data.
     *
     * Implicit build the user meta data, too.
     *
     * @see #preamble(Map)
     */
    protected void _buildMetaData(Map<String, Object> metaData) {

        String entityTypeName = entityType.getSimpleName();
        // String entityTypeName = DisplayNameUtil.getDisplayName(entityType);
        metaData.put(ENTITY_TYPE_NAME, entityTypeName);

        metaData.put(VERB_CREATE_EN, "Create");
        metaData.put(VERB_CREATE_LANG, "创建");

        metaData.put(VERB_EDIT_EN, "Update");
        metaData.put(VERB_EDIT_LANG, "编辑");

        preamble(metaData);
    }

    /**
     * Build user meta data.
     *
     * @param metaData
     *            (Output) The result meta data.
     */
    protected abstract void preamble(Map<String, Object> metaData);

    protected String viewOf(String localView) {
        return _prefix + localView;
    }

    @RequestMapping("index.htm")
    public Map<String, Object> index(HttpServletRequest req, HttpServletResponse resp) {
        ModelMap mm = new ModelMap();

        // Index by data-table:
        // List<AllowListDto> list = AllowListDto.marshalList(0, allowListDao.list());
        // mm.put("list", list);

        return mm;
    }

    public Map<String, Object> content(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

    /**
     * Should construct a JSON response.
     */
    public void data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

    @RequestMapping("createForm.htm")
    public ModelAndView createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", true);

        Map<String, Object> map = form(req, resp);

        map.put("verb", "create");
        map.put("verb_en", metaData.getString(VERB_CREATE_EN));
        map.put("verb_zh", metaData.getString(VERB_CREATE_LANG));

        return new ModelAndView(viewOf("form"), map);
    }

    @RequestMapping("editForm.htm")
    public ModelAndView editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", false);

        Map<String, Object> map = form(req, resp);

        map.put("verb", "update");
        map.put("verb_en", metaData.getString(VERB_EDIT_EN));
        map.put("verb_zh", metaData.getString(VERB_EDIT_LANG));

        return new ModelAndView(viewOf("form"), map);
    }

    protected abstract Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException;

    @RequestMapping("create.htm")
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("create", true);
        return createOrUpdate(req, resp);
    }

    @RequestMapping("update.htm")
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("create", false);
        return createOrUpdate(req, resp);
    }

    /**
     * Use <code>req.getAttribute("create"): Boolean</code> to distinguish create and update.
     */
    protected abstract ModelAndView createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    @RequestMapping("delete.htm")
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        String idString = req.getParameter("id");
        K id = EntityUtil.parseId(entityType, idString);

        E entity = dataManager.load(entityType, id);
        if (entity != null)
            try {
                dataManager.delete(entity);
            } catch (DataIntegrityViolationException e) {
                resp.setCharacterEncoding("utf-8");

                String message = metaData.getString(ENTITY_TYPE_NAME) + " " + entity.getName() + " 正在被其它对象使用中，删除失败。";
                Javascripts.alertAndBack(message).dump(req, resp);

                return null;
            }

        return viewOf("index");
    }

    // Utils...

    protected static Map<String, Object> it(Object it) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("it", it);
        return map;
    }

}
