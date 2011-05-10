package user.hibernate.fea2.ext;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import user.hibernate.fea2.Fruit;

@Entity
@DiscriminatorValue("BNNA")
public class Banana
        extends Fruit {

    private static final long serialVersionUID = 1L;

    int length;

    public Banana() {
        super("no-name");
    }

    public Banana(String name, int length) {
        super(name);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
