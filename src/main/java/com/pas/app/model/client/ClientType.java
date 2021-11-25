package com.pas.app.model.client;

import com.pas.app.model.Entity;

import java.math.BigDecimal;

public abstract class ClientType {
    public abstract BigDecimal applyDiscount(BigDecimal price);
}
