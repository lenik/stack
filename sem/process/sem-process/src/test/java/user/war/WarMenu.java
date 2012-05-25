package user.war;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class WarMenu
        extends MenuComposite {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    static Location BASE = WEB_APP.join(WarModule.PREFIX_);

    MenuNode WAR = menu(_frame_.BIZ1, 0, "war");
    MenuNode attack = entry(WAR, 10, "attack", BASE.join("attack/index-rich.jsf"));
    MenuNode build = entry(WAR, 20, "build", BASE.join("build/index-rich.jsf"));

}
