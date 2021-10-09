package com.vgorash.soa.service;

import com.vgorash.soa.model.*;
import com.vgorash.soa.util.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TicketService {

    private Ticket getTicketFromDB(RequestStructure requestStructure){
        if(Objects.isNull(requestStructure.getId())){
            requestStructure.setResponseCode(400);
            requestStructure.setMessage("id is incorrect");
            return null;
        }
        Ticket ticket = JPAUtil.getTicket(requestStructure.getId());
        if(Objects.isNull(ticket)){
            requestStructure.setResponseCode(404);
            requestStructure.setMessage("object not found") ;
            return null;
        }
        return ticket;
    }

    private Ticket getTicketFromRequest(RequestStructure requestStructure){
        if(Objects.isNull(requestStructure.getRequestBody()) || requestStructure.getRequestBody().length() == 0){
            requestStructure.setResponseCode(400);
            requestStructure.setMessage("empty body");
            return null;
        }
        Ticket ticket = XStreamUtil.fromXML(requestStructure);
        if(Objects.isNull(ticket)){
            requestStructure.setResponseCode(400);
            return null;
        }
        return ticket;
    }

    public void getTicketList(RequestStructure requestStructure){
        TicketListWrap tickets = JPAUtil.getTickets(requestStructure);
        if(Objects.isNull(tickets)){
            return;
        }
        requestStructure.setMessage(XStreamUtil.toXML(tickets));
        requestStructure.setResponseCode(200);
    }

    public void getTicket(RequestStructure requestStructure){
        Ticket ticket = getTicketFromDB(requestStructure);
        if(Objects.isNull(ticket)){
            return;
        }
        requestStructure.setMessage(XStreamUtil.toXML(ticket));
        requestStructure.setResponseCode(200);
    }

    public void addTicket(RequestStructure requestStructure){
        Ticket ticket = getTicketFromRequest(requestStructure);
        if(Objects.isNull(ticket)){
            return;
        }
        ticket.setCreationDate(Date.from(Instant.now()));
        ticket.setId(null);
        String validationErrors = ValidationUtil.validate(ticket);
        if(validationErrors.length() != 0){
            requestStructure.setResponseCode(400);
            requestStructure.setMessage(validationErrors);
            return;
        }
        if(!Objects.isNull(ticket.getEvent())){
            JPAUtil.saveEvent(ticket.getEvent());
        }
        JPAUtil.saveTicket(ticket);
        requestStructure.setMessage(XStreamUtil.toXML(ticket));
        requestStructure.setResponseCode(200);
    }

    public void deleteTicket(RequestStructure requestStructure){
        Ticket ticket = getTicketFromDB(requestStructure);
        if(Objects.isNull(ticket)){
            return;
        }
        JPAUtil.deleteTicket(ticket);
        requestStructure.setMessage("");
        requestStructure.setResponseCode(200);
    }

    public void modifyTicket(RequestStructure requestStructure){
        Ticket oldTicket = getTicketFromDB(requestStructure);
        if(Objects.isNull(oldTicket)){
            return;
        }
        Ticket newTicket = getTicketFromRequest(requestStructure);
        if(Objects.isNull(newTicket)){
            return;
        }
        newTicket.setCreationDate(Date.from(Instant.now()));
        String validationErrors = ValidationUtil.validate(newTicket);
        if(validationErrors.length() != 0){
            requestStructure.setResponseCode(400);
            requestStructure.setMessage(validationErrors);
            return;
        }
        if(!Objects.isNull(newTicket.getEvent())){
            Event newEvent = newTicket.getEvent();
            if(Objects.isNull(newEvent.getId())){
                JPAUtil.saveEvent(newEvent);
            }
            else{
                Event oldEvent = JPAUtil.getEvent(newEvent.getId());
                if(Objects.isNull(oldEvent)){
                    requestStructure.setResponseCode(404);
                    requestStructure.setMessage("event not found");
                    return;
                }
                oldEvent.copy(newEvent);
                JPAUtil.saveEvent(oldEvent);
            }
        }
        oldTicket.copy(newTicket);
        JPAUtil.saveTicket(oldTicket);
        requestStructure.setMessage(XStreamUtil.toXML(oldTicket));
        requestStructure.setResponseCode(200);
    }
}
