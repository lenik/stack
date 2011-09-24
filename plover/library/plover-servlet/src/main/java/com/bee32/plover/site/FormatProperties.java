package com.bee32.plover.site;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.free.IFile;
import javax.free.IStreamInputSource;
import javax.free.ParseException;

public class FormatProperties {

    Map<String, FormatPropertyLine> map;

    public FormatProperties() {
        map = new LinkedHashMap<String, FormatPropertyLine>();
    }

    public String getProperty(String key) {
        FormatPropertyLine property = map.get(key);
        if (property == null)
            return null;
        else
            return property.getValue();
    }

    public void setProperty(String key, String value) {
        FormatPropertyLine property = map.get(key);
        if (property == null) {
            property = new FormatPropertyLine(key, value);
            map.put(key, property);
        } else {
            property.setValue(value);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (FormatPropertyLine property : map.values()) {
            sb.append(property.toString());
            sb.append('\n');
        }
        return sb.toString();
    }

    public void parse(IFile file)
            throws IOException, ParseException {
        parse(file.getName(), file.forRead().lines(true));
    }

    public void parse(String name, IStreamInputSource source)
            throws IOException, ParseException {
        parse(name, source.forRead().lines(true));
    }

    public void parse(String fileName, Iterable<String> lines)
            throws IOException, ParseException {
        int lineNo = 0;

        for (String line : lines) { // chopped.
            lineNo++;

            String trim = line;
            int sharp = trim.indexOf('#');
            if (sharp != -1)
                trim = trim.substring(0, sharp);
            trim = trim.trim();
            if (trim.isEmpty())
                continue;

            FormatPropertyLine property = null;
            try {
                property = new FormatPropertyLine(line);
            } catch (ParseException e) {
                e.setSource(fileName);
                e.setLine(lineNo);
                throw e;
            }

            String key = property.getKey();
            map.put(key, property);
        }
    }

}
