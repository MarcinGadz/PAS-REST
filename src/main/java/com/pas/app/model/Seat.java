package com.pas.app.model;

import java.util.ArrayList;
import java.util.List;

public class Seat extends Entity {
    private int row;
    private int column;
    private Hall hall;
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
        return super.equals(o);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
