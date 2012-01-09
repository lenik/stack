package user.war;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class WarMenu
        extends MenuContribution {

    static Location BASE = WEB_APP.join(WarModule.PREFIX_);

    static MenuNode WAR = menu(SEMFrameMenu.MAIN, "war");
    static MenuNode attack = entry(WAR, "attack", BASE.join("attack/index.do"));
    static MenuNode build = entry(WAR, "build", BASE.join("build/index.do"));

    @Override
    protected void preamble() {
    }

}
