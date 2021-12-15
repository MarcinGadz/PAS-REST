package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Entity;

import java.util.List;
import java.util.UUID;

public abstract class ManagerGeneric<T extends Entity> {

    private RepositoryGeneric<T> repo;

    public ManagerGeneric(RepositoryGeneric<T> repo) {
        this.repo = repo;
    }

    public ManagerGeneric() {
    }

    public RepositoryGeneric<T> getRepo() {
        return repo;
    }

    public void setRepo(RepositoryGeneric<T> repo) {
        this.repo = repo;
    }

    public boolean existsById(UUID id) {
        return repo.existsById(id);
    }

    public synchronized T add(T object) {
        object.setId(UUID.randomUUID());
        return this.repo.add(object);
    }

    public synchronized void remove(T obj) {
        this.repo.remove(obj);
    }

    public T getById(UUID id) {
        return repo.getById(id);
    }

    public List<T> getAll() {
        return repo.getAll();
    }

    public synchronized T update(UUID id, T obj) {
        T tmp = repo.getById(id);
        if (tmp != null) {
            repo.remove(tmp);
            obj.setId(id);
            repo.add(obj);
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
