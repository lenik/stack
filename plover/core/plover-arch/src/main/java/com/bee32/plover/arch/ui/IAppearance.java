package com.bee32.plover.arch.ui;

import com.bee32.plover.arch.ui.impl.AccessibleContextImpl;

/**
 * 基本的用户界面信息。
 *
 * @see AccessibleContextImpl
 */
public interface IAppearance {

    int traitsIndex = -1428529429; // IUIInfo

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
     * 获取图像映射。
     *
     * @return non-<code>null</code> {@link IImageMap}，如果没有可用的图像，返回 {@link EmptyImageMap}。
     */
    IImageMap getImageMap();

    /**
     * 获取参考、帮助信息目录。
     *
     * @return <code>null</code> 若没有可用的参考帮助信息。
     */
    IRefdocs getRefdocs();

}
