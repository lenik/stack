package com.bee32.sems.bom.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class BomAdminJsfController extends MultiActionController {

    @RequestMapping("/bom/bomAdminJsf.htm")
    public Map<?, ?> bomAdminJsf(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        return map;
    }

    @RequestMapping("/bom/materialPriceStrategy.htm")
    public Map<?, ?> materialPriceStrategy(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        return map;
    }
}