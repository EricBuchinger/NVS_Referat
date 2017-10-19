package at.htl.schichtbetrieb.entities;

import java.util.Date;

/**
 * Created by phili on 19.10.2017.
 */

public class WorkDay{
    private int id;
    private Date date;
    private int percentFinished;
    private int totalMinutesOfWork;
    private int actualMinutesPassed;

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

    public int getPercentFinished() {
        return actualMinutesPassed == 0 ? 0 : actualMinutesPassed*100/totalMinutesOfWork;
    }

    public void setPercentFinished(int percentFinished) {
        this.percentFinished = percentFinished;
    }

    public int getTotalMinutesOfWork() {
        return totalMinutesOfWork;
    }

    public void setTotalMinutesOfWork(int totalMinutesOfWork) {
        this.totalMinutesOfWork = totalMinutesOfWork;
    }

    public int getActualMinutesPassed() {
        return actualMinutesPassed;
    }

    public void setActualMinutesPassed(int actualMinutesPassed) {
        this.actualMinutesPassed = actualMinutesPassed;
    }
    //endregion

    public WorkDay(){
        setPercentFinished(0);
        setDate(new Date(2013,10,04));
        setActualMinutesPassed(0);

    }
}
