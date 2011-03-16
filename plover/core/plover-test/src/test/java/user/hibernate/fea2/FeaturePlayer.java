package user.hibernate.fea2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class FeaturePlayer {

    public void run() {
        System.out.println("121");
    }

}