package user.hibernate.fea2;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Green;

@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "friend_seq", allocationSize = 1)
public class Friend
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    IFruit fav;

    List<Fruit> fruits;

    public Friend() {
        super();
    }

    public Friend(String name) {
        super(name);
    }

    @Column(length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(targetEntity = Fruit.class)
    public IFruit getFav() {
        return fav;
    }

    public void setFav(IFruit fav) {
        this.fav = fav;
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
