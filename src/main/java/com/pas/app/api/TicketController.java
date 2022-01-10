package com.pas.app.api;

import com.pas.app.managers.TicketManager;
import com.pas.app.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private TicketManager manager;

    @Autowired
    public TicketController(TicketManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public List<Ticket> getAll() {
        return manager.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getId(@PathVariable("id") UUID id) {
        try {
            Ticket f = manager.getById(id);
            return ResponseEntity.ok(f);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Ticket> create(@RequestBody Ticket f) {
        try {
            f = manager.add(f);
            return new ResponseEntity<>(f, HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Ticket> update(@PathVariable("id") UUID id, @RequestBody Ticket f) {
        try {
            f = manager.update(id, f);
            return new ResponseEntity<>(f, HttpStatus.ACCEPTED);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Ticket> delete(@PathVariable("id") UUID id) {

        try {
            manager.remove(manager.getById(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.accepted().build();
    }

}
