package com.bee32.plover.arch;

public abstract class Assembled
        extends Component {

    private boolean assembled;

    public Assembled() {
        super();
    }

    public Assembled(String name, boolean autoName) {
        super(name, autoName);
    }

    public Assembled(String name) {
        super(name);
    }

    public void assembleOnce() {
        if (!assembled) {
            synchronized (this) {
                if (!assembled) {
                    __assemble();
                    assemble();
                    postAssemble();
                    assembled = true;
                }
            }
        }
    }

    protected void __assemble() {
    }

    protected void assemble() {
    }

    protected void postAssemble() {
    }

}
