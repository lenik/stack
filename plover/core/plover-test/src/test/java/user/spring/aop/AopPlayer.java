package user.spring.aop;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class AopPlayer {

    public static void main(String[] args) {

        new AopContext().getBean(AopPlayer.class).run();

    }

    public String run() {
        System.out.println("Run");
        return "hello";
    }

}
