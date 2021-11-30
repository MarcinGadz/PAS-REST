package com.pas.app.model.client;

import java.math.BigDecimal;

public class Regular extends ClientType {
    public Regular() {
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price;
    }
}
