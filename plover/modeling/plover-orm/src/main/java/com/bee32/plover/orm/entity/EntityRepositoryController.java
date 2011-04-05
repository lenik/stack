package com.bee32.plover.orm.entity;

import java.io.Serializable;

import javax.free.ParseException;

import com.bee32.plover.disp.util.IArrival;
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
    Object delete(IRestfulRequest req, IRestfulResponse resp)
            throws ParseException {

        EntityRepository<E, K> repo = (EntityRepository<E, K>) req.getTarget();

        IArrival arrival = req.getArrival();
        String path = arrival.getConsumedPath();

        String keyString = path;
        Serializable key = repo.parseKey(keyString);

        repo.deleteByKey(key);

        return repo;
    }

}
