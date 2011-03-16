package user.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.stereotype.Component;

@Component
public class MyScopes
        extends CustomScopeConfigurer {

    public MyScopes() {
        Map<String, Object> scopes = new HashMap<String, Object>();

        scopes.put("tick", new TickScope(1));

        this.setScopes(scopes);
    }

}
