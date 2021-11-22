package com.vgorash.beans.beans;

import com.vgorash.beans.model.Ticket;

import javax.ejb.Remote;

@Remote
public interface ValidationUtil {
    String validate(Ticket ticket);
}
