package com.vgorash.soa.controller;

import com.vgorash.soa.service.TicketService;
import com.vgorash.soa.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Path("/tickets")
public class MainController {

    private final TicketService ticketService;

    public MainController(){
        this.ticketService = new TicketService();
    }

    private Response processResponse(RequestStructure requestStructure){
        return Response.status(requestStructure.getResponseCode())
                .entity(requestStructure.getMessage()).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public Response getTicketList(@Context UriInfo uriParams){
        RequestStructure requestStructure = new RequestStructure();
        requestStructure.setParams(new HashMap<>());
        MultivaluedMap<String, String> mpAllQueParams = uriParams.getQueryParameters();
        for(String s : mpAllQueParams.keySet()){
            List<String> values = mpAllQueParams.get(s);
            String[] newValues = new String[values.size()];
            for(int i=0; i<values.size(); i++){
                newValues[i] = values.get(i);
            }
            requestStructure.getParams().put(s, newValues);
        }
        ticketService.getTicketList(requestStructure);
        return processResponse(requestStructure);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getTicket(@PathParam("id") Long id){
        RequestStructure requestStructure = new RequestStructure();
        requestStructure.setId(id);
        ticketService.getTicket(requestStructure);
        return processResponse(requestStructure);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response modifyTicket(@PathParam("id") Long id, String body){
        RequestStructure requestStructure = new RequestStructure();
        requestStructure.setId(id);
        requestStructure.setRequestBody(body);
        ticketService.modifyTicket(requestStructure);
        return processResponse(requestStructure);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_XML)
    public Response addTicket( String body){
        RequestStructure requestStructure = new RequestStructure();
        requestStructure.setRequestBody(body);
        ticketService.addTicket(requestStructure);
        return processResponse(requestStructure);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteTicket(@PathParam("id") Long id){
        RequestStructure requestStructure = new RequestStructure();
        requestStructure.setId(id);
        ticketService.deleteTicket(requestStructure);
        return processResponse(requestStructure);
    }

    @GET
    @Path("/avgprice")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAveragePrice(){
        RequestStructure requestStructure = new RequestStructure();
        Double avg = JPAUtil.getAveragePrice();
        requestStructure.setMessage(Objects.isNull(avg) ? null : avg.toString());
        requestStructure.setResponseCode(200);
        return processResponse(requestStructure);
    }

    @GET
    @Path("/withcommentslike")
    @Produces(MediaType.APPLICATION_XML)
    public Response getTicketsWithCommentsLike(@QueryParam(value = "string") String str){
        RequestStructure requestStructure = new RequestStructure();
        if(Objects.isNull(str)){
            requestStructure.setResponseCode(400);
            requestStructure.setMessage("Missed required param 'string'");
        }
        else {
            requestStructure.setMessage(XStreamUtil.toXML(new TicketListWrap(JPAUtil.getCommentsLike(str), 0)));
            requestStructure.setResponseCode(200);
        }
        return processResponse(requestStructure);
    }

    @GET
    @Path("/withcommentslower")
    @Produces(MediaType.APPLICATION_XML)
    public Response getTicketsWithCommentsLower(@QueryParam(value = "string") String str){
        RequestStructure requestStructure = new RequestStructure();
        if(Objects.isNull(str)){
            requestStructure.setResponseCode(400);
            requestStructure.setMessage("Missed required param 'string'");
        }
        else {
            requestStructure.setMessage(XStreamUtil.toXML(new TicketListWrap(JPAUtil.getCommentsLower(str), 0)));
            requestStructure.setResponseCode(200);
        }
        return processResponse(requestStructure);
    }
}
