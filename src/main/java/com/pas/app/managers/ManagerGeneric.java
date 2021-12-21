package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Entity;

import java.util.List;
import java.util.NoSuchElementException;
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

    public T add(T object) {
        object.setId(UUID.randomUUID());
        return this.repo.add(object);
    }

    public void remove(T obj) {
        this.repo.remove(obj);
    }

    public T getById(UUID id) {
        T obj = repo.getById(id);
        if(obj == null) {
            throw new NoSuchElementException("Object does not exists");
        }
        return obj;
    }

    public List<T> getAll() {
        return repo.getAll();
    }

    public T update(UUID id, T obj) {
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
