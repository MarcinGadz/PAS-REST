package com.pas.app.DAO;

import com.pas.app.model.Entity;

import java.util.*;

public abstract class RepositoryGeneric<T extends Entity> {
    private Set<T> objects = new HashSet<T>();

    public T getById(UUID id) {
        return objects.stream().filter(obj -> obj.getId() == id).findFirst().orElse(null);
    }

    public List<T> getAll() {
        return new ArrayList<>(objects);
    }

    public T add(T object) {
        objects.add(object);
        return object;
    }

    public void remove(T object) {
        objects.remove(object);
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        objects.forEach(sb::append);
        return sb.toString();
    }

    public long getSize() {
        return objects.size();
    }
}
