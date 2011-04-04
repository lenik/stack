package com.bee32.plover.orm.entity;

import java.io.Serializable;

import com.bee32.plover.arch.util.IStruct;
import com.bee32.plover.arch.util.MapStruct;
import com.bee32.plover.disp.IArrival;
import com.bee32.plover.restful.IRestfulRequest;
import com.bee32.plover.restful.IRestfulResponse;

public class EntityRepositoryController {

    public Object create(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {

        IEntityRepository<IEntity<?>, ?> repo = (EntityRepository<IEntity<?>, ?>) req.getTarget();

        IStruct struct = new MapStruct(req.getParameterMap());
        IEntity<?> entity = repo.populate(struct);

        repo.saveOrUpdate(entity);

        return entity;
    }

    public Object update(IRestfulRequest req, IRestfulResponse resp)
            throws Exception {

        Object target = req.getTarget();
        EntityRepository<IEntity<?>, ?> repo = (EntityRepository<IEntity<?>, ?>) target;

        IArrival arrival = req.getArrival();

        String keyString = req.getDispatchPath();
        Serializable key = repo.parseKey(keyString);

        IStruct struct = new MapStruct(req.getParameterMap());
        IEntity<?> entity = repo.populate(struct);

        repo.update(entity);

        return entity;
    }

    public Object delete(IRestfulRequest req, IRestfulResponse resp) {

        Object target = req.getTarget();
        EntityRepository<IEntity<?>, ?> repo = (EntityRepository<IEntity<?>, ?>) target;

        String keyString = context.getPath();
        Serializable key = repo.parseKey(keyString);

        repo.deleteByKey(key);

        return repo;
    }

}
