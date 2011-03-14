package user.hibernate.anno;

import com.bee32.plover.orm.entity.IEntity;

public interface IFruit
        extends IEntity<Long> {

    String getName();

    void setName(String name);

    int getPrice();

    void setPrice(int price);

}
