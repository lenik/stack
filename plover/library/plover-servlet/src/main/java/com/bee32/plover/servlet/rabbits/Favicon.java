package com.bee32.plover.servlet.rabbits;

import java.io.IOException;

import javax.free.ClassResource;
import javax.free.OutputStreamTarget;
import javax.free.URLResource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Favicon
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        URLResource ico = ClassResource.classData(Favicon.class, "rabbit.ico");

        ServletOutputStream out = resp.getOutputStream();
        new OutputStreamTarget(out).forWrite().writeBytes(ico);

        out.close();
    }

}
