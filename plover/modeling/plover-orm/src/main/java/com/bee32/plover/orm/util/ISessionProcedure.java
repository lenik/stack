package com.bee32.plover.orm.util;

import org.hibernate.Session;

public interface ISessionProcedure {

    Object run(Session session)
            throws Exception;

}
