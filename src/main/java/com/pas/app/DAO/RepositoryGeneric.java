package com.pas.app.DAO;

import com.pas.app.model.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryGeneric<T extends Entity> {
    private List<T> objects = new ArrayList<>();

    public T register(T object) {
        objects.add(object);
        return object;
    }

    public void unregister(T object) {
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

    public List<T> getAll() {
        return new ArrayList<>(objects);
    }
}
