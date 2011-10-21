package com.bee32.plover.web.faces.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.plover.inject.ControllerTemplate;

@ControllerTemplate
@Lazy
@RequestMapping("/test/*")
public class LongViewController {

    @RequestMapping("longview.do")
    public String longView() {
        return "lview";
    }

}
