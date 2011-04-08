package user.hibernate.fea1;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
@DiscriminatorValue("CMYK")
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

    @Column(name = "x")
    public int getCyan() {
        return cyan;
    }

    public void setCyan(int cyan) {
        this.cyan = cyan;
    }

    @Column(name = "y")
    public int getMagenta() {
        return magenta;
    }

    public void setMagenta(int magenta) {
        this.magenta = magenta;
    }

    @Column(name = "z")
    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    @Column(name = "w")
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
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 1;
        result = prime * result + black;
        result = prime * result + cyan;
        result = prime * result + magenta;
        result = prime * result + yellow;
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<String> otherEntity) {
        CMYK other = (CMYK) otherEntity;
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
    public void toString(PrettyPrintStream out, FormatStyle format) {
        String hex = String.format("%s (%02x%02x%02x%02x)", name, cyan, magenta, yellow, black);
        out.print(hex);
    }

}
