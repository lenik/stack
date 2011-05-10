package user.hibernate.fea1;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.annotations.AccessType;
import org.hibernate.annotations.BatchSize;

import com.bee32.plover.orm.entity.EntityBase;
import com.bee32.plover.orm.ext.color.BlueEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@BatchSize(size = 5)
public class Color
        extends BlueEntity<String> {

    private static final long serialVersionUID = 1L;

    protected String code;

    public Color() {
    }

    public Color(String name) {
        super(name);
    }

    @AccessType("field")
    @Override
    public String getName() {
        return super.getName();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected Boolean naturalEquals(EntityBase<String> other) {
        Color o = (Color) other;
        if (code == null || o.code == null)
            return false;
        return code.equals(o.code);
    }

    @Override
    protected Integer naturalHashCode() {
        return code == null ? 0 : code.hashCode();
    }

}
