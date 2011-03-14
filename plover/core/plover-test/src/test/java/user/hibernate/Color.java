package user.hibernate;

import javax.free.Nullables;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Color
        extends EntityBean<String> {

    private static final long serialVersionUID = 1L;

    protected String code;

    public Color() {
    }

    public Color(String name) {
        super(name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    protected int hashCodeEntity() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    protected boolean equalsEntity(EntityBean<String> otherEntity) {
        Color o = (Color) otherEntity;

        if (!Nullables.equals(code, o.code))
            return false;

        return true;
    }

}
