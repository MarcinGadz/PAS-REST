package com.example.mvc.model;

//import javax.json.bind.annotation.JsonbTransient;


import javax.json.bind.annotation.JsonbTransient;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Film extends Entity {
    private String title;
    private String genre;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    @JsonbTransient
    private List<Ticket> tickets;
    private BigDecimal basePrice;

    public Film() {
    }

    public Film(String title, String genre, LocalDateTime beginTime, LocalDateTime endTime, BigDecimal basePrice) {
        this.title = title;
        this.genre = genre;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
    }

    public Date getBeginDate() {
        Instant instant = beginTime.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        return Date.from(instant);
    }

    public void setBeginDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        beginTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Warsaw"));
    }

    public Date getEndDate() {
        Instant instant = endTime.atZone(ZoneId.of("Europe/Warsaw")).toInstant();
        return Date.from(instant);
    }

    public void setEndDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        endTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Warsaw"));
    }

    public void addTicket(Ticket t) {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }
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

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Boolean isWeekend(LocalDateTime currentDate) {
        return DayOfWeek.of(currentDate.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SATURDAY ||
                DayOfWeek.of(currentDate.get(ChronoField.DAY_OF_WEEK)) == DayOfWeek.SUNDAY;
    }

    public BigDecimal adjustWeekendPrice(LocalDateTime beginTime) {
        if (isWeekend(beginTime)) {
            return basePrice.multiply(BigDecimal.valueOf(1.2));
        }
        return basePrice;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", tickets=" + tickets +
                ", basePrice=" + basePrice +
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
