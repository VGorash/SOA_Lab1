package com.vgorash.beans.beans;

import com.vgorash.beans.model.Ticket;

import javax.ejb.Local;

@Local
public interface ValidationUtil {
    String validate(Ticket ticket);
}
