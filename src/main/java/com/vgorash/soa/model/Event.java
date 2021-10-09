package com.vgorash.soa.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "soa_event")
@XStreamAlias("event")
public class Event {

    @Id
    @GeneratedValue
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    private java.time.LocalDateTime date; //Поле может быть null

    private EventType eventType; //Поле может быть null

    public void copy(Event event){
        this.name = event.name;
        this.date = event.date;
        this.eventType = event.eventType;
    }
}