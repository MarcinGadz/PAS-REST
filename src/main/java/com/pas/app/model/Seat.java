package com.pas.app.model;

public class Seat extends Entity {
    private int row;
    private int column;
    private Hall litera;

    public Seat(int row, int column, Hall litera) {
        this.row = row;
        this.column = column;
        this.litera = litera;

//        jeśli (row < 0 || row > 30) || (column < 0 || column > 30) rzuć wyjątek
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Hall getLitera() {
        return litera;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", litera=" + litera +
                '}';
    }

    public String getSeatID() {
        String id = "C" + getColumn() + "R" + getRow() + "H";
//        trzeba dokleic czesc z hallami, ale nw jak to wyglada narazie jeszcze
        return id;
    }
}
