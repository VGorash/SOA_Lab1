package com.vgorash.common.interfaces;

import com.vgorash.common.model.Ticket;
import com.vgorash.common.model.TicketListWrap;

import javax.ejb.Remote;
import java.util.Map;

@Remote
public interface TicketService {

    TicketListWrap getTicketList(Map<String, String[]> params);
    Ticket getTicket(Long id);
    Ticket addTicket(Ticket ticket);
    void deleteTicket(Long id);
    Ticket modifyTicket(Long id, Ticket ticket);
    TicketListWrap getTicketListWithCommentsLike(String str);
    TicketListWrap getTicketListWithCommentsLower(String str);
    Double getAveragePrice();
}
