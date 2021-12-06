package com.vgorash.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "soa_event")
@XmlRootElement(name = "event")
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Serializable{

    @Id
    @GeneratedValue
    @XmlElement
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotEmpty
    @XmlElement
    private String name; //Поле не может быть null, Строка не может быть пустой

    @XmlElement
    private Date date; //Поле может быть null

    @XmlElement
    private EventType eventType; //Поле может быть null

    public void copy(Event event){
        this.name = event.name;
        this.date = event.date;
        this.eventType = event.eventType;
    }
}