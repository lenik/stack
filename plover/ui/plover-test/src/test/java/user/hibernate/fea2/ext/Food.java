package user.hibernate.fea2.ext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.bee32.plover.arch.util.Identity;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.ox1.color.Green;

@Entity
@Green
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "stereo")
@SequenceGenerator(name = "idgen", sequenceName = "food_seq", allocationSize = 1)
public class Food
        extends EntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    int rank;
    Map<Food, Double> ingredients;
    SortedMap<String, Food> relatedFoods;

    public Food() {
        super();
    }

    public Food(String name) {
        super(name);
    }

    @Column(length = 100)
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    @ElementCollection
    @MapKeyJoinColumn(name = "ingr")
    @Column(name = "percent")
    @Cascade(CascadeType.ALL)
    public Map<Food, Double> getIngredients() {
        if (ingredients == null)
            ingredients = new HashMap<Food, Double>();
        return ingredients;
    }

    public void setIngredients(Map<Food, Double> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Food food, double percent) {
        getIngredients().put(food, percent);
    }

    public void removeIngredient(Food food) {
        getIngredients().remove(food);
    }

    @OneToMany
    @MapKeyColumn(name = "relname")
    @Column(name = "relfood")
    @Sort(type = SortType.NATURAL)
    @Cascade(CascadeType.ALL)
    public SortedMap<String, Food> getRelatedFoods() {
        if (relatedFoods == null)
            relatedFoods = new TreeMap<String, Food>();
        return relatedFoods;
    }

    public void setRelatedFoods(SortedMap<String, Food> relatedFoods) {
        this.relatedFoods = relatedFoods;
    }

    public void addRelated(String relname, Food food) {
        getRelatedFoods().put(relname, food);
    }

    public void removeRelated(String relname) {
        getRelatedFoods().remove(relname);
    }

    @Override
    protected Serializable naturalId() {
        if (name == null)
            return new Identity(this);
        return name;
    }

    @Override
    protected CriteriaElement selector(String prefix) {
        if (name == null)
            throw new NullPointerException("name");
        return new Equals(prefix + "name", name);
    }

}
