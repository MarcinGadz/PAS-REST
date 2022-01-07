package com.example.mvc.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.UUID;

@FacesConverter("com.example.mvc.util.UUIDConverter")
public class UUIDConverter implements Converter<UUID> {
    @Override
    public UUID getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return UUID.fromString(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, UUID uuid) {
        return uuid.toString();
    }
}
