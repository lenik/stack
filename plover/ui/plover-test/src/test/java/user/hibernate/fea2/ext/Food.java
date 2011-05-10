package user.hibernate.fea2.ext;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.MapKey;
import org.hibernate.annotations.MapKeyManyToMany;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.GreenEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "stereo")
public class Food
        extends GreenEntity<Integer> {

    private static final long serialVersionUID = 1L;

    private int rank;
    private Map<Food, Double> ingredients;
    private SortedMap<String, Food> relatedFoods;

    public Food() {
        super();
    }

    public Food(String name) {
        super(name);
    }

    @AccessType("field")
    @Override
    public String getName() {
        return super.getName();
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    @CollectionOfElements
    @MapKeyManyToMany(joinColumns = @JoinColumn(name = "ingr"))
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
    @MapKey(columns = @Column(name = "relname"))
    @Sort(type = SortType.NATURAL)
    @Column(name = "relfood")
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
    protected Boolean naturalEquals(EntityBase<Integer> other) {
        Food o = (Food) other;

        if (name == null || o.name == null)
            return false;

        return name.equals(o.name);
    }

    @Override
    protected Integer naturalHashCode() {
        return name == null ? 0 : name.hashCode();
    }

}
