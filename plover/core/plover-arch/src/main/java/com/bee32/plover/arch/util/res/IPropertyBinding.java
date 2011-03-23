package com.bee32.plover.arch.util.res;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public interface IPropertyBinding {

    PropertyDispatcher bind(Properties properties);

    PropertyDispatcher bind(ResourceBundle resourceBundle);

    PropertyDispatcher bind(ResourceBundle resourceBundle, Locale locale);

    PropertyDispatcher bind(Class<?> baseClass);

    PropertyDispatcher bind(Class<?> baseClass, Locale locale);

}
