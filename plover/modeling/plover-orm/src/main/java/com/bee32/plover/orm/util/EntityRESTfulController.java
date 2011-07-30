package com.bee32.plover.orm.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.free.ParseException;
import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityRepository;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.restful.IRESTfulRequest;
import com.bee32.plover.restful.IRESTfulResponse;

/**
 * ERController 中实际执行动作的是 CDM，
 *
 * 之所以这样是因为 Hibernate 中执行数据操作需要事物环境，在 Module 中所追加的是 Dao 对象，不具有事物上下文。
 *
 * 以后可能通过 Proxy 的方法建立 OTF 事物环境，目前 CDM 应该足以应付。
 *
 * 同时需要注意的是，ER 并非毫无用处，这里 populate 还是需要 ER 的具体实现支持。
 */
@Controller
@Lazy
public class EntityRESTfulController<E extends Entity<K>, K extends Serializable> {

    @Inject
    CommonDataManager dataManager = MemdbDataManager.getInstance();

    protected <_E extends Entity<? extends _K>, _K extends Serializable> //
    IEntityAccessService<_E, _K> asFor(Class<? extends _E> entityType) {
        IEntityAccessService<_E, _K> service = dataManager.asFor(entityType);
        return service;
    }

    public void index(IRESTfulRequest req, IRESTfulResponse resp)
            throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("TODO...");
    }

    public Object create(IRESTfulRequest req, IRESTfulResponse resp)
            throws Exception {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        E entity = repo.populate(req);

        asFor(repo.getEntityType()).saveOrUpdate(entity);

        return entity;
    }

    public Object update(IRESTfulRequest req, IRESTfulResponse resp)
            throws Exception {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        String refName = req.getRestPath();
        K key = repo.convertRefNameToKey(refName);

        E entity = repo.populate(req);
        EntityAccessor.setId(entity, key);

        String _version = req.getParameter("version");
        if (_version != null) {
            int version = Integer.parseInt(_version);
            EntityAccessor.setVersion(entity, version);
        }

        asFor(repo.getEntityType()).update(entity);
        return entity;
    }

    public Object delete(IRESTfulRequest req, IRESTfulResponse resp)
            throws ParseException {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        String refName = req.getRestPath();
        K key = repo.convertRefNameToKey(refName);

        E entity = repo.get(key);
        asFor(repo.getEntityType()).delete(entity);

        // MethodNames.INDEX isn't imported.
        resp.setMethod("index");
        return repo;
    }

}
