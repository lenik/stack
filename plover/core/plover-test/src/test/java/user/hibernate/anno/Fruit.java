package user.hibernate.anno;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import user.hibernate.anno.ext.Food;

@Entity
public class Fruit
        extends Food {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.FIELD)
    int id;

    @Version
    int version;

    String name;

    @Access(AccessType.PROPERTY)
    int price;

    @Transient
    String otherField;

    public int getId() {
        return id;
    }

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getPrice() {
        System.err.println("Get price called");
        return price;
    }

    public void setPrice(int price) {
        System.err.println("Set on price");
        this.price = price / 2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((otherField == null) ? 0 : otherField.hashCode());
        result = prime * result + price;
        result = prime * result + version;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fruit other = (Fruit) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (otherField == null) {
            if (other.otherField != null)
                return false;
        } else if (!otherField.equals(other.otherField))
            return false;
        if (price != other.price)
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Fruit [id=" + id + ", version=" + version + ", name=" + name + ", price=" + price + ", otherField="
                + otherField + "]";
    }

}
