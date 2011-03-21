package user.hibernate.fea2;

import com.bee32.plover.orm.entity.IEntity;

public interface IFruit
        extends IEntity<Integer> {

    int getPrice();

    void setPrice(int price);

}
