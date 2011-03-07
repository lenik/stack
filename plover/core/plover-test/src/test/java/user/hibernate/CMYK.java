package user.hibernate;

public class CMYK
        extends Color {

    private static final long serialVersionUID = 1L;

    protected int cyan;
    protected int magenta;
    protected int yellow;
    protected int black;

    public CMYK() {
        super();
    }

    public CMYK(String name, int cyan, int magenta, int yellow, int black) {
        super(name);
        this.cyan = cyan;
        this.magenta = magenta;
        this.yellow = yellow;
        this.black = black;
    }

    public int getCyan() {
        return cyan;
    }

    public void setCyan(int cyan) {
        this.cyan = cyan;
    }

    public int getMagenta() {
        return magenta;
    }

    public void setMagenta(int magenta) {
        this.magenta = magenta;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + black;
        result = prime * result + cyan;
        result = prime * result + magenta;
        result = prime * result + yellow;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CMYK other = (CMYK) obj;
        if (black != other.black)
            return false;
        if (cyan != other.cyan)
            return false;
        if (magenta != other.magenta)
            return false;
        if (yellow != other.yellow)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s (%02x%02x%02x%02x)", name, cyan, magenta, yellow, black);
    }

}
