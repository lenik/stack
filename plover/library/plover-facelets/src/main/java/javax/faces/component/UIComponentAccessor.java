package javax.faces.component;

import javax.faces.context.FacesContext;

public class UIComponentAccessor {

    public static FacesContext getFacesContext(UIComponent uiComponent) {
        return uiComponent.getFacesContext();
    }

    public static StateHelper getStateHelper(UIComponent uiComponent) {
        return uiComponent.getStateHelper();
    }

    public static Object forceRemoveAttribute(UIComponent uiComponent, String attributeName) {
        StateHelper stateHelper = getStateHelper(uiComponent);
        return stateHelper.remove(UIComponentBase.PropertyKeys.attributesMap, attributeName);
    }

}
