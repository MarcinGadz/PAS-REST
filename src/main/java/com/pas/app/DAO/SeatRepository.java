package com.pas.app.DAO;

import com.pas.app.model.Hall;
import com.pas.app.model.Seat;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class SeatRepository extends RepositoryGeneric<Seat> {
    public SeatRepository() {
        Seat tmp = new Seat(1, 1, Hall.A);
        Seat tmp2 = new Seat(1, 2, Hall.A);
        Seat tmp3 = new Seat(2, 1, Hall.B);
        tmp.setId(UUID.fromString("842531e8-5c60-11ec-bf63-0242ac130002"));
        tmp2.setId(UUID.fromString("8feaf7b0-5c60-11ec-bf63-0242ac130002 "));
        tmp3.setId(UUID.fromString("95e24d76-5c60-11ec-bf63-0242ac130002"));
        add(tmp);
        add(tmp2);
        add(tmp3);
    }
}
