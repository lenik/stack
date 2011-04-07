package user.hibernate.fea2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import user.hibernate.fea2.ext.Banana;
import user.hibernate.fea2.ext.BananaDao;
import user.hibernate.fea2.ext.Food;
import user.hibernate.fea2.ext.FoodDao;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;
import com.bee32.plover.inject.spring.ContextConfiguration;

@Import(ScanTestxContext.class)
@ContextConfiguration("context.xml")
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

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            FeaturePlayer player = ApplicationContextBuilder.create(FeaturePlayer.class);

            player.run();

            System.out.println("Press enter to try again");
            stdin.readLine();
        }
    }

}