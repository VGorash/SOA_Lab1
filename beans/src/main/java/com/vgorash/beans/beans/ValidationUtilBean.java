package com.vgorash.beans.beans;

import com.vgorash.beans.model.*;

import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@Stateless
public class ValidationUtilBean implements ValidationUtil{

    private final Validator validator;

    public ValidationUtilBean(){
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public String validate(Ticket ticket){
        StringBuilder result = new StringBuilder();
        Set<ConstraintViolation<Ticket>> ticketViolations = validator.validate(ticket);
        for(ConstraintViolation<Ticket> cv : ticketViolations){
            result.append("Ticket ").append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append(";");
        }
        if(!Objects.isNull(ticket.getEvent())){
            Set<ConstraintViolation<Event>> eventViolations = validator.validate(ticket.getEvent());
            for(ConstraintViolation<Event> cv : eventViolations){
                result.append("Event ").append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append(";");
            }
        }
        if(!Objects.isNull(ticket.getCoordinates())){
            Set<ConstraintViolation<Coordinates>> coordViolations = validator.validate(ticket.getCoordinates());
            for(ConstraintViolation<Coordinates> cv : coordViolations){
                result.append("Coordinates ").append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append(";");
            }
        }
        return result.toString();
    }
}
