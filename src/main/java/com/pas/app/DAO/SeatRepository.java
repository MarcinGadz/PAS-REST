package com.pas.app.DAO;

import com.pas.app.model.Hall;
import com.pas.app.model.Seat;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SeatRepository extends RepositoryGeneric<Seat> {
    public SeatRepository() {
        Seat tmp = new Seat(1, 1, Hall.A);
        Seat tmp2 = new Seat(1, 2, Hall.A);
        Seat tmp3 = new Seat(2, 1, Hall.B);
        add(tmp);
        add(tmp2);
        add(tmp3);
    }
}
