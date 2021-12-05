package com.vgorash.common.interfaces;

import com.vgorash.common.model.Event;
import com.vgorash.common.model.Ticket;
import com.vgorash.common.model.TicketListWrap;

import javax.ejb.Local;
import java.util.Map;

@Local
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
