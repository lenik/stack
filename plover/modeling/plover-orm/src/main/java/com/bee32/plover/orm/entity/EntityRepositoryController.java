package com.bee32.plover.orm.entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.free.ParseException;
import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.restful.IRestfulResponse;

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
public class EntityRepositoryController<E extends EntityBean<K>, K extends Serializable> {

    @Inject
    CommonDataManager dataManager;

    public void index(IRestfulRequest req, IRestfulResponse resp)
            throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("TODO...");
    }

    public Object create(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        E entity = repo.populate(req);

        dataManager.saveOrUpdate(entity);

        return entity;
    }

    public Object update(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        String keyString = req.getRestPath();
        K key = repo.parseKey(keyString);

        E entity = repo.populate(req);
        entity.setId(key);

        String _version = req.getParameter("version");
        if (_version != null) {
            int version = Integer.parseInt(_version);
            entity.setVersion(version);
        }

        dataManager.update(entity);
        return entity;
    }

    public Object delete(IRestfulRequest req, IRestfulResponse resp)
            throws ParseException {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        String keyString = req.getRestPath();
        K key = repo.parseKey(keyString);

        E entity = repo.retrieve(key);
        dataManager.delete(entity);

        // MethodNames.INDEX isn't imported.
        resp.setMethod("index");
        return repo;
    }

}
