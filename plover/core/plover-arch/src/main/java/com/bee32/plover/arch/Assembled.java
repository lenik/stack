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
                    scan();
                    preprocess();
                    wireUp();
                    postprocess();
                    assembled = true;
                }
            }
        }
    }

    /**
     * [1] Pre-assemble.
     *
     * Scan components to be assembled.
     */
    protected void scan() {
    }

    /**
     * [2] Pre-process.
     *
     * Add decorations to the components before wire them up.
     */
    protected void preprocess() {
    }

    /**
     * [3] Assembling.
     *
     * Wire up components.
     */
    protected void wireUp() {
    }

    /**
     * [4] Post-process.
     *
     * Add decorations to the components having been wired up.
     */
    protected void postprocess() {
    }

}
