package com.pas.app.DAO;

import com.pas.app.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepository extends RepositoryGeneric<User> {
    public UserRepository() {
        User u = new User("Jan", "Kowalski", "janko123");
        User u2 = new User("John", "Doe", "JD");
        u.setId(UUID.fromString("9ca646ee-5c60-11ec-bf63-0242ac130002"));
        u2.setId(UUID.fromString("a152a70a-5c60-11ec-bf63-0242ac130002"));
        u2.setActive(true);
        add(u);
        add(u2);
    }

    public User get(String login) {
        return super.getAll().stream().filter(c -> c.getLogin().equals(login)).findFirst().orElse(null);
    }

    public List<User> getWithCharsInLogin(String chars) {
        return super.getAll().stream().filter(c -> c.getLogin().contains(chars)).collect(Collectors.toList());
    }

    public void activate(UUID id) {
        User c = getById(id);
        c.setActive(true);
    }

    public void deactivate(UUID id) {
        User c = getById(id);
        if (c != null) {
            c.setActive(false);
        }
    }
}
