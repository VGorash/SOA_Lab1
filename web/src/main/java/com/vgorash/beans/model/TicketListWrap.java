package com.vgorash.beans.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Data
@XmlRootElement(name = "ticketList")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketListWrap implements Serializable {

    public TicketListWrap(){}

    public TicketListWrap(List<Ticket> tickets, int totalTickets){
        this.tickets = tickets;
        this.totalTickets = totalTickets;
    }

    public TicketListWrap(List<Ticket> tickets){
        this.tickets = tickets;
        this.totalTickets = tickets.size();
    }

    @XmlElement(name = "ticket")
    private List<Ticket> tickets;

    @XmlElement
    private int totalTickets;

}
