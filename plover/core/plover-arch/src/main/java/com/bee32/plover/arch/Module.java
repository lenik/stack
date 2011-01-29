package com.bee32.plover.arch;

import com.bee32.plover.arch.credit.Credit;

public class Module
        extends Component
        implements IModule {

    private Credit credit;

    public Module() {
        super();
    }

    public Module(String name) {
        super(name);

    }

    @Override
    public Credit getCredit() {
        return credit;
    }

    /**
     * Make it final if you don't want it be modified by any future derivations.
     */
    protected void setCredit(Credit credit) {
        this.credit = credit;
    }

    @Override
    public String getCopyright() {
        return null;
    }

}
