package user.spring.aop;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanStdContext;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;

@Import(ScanStdContext.class)
@Component
@Lazy
public class AopPlayer {

    public static void main(String[] args) {
        AopPlayer player = ApplicationContextBuilder.create(AopPlayer.class);
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
