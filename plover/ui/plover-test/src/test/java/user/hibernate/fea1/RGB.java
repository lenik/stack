package user.hibernate.fea1;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.BatchSize;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
@DiscriminatorValue("RGB")
@BatchSize(size = 5)
public class RGB
        extends Color {

    private static final long serialVersionUID = 1L;

    protected int red;
    protected int green;
    protected int blue;

    public RGB() {
        super();
    }

    public RGB(String name) {
        super(name);
    }

    public RGB(String name, java.awt.Color color) {
        if (color == null)
            throw new NullPointerException("color");
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public RGB(String name, int red, int green, int blue) {
        super(name);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Column(name = "x")
    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    @Column(name = "y")
    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    @Column(name = "z")
    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 1;
        result = prime * result + blue;
        result = prime * result + green;
        result = prime * result + red;
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<String> otherEntity) {
        RGB other = (RGB) otherEntity;
        if (blue != other.blue)
            return false;
        if (green != other.green)
            return false;
        if (red != other.red)
            return false;
        return true;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        String hex = String.format("%s (%02x%02x%02x)", name, red, green, blue);
        out.print(hex);
    }

}
