package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Entity;

import java.util.List;
import java.util.UUID;

public abstract class ManagerGeneric<T extends Entity> {

    private final Object lock = new Object();

    public Object getLock() {
        return lock;
    }

    private RepositoryGeneric<T> repo;

    public RepositoryGeneric<T> getRepo() {
        return repo;
    }

    public void setRepo(RepositoryGeneric<T> repo) {
        this.repo = repo;
    }

    public ManagerGeneric(RepositoryGeneric<T> repo) {
        this.repo = repo;
    }

    public ManagerGeneric() {
    }

    public boolean existsById(UUID id) {
        return repo.existsById(id);
    }

    public T add(T object) {
        return this.repo.add(object);
    }

    public void remove(T obj) {
        synchronized (lock) {
            this.repo.remove(obj);
        }
    }

    public T getById(UUID id) {
        return repo.getById(id);
    }

    public List<T> getAll() {
        return repo.getAll();
    }

    public T update(UUID id, T obj) {
        synchronized(lock) {
            T tmp = repo.getById(id);
            if (tmp != null) {
                remove(tmp);
                obj.setId(id);
                add(obj);
                return obj;
            }
            return null;
        }
    }

    public String generateReport() {
        return this.repo.generateReport();
    }

    public long getRepoSize() {
        return this.repo.getSize();
    }
}
