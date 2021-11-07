package com.vgorash.web.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name="soa_ticket")
@XStreamAlias("ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    @Embedded
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(0)
    private Integer price; //Поле может быть null, Значение поля должно быть больше 0

    @Size(max = 333)
    private String comment; //Длина строки не должна быть больше 333, Поле может быть null

    private TicketType type; //Поле может быть null

    @ManyToOne
    private Event event; //Поле может быть null

    public void copy(Ticket ticket){
        this.name = ticket.name;
        this.coordinates = ticket.coordinates;
        this.price = ticket.price;
        this.comment = ticket.comment;
        this.type = ticket.type;
        this.event = ticket.event;
    }
}
