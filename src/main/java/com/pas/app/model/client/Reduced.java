package com.pas.app.model.client;

import java.math.BigDecimal;

public class Reduced extends ClientType {
    private final BigDecimal discount = new BigDecimal("0.8");

    public Reduced() {
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price.multiply(discount);
    }
}
