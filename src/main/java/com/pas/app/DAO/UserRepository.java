package com.pas.app.DAO;

import com.pas.app.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserRepository extends RepositoryGeneric<User> {
    public UserRepository() {
        //TODO add init data to repo
    }

    public User get(String login) {
        return super.getAll().stream().filter(c -> c.getLogin().equals(login)).findFirst().orElse(null);
    }

    public List<User> getWithCharsInLogin(String chars) {
        return super.getAll().stream().filter(c -> c.getLogin().contains(chars)).collect(Collectors.toList());
    }

    public void activate(User c) {
        c = getById(c.getId());
//        remove(c);
        c.setActive(true);
//        add(c);
    }

    public void deactivate(User c) {
        c = getById(c.getId());
        if (c != null) {
            c.setActive(false);
        }
    }
}
