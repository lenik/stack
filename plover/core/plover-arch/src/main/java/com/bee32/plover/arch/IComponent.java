package com.bee32.plover.arch;

import com.bee32.plover.arch.ui.LazyAppearance;
import com.bee32.plover.arch.util.ExceptionSupport;

public interface IComponent {

    /**
     * 获取可区分的名称，通常在某个作用域中是唯一的。
     *
     * @return <code>null</code> 若未定义可区分的名称。
     */
    String getName();

    /**
     * 获取组件的显示外观和参考信息。
     */
    LazyAppearance getAppearance();

    /**
     * 获取异常处理支持。
     * <p>
     * 如果组件支持由调用者处理异常，而不是显示的抛出异常，则应该返回一个非空异常处理支持对象。
     *
     * @return <code>null</code> 表示不支持。
     */
    ExceptionSupport getExceptionSupport();

}
