package user.hibernate.fea2;

import javax.persistence.Entity;

import user.hibernate.fea2.ext.Food;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class Fruit
        extends Food
        implements IFruit {

    private static final long serialVersionUID = 1L;

    int price;

    public Fruit() {
        super();
    }

    public Fruit(String name) {
        super(name);
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 1;
        result = prime * result + price;
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<Integer> other) {
        Fruit o = (Fruit) other;

        if (price != o.price)
            return false;

        return true;
    }

}
