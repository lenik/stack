package com.bee32.sem.session;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.bee32.icsf.login.LoginException;
import com.bee32.icsf.login.LoginInfo;
import com.bee32.plover.ox1.principal.User;

/**
 * See also:
 * <ul>
 * <li><a href="http://goo.gl/fHCrT">JSF 2 Custom AJAX component.
 * </ul>
 */
@FacesComponent("com.bee32.sem.session.UILogin")
public class UILogin
        extends UIInput {

    @Override
    public String getFamily() {
        // TODO ... what the hell..
        return UIInput.COMPONENT_FAMILY;
    }

    static boolean _1;

    @Override
    public void encodeEnd(FacesContext context)
            throws IOException, LoginException {

        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("span", null);
        writer.writeAttribute("id", getClientId(context), "id");
        writer.writeAttribute("name", getClientId(context), "clientId"); // ???

        User currentUser = LoginInfo.getInstance().getInternalUser();
        writer.write("Logged in as " + currentUser.getName());

        if (_1) {
            ClientBehaviorContext cbc = ClientBehaviorContext.createClientBehaviorContext(context, this, "click",
                    getClientId(context), null);
            Map<String, List<ClientBehavior>> behaviors = getClientBehaviors();
            if (behaviors.containsKey("click")) {
                String click = behaviors.get("click").get(0).getScript(cbc);
                writer.writeAttribute("onclick", click, null);
            }
            writer.write("Click me!");
        }

        writer.endElement("span");
    }

}
