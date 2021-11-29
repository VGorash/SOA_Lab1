package com.vgorash.beans.beans;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.vgorash.beans.interfaces.JPAUtil;
import com.vgorash.beans.interfaces.TicketService;
import com.vgorash.beans.interfaces.ValidationUtil;
import com.vgorash.beans.model.*;
import com.vgorash.beans.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TicketServiceBean implements TicketService {

    @EJB
    private ValidationUtil validationUtil;

    @EJB
    private JPAUtil jpaUtil;

    @Override
    public TicketListWrap getTicketList(Map<String, String[]> params){
        try {
            return jpaUtil.getTickets(params);
        }
        catch (QueryParamsException e){
            throw new TicketServiceException(e.getMessage(), 400);
        }
    }

    @Override
    public Ticket getTicket(Long id){
        if(Objects.isNull(id)){
            throw new TicketServiceException("id is incorrect", 400);
        }
        Ticket ticket = jpaUtil.getTicket(id);
        if(ticket == null){
            throw new TicketServiceException("ticket with id " + id + " is not found", 404);
        }
        return ticket;
    }

    @Override
    public Ticket addTicket(Ticket ticket){
        if(ticket == null){
            throw new TicketServiceException("empty request body", 400);
        }
        ticket.setCreationDate(Date.from(Instant.now()));
        ticket.setId(null);
        String validationErrors = validationUtil.validate(ticket);
        if(validationErrors.length() != 0){
            throw new TicketServiceException(validationErrors, 400);
        }
        if(!Objects.isNull(ticket.getEvent())){
            Event newEvent = ticket.getEvent();
            if(Objects.isNull(newEvent.getId())){
                jpaUtil.saveEvent(newEvent);
            }
        }
        jpaUtil.saveTicket(ticket);
        return ticket;
    }

    @Override
    public void deleteTicket(Long id){
        Ticket ticket = getTicket(id);
        jpaUtil.deleteTicket(ticket);
    }

    @Override
    public Ticket modifyTicket(Long id, Ticket newTicket){
        Ticket oldTicket = getTicket(id);
        if(newTicket == null){
            throw new TicketServiceException("empty request body", 400);
        }
        newTicket.setCreationDate(Date.from(Instant.now()));
        String validationErrors = validationUtil.validate(newTicket);
        if(validationErrors.length() != 0){
            throw new TicketServiceException(validationErrors, 400);
        }
        if(!Objects.isNull(newTicket.getEvent())){
            Event newEvent = newTicket.getEvent();
            if(Objects.isNull(newEvent.getId())){
                jpaUtil.saveEvent(newEvent);
            }
            else{
                Event oldEvent = jpaUtil.getEvent(newEvent.getId());
                if(Objects.isNull(oldEvent)){
                    throw new TicketServiceException("ticket with id " + newEvent.getId() + " is not found", 404);
                }
                oldEvent.copy(newEvent);
                jpaUtil.saveEvent(oldEvent);
            }
        }
        oldTicket.copy(newTicket);
        jpaUtil.saveTicket(oldTicket);
        return oldTicket;
    }

    @Override
    public TicketListWrap getTicketListWithCommentsLike(String str){
        return jpaUtil.getCommentsLike(str);
    }

    @Override
    public TicketListWrap getTicketListWithCommentsLower(String str) {
        return jpaUtil.getCommentsLower(str);
    }

    @Override
    public Double getAveragePrice() {
        return jpaUtil.getAveragePrice();
    }
}
