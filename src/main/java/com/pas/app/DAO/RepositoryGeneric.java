package com.pas.app.DAO;

import com.pas.app.model.Entity;

import java.util.*;

public abstract class RepositoryGeneric<T extends Entity> {
    private Set<T> objects = new HashSet<T>();

    public T getById(UUID id) {
        T res = objects.stream().filter(obj -> obj.getId().equals(id)).findAny().orElse(null);
        return res;
    }

    public boolean existsById(UUID id) {
        return !(getById(id) == null);
    }

    public List<T> getAll() {
        return new ArrayList<>(objects);
    }

    public synchronized T add(T object) {
        objects.add(object);
        return object;
    }

    public synchronized void remove(T object) {
        objects.remove(getById(object.getId()));
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
