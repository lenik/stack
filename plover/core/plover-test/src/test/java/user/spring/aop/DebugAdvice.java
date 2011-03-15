package user.spring.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DebugAdvice {

    @Pointcut("execution(* user..AopPlayer.run())")
    void run() {
    }

    @AfterReturning(pointcut = "run()", returning = "retval")
    public void afterReturning(Object retval) {
        counter++;
        System.out.println("Run() returned: " + retval);
        System.out.println("Counter=" + counter);
    }

    public static int counter = 0;

}
