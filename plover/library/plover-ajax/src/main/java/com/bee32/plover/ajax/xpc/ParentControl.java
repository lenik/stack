package com.bee32.plover.ajax.xpc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 父页面控制
 *
 * 允许 AJAX 请求返回外层控制信息。
 *
 * 增加/修改了如下 ajax 调用参数 （通过 settings）：
 * <ul>
 * <li>error(XMLHttpRequest, textStatus, errorThrown)
 * <li>success(data, textStatus, XMLHttpRequest)
 * </ul>
 *
 * 为了防止恶意代码注入，在返回的控制信息中必须附加验证码。目前为了简化起见， 只使用了随机数，并用下列方式实现密钥分享，在真正应用中这是不安全的。 将来改變这一算法應不影響程序結構。
 *
 * <ol>
 * <li>Parent生成隨機數 secret，计算 E = 9*secret^2 + 102*secret + 57，发送 E 给远程.
 * <li>远程计算 secret = (SQRT(E - 232) - 17) / 3
 * <li>远程计算 HMAC= hmac(secret, message)，作为验证码
 * <li>Parent 收到 message，重新计算 HMAC 并核对，如果 HMAC 错误则丢弃控制信息。
 * </ol>
 *
 * HMAC 使用函数：
 *
 * <pre>
 * k = sha1(string(secret))
 * hmac(k, m) = sha1((k + opad) || sha1((k + ipad) || message))
 * </pre>
 *
 * 对应的 Javascript 函数为 <code>$.hmac(secret, m)</code>
 *
 * 依赖于：
 *
 * @see jQuery.sha1
 */
public class ParentControl {

    public static final String X_PARENT_CONTROL = "X-Parent-Control";
    public static final String X_PARENT_LOCATION = "X-Parent-Location";
    public static final String X_PARENT_INVOKE = "X-Parent-Invoke";
    public static final String X_PARENT_AUTH = "X-Parent-Auth";

    public static final String X_PARENT_CONTROL_UNAUTH = "**unauthorized**";

    public static void initParentControl(HttpServletResponse response) {
        response.setHeader(X_PARENT_CONTROL, "1.0");
    }

// static String _encodeBase64(String s) {
// byte[] utf8 = s.getBytes(HMAC.utf8);
// byte[] bin = Base64.encodeBase64(utf8);
// return new String(bin);
// }

    static String _encode(String s) {
        s = s.trim();
        try {
            s = URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        s = s.replace("+", " ");
        return s;
    }

    static String _hmac(String authSecret, String script, String location) {
        StringBuilder payload = new StringBuilder();
        if (script != null) {
            payload.append("I:");
            payload.append(script.length());
            payload.append(":");
            payload.append(script);
        }
        if (location != null) {
            payload.append("L:");
            payload.append(location.length());
            payload.append(":");
            payload.append(location);
        }
        return HMAC.hmac_utf8(authSecret, payload.toString());
    }

    public static void sendParentRedirect(HttpServletResponse response, String location) {
        sendParentRedirect(response, location, X_PARENT_CONTROL_UNAUTH);
    }

    public static void sendParentRedirect(HttpServletResponse response, String location, String authSecret) {
        String locationEncoded = _encode(location);

        initParentControl(response);
        response.setHeader(X_PARENT_LOCATION, locationEncoded);
        String hmac = _hmac(authSecret, null, locationEncoded);
        response.setHeader(X_PARENT_AUTH, hmac);
    }

    public static void sendParentInvocation(HttpServletResponse response, String script) {
        sendParentInvocation(response, script, X_PARENT_CONTROL_UNAUTH);
    }

    public static void sendParentInvocation(HttpServletResponse response, String script, String authSecret) {
        String scriptEncoded = _encode(script);

        initParentControl(response);
        response.setHeader(X_PARENT_INVOKE, scriptEncoded);
        String hmac = _hmac(authSecret, scriptEncoded, null);
        response.setHeader(X_PARENT_AUTH, hmac);
    }

    public static void sendParentInvocationAndRedirect(HttpServletResponse response, String script, String location) {
        sendParentInvocationAndRedirect(response, script, location, X_PARENT_CONTROL_UNAUTH);
    }

    public static void sendParentInvocationAndRedirect(HttpServletResponse response, String script, String location,
            String authSecret) {
        String scriptEncoded = _encode(script);
        String locationEncoded = _encode(location);

        initParentControl(response);
        response.setHeader(X_PARENT_INVOKE, scriptEncoded);
        response.setHeader(X_PARENT_LOCATION, locationEncoded);
        String hmac = _hmac(authSecret, scriptEncoded, locationEncoded);
        response.setHeader(X_PARENT_AUTH, hmac);
    }

}
