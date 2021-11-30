package com.pas.app.model;

import java.util.UUID;

public abstract class Entity {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
