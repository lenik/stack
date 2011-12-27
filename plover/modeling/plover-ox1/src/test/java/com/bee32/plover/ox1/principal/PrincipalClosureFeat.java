package com.bee32.plover.ox1.principal;

import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.ox1.PloverOx1Unit;
import com.bee32.plover.test.ICoordinator;

@Using(PloverOx1Unit.class)
public class PrincipalClosureFeat
        extends WiredDaoFeat<PrincipalClosureFeat> {

    Group g1 = new Group("group1");
    User u1 = new User("user1");
    Role r1 = new Role("role1");

    public void clean() {
        asFor(PrincipalResponsible.class).deleteAll();
        List<PrincipalResponsible> prList = asFor(PrincipalResponsible.class).list();
        for (PrincipalResponsible pr : prList)
            asFor(PrincipalResponsible.class).delete(pr);
        asFor(Principal.class).deleteAll();
    }

    public void create1() {
        asFor(Principal.class).saveOrUpdateAll(g1, u1, r1);
    }

    public void attach1() {
        r1.addResponsibleGroup(g1);
        g1.addMemberUser(u1);
        asFor(Principal.class).saveOrUpdateAll(r1, g1);
    }

    public void detachGroup1() {
        g1.removeMemberUser(u1);
        asFor(Principal.class).saveOrUpdateAll(g1);
    }

    @Transactional(readOnly = true)
    public void dumpClosure() {
        _dumpClosure(r1);
        _dumpClosure(g1);
        _dumpClosure(u1);
    }

    void _dumpClosure(Principal principal) {
        System.out.println("Closure of " + principal.getDisplayName());
        for (PrincipalResponsible pr : principal.getClosure()) {
            System.out.println("    " + pr.getResponsible().getDisplayName());
        }
    }

    public static void main(String[] args)
            throws IOException {
        new PrincipalClosureFeat().mainLoop(new ICoordinator<PrincipalClosureFeat>() {
            @Override
            public void main(PrincipalClosureFeat feat)
                    throws Exception {
                System.out.println("** Clean");
                feat.clean();

                System.out.println("** Create");
                feat.create1();
                feat.dumpClosure();

                System.out.println("** Attach group1, role1");
                feat.attach1();
                feat.dumpClosure();

                System.out.println("** Detach group1");
                feat.detachGroup1();
                feat.dumpClosure();
            }
        });
    }

}
