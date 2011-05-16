package com.bee32.plover.orm.ext.util;

import java.util.HashMap;
import java.util.Map;

public interface CEnum {

    char toChar();

    // static $ fromChar(char ch);

    class CharMap<T> {

        Map<Character, T> map = new HashMap<Character, T>();

        public T get(Object key) {
            return map.get(key);
        }

        public T put(Character key, T value) {
            return map.put(key, value);
        }

    }

}
