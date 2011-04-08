package user.hibernate.fea2;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.BananaDao;
import user.hibernate.fea2.ext.Food;
import user.hibernate.fea2.ext.FoodDao;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.test.FeaturePlayer;

@Import(WiredDaoTestCase.class)
@Using(Fea2Unit.class)
@Scope("prototype")
@Transactional
@Service
public class PlayFood
        extends FeaturePlayer<PlayFood> {

    @Inject
    FoodDao foodDao;

    @Inject
    FruitDao fruitDao;

    @Inject
    BananaDao bananaDao;

    @Transactional
    public void prepare() {
        Fruit apple = new Fruit("apple");
        apple.setPrice(10);
        fruitDao.save(apple);

        Banana banana = new Banana("english", 12);
        bananaDao.save(banana);

        Food aba = new Food("aba");
        aba.addIngredient(apple, 301);
        aba.addIngredient(banana, 106);
        foodDao.save(aba);

        banana.addRelated("color", apple);
        foodDao.save(banana);

        foodDao.flush();
        foodDao.evict(apple);
        foodDao.evict(banana);
        foodDao.evict(aba);
    }

    @Transactional
    public void listFood() {
        for (Food fruit : foodDao.list())
            System.out.println("Food: " + fruit);
    }

    @Override
    protected void main(PlayFood player)
            throws Exception {
        player.prepare();
        player.listFood();
    }

    public static void main(String[] args)
            throws Exception {
        new PlayFood().mainLoop();
    }

}