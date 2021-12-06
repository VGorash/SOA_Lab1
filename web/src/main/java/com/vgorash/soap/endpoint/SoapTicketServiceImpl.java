package com.vgorash.soap.endpoint;

import com.vgorash.common.interfaces.TicketService;
import com.vgorash.common.model.Ticket;
import com.vgorash.common.model.TicketListWrap;
import com.vgorash.common.util.TicketServiceException;
import com.vgorash.soap.faults.WrongParamsFault;
import com.vgorash.soap.util.Filter;

import javax.ejb.EJB;
import javax.jws.WebService;
import java.util.HashMap;

@WebService(endpointInterface = "com.vgorash.soap.endpoint.SoapTicketService")
public class SoapTicketServiceImpl implements SoapTicketService{

    @EJB
    private TicketService ticketService;

    @Override
    public Ticket getTicket(Long id) throws WrongParamsFault {
        try {
            return ticketService.getTicket(id);
        }
        catch (TicketServiceException e){
            throw new WrongParamsFault(e.getMessage(), e.getResponseCode());
        }
    }

    @Override
    public TicketListWrap getTickets(Filter filter) throws WrongParamsFault{
        try {
            if(filter == null) return ticketService.getTicketList(new HashMap<>());
            return ticketService.getTicketList(filter.toMap());
        }
        catch (TicketServiceException e){
            throw new WrongParamsFault(e.getMessage(), e.getResponseCode());
        }
        catch (Exception e){
            throw new WrongParamsFault(e.getMessage(), 500);
        }
    }

    @Override
    public Ticket modifyTicket(Long id, Ticket ticket) throws WrongParamsFault {
        try {
            return ticketService.modifyTicket(id, ticket);
        }
        catch (TicketServiceException e){
            throw new WrongParamsFault(e.getMessage(), e.getResponseCode());
        }
    }

    @Override
    public Ticket addTicket(Ticket ticket) throws WrongParamsFault {
        try {
            return ticketService.addTicket(ticket);
        }
        catch (TicketServiceException e){
            throw new WrongParamsFault(e.getMessage(), e.getResponseCode());
        }
    }

    @Override
    public void deleteTicket(Long id) throws WrongParamsFault {
        try {
            ticketService.deleteTicket(id);
        }
        catch (TicketServiceException e){
            throw new WrongParamsFault(e.getMessage(), e.getResponseCode());
        }
    }

    @Override
    public String getAveragePrice() {
        return ticketService.getAveragePrice().toString();
    }

    @Override
    public TicketListWrap getTicketsWithCommentsLike(String query) {
        return ticketService.getTicketListWithCommentsLike(query);
    }

    @Override
    public TicketListWrap getTicketsWithCommentsLower(String query) {
        return ticketService.getTicketListWithCommentsLower(query);
    }
}
