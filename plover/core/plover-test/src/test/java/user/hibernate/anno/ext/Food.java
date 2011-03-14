package user.hibernate.anno.ext;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Food
        implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int id;

    @Version
    protected int version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @Access(AccessType.FIELD)
    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
