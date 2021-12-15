package com.pas.app.api;

import com.pas.app.managers.UserManager;
import com.pas.app.model.Ticket;
import com.pas.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pas.app.managers.TicketManager;
import com.pas.app.model.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
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
        if(!manager.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User f = manager.getById(id);
        return ResponseEntity.ok(f);
    }

    @GetMapping("/find")
    public ResponseEntity findByLogin(@PathVariable("login") String login) {
        if(login == null) {
            return ResponseEntity.badRequest().build();
        }
        User user = manager.getUser(login);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findcontaining/{login}")
    public ResponseEntity<List<User>> findWithCharsInLogin(@PathVariable("login") String login) {
                if (login == null) {
            return ResponseEntity.notFound().build();
        }
        List<User> users = manager.getWithCharsInLogin(login);
        if(users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity activate(@PathVariable("id") UUID id) {
        if(!manager.existsById(id)) {

            return ResponseEntity.notFound().build();
        }
        manager.activate(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity deactivate(@PathVariable("id") UUID id) {
        if(!manager.existsById(id)) {

            return ResponseEntity.notFound().build();
        }
        manager.deactivate(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        if (u == null || u.getFirstName() == null || u.getLastName() == null || u.getLogin() == null) {
            return ResponseEntity.badRequest().build();
        }
        u = manager.register(u);

        return new ResponseEntity<>(u, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") UUID id, User u) {
        if(!manager.existsById(id)) {

            return ResponseEntity.notFound().build();
        }
        if (id ==  null || u == null || u.getFirstName() == null || u.getLastName() == null || u.getLogin() == null) {
            return ResponseEntity.badRequest().build();
        }
        u = manager.update(id, u);

        return new ResponseEntity<>(u, HttpStatus.ACCEPTED);

    }


//
//    @DELETE
//    @Path("/{id}")
//    public Response delete(@PathParam("id") UUID id) {
//        if (manager.existsById(id)) {
//            manager.deactivate(id);
//            return Response.status(Response.Status.OK).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
//

    @GetMapping("/{id}/active")
    public ResponseEntity<List<Ticket>> getActive(@PathVariable("id") UUID id) {
        if(manager.existsById(id)) {
            List<Ticket> t = manager.getActiveTickets(id);
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}/past")
    public ResponseEntity<List<Ticket>> getPast(@PathVariable("id") UUID id) {
        if(manager.existsById(id)) {
            List<Ticket> t = manager.getInactiveTickets(id);
            return ResponseEntity.ok(t);
        }
        return ResponseEntity.notFound().build();
    }
}
