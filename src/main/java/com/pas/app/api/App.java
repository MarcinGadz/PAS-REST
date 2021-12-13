package com.pas.app.api;

import com.pas.app.DAO.FilmRepository;
import com.pas.app.managers.FilmManager;
import com.pas.app.model.Film;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationPath("/api")
public class App extends Application {
    FilmManager filmManager = new FilmManager();
    FilmRepository filmRepository = new FilmRepository();
//    Film film1 = new Film("tytul", "typ", LocalDateTime.now(),
//            LocalDateTime.now().plusHours(2),new BigDecimal(20));

}
