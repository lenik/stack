package com.bee32.plover.orm.dao;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.site.scope.PerSite;

@ComponentTemplate
@PerSite
public abstract class GenericDao
        extends HibernateDaoSupport {

}
