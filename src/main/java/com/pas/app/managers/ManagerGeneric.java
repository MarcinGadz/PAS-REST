package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Entity;

import java.util.List;
import java.util.UUID;

public abstract class ManagerGeneric<T extends Entity> {

    private final RepositoryGeneric<T> repo;

    public ManagerGeneric(RepositoryGeneric<T> repo) {
        this.repo = repo;
    }

    public T add(T object) {
        return this.repo.add(object);
    }

    public void remove(T obj) {
        this.repo.remove(obj);
    }

    public T getById(UUID id) {
        return repo.getById(id);
    }

    public List<T> getAll() {
        return repo.getAll();
    }

    public T update(UUID id, T obj) {
        T tmp = repo.getById(id);
        if (tmp != null) {
            remove(tmp);
            obj.setId(id);
            add(obj);
            return obj;
        }
        return null;
    }

    public String generateReport() {
        return this.repo.generateReport();
    }

    public long getRepoSize() {
        return this.repo.getSize();
    }
}
