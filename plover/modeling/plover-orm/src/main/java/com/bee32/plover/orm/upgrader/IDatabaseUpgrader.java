package com.bee32.plover.orm.upgrader;

import java.sql.SQLException;

/**
 * 规范：
 * <ol>
 * <li>系统管理整个数据库的版本
 * <li>每个模块维护自己的升级包
 * <li>升级包在 session-factory 之前执行，与hibernate无关
 * <li>升级后的数据库结构应该与当前的ORM保持一致
 * <li>升级包分为2个部分：<br>
 * 容错部分：可以部分失败<br>
 * 关键部分：必须整体成功或失败<br>
 * 如果运行升级包失败，容错部分可以进入数据库，而关键部分必须回滚。
 * </ol>
 */
public interface IDatabaseUpgrader {

    void upgrade(IDatabaseManager databaseManager)
            throws SQLException;

}
