package com.pas.app.model;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Film extends Entity{
    private String title;
    private String genre;
    private Date beginTime;
    private Date endTime;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    private BigDecimal basePrice;

    public Film(String title, String genre, Date beginTime, Date endTime, BigDecimal basePrice) {
        this.title = title;
        this.genre = genre;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.basePrice = basePrice;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
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
}
