package com.bee32.zebra.oa.contact.impl;

import net.bodz.bas.repr.path.PathToken;

import com.bee32.zebra.tk.foo.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 所有感兴趣的相关企、事业单位，包括：客户、供应商、雇员、竞争对手等等。
 * 
 * @label 企、事业单位
 * 
 * @rel person/: 管理联系人
 * @rel tag/?schema=10: 管理标签
 * 
 * @see <a href="http://www.xchen.com.cn/jjlw/rlzylw/580450.html">企业人员优化配置的策略</a>
 * @see <a href="http://baike.baidu.com/view/4295887.htm">客户分类</a>
 * @see <a href="http://blog.itpub.net/13723059/viewspace-594661/"> 客户分类管理法</a>
 * @see <a href="http://www.vsharing.com/k/CRM/2012-5/658445.html">CRM实现客户分类四大步骤</a>
 */
@PathToken("org")
public class OrganizationManager
        extends FooManager {

    OrganizationMapper mapper;

    public OrganizationManager() {
        mapper = VhostDataService.getInstance().getMapper(OrganizationMapper.class);
    }

    public OrganizationMapper getMapper() {
        return mapper;
    }

}
