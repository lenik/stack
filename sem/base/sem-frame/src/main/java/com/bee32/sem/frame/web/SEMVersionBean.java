package com.bee32.sem.frame.web;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.free.AbstractNonNullComparator;
import javax.free.LineReader;
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

    TreeMap<String, VersionDescription> versionLog = new TreeMap<String, VersionDescription>(VersionComparator.INSTANCE);

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

        URL verlogUrl = loader.getResource("META-INF/verlog");
        String verlog = null;

        if (verlogUrl != null) {
            URLResource resource = new URLResource(verlogUrl);
            try {
                verlog = resource.forRead().readTextContents();
            } catch (IOException e) {
                // throw new IllegalUsageException("Failed to load META-INF/version file", e);
            }
        }

        if (versionText != null)
            parse(versionText);
        if (verlog != null)
            parseLog(verlog);
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

    public void parseLog(String verlog) {
        StringReader reader = new StringReader(verlog);
        LineReader lines = new LineReader(reader);

        VersionDescription ver = null;

        try {
            String line;
            while ((line = lines.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;

                boolean commitLine = line.startsWith(" ") || line.startsWith("\t");
                line = line.trim();

                if (commitLine) {
                    String commit = line.trim();
                    if (commit.startsWith("* "))
                        commit = commit.substring(2);

                    if (ver != null)
                        ver.addCommit(commit);
                    continue;
                }

                if (ver != null) {
                    versionLog.put(ver.getVersion(), ver);
                    ver = new VersionDescription();
                }

                int dash = line.indexOf('-');
                // String title = line.substring(0, dash);
                line = line.substring(dash + 1);
                int space = line.indexOf(' ');
                String version;
                String[] authors = {};
                String date = null;
                if (space == -1) {
                    version = line.trim();
                } else {
                    version = line.substring(0, space);
                    line = line.substring(space + 1).trim();
                    if (line.startsWith("(") && line.endsWith(")"))
                        line = line.substring(1, line.length() - 1).trim();

                    int lastComma = line.lastIndexOf(", ");
                    if (lastComma != -1) {
                        String lastWord = line.substring(lastComma + 1).trim();
                        if (lastWord.startsWith("20")) {
                            date = lastWord;
                            line = line.substring(0, lastComma).trim();
                        }
                    }
                    authors = line.split(", ");
                }
                ver = new VersionDescription();
                ver.setVersion(version);
                ver.setDate(date);
                ver.setAuthors(authors);
            } // while

            if (ver != null)
                versionLog.put(ver.getVersion(), ver);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
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

    public TreeMap<String, VersionDescription> getVersionLog() {
        return versionLog;
    }

    public List<VersionDescription> getVersionLog10() {
        return getVersionLogList(10);
    }

    public List<VersionDescription> getVersionLog30() {
        return getVersionLogList(30);
    }

    public List<VersionDescription> getVersionLog100() {
        return getVersionLogList(100);
    }

    public List<VersionDescription> getVersionLogList(int showLogSize) {
        List<VersionDescription> versionLogList = new ArrayList<VersionDescription>();

        int n = 0;
        for (VersionDescription ver : versionLog.values()) {
            versionLogList.add(ver);
            if (++n >= showLogSize)
                break;
        }
        return versionLogList;
    }

}

class VersionComparator
        extends AbstractNonNullComparator<String> {

    static int[] parseVersion(String ver) {
        StringTokenizer toks = new StringTokenizer(ver, ".");
        int n = toks.countTokens();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            String tok = toks.nextToken();
            array[i] = Integer.parseInt(tok);
        }
        return array;
    }

    @Override
    public int compareNonNull(String v1, String v2) {
        int[] a1 = parseVersion(v1);
        int[] a2 = parseVersion(v2);
        int n = Math.max(a1.length, a2.length);
        for (int i = 0; i < n; i++) {
            int e1 = i < a1.length ? a1[i] : 0;
            int e2 = i < a2.length ? a2[i] : 0;
            if (e1 != e2)
                return -(e1 - e2);
        }
        return 0;
    }

    public static final VersionComparator INSTANCE = new VersionComparator();

}