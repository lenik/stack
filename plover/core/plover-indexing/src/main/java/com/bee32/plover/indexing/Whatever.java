package com.bee32.plover.indexing;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

class ModelRequest {
}

class ModelResponse {
}

interface Dispatcher {
    // req = Negotiation
    Object dispatch(Object context, String path);
}

interface IRenderer {
    void render(Object obj, IDisplay display);
}

interface IDisplay {

}

interface IHtmlDisplay
        extends IDisplay {
}

class UserApp
        extends HibernateDaoSupport {

    private int userId;

    void listToVerifyActivities_OneMan() {
        // Activity is-a Indexable
        // ActivityIndexInfo

        DetachedCriteria criteria = DetachedCriteria.forClass(Activity.class);
        criteria.add(Restrictions.like("post", "man=" + userId));
        criteria.getExecutableCriteria(getSession()).list();
    }

}

class Activity {

    void verify(String oneMan) {
        setState(VERIFIED, oneMan, message);
    }

}

// Nothing related to the message...
class VInfo {

    boolean state;

//    (state 1)
//    (step jerry_tom 8432847 Some_messages_left_for_checking)
//    (step Some_one_Else 8432847 \LMore\R_messages)
//     The most recent month.

}