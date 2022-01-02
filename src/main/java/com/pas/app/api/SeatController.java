package com.pas.app.api;

import com.pas.app.managers.SeatsManager;
import com.pas.app.model.Film;
import com.pas.app.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/seat")
public class SeatController {

    private SeatsManager manager;

    @Autowired
    public SeatController(SeatsManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public List<Seat> getAll() {
        return manager.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeat(@PathVariable("id") UUID id) {
        try {
            Seat s = manager.getById(id);
            return ResponseEntity.ok(s);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Seat> postSeat(@RequestBody Seat f) {
        try {
            f = manager.add(f);
            return new ResponseEntity<>(f, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat>  update(@PathVariable("id") UUID id, @RequestBody Seat f) {
        try {
            f = manager.update(id, f);
            return new ResponseEntity<>(f, HttpStatus.ACCEPTED);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Film> delete(@PathVariable("id") UUID id) {
        try {
            manager.remove(manager.getById(id));
            return ResponseEntity.accepted().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
