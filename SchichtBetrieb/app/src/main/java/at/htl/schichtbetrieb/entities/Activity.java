package at.htl.schichtbetrieb.entities;

import java.util.Date;

/**
 * Created by ericb on 19.10.2017.
 */

public class Activity {
    private String name;
    private Date from;
    private Date til;

    public Activity(String name, Date from, Date til) {
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
}
