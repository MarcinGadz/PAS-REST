package com.pas.app.model;

import javax.json.bind.annotation.JsonbTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Seat extends Entity {
    private int row;
    private int column;
    private Hall hall;
    @JsonbTransient
    private List<Ticket> ticketList;

    public Seat() {
    }

    public void addTicket(Ticket t) {
        if(ticketList == null) {
            ticketList = new ArrayList<>();
        }
        ticketList.add(t);
    }

    public void removeTicket(Ticket t) {
        ticketList.remove(t);
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Seat(int row, int column, Hall hall) {
        this.row = row;
        this.column = column;
        this.hall = hall;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Hall getHall() {
        return hall;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", litera=" + hall +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Seat seat = (Seat) o;
        return row == seat.row && column == seat.column && hall == seat.hall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), row, column, hall);
    }
}
