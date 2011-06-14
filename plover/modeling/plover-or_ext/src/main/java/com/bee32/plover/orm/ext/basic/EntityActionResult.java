package com.bee32.plover.orm.ext.basic;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.servlet.View;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.servlet.mvc.ActionResult;

@Deprecated
public class EntityActionResult
        extends ActionResult {

    Entity<?> entity;
    EntityDto<?, ?> dto;

    public EntityActionResult(String viewName) {
        super(viewName);
    }

    public EntityActionResult(String viewName, Map<String, ?> model) {
        super(viewName, model);
    }

    public EntityActionResult(View view) {
        super(view);
    }

    public EntityActionResult(View view, Map<String, ?> model) {
        super(view, model);
    }

    @Override
    protected void wireUp() {
        put("entity", entity);
        put("dto", dto);
    }

    public <T> T errorAlert(String message)
            throws IOException {
        return Javascripts.alertAndBack(message).dump(getResponse());
    }

}
