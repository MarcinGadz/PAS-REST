package com.pas.app.api;

import com.pas.app.managers.FilmManager;
import com.pas.app.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/film")

public class FilmController {

    private FilmManager manager;

    @Autowired
    public FilmController(FilmManager manager) {
        this.manager = manager;
    }

    @GetMapping
    public List<Film> getAll() {
        return manager.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> get(@PathVariable("id") UUID id) {
        try {
            Film f = manager.getById(id);
            return ResponseEntity.ok(f);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Film> postFilm(@RequestBody Film f) {
        try {
            f = manager.add(f);
            return new ResponseEntity<>(f, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> update(@PathVariable("id") UUID id, @RequestBody Film f) {
        try {
            System.out.println("lol");
            f = manager.update(id, f);
            return new ResponseEntity<>(f, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Film> deleteFilm(@PathVariable("id") UUID id) {
        try {
            manager.remove(manager.getById(id));
            return ResponseEntity.accepted().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
