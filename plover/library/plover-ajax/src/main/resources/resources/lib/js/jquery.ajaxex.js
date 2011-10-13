/**
 * 允许 AJAX 请求返回外层控制信息。
 *
 * 增加/修改了如下 ajax 调用参数 （通过 settings）：
 * <ul>
 * <li> error(XMLHttpRequest, textStatus, errorThrown)
 * <li> success(data, textStatus, XMLHttpRequest)
 * </ul>
 *
 * 为了防止恶意代码注入，在返回的控制信息中必须附加验证码。目前为了简化起见， 只使用了随机数，并用下列方式实现密钥分享，在真正应用中这是不安全的。
 * 将来改變这一算法應不影響程序結構。
 *
 * <ol>
 * <li> Parent生成隨機數 secret，计算 E = 9*secret^2 + 102*secret + 57，发送 E 给远程.
 * <li> 远程计算 secret = (SQRT(E - 232) - 17) / 3
 * <li> 远程计算 HMAC= hmac(secret, message)，作为验证码
 * <li> Parent 收到 message，重新计算 HMAC 并核对，如果 HMAC 错误则丢弃控制信息。
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
 *
 */
(function($) {

    var _s2v = function(s) {
        var v = Array(//
        parseInt(s.substr(0, 8), 16), //
        parseInt(s.substr(8, 8), 16), //
        parseInt(s.substr(16, 8), 16), //
        parseInt(s.substr(24, 8), 16));
        return v;
    };

    var _fromhex = function(hex) {
        var bin = '';
        for ( var i = 0; i < hex.length; i += 2) {
            var byt = parseInt(hex.substr(i, 2), 16);
            bin += String.fromCharCode(byt);
        }
        return bin;
    };

    var hmac = function(secret, m) {
        var k = $.sha1('' + secret);
        var opad = 0x50;
        var ipad = 0x36;
        var ko = '', ki = '';
        for ( var i = 0; i < k.length; i += 2) {
            var byt = parseInt(k.substr(i, 2), 16);
            ko += String.fromCharCode(byt ^ opad);
            ki += String.fromCharCode(byt ^ ipad);
        }
        var m_utf8 = $.utf8Encode(m);
        var inner = $.sha1('' + ki + m_utf8);
        inner = _fromhex(inner);
        var outer = $.sha1('' + ko + inner);
        return outer;
    };

    var sessionSecret = "**unauthorized**";
    var xpcEnabled = true;

    var xpcHandler = function(xhr) {
        var xpcVer = xhr.getResponseHeader("X-Parent-Control");
        if (xpcVer == null || !xpcEnabled)
            return;

        // IE Fix:
        if (xpcVer == "")
            return;

        if (xpcVer != "1.0")
            throw "Unsupported X-Parent-Control version: " + xpcVer;
        var xpcAuth = xhr.getResponseHeader("X-Parent-Auth");
        if (xpcAuth == null)
            throw "Unauthorized XPC Command";

        var xpcInvoke = xhr.getResponseHeader("X-Parent-Invoke");
        var xpcLoc = xhr.getResponseHeader("X-Parent-Location");

        var xpcPayload = "";
        if (xpcInvoke != null)
		xpcPayload += "I:" + xpcInvoke.length + ":" + xpcInvoke;
        if (xpcLoc != null)
		xpcPayload += "L:" + xpcLoc.length + ":" + xpcLoc;

        var expectedAuth = hmac(sessionSecret, xpcPayload);
        if (expectedAuth != xpcAuth)
            throw "Invalid XPC auth code";

        if (xpcInvoke != null) {
		xpcInvoke = decodeURIComponent(xpcInvoke);
            eval(xpcInvoke); // specify javascript lang?
        }

        if (xpcLoc != null) {
		xpcLoc = decodeURIComponent(xpcLoc);
            location.href = xpcLoc;
        }
    };

    var hooked = true;
    if (!hooked) {
        $(document).ajaxComplete(function(e, x, s) {
            xpcHandler(x);
        });
    } else {
        var __ajax = $.ajax;
        $.ajax = function(__s) {
            var s = {};
            $.extend(s, __s, {
                __complete: __s.complete,
                complete : function(xhr, ts) {
                    xpcHandler(xhr);
                    if (this.__complete != null)
                        this.__complete(xhr, ts);
                }
            });
            return __ajax(s);
        };
    }
})(jQuery);