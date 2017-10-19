package at.htl.schichtbetrieb.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


import java.util.Calendar;
import java.util.Date;

import at.htl.schichtbetrieb.entities.WorkDay;
import at.htl.schichtbetrieb.fragments.WorkDayFragment;

/**
 * Created by phili on 19.10.2017.
 */

public class WorkDaySimulationService extends IntentService {
    private WorkDay workDay;
    private Date lastTimeServiceCalled;

    public WorkDaySimulationService(String name, WorkDay workDay) {
        super(name);
        setWorkDay(workDay);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(lastTimeServiceCalled == null){
            lastTimeServiceCalled = Calendar.getInstance().getTime();
            return;
        }
        if(Calendar.getInstance().getTime().getTime() - lastTimeServiceCalled.getTime() >= 1000){ //jede Sekunde vergeht intern eine Minute
            int newActualMinutesPassed = workDay.getActualMinutesPassed() + 1;

            if(newActualMinutesPassed >= workDay.getTotalMinutesOfWork())
                this.stopSelf();

            workDay.setActualMinutesPassed(newActualMinutesPassed);
        }
        WorkDayFragment.actualWorkDay = workDay;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }

    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }
}
