package com.bee32.sem.process.verify.builtin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public abstract class GenericController
        extends MultiActionController {

    private final String _prefix;

    public GenericController() {
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

}
