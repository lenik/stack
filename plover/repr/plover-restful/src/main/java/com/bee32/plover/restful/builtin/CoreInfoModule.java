package com.bee32.plover.restful.builtin;

import com.bee32.plover.arch.Module;
import com.bee32.plover.arch.credit.Credit;
import com.bee32.plover.arch.credit.CreditSubject;
import com.bee32.plover.arch.credit.builtin.Lenik;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;


@Oid({ 3, 12, PloverOids.Core })
public class CoreInfoModule
        extends Module {

    @Override
    protected void preamble() {
    }

    @Override
    public Credit getCredit() {
        return CREDIT;
    }

    public static final Credit CREDIT;
    static {
        CREDIT = new Credit();
        CREDIT.addContributor(CreditSubject.SystemArchitect, Lenik.getLenik());
    }

}
