package com.vgorash.beans.beans;

import com.vgorash.beans.model.Event;
import com.vgorash.beans.model.Ticket;
import com.vgorash.beans.model.TicketListWrap;

import javax.ejb.Remote;
import java.util.Map;

@Remote
public interface JPAUtil {
    void saveTicket(Ticket ticket);
    void saveEvent(Event event);
    TicketListWrap getTickets(Map<String, String[]> params);
    Ticket getTicket(Long id);
    Event getEvent(Integer id);
    void deleteTicket(Ticket ticket);
    Double getAveragePrice();
    TicketListWrap getCommentsLike(String str);
    TicketListWrap getCommentsLower(String str);
}
