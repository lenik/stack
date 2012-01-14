package user;

import java.io.Serializable;

/**
 * * This class provides a way of representing a color as an RGB triplet.
 *
 * @author "Neil Griffin"
 */
public class Color
        implements Serializable {

    private static final long serialVersionUID = -3810147232196826614L;

    private int red;
    private int green;
    private int blue;

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getRed() {
        return red;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setRed(int red) {
        this.red = red;
    }

}
