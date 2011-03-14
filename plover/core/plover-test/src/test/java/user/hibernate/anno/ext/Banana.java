package user.hibernate.anno.ext;

import javax.persistence.Entity;

import user.hibernate.anno.Fruit;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityFormat;
import com.bee32.plover.util.PrettyPrintStream;

@Entity
public class Banana
        extends Fruit {

    private static final long serialVersionUID = 1L;

    int length;

    public Banana() {
        setName("banana");
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
    protected boolean equalsEntity(EntityBean<Long> other) {
        Banana o = (Banana) other;
        return length == o.length;
    }

    @Override
    public void toString(PrettyPrintStream out, EntityFormat format) {
        out.print(" length=" + length);
    }

}
