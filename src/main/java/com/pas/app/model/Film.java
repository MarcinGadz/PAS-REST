package com.pas.app.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

public class Film extends Entity {
    private String title;
    private String genre;
    private Date beginTime;
    private Date endTime;
    private List<Ticket> tickets;
    private int basePrice;

    public Film(String title, String genre, Date beginTime, Date endTime, int basePrice) {
        this.title = title;
        this.genre = genre;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
    }

    public void addTicket(Ticket t) {
        tickets.add(t);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public Boolean isWeekend(LocalDate currentDate) {
        return DayOfWeek.of(currentDate.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SATURDAY ||
                DayOfWeek.of(currentDate.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SUNDAY;
    }

    public BigInteger getDuration() {
        //TODO: Zmienic date bo w Date wszystko jest deprecated
        return new BigInteger("1");
    }

    public BigDecimal adjustWeekendPrice(Date beginTime) {
        return new BigDecimal(0);
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
