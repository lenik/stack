package com.bee32.plover.util.i18n;

import java.util.Locale;

public interface ILocaleAware {

    Locale NATIVE_LOCALE = Locale.getDefault();

    Locale en_US = Locale.forLanguageTag("en-US");
    Locale zh_CN = Locale.forLanguageTag("zh-CN");

}
