package com.pas.app.api;

import com.pas.app.managers.FilmManager;
import com.pas.app.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/film")

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
    public ResponseEntity<Film> gowno(@PathVariable("id") UUID id) {
        if(!manager.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Film f = manager.getById(id);
        return ResponseEntity.ok(f);
    }



    @PostMapping
    public ResponseEntity<Film> postFilm(@RequestBody Film f) {
        if (f == null || f.getBeginTime() == null || f.getEndTime() == null
        || f.getGenre() == null || f.getBasePrice() == null || f.getTitle() == null) {
            return ResponseEntity.badRequest().build();
        }
        f = manager.add(f);

        return new ResponseEntity<>(f, HttpStatus.CREATED);

    }

@PutMapping("/{id}")
    public ResponseEntity<Film>  update(@PathVariable("id") UUID id, Film f) {
        if(!manager.existsById(id)) {

            return ResponseEntity.notFound().build();
        }
        if (f == null || f.getBeginTime() == null || f.getEndTime() == null
                || f.getGenre() == null || f.getBasePrice() == null || f.getTitle() == null) {

            return ResponseEntity.badRequest().build();
        }
        f = manager.update(id, f);
        return new ResponseEntity<>(f, HttpStatus.ACCEPTED);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Film> deleteFilm(@PathVariable("id") UUID id) {
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
