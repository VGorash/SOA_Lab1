package com.vgorash.web.beans;

import com.vgorash.web.util.RequestStructure;

import javax.ejb.Local;

@Local
public interface TicketService {

    void getTicketList(RequestStructure requestStructure);
    void getTicket(RequestStructure requestStructure);
    void addTicket(RequestStructure requestStructure);
    void deleteTicket(RequestStructure requestStructure);
    void modifyTicket(RequestStructure requestStructure);
}
