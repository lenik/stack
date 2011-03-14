package user.hibernate.anno;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class Friend
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    List<Fruit> fruits;

    public Friend() {
        super();
    }

    public Friend(String name) {
        super(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Fruit.class)
    public List<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public synchronized void addFruit(Fruit fruit) {
        if (fruits == null)
            fruits = new ArrayList<Fruit>();
        fruits.add(fruit);
    }

}
