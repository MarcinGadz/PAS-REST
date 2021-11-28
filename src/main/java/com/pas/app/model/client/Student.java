package com.pas.app.model.client;

import java.math.BigDecimal;

public class Student extends ClientType{
    private final BigDecimal discount = new BigDecimal("0.5");

    public Student() {
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price.multiply(discount);
    }
}
