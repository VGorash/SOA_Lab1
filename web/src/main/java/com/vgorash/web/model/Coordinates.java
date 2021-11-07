package com.vgorash.web.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Embeddable
@XStreamAlias("coordinates")
public class Coordinates {
    @NotNull
    @Max(916)
    private Float x; //Максимальное значение поля: 916, Поле не может быть null

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