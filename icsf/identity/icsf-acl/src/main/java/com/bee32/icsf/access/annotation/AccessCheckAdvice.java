package com.bee32.icsf.access.annotation;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.access.alt.R_Authority;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ResourceRegistry;
import com.bee32.icsf.login.SessionUser;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDao;
import com.bee32.plover.javascript.JavascriptChunk;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.plover.servlet.util.ThreadServletContext;

/**
 * 权限控制aspect类,用spring aop来配置执行
 */
@Aspect
@Component
@Lazy
public class AccessCheckAdvice
        implements ILocationConstants {

    @Pointcut("execution(@com.bee32.icsf.access.annotation.AccessCheck * com.bee32..*(..))")
    void checkpoint() {
    }

    // private PageContext pageContext;

    @Inject
    private UserDao userDao;

    @Inject
    private R_Authority authority;

    @Before("checkpoint()")
    public void beforeCheckpoint(JoinPoint joinpoint)
            throws IOException {

        MethodSignature signature = (MethodSignature) joinpoint.getSignature();
        Method method = signature.getMethod();

        String simpleName = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String pointName = simpleName + "." + methodName;

        String resourceName = "ap:" + pointName;
        Resource apResource = ResourceRegistry.query(resourceName);

        if (apResource == null)
            return;

        User currentUser = SessionUser.getInstance().getInternalUserOpt();

        String errMessage = null;
        if (currentUser == null) {
            // 用户没有登录
            errMessage = "你没有登录";

        } else {
            User user = userDao.get(currentUser.getId());

            // 用户己登录，判断对当前方法有没有公限
            Permission permission = authority.getPermission(apResource, user);
            if (!permission.isExecutable()) {
                // 当前用户即不为admin,并且当前用户对当前facade中的方法没有权限
                String displayName = apResource.getAppearance().getDisplayName();
                errMessage = "你没有[" + displayName + "]的权限!";
            }
        }

        if (errMessage != null) {
            // XXX 能否通过抛出异常的方法，而不是控制 response?

            // HttpServletRequest request = ThreadServletContext.requireRequest();
            HttpServletResponse response = ThreadServletContext.getResponse();

            response.setCharacterEncoding("utf-8");

            JavascriptChunk alertChunk = new JavascriptChunk();
            alertChunk.println("alert('" + HtmlUtils.htmlEscape(errMessage) + "'); ");

            alertChunk.print("location='");
            alertChunk.print(WEB_APP.join("login.do"));
            alertChunk.println("'; ");

            alertChunk.dump(response);
        }

    }

}
