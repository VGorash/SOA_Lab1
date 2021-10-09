package com.vgorash.soa.controller;

import com.vgorash.soa.service.TicketService;
import com.vgorash.soa.util.JPAUtil;
import com.vgorash.soa.util.RequestStructure;
import com.vgorash.soa.util.TicketListWrap;
import com.vgorash.soa.util.XStreamUtil;

import java.io.*;
import java.util.Objects;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "main", value = "/api/tickets/*")
public class MainServlet extends HttpServlet {

    private TicketService ticketService;

    public void init() {
        ticketService = new TicketService();
    }

    static void preprocessResponse(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }

    static void processResponse(HttpServletResponse response, RequestStructure requestStructure) throws IOException {
        response.setContentType("application/xml");
        response.setStatus(requestStructure.getResponseCode());
        PrintWriter out = response.getWriter();
        out.println(requestStructure.getMessage());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        preprocessResponse(response);
        RequestStructure requestStructure = RequestStructure.fromRequest(request);
        if(!Objects.isNull(requestStructure.getId())){
            ticketService.getTicket(requestStructure);
        }
        else if (Objects.isNull(requestStructure.getMessage())){
            ticketService.getTicketList(requestStructure);
        }
        else if(requestStructure.getMessage().equals("avgprice")){
            Double avg = JPAUtil.getAveragePrice();
            requestStructure.setMessage(Objects.isNull(avg) ? null : avg.toString());
            requestStructure.setResponseCode(200);
        }
        else if(requestStructure.getMessage().equals("withcommentslike")){
            requestStructure.setMessage(XStreamUtil.toXML(new TicketListWrap(JPAUtil.getCommentsLike(requestStructure.getParams().get("string")[0]), 0)));
            requestStructure.setResponseCode(200);
        }
        else if(requestStructure.getMessage().equals("withcommentslower")){
            requestStructure.setMessage(XStreamUtil.toXML(new TicketListWrap(JPAUtil.getCommentsLower(requestStructure.getParams().get("string")[0]), 0)));
            requestStructure.setResponseCode(200);
        }
        else{
            requestStructure.setMessage("resource not found");
            requestStructure.setResponseCode(404);
        }
        processResponse(response, requestStructure);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        preprocessResponse(response);
        RequestStructure requestStructure = RequestStructure.fromRequest(request);
        ticketService.modifyTicket(requestStructure);
        processResponse(response, requestStructure);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        preprocessResponse(response);
        RequestStructure requestStructure = RequestStructure.fromRequest(request);
        ticketService.addTicket(requestStructure);
        processResponse(response, requestStructure);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
        preprocessResponse(response);
        RequestStructure requestStructure = RequestStructure.fromRequest(request);
        ticketService.deleteTicket(requestStructure);
        processResponse(response, requestStructure);
    }

    public void doOptions(HttpServletRequest request, HttpServletResponse response){
        preprocessResponse(response);
    }

    public void destroy() {
    }
}