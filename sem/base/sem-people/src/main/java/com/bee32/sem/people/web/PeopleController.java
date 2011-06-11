package com.bee32.sem.people.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class PeopleController extends MultiActionController {

    @RequestMapping("/people/personAdmin.htm")
    public Map<?, ?> personAdmin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        return map;
    }

    @RequestMapping("/people/orgAdmin.htm")
    public Map<?, ?> orgAdmin(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        return map;
    }

}
