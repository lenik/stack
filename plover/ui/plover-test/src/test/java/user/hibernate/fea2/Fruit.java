package user.hibernate.fea2;

import javax.persistence.Entity;

import user.hibernate.fea2.ext.Food;

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

}
