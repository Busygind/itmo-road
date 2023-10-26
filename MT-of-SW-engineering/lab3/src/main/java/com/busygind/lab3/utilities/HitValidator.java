package com.busygind.lab3.utilities;

import com.busygind.lab3.beans.HitBean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class HitValidator implements Validator {

    public boolean hitIsValid(HitBean hit) {
        try {
            double x = hit.getX();
            double y = hit.getY();
            double r = hit.getR();
            if (x <= 4 && x >= -4 && y <= 3 && y >= -5 && r <= 3 && r >= 1) {
                return true;
            }
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return false;
    }

    // задел на расширение, пока useless
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

    }
}
