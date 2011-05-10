package user.hibernate.fea1;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.BatchSize;

import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
@DiscriminatorValue("ARGB")
@BatchSize(size = 10)
public class ARGB
        extends RGB {

    private static final long serialVersionUID = 1L;

    protected int alpha;

    public ARGB() {
        super();
    }

    public ARGB(String name) {
        super(name);
    }

    public ARGB(String name, int red, int green, int blue, int alpha) {
        super(name, red, green, blue);
        this.alpha = alpha;
    }

    @Column(name = "w")
    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        super.toString(out, format);
        out.print(" alpha=" + alpha);
    }

}
