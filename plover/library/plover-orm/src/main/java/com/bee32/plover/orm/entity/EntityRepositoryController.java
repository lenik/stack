package com.bee32.plover.orm.entity;

import java.io.Serializable;

import com.bee32.plover.disp.IArrival;
import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.restful.IRestfulResponse;

public class EntityRepositoryController {

    public <E extends IEntity<K>, K extends Serializable> //
    Object create(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        E entity = repo.populate(req);

        repo.saveOrUpdate(entity);

        return entity;
    }

    public <E extends IEntity<K>, K extends Serializable> //
    Object update(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        IArrival arrival = req.getArrival();

        String keyString = req.getDispatchPath();
        Serializable key = repo.parseKey(keyString);

        E entity = repo.populate(req);

        repo.update(entity);

        return entity;
    }

    public <E extends IEntity<K>, K extends Serializable> //
    Object delete(IRestfulRequest req, IRestfulResponse resp) {

        Object target = req.getTarget();
        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        String keyString = arrival.getPath();
        Serializable key = repo.parseKey(keyString);

        repo.deleteByKey(key);

        return repo;
    }

}
