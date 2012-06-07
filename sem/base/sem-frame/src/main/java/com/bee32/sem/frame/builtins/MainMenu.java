package com.bee32.sem.frame.builtins;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.sem.frame.menu.SuperfishMenuBuilder;

@Component
@Lazy
public class MainMenu {

    static Logger logger = LoggerFactory.getLogger(MainMenu.class);

    SuperfishMenuBuilder superfishMenuBuilder;

    @PostConstruct
    public void post() {
        superfishMenuBuilder = new SuperfishMenuBuilder(null /* main-menu */, null);
    }

    public String getSuperfishHtml() {
        return superfishMenuBuilder.toString();
    }

}
