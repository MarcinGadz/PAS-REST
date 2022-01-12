package com.example.mvc.util;

import com.example.mvc.model.Hall;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.Validator;
import javax.inject.Named;
import javax.validation.ConstraintViolation;

import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


@FacesValidator("com.example.mvc.util.SeatValidator")
public class SeatValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(!Objects.equals(o, Hall.A) && !Objects.equals(o, Hall.B) && !Objects.equals(o, Hall.C)) {
            FacesMessage msg = new FacesMessage("Must be A, B or C");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        };
//
    }
}
