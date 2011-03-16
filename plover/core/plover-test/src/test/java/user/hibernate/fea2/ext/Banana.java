package user.hibernate.fea2.ext;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import user.hibernate.fea2.Fruit;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
@DiscriminatorValue("BNNA")
public class Banana
        extends Fruit {

    private static final long serialVersionUID = 1L;

    int length;

    public Banana() {
        super("no-name");
    }

    public Banana(String name, int length) {
        super(name);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    protected int hashCodeEntity() {
        return length * 31;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Integer> other) {
        Banana o = (Banana) other;
        return length == o.length;
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        super.toString(out, format);
        out.print(" length=" + length);
    }

}
