package com.bee32.plover.servlet.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.free.FilePath;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

public abstract class CompositeController
        extends _CompositeController {

    public static final String PREFIX_ATTRIBUTE = "__PLOVER_PREFIX";
    public static final String PATH_PARAMETER_ATTRIBUTE = "__PLOVER_PATH_PARAM";
    public static final String ACTION_NAME_ATTRIBUTE = "__PLOVER_ACTION_NAME";

    Map<String, IActionHandler> actionMap = new HashMap<String, IActionHandler>();

    /**
     * <ol>
     * <li>(hostname) http://...
     * <li>(prefix) /prefix1
     * <li>(path-param) /sub1
     * <li>(action-name) /action1/subAction1
     * <li>(ext) .doXXX
     * <li>(query-string) ?...
     * </ol>
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse resp)
            throws Exception {
        String internalPath; // internal path always starts with a slash /.
        {
            String path = getRequestPath(request);
            assert path.startsWith(_prefix);
            internalPath = path.substring(_prefix.length());

            /*
             * String _internalPath = (String)
             * req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
             * assertEquals(_internalPath + "/", internalPath);
             */
        }

        internalPath = FilePath.stripExtension(internalPath);

        int lastSlash = internalPath.length();
        while (true) {
            lastSlash = internalPath.lastIndexOf('/', lastSlash - 1);
            if (lastSlash == -1)
                break;

            String actionNameTest = internalPath.substring(lastSlash);
            assert actionNameTest.startsWith("/");
            actionNameTest = actionNameTest.substring(1);

            IActionHandler handler = actionMap.get(actionNameTest);
            if (handler != null) {
                ActionRequest req = newRequest(request);
                ActionResult result = newResult((String) null);
                result.setResponse(resp);

                // request.setAttribute(PREFIX_ATTRIBUTE, _prefix);
                req.setPrefix(_prefix);

                String pathWithoutActionName = internalPath.substring(0, lastSlash);
                // request.setAttribute(PATH_PARAMETER_ATTRIBUTE, pathWithoutActionName);
                req.setPathParameter(pathWithoutActionName);

                // request.setAttribute(ACTION_NAME_ATTRIBUTE, actionNameTest);
                req.setActionName(actionNameTest);

                result = invokeHandler(handler, req, result);

                result.wireUp();
                return result;
            }
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "(internal) path: " + internalPath);
        return null;
    }

    protected ActionResult invokeHandler(IActionHandler handler, ActionRequest req, ActionResult result)
            throws Exception {
        result = handler.handleRequest(req, result);
        return result;
    }

    protected void addHandler(IActionHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");
        String handlerName = handler.getName();
        addHandler(handlerName, handler);
    }

    protected void addHandler(String name, IActionHandler handler) {
        if (name == null)
            throw new NullPointerException("name");
        if (handler == null)
            throw new NullPointerException("handler");
        actionMap.put(name, handler);
    }

    protected ActionRequest newRequest(HttpServletRequest request) {
        return new ActionRequest(request);
    }

    protected ActionResult newResult(String viewName) {
        return new ActionResult(viewName);
    }

    protected ActionResult newResult(String viewName, Map<String, ?> model) {
        return new ActionResult(viewName, model);
    }

    protected ActionResult newResult(View view) {
        return new ActionResult(view);
    }

    protected ActionResult newResult(View view, Map<String, ?> model) {
        return new ActionResult(view, model);
    }

}
