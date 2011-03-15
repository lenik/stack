package user.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.AopContext;
import com.bee32.plover.inject.LegacyContext;

@Component
@Lazy
public class AopPlayer {

    public static void main(String[] args) {

        ApplicationContext context = new AopContext(new LegacyContext()).getApplicationContext();

        AopPlayer player = context.getBean(AopPlayer.class);
        player.main();
        player.run();
    }

    public void main() {
        run();
        System.out.println("DebugAdvice.counter = " + DebugAdvice.counter);
        // assertTrue(DebugAdvice.counter > 0);
    }

    public String run() {
        System.out.println("Run");
        return "hello";
    }

}
