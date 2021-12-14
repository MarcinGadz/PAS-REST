package com.pas.app.api;

import com.pas.app.managers.SeatsManager;
import com.pas.app.model.Film;
import com.pas.app.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seat")
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
        if(!manager.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Seat s = manager.getById(id);
        return ResponseEntity.ok(s);
    }


    @PostMapping
    public ResponseEntity<Seat> postSeat(@RequestBody Seat f) {
        if (f.getHall() == null || f.getRow() < 0 || f.getColumn() < 0) {
            return ResponseEntity.badRequest().build();
        }
        f = manager.add(f);

        return new ResponseEntity<>(f, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat>  update(@PathVariable("id") UUID id, Seat f) {
        if(!manager.existsById(id)) {

            return ResponseEntity.notFound().build();
        }
        if (f.getHall() == null || f.getRow() < 0 || f.getColumn() < 0){
            return ResponseEntity.badRequest().build();
        }
        f = manager.update(id, f);
        return new ResponseEntity<>(f, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Film> delete(@PathVariable("id") UUID id) {
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
