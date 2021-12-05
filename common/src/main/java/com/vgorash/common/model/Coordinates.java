package com.vgorash.common.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates implements Serializable {
    @NotNull
    @Max(916)
    @XmlElement
    private Float x; //Максимальное значение поля: 916, Поле не может быть null

    @XmlElement
    private int y;

    public static Coordinates fromString(String s){
        String[] coords = s.split(",");
        if(coords.length != 2){
            throw new IllegalArgumentException("invalid coordinates format");
        }
        Coordinates result = new Coordinates();
        result.setX(Float.parseFloat(coords[0]));
        result.setY(Integer.parseInt(coords[1]));
        return result;
    }
}