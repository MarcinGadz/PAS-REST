package com.pas.app.model.client;

import java.math.BigDecimal;

public abstract class ClientType {
    public abstract BigDecimal applyDiscount(BigDecimal price);
}
