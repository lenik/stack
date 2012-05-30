package user.war;

import com.bee32.plover.arch.AppProfile;

public class WarProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(WarMenu.class, WarMenu.ENABLED, true);
    }

}
