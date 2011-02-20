package com.bee32.plover.restful.builtin;

import com.bee32.plover.arch.Module;
import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.credit.Subject;
import com.bee32.plover.arch.credit.builtin.Lenik;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.CORE, 1 })
public class CoreInfoModule
        extends Module {

    @Override
    public Credit getCredit() {
        return CREDIT;
    }

    static Credit CREDIT;
    {
        CREDIT = new Credit();
        CREDIT.addContributor(Subject.SystemArchitect, Lenik.getLenik());
    }

}
