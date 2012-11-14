package com.bee32.plover.util;

import java.util.regex.Pattern;

import javax.free.PatternProcessor;

public class ChineseCodec {

    static Pattern encodedChar = Pattern.compile("_u[0-9a-f]{4}_");

    static class Decoder
            extends PatternProcessor {

        public Decoder() {
            super(encodedChar);
        }

        @Override
        protected void matched(String part) {
            String hex = part.substring(2, part.length() - 1);
            int num = Integer.parseInt(hex, 16);
            char ch = (char) num;
            print(ch);
        }

    }

    public static String encode(String s) {
        int n = s.length();
        StringBuilder encoded = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch < '\u0080')
                encoded.append(ch);
            else {
                encoded.append("_u");
                String hex = Integer.toHexString(ch);
                switch (hex.length()) {
                case 0:
                    assert false;
                case 1:
                    encoded.append('0');
                case 2:
                    encoded.append('0');
                case 3:
                    encoded.append('0');
                    encoded.append(hex);
                    break;
                default:
                    hex = hex.substring(hex.length() - 4);
                    encoded.append(hex);
                }
                encoded.append('_');
            }
        }
        return encoded.toString();
    }

    public static String decode(String s) {
        return new Decoder().process(s);
    }

}
