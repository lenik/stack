package com.bee32.sem.frame.menu;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.ComponentBuilder;
import com.bee32.sem.frame.action.IAction;

public class MenuItem
        extends Component
        implements IMenuItem {

    private MenuSection section;
    private int order;

    private int flags;
    private String preferredStyleClass;
    private String preferredStyle;

    private IAction action;

    public MenuItem(String name) {
        this(name, null);
    }

    public MenuItem(String name, IAction action) {
        super(name);
        this.action = action;
    }

    @Override
    public MenuSection getSection() {
        return section;
    }

    @Override
    public void setSection(MenuSection section) {
        this.section = section;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public String getPreferredStyleClass() {
        return preferredStyleClass;
    }

    @Override
    public void setPreferredStyleClass(String preferredStyleClass) {
        this.preferredStyleClass = preferredStyleClass;
    }

    @Override
    public String getPreferredStyle() {
        return preferredStyle;
    }

    @Override
    public void setPreferredStyle(String preferredStyle) {
        this.preferredStyle = preferredStyle;
    }

    @Override
    public IAction getAction() {
        return action;
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    public MenuItem populate(IMenuItem other) {
        if (other == null)
            throw new NullPointerException("other");

        this.name = other.getName();
        this.section = other.getSection();
        this.order = other.getOrder();
        this.flags = other.getFlags();
        this.preferredStyleClass = other.getPreferredStyleClass();
        this.preferredStyle = other.getPreferredStyle();
        this.action = other.getAction();

        ComponentBuilder.setAppearance(this, other.getAppearance());

        return this;
    }

}
