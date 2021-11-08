package com.vgorash.beans.beans;

import com.vgorash.beans.util.RequestStructure;

import javax.ejb.Local;

@Local
public interface TicketService {

    void getTicketList(RequestStructure requestStructure);
    void getTicket(RequestStructure requestStructure);
    void addTicket(RequestStructure requestStructure);
    void deleteTicket(RequestStructure requestStructure);
    void modifyTicket(RequestStructure requestStructure);
    void getTicketListWithCommentsLike(RequestStructure requestStructure);
    void getTicketListWithCommentsLower(RequestStructure requestStructure);
    void getAveragePrice(RequestStructure requestStructure);
}
