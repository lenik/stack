package user.war;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class WarMenu
        extends MenuContribution {

    static Location BASE = WEB_APP.join(WarModule.PREFIX_);

    static MenuNode attack = entry(SEMFrameMenu.MAIN, "attack", BASE.join("attack/index.do"));

    @Override
    protected void preamble() {
    }

}
