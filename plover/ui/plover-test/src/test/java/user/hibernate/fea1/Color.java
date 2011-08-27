package user.hibernate.fea1;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.BatchSize;

import com.bee32.plover.ox1.dict.ShortNameDict;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@BatchSize(size = 5)
public class Color
        extends ShortNameDict {

    private static final long serialVersionUID = 1L;

    public Color() {
        super();
    }

    public Color(String name) {
        super(name, null);
    }

    public Color(String name, String label) {
        super(name, label);
    }

}
