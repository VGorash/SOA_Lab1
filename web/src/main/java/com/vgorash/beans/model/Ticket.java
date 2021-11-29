package com.vgorash.beans.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="soa_ticket")
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket implements Serializable{
    @Id
    @GeneratedValue
    @XmlElement
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotEmpty
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    @Embedded
    @XmlElement
    private Coordinates coordinates; //Поле не может быть null

    @NotNull
    @XmlElement
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Min(0)
    @XmlElement
    private Integer price; //Поле может быть null, Значение поля должно быть больше 0

    @Size(max = 333)
    @XmlElement
    private String comment; //Длина строки не должна быть больше 333, Поле может быть null

    @XmlElement
    private TicketType type; //Поле может быть null

    @XmlElement
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
