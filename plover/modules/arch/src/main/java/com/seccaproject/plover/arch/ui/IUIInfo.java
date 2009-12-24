package com.seccaproject.plover.arch.ui;

import com.seccaproject.plover.arch.ui.impl.AccessibleContextImpl;

/**
 * 基本的用户界面信息。
 *
 * @see AccessibleContextImpl
 */
public interface IUIInfo {
//
// String getDisplayName(Locale locale);
//
// String getDescription(Locale locale);
//
// URL getIcon(Locale locale, IconVariant variant);

    /**
     * 获取显示名称。
     *
     * @return <code>null</code> 若未定义显示名称。
     */
    String getDisplayName();

    /**
     * 获取描述信息。
     *
     * @return <code>null</code> 若没有描述信息。
     */
    String getDescription();

    /**
     * 获取图标字典。
     *
     * @return non-<code>null</code> {@link IIconMap}，如果没有可用的图标，返回 {@link EmptyIconMap}。
     */
    IIconMap getIconMap();

    /**
     * 获取参考、帮助信息目录。
     *
     * @return <code>null</code> 若没有可用的参考帮助信息。
     */
    IRefdocs getRefdocs();

}
