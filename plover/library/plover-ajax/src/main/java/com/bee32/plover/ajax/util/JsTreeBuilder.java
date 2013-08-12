package com.bee32.plover.ajax.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bee32.plover.ajax.JsonUtil;

/**
 * Js-树 构建器
 *
 * <p lang="en">
 * Js-Tree Builder
 */
public class JsTreeBuilder {

    public static <T> T dump(HttpServletResponse resp, IJsTreeNode node)
            throws IOException {
        Object _tree = buildJsonTree(node);
        return JsonUtil.dump(resp, _tree);
    }

    public static Object buildJsonTree(IJsTreeNode node) {
        Map<String, Object> _tree = new HashMap<String, Object>();

        Object root = buildJsonNode(node);

        _tree.put("data", root);
        // _tree.put("metadata", "a string, array, object, etc.");
        // _tree.put("language", "en");
        return _tree;
    }

    static Object buildJsonNode(IJsTreeNode node) {
        Map<String, Object> _node = new HashMap<String, Object>();

        String title = node.getTitle();
        if (title != null)
            _node.put("data", title);

        Map<?, ?> attributes = node.getAttributes();
        if (attributes != null)
            _node.put("attr", attributes);

        boolean expanded = node.isExpanded();
        if (expanded)
            _node.put("state", "open");

        List<IJsTreeNode> children = node.getChildren();
        if (children != null) {
            List<Object> _children = new ArrayList<Object>();
            for (IJsTreeNode child : children) {
                Object _child = buildJsonNode(child);
                _children.add(_child);
            }
            _node.put("children", _children);
        }
        return _node;
    }

}
