package com.vgorash.beans.util;

import com.vgorash.beans.model.Coordinates;
import com.vgorash.beans.model.Event;
import com.vgorash.beans.model.Ticket;
import com.vgorash.beans.model.TicketType;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

public class JPAUtil {

    private static final EntityManager entityManager = createEntityManager();
    private static final HashMap<String, Function<String, Object>> possibleParams = createPossibleParams();

    private static EntityManager createEntityManager(){
        return Persistence.createEntityManagerFactory("hibernate").createEntityManager();
    }

    private static HashMap<String, Function<String, Object>> createPossibleParams(){
        HashMap<String, Function<String, Object>> result = new HashMap<>();
        result.put("id", Long::parseLong);
        result.put("name", s->s);
        result.put("coordinates", Coordinates::fromString);
        result.put("creationDate", s->Date.from(LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")).toInstant(ZoneOffset.UTC)));
        result.put("price", Integer::parseInt);
        result.put("comment", s->s);
        result.put("type", TicketType::valueOf);
        result.put("event", Integer::parseInt);
        return result;
    }

    public static void saveTicket(Ticket ticket){
        entityManager.getTransaction().begin();
        entityManager.persist(ticket);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public static void saveEvent(Event event){
        entityManager.getTransaction().begin();
        entityManager.persist(event);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public static TicketListWrap getTickets(RequestStructure requestStructure){
        Map<String, String[]> params = requestStructure.getParams();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        try {
            CriteriaQuery<Ticket> criteriaQuery = criteriaBuilder.createQuery(Ticket.class);
            Root<Ticket> ticketRoot = criteriaQuery.from(Ticket.class);
            //filtering
            List<Predicate> predicates = new ArrayList<>();
            for(String s : params.keySet()){
                if(possibleParams.containsKey(s)){
                    predicates.add(criteriaBuilder.equal(ticketRoot.get(s), possibleParams.get(s).apply(params.get(s)[0])));
                }
            }
            criteriaQuery.select(ticketRoot).where(predicates.toArray(new Predicate[0]));
            // sorting
            if(params.containsKey("orderBy")){
                List<Order> orders = new ArrayList<>();
                for(String s : params.get("orderBy")){
                    String[] order = s.split(",");
                    if(order.length != 2){
                        throw new RuntimeException(s+": each order parameter should have 'asc' or 'desc' value");
                    }
                    if(possibleParams.containsKey(order[0]) && order[1].equals("desc")){
                        orders.add(criteriaBuilder.desc(ticketRoot.get(order[0])));
                    }
                    else if(possibleParams.containsKey(order[0]) && order[1].equals("asc")){
                        orders.add(criteriaBuilder.asc(ticketRoot.get(order[0])));
                    }
                    else {
                        throw new RuntimeException(s+" is not a correct order parameter");
                    }
                }
                criteriaQuery.orderBy(orders);
            }
            // pagination
            TypedQuery<Ticket> query = entityManager.createQuery(criteriaQuery);
            int countResults = query.getResultList().size();
            if(params.containsKey("pageNumber") && params.containsKey("pageSize")){
                int pageNumber = Integer.parseInt(params.get("pageNumber")[0]);
                int perPage = Integer.parseInt(params.get("pageSize")[0]);

                if(((long) (pageNumber - 1) * perPage >= countResults && countResults > 0) || pageNumber <= 0){
                    throw new RuntimeException("pagination out of bounds");
                }
                query.setFirstResult((pageNumber - 1) * perPage);
                query.setMaxResults(perPage);
            }
            return new TicketListWrap(query.getResultList(), countResults);
        }
        catch (Exception e){
            requestStructure.setResponseCode(400);
            requestStructure.setMessage(e.getMessage());
            return null;
        }
    }

    public static Ticket getTicket(Long id){
        return entityManager.find(Ticket.class, id);
    }

    public static Event getEvent(Integer id){
        return entityManager.find(Event.class, id);
    }

    public static void deleteTicket(Ticket ticket){
        entityManager.getTransaction().begin();
        entityManager.remove(ticket);
        entityManager.getTransaction().commit();
    }

    public static Double getAveragePrice(){
        Query countQuery = entityManager.createQuery("select avg (t.price) from Ticket t");
        return (Double) countQuery.getSingleResult();
    }

    public static TicketListWrap getCommentsLike(String str){
        return new TicketListWrap(entityManager.createQuery("select t from Ticket t where t.comment like :str", Ticket.class).setParameter("str", "%"+str+"%").getResultList());
    }
    public static TicketListWrap getCommentsLower(String str){
        return new TicketListWrap(entityManager.createQuery("select t from Ticket t where t.comment < :str", Ticket.class).setParameter("str", str).getResultList());
    }

}
