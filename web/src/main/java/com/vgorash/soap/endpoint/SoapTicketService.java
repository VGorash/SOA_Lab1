package com.vgorash.soap.endpoint;

import com.vgorash.common.model.Ticket;
import com.vgorash.common.model.TicketListWrap;
import com.vgorash.soap.faults.WrongParamsFault;
import com.vgorash.soap.util.Filter;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "SoapTicketService")
public interface SoapTicketService {

    @WebMethod
    TicketListWrap getTickets(Filter filter) throws WrongParamsFault;

    @WebMethod
    Ticket getTicket(Long id) throws WrongParamsFault;

    @WebMethod
    Ticket modifyTicket(Long id, Ticket ticket) throws WrongParamsFault;

    @WebMethod
    Ticket addTicket(Ticket ticket) throws WrongParamsFault;

    @WebMethod
    void deleteTicket(Long id) throws WrongParamsFault;

    @WebMethod
    String getAveragePrice();

    @WebMethod
    TicketListWrap getTicketsWithCommentsLike(String query);

    @WebMethod
    TicketListWrap getTicketsWithCommentsLower(String query);
}
