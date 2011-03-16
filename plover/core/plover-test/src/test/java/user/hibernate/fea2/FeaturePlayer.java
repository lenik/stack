package user.hibernate.fea2;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.BananaDao;
import user.hibernate.fea2.ext.Food;
import user.hibernate.fea2.ext.FoodDao;

@Scope("prototype")
@Component
@Transactional
public class FeaturePlayer {

    @Inject
    FoodDao foodDao;

    @Inject
    FruitDao fruitDao;

    @Inject
    BananaDao bananaDao;

    public void run() {
        prepare();
        listFood();
    }

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
    }

    @Transactional
    public void listFood() {
        for (Food fruit : foodDao.list())
            System.out.println("Food: " + fruit);
    }

}