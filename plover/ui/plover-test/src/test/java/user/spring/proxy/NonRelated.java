package user.spring.proxy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class NonRelated {

    public NonRelated() {
        System.out.println("NonRelated Constructor");
    }

}
