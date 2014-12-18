package com.bee32.zebra.oa.thread.impl;

import net.bodz.bas.repr.path.IPathArrival;
import net.bodz.bas.repr.path.ITokenQueue;
import net.bodz.bas.repr.path.PathDispatchException;

import com.bee32.zebra.oa.thread.Topic;
import com.bee32.zebra.tk.sea.FooManager;
import com.bee32.zebra.tk.sql.VhostDataService;

/**
 * 用于跟踪产品、服务的生命周期。项目可以是潜在的销售机会，或已在履行的契约。
 * 
 * @label 项目
 * 
 * @rel cat/?schema=1: 管理分类
 * @rel phase/?schema=1: 管理进展阶段
 * @rel tag/?schema=1: 管理标签
 * 
 * @see <a href="http://www.bensino.com/news/sInstitute/2/2012/1212/40641.html">SKU不是越多越好</a>
 * @see <a href="http://www.360doc.com/content/08/0109/14/25392_958214.shtml">物料编码规划</a>
 * @see <a href="http://baike.baidu.com/view/395868.htm">什么是物料清单（BOM）</a>
 * @see <a href="http://book.ebusinessreview.cn/bookpartinfo-71335.html">几种不同的 BOM 拓扑结构</a>
 */
public class TopicManager
        extends FooManager {

    TopicMapper mapper;

    public TopicManager() {
        super(Topic.class);
        mapper = VhostDataService.getInstance().getMapper(TopicMapper.class);
    }

    public TopicMapper getMapper() {
        return mapper;
    }

    @Override
    public IPathArrival dispatch(IPathArrival previous, ITokenQueue tokens)
            throws PathDispatchException {
        return super.dispatch(previous, tokens);
    }

}
