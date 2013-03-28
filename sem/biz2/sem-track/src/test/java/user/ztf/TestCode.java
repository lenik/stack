package user.ztf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public class TestCode {

    public static void main(String[] args)
            throws Exception {

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClazz = pool.get("com.bee32.sem.track.random.TestBean");

        CtField field = new CtField(pool.get("java.lang.String"), "test", ctClazz);
        ctClazz.addField(field);

        CtClass[] paramTypes = { pool.get("java.lang.String") };
        CtMethod ctMethod = new CtMethod(pool.get("java.lang.String"), "getTest", paramTypes, ctClazz);
        ctMethod.setBody("{ return \" the parameter: \" + $1 ;}");
        ctClazz.addMethod(ctMethod);

        Class<TestBean> clazz = ctClazz.toClass();

        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            System.out.println(m.getName());
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getType());
        }

        TestBean bean = clazz.newInstance();
        Method method = clazz.getMethod("getTest", new Class[] { String.class });
        String invoke = (String) method.invoke(bean, new Object[] { "parameter" });
        System.out.println(invoke);

    }

}
