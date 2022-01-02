package com.pas.app.api;

import com.pas.app.managers.UserManager;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserManager manager;

    @Autowired
    public UserController(UserManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public List<User> getAll() {
        return manager.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") UUID id) {
        try {
            User f = manager.getById(id);
            return ResponseEntity.ok(f);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find")
    public ResponseEntity<User> findByLogin(@RequestParam("login") String login) {
        if (login == null || login.trim().equals("")) {
            return ResponseEntity.badRequest().build();
        }
        try {
            User user = manager.getUser(login);
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findcontaining")
    public ResponseEntity<List<User>> findWithCharsInLogin(@RequestParam("login") String login) {
        if (login == null || login.trim().equals("")) {
            return ResponseEntity.notFound().build();
        }
        List<User> users = manager.getWithCharsInLogin(login);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity activate(@PathVariable("id") UUID id) {
        try {
            manager.activate(id);
            return ResponseEntity.accepted().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity deactivate(@PathVariable("id") UUID id) {
        try {
            manager.deactivate(id);
            return ResponseEntity.accepted().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        try {
            u = manager.register(u);
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") UUID id, @RequestBody User u) {
        try {
            u = manager.update(id, u);
            return new ResponseEntity<>(u, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/active")
    public ResponseEntity<List<Ticket>> getActive(@PathVariable("id") UUID id) {
        try {
            List<Ticket> t = manager.getActiveTickets(id);
            return ResponseEntity.ok(t);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/past")
    public ResponseEntity<List<Ticket>> getPast(@PathVariable("id") UUID id) {
        try {
            List<Ticket> t = manager.getInactiveTickets(id);
            return ResponseEntity.ok(t);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
