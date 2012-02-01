package com.bee32.plover.util.zh;

import net.sourceforge.pinyin4j.PinyinHelper;

public class ZhUtil {

    /**
     * 返回中文的首字母
     * <p>
     * 对应 EL： pc:pinyinAbbr(string)
     *
     * @param zhString
     *            Non-<code>null</code> chinese string.
     * @return Non-<code>null</code> pinyin abbreviation of the given string.
     */
    public static String pinyinAbbr(String zhString) {
        StringBuilder buf = new StringBuilder(zhString.length());

        for (int j = 0; j < zhString.length(); j++) {
            char zh = zhString.charAt(j);

            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(zh);

            if (pinyinArray != null) {
                char pinyinLetter = pinyinArray[0].charAt(0);
                buf.append(pinyinLetter);
            } else {
                buf.append(zh);
            }
        }
        return buf.toString();
    }

}
