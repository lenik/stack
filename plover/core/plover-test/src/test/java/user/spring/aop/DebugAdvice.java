package user.spring.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;

//@Component
//@Aspect
public class DebugAdvice {

    @Pointcut("execution(* user..*Test.run())")
    void run() {
    }

    @AfterReturning(pointcut = "run()", returning = "retval")
    public void afterReturning(Object retval) {
        System.out.println("Run() returned: " + retval);
    }

}
