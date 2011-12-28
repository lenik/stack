package com.bee32.sem.mail.web;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.bee32.icsf.principal.UserDto;

@FacesConverter("user")
public class UserConverter
        implements Converter {

    UserDtoClass udc = new UserDtoClass();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
            throws ConverterException {
        if (value.trim().endsWith(""))
            return null;
        else {
            try {
                Integer userId = Integer.parseInt(value);
                for (UserDto ud : udc.getUserList()) {
                    if (ud.getId() == userId)
                        return ud;
                }

            } catch (Exception e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "CONVERTION ERROR",
                        "not a valid USER"));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
            throws ConverterException {
        if (value == null || value.equals(""))
            return "";
        else
            return String.valueOf(((UserDto) value).getId());
    }

}
