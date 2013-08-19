package user.war;

import com.bee32.plover.arch.AppProfile;

/**
 * 战争管理
 *
 * <p lang="en">
 * War Management
 */
public class WarProfile
        extends AppProfile {

    @Override
    protected void preamble() {
        setParameter(WarMenu.class, WarMenu.ENABLED, true);
    }

}
