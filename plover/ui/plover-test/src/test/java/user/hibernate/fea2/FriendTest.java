package user.hibernate.fea2;

import org.junit.Assert;
import org.junit.Test;

public class FriendTest
        extends Assert {

    @Test
    public void testStringFormat() {
        Fruit apple = new Fruit("apple");
        Friend lily = new Friend("lily");
        lily.setFav(apple);
        System.out.println(lily);
    }

}
