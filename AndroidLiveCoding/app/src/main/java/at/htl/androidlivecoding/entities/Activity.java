package at.htl.androidlivecoding.entities;

import android.content.ContentValues;

import java.util.Date;

import at.htl.androidlivecoding.contracts.CVAble;


/**
 * Created by ericb on 19.10.2017.
 */

public class Activity implements CVAble {
    private int id;
    private String name;
    private Date from;
    private Date til;

    public Activity(int id, String name, Date from, Date til) {
        this.setId(id);
        this.name = name;
        this.from = from;
        this.til = til;
    }

    public Activity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTil() {
        return til;
    }

    public void setTil(Date til) {
        this.til = til;
    }

    @Override
    public String toString() {
        return "Activity || Name: " + getName() + " | ID: " + getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public ContentValues toCV() {
        ContentValues cv = new ContentValues();
        cv.put("NAME", getName());
        cv.put("UNTIL", String.valueOf(getTil()));
        cv.put("_FROM", String.valueOf(getFrom()));

        return cv;
    }
}
