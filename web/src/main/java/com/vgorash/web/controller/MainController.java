package com.vgorash.web.controller;

import com.vgorash.beans.beans.TicketService;
import com.vgorash.beans.model.Ticket;
import com.vgorash.beans.model.TicketListWrap;
import com.vgorash.beans.util.TicketServiceException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/tickets")
public class MainController {

    @EJB
    private TicketService ticketService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public TicketListWrap getTicketList(@Context UriInfo uriParams){
        Map<String, String[]> params = new HashMap<>();
        MultivaluedMap<String, String> mpAllQueParams = uriParams.getQueryParameters();
        for(String s : mpAllQueParams.keySet()){
            List<String> values = mpAllQueParams.get(s);
            String[] newValues = new String[values.size()];
            for(int i=0; i<values.size(); i++){
                newValues[i] = values.get(i);
            }
            params.put(s, newValues);
        }
        try {
            return ticketService.getTicketList(params);
        }
        catch (TicketServiceException e){
            throw e.toWebApplicationException();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Ticket getTicket(@PathParam("id") Long id){
        try {
            return ticketService.getTicket(id);
        }
        catch (TicketServiceException e){
            throw e.toWebApplicationException();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Ticket modifyTicket(@PathParam("id") Long id, Ticket ticket){
        try {
            return ticketService.modifyTicket(id, ticket);
        }
        catch (TicketServiceException e){
            throw e.toWebApplicationException();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Ticket addTicket(Ticket ticket){
        try {
            return ticketService.addTicket(ticket);
        }
        catch (TicketServiceException e){
            throw e.toWebApplicationException();
        }
    }

    @DELETE
    @Path("/{id}")
    public void deleteTicket(@PathParam("id") Long id){
        try {
            ticketService.deleteTicket(id);
        }
        catch (TicketServiceException e){
            throw e.toWebApplicationException();
        }
    }

    @GET
    @Path("/avgprice")
    @Produces(MediaType.APPLICATION_XML)
    public String getAveragePrice(){
        return "<averagePrice>" + ticketService.getAveragePrice() + "</averagePrice>";
    }

    @GET
    @Path("/withcommentslike")
    @Produces(MediaType.APPLICATION_XML)
    public TicketListWrap getTicketsWithCommentsLike(@QueryParam(value = "string") String str){
        if(Objects.isNull(str)){
            throw new BadRequestException("missed required parameter 'string");
        }
        return ticketService.getTicketListWithCommentsLike(str);
    }

    @GET
    @Path("/withcommentslower")
    @Produces(MediaType.APPLICATION_XML)
    public TicketListWrap getTicketsWithCommentsLower(@QueryParam(value = "string") String str){
        if(Objects.isNull(str)){
            throw new BadRequestException("missed required parameter 'string");
        }
        return ticketService.getTicketListWithCommentsLower(str);
    }
}
