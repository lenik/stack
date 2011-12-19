package com.bee32.sem.hr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.free.DecodeException;
import javax.free.IntegerComparator;
import javax.free.UnexpectedException;

import com.bee32.plover.collections.map.RangeFromMap;

public class ScoreLevelMap
        extends RangeFromMap<Integer, String> {

    private static final long serialVersionUID = 1L;

    public ScoreLevelMap() {
        super(IntegerComparator.INSTANCE);
    }

    @Override
    public String put(Integer key, String value) {
        if (value.contains("\n") || value.contains(":"))
            throw new IllegalArgumentException("Bad level name: " + value);
        return super.put(key, value);
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Integer, String> entry : entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static ScoreLevelMap decode(String encoded)
            throws DecodeException {
        if (encoded == null)
            throw new NullPointerException("encoded");

        ScoreLevelMap levelMap = new ScoreLevelMap();

        StringReader reader = new StringReader(encoded);
        BufferedReader lines = new BufferedReader(reader);
        String line;
        try {
            while ((line = lines.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                int colon = line.indexOf(':');
                if (colon == -1)
                    throw new DecodeException("Bad map entry def: " + line);
                String key = line.substring(0, colon);
                String label = line.substring(colon + 1);
                int score = Integer.parseInt(key);
                levelMap.put(score, label);
            }
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
        return levelMap;
    }

}
