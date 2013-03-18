package user.hibernate.fea2;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.BananaDao;
import user.hibernate.fea2.ext.Food;
import user.hibernate.fea2.ext.FoodDao;

@Using(Fea2Unit.class)
public class FoodFeat
        extends WiredDaoFeat<FoodFeat> {

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

    public static void main(String[] args)
            throws Exception {
        new FoodFeat().mainLoop(new ICoordinator<FoodFeat>() {
            @Override
            public void main(FoodFeat feat)
                    throws Exception {
                feat.prepare();
                feat.listFood();
            }
        });
    }

}