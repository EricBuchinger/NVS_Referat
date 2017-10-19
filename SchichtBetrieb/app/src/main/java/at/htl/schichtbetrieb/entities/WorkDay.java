package at.htl.schichtbetrieb.entities;

import java.util.Date;

/**
 * Created by phili on 19.10.2017.
 */

public class WorkDay{
    private int id;
    private Date date;
    private int procentFinished;

    //region Getter & Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getProcentFinished() {
        return procentFinished;
    }

    public void setProcentFinished(int procentFinished) {
        this.procentFinished = procentFinished;
    }
    //endregion

    public WorkDay(){
        setProcentFinished(0);
        setDate(new Date(2013,10,04));
    }
}
