package user.hibernate.anno.ext;

import javax.persistence.Entity;

import user.hibernate.anno.Fruit;

@Entity
public class Banana
        extends Fruit {

    int length;

}
