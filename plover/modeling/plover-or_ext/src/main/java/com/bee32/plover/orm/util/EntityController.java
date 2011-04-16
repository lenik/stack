package com.bee32.plover.orm.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.free.IllegalUsageException;
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
import org.springframework.web.util.HtmlUtils;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.javascript.JavascriptChunk;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.EntityBean;

@ComponentTemplate
@Lazy
public abstract class EntityController<E extends EntityBean<K>, K extends Serializable>
        extends MultiActionController {

    private final String _prefix;

    @Inject
    protected CommonDataManager dataManager;

    protected final Class<E> entityType;
    {
        Class<?>[] pv = ClassUtil.getOriginPVClass(getClass());
        if (pv == null) {
            Class<?> superclass = getClass().getSuperclass();
            // DTO class foo.Bar must be declared with type parameter.
            throw new IllegalUsageException("EntityController" + superclass + " must be declared with type parameter");
        }

        @SuppressWarnings("unchecked")
        Class<E> entityType = (Class<E>) pv[0];

        this.entityType = entityType;
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

    @RequestMapping("createForm.htm")
    public ModelAndView createForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", true);

        Map<String, Object> map = form(req, resp);

        map.put("verb", "create");
        map.put("verb_en", "Create");
        map.put("verb_zh", "创建");

        return new ModelAndView(viewOf("form"), map);
    }

    @RequestMapping("editForm.htm")
    public ModelAndView editForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        req.setAttribute("create", false);

        Map<String, Object> map = form(req, resp);

        map.put("verb", "update");
        map.put("verb_en", "Edit");
        map.put("verb_zh", "编辑");

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

    protected abstract ModelAndView createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    @RequestMapping("delete.htm")
    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {

        int id = Integer.parseInt(req.getParameter("id"));

        E entity = dataManager.load(entityType, id);
        if (entity != null)
            try {
                dataManager.delete(entity);
            } catch (DataIntegrityViolationException e) {
                resp.setCharacterEncoding("utf-8");

                PrintWriter out = resp.getWriter();

                String message = "分级审核策略 " + entity.getName() + " 正在被其它对象使用中，删除失败。";

                JavascriptChunk chunk = new JavascriptChunk();
                chunk.println("alert('" + HtmlUtils.htmlEscape(message) + "'); ");
                chunk.println("history.back(); ");
                chunk.dump(req, resp);

                return null;
            }

        return viewOf("index");
    }

}
