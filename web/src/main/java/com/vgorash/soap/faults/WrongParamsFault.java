package com.vgorash.soap.faults;

import javax.xml.ws.WebFault;

@WebFault(name = "wrongParams")
public class WrongParamsFault extends Exception{

    private final int code;

    public int getCode() {
        return code;
    }

    public WrongParamsFault(String message, int code){
        super(message);
        this.code = code;
    }
}
