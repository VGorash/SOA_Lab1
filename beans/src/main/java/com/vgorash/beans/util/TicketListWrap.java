package com.vgorash.beans.util;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.vgorash.beans.model.Ticket;
import lombok.Data;

import java.util.List;

@Data
@XStreamAlias("ticketList")
public class TicketListWrap {

    public TicketListWrap(List<Ticket> tickets, int totalTickets){
        this.tickets = tickets;
        this.totalTickets = totalTickets;
    }

    public TicketListWrap(List<Ticket> tickets){
        this.tickets = tickets;
        this.totalTickets = tickets.size();
    }

    @XStreamImplicit
    private List<Ticket> tickets;

    private int totalTickets;

}
