package com.bee32.sem.frame.web;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.free.URLResource;

import org.springframework.stereotype.Component;

/**
 * The version file is like:
 *
 * <pre>
 * 1.2 12135 tag
 * </pre>
 *
 * Where <code>1.2</code> is the version number, and <code>12135</code> is the release number.
 *
 * The <code>tag</code> is optional, which is for codename.
 */
@Component("semVersion")
public class SEMVersionBean
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String version = "0";
    String release = "0";
    String tag = "dev";

    public SEMVersionBean() {
        ClassLoader loader = getClass().getClassLoader();
        URL versionUrl = loader.getResource("META-INF/version");
        String versionText = null;

        if (versionUrl != null) {
            URLResource resource = new URLResource(versionUrl);
            try {
                versionText = resource.forRead().readTextContents();
            } catch (IOException e) {
                // throw new IllegalUsageException("Failed to load META-INF/version file", e);
            }
        }

        if (versionText != null)
            parse(versionText);
    }

    public SEMVersionBean(String versionText) {
        parse(versionText);
    }

    static final Pattern word = Pattern.compile("\\S+", Pattern.MULTILINE);

    public void parse(String s) {
        List<String> list = new ArrayList<String>();
        Matcher m = word.matcher(s);
        while (m.find()) {
            String word = m.group();
            list.add(word);
        }
        System.out.println(list);

        switch (list.size()) {
        default:
        case 3:
            tag = list.get(2);

        case 2:
            release = list.get(1);

        case 1:
            version = list.get(0);
        }
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
