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
        tmp.setId(UUID.fromString("2ec20a37-c611-6792-92f4-981df080107c"));
        tmp2.setId(UUID.fromString("2ec20a17-c611-6792-92f4-981df080107c"));
        tmp3.setId(UUID.fromString("2ec20h67-c611-6792-92f4-981df080107c"));
        add(tmp);
        add(tmp2);
        add(tmp3);
    }
}
