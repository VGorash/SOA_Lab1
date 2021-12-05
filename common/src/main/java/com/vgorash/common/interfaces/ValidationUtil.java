package com.vgorash.common.interfaces;

import com.vgorash.common.model.Ticket;

import javax.ejb.Remote;

@Remote
public interface ValidationUtil {
    String validate(Ticket ticket);
}
