package com.bee32.sem.process;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.process.verify.builtin.PassToNextPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.SingleVerifierRankedPolicy;
import com.bee32.sem.process.verify.preference.VerifyPolicyPref;

/**
 * 企业过程模块
 *
 * <p lang="en">
 */
@Oid({ 3, 15, SEMOids.Process, SEMOids.process.Process })
public class SEMProcessModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/2/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(VerifyPolicyPref.class, "pref");
        declareEntityPages(SingleVerifierPolicy.class, "v1");
        declareEntityPages(SingleVerifierRankedPolicy.class, "v1x");
        declareEntityPages(PassToNextPolicy.class, "p2next");
        // export(VerifyPolicyPrefDao.class);
    }

}
