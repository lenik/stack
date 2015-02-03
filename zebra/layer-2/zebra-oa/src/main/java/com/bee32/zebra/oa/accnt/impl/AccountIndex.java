package com.bee32.zebra.oa.accnt.impl;

import net.bodz.bas.meta.decl.ObjectType;
import net.bodz.bas.rtx.IQueryable;

import com.bee32.zebra.oa.accnt.Account;
import com.bee32.zebra.tk.repr.QuickIndex;

/**
 * 会计科目为会计学相当重要的基本研究方法与辅助工作， 它将会计要素视性质分属、分纲的设置，例如现金即为资产会计要素的一大会计科目。
 * 
 * 一般来说，会计科目是设置帐户、处理帐务的依据。
 * 
 * @label 会计科目
 * 
 * @rel tag/: 管理标签
 * 
 * @see <a href="http://zh.wikipedia.org/wiki/%E8%B2%A1%E5%8B%99%E5%A0%B1%E8%A1%A8">财务报表</a>
 * @see <a
 *      href="http://zh.wikipedia.org/wiki/%E5%9B%BD%E9%99%85%E8%B4%A2%E5%8A%A1%E6%8A%A5%E5%91%8A%E5%87%86%E5%88%99">国际财务报告准则（IFRS）</a>
 * @see <a href="http://www.lyskj.org/NewDetail/141.aspx">趣解会计科目与账户</a>
 */
@ObjectType(Account.class)
public class AccountIndex
        extends QuickIndex {

    public AccountIndex(IQueryable context) {
        super(context);
    }

}
