package user.spring;

import org.springframework.stereotype.Component;

@Component
public class BigBlob {

    String name;

    public BigBlob() {
        System.out.println("Creating BigBlob...");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("Set name to " + name);
        this.name = name;
    }

    @Override
    protected void finalize()
            throws Throwable {
        System.out.println("Destroying BigBlob...");
    }

    @Override
    public String toString() {
        return "BigBlob [name=" + name + "]";
    }

}
