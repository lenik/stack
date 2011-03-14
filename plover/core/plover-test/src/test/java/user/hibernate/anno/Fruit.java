package user.hibernate.anno;

import javax.free.Nullables;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class Fruit
        extends EntityBean<Long>
        implements IFruit {

    private static final long serialVersionUID = 1L;

    String name;

    int price;

    @Transient
    String otherField;

    public Fruit() {
        super();
    }

    public Fruit(String name) {
        super();
        setName(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price / 2;
    }

    @Override
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((otherField == null) ? 0 : otherField.hashCode());
        result = prime * result + price;
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Long> other) {
        Fruit o = (Fruit) other;

        if (price != o.price)
            return false;

        if (!Nullables.equals(otherField, o.otherField))
            return false;

        return true;
    }

}
