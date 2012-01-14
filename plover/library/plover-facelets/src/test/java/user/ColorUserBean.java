package user;

import com.bee32.plover.web.faces.view.ViewBean;

public class ColorUserBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    Color color = new Color();

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
