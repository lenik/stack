package com.bee32.plover.site;

import com.googlecode.jatl.Html;

public class SiteManagerServletTest {

    public static void main(String[] args)
            throws Exception {
        HtmlBuilder t = new HtmlBuilder() {
            {
                bind("id", "foo");
                bind("coolName", "Awesomo");
                html();
                body();
                h1().text("Name Games").end();
                p().id("${id}").text("Hello ${coolName}, and hello").end();
                makeList("Kyle", "Stan", "Eric", "${coolName}");
                endAll();
                done();
            }

            Html makeList(String... names) {
                ul();
                for (String name : names) {
                    li().text(name).end();
                }
                return end();
            }
        };

        System.out.println(t);
    }

}
