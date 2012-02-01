package com.bee32.plover.faces.test;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Lazy
@RequestMapping("/test/*")
public class LongViewController {

    @RequestMapping("longview.do")
    public String longView() {
        return "lview";
    }

}
