package user.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class BigBlob
        implements InitializingBean, IBigBlob {

    String contents;

    public BigBlob() {
        System.out.println("Creating BigBlob...");
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public void setContents(String contents) {
        System.out.println("Set contents to " + contents);
        this.contents = contents;
    }

    @Override
    protected void finalize()
            throws Throwable {
        System.out.println("Destroying BigBlob...");
    }

    @Override
    public String toString() {
        return "BigBlob [contents =" + contents + "]";
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {
        System.out.println("afterPropertiesSet: " + this);
        this.contents = "blob xyz";
    }

}
