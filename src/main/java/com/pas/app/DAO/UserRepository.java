package com.pas.app.DAO;

import com.pas.app.model.User;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepository extends RepositoryGeneric<User> {
    public UserRepository() {
        User u = new User("Jan", "Kowalski", "Janko123");
        User u2 = new User("John", "Doe", "JD");
        User u3 = new User("Testname", "Testsurname", "Testlogin");
        u.setId(UUID.fromString("df022edf-bb0a-42c4-a4b7-7c37af542fd9"));
        u2.setId(UUID.fromString("149bf059-f6a3-4c7b-8bfc-a577a485ac32"));
        u3.setId(UUID.fromString("a3bf9df2-6b2b-11ec-90d6-0242ac120003"));
        u2.setActive(true);
        u3.setActive(true);
        add(u);
        add(u2);
        add(u3);
    }

    public User get(String login) {
        return super.getAll().stream().filter(c -> c.getLogin().equals(login)).findFirst().orElse(null);
    }

    public List<User> getWithCharsInLogin(String chars) {
        return super.getAll().stream().filter(c -> c.getLogin().contains(chars)).collect(Collectors.toList());
    }

    public synchronized void activate(UUID id) {
        User c = getById(id);
        c.setActive(true);
    }

    @Override
    public synchronized User add(User object) {
        if (super.getAll().stream().anyMatch(c -> c.getLogin().equals(object.getLogin()))) {
            throw new IllegalArgumentException("User with specified login already exists");
        }
        return super.add(object);
    }

    public synchronized void deactivate(UUID id) {
        User c = getById(id);
        if (c != null) {
            c.setActive(false);
        }
    }
}

