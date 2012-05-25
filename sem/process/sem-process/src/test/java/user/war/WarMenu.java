package user.war;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class WarMenu
        extends MenuComposite {

    static Location BASE = WEB_APP.join(WarModule.PREFIX_);

    static MenuNode WAR = menu(SEMFrameMenu.BIZ1, 0, "war");
    static MenuNode attack = entry(WAR, 10, "attack", BASE.join("attack/index-rich.jsf"));
    static MenuNode build = entry(WAR, 20, "build", BASE.join("build/index-rich.jsf"));

}
