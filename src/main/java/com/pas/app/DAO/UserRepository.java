package com.pas.app.DAO;

import com.pas.app.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
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

    public void activate(UUID id) {
        User c = getById(id);
        //TODO check
//        remove(c);
        c.setActive(true);
//        add(c);
    }

    public void deactivate(UUID id) {
        User c = getById(id);
        //TODO check
        if (c != null) {
            c.setActive(false);
        }
    }
}
