package com.pas.app.managers;

import com.pas.app.DAO.RepositoryGeneric;
import com.pas.app.model.Entity;

import java.util.List;

public abstract class ManagerGeneric<T extends Entity> {
    private final RepositoryGeneric<T> repo;

    public ManagerGeneric(RepositoryGeneric<T> repo) {
        this.repo = repo;
    }
    public T register(T object) {
        return this.repo.register(object);
    }
    public void unregister(T object) {
        this.repo.unregister(object);
    }
    public String generateReport() {
        return this.repo.generateReport();
    }
    public long getRepoSize() {
        return this.repo.getSize();
    }

    public List<T> getAll() {
        return repo.getAll();
    }
}
