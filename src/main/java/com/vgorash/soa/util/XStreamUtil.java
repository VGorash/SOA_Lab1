package com.vgorash.soa.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.vgorash.soa.model.Coordinates;
import com.vgorash.soa.model.Event;
import com.vgorash.soa.model.Ticket;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class XStreamUtil {

    private static final XStream xStream = createXStream();

    private static XStream createXStream(){
        XStream result = new XStream();
        result.processAnnotations(Ticket.class);
        result.processAnnotations(Event.class);
        result.processAnnotations(TicketListWrap.class);
        result.addDefaultImplementation(Timestamp.class, Date.class);
        Class<?>[] classes = new Class[] {Ticket.class, Event.class, Coordinates.class};
        result.allowTypes(classes);
        result.setMode(XStream.NO_REFERENCES);
        return result;
    }

    public static String toXML(Ticket ticket){
        return xStream.toXML(ticket);
    }

    public static String toXML(TicketListWrap wrap){
        return xStream.toXML(wrap);
    }

    public static Ticket fromXML(RequestStructure rs){
        try{
            return (Ticket) xStream.fromXML(rs.getRequestBody());
        }
        catch (ConversionException e){
            rs.setMessage(Objects.isNull(e.get("message")) ? e.get("cause-message") : e.get("message"));
            return null;
        }
    }
}
