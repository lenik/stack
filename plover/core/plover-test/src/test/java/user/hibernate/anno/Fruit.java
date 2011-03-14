package user.hibernate.anno;

import javax.free.Nullables;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class Fruit
        extends EntityBean<Long> {

    private static final long serialVersionUID = 1L;

    String name;

    int price;

    @Transient
    String otherField;

// public String getName() {
// return name;
// }
//
// public void setName(String name) {
// this.name = name;
// }

    public int getPrice() {
        System.err.println("Get price called");
        return price;
    }

    public void setPrice(int price) {
        System.err.println("Set on price");
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
