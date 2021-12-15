package com.pas.app.api;

import com.pas.app.managers.TicketManager;
import com.pas.app.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private TicketManager manager;


    @Autowired
    public void TicketController(TicketManager manager) {
            this.manager = manager;
        }

    @GetMapping
    public List<Ticket> getAll() {
        return manager.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getId(@PathVariable("id") UUID id) {
        if(!manager.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Ticket f = manager.getById(id);
        return ResponseEntity.ok(f);
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket f) {
        if(f.getFilm() == null || f.getSeat() == null || f.getClient() == null
                || f.getFilm().getId() == null || f.getSeat().getId() == null
        || f.getClient().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        f = manager.add(f);
        return new ResponseEntity<>(f, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Ticket>  update(@PathVariable("id") UUID id, Ticket f) {
        if(!manager.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if(f == null || f.getClient().getId() == null || f.getFilm().getId() == null || f.getSeat() == null) {

            return ResponseEntity.badRequest().build();
        }
        f = manager.update(id, f);
        return new ResponseEntity<>(f, HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> deleteFilm(@PathVariable("id") UUID id) {
        if(!manager.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        try {
            manager.remove(manager.getById(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.accepted().build();
    }

}
