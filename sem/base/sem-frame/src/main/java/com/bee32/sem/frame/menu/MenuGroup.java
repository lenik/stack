package com.bee32.sem.frame.menu;

import com.bee32.plover.arch.Component;

public class MenuGroup
        extends Component {

    private int order;

    public MenuGroup(String name, int order) {
        super(name);
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
