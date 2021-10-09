package com.vgorash.soa.util;

import com.vgorash.soa.model.Coordinates;
import com.vgorash.soa.model.Event;
import com.vgorash.soa.model.Ticket;

import javax.validation.*;
import java.util.Objects;
import java.util.Set;

public class ValidationUtil {

    private static Validator validator = setupValidator();

    private static Validator setupValidator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static String validate(Ticket ticket){
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
