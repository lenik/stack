package com.bee32.sem.hr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;

import javax.free.DecodeException;
import javax.free.IntegerComparator;
import javax.free.UnexpectedException;

import com.bee32.plover.collections.map.RangeFromMap;

public class ScoreLevelMap
        extends RangeFromMap<Integer, ScoreLevel> {

    private static final long serialVersionUID = 1L;

    public ScoreLevelMap() {
        super(IntegerComparator.INSTANCE);
    }

    public String encode() {
        StringBuilder sb = new StringBuilder();
        for (Entry<Integer, ScoreLevel> entry : entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue().getLabel());
            sb.append(":");
            sb.append(entry.getValue().getBonus());
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
                String tmp = line.substring(colon + 1);
                String label = null;
                BigDecimal bonus = BigDecimal.ZERO;
                int colon1 = tmp.indexOf(":");
                if (colon1 == -1) {
                    label = tmp;
                } else {
                    label = tmp.substring(0, colon1);
                    String sbonus = tmp.substring(colon1 + 1);
                    bonus = new BigDecimal(sbonus);
                }
                ScoreLevel level = new ScoreLevel();
                level.setLabel(label);
                level.setBonus(bonus);

                int score = Integer.parseInt(key);
                levelMap.put(score, level);
            }
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
        return levelMap;
    }

}
