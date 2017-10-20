package at.htl.schichtbetrieb.activities;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContentResolverCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;
import java.util.LinkedList;

import at.htl.schichtbetrieb.R;
import at.htl.schichtbetrieb.dataaccess.WorkerDBHelper;
import at.htl.schichtbetrieb.entities.Activity;
import at.htl.schichtbetrieb.entities.Worker;
import at.htl.schichtbetrieb.fragments.WorkDayFragment;
import at.htl.schichtbetrieb.services.BackgroundService;
import at.htl.schichtbetrieb.services.Example;

public class StartUpActivity extends AppCompatActivity implements WorkDayFragment.OnFragmentInteractionListener {

    //public static Worker worker1 = new Worker(0,"Hans Peter",false,new Activity("Putzen", new Date(), new Date()));
    //public static Worker worker2 = new Worker(1,"Jakob",false,new Activity("Waschen", new Date(), new Date()));

    private WorkerDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);


        //SqlLite
        dbHelper = new WorkerDBHelper(this.getApplicationContext());
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 0, 0);


        /*ContentResolver contentResolver = getContentResolver();
        contentResolver.insert();

        MediaStore.Images.Media.insertImage(contentResolver, getAssets("worker1.png"))
        */

        FragmentManager fm = getSupportFragmentManager();

        WorkDayFragment workDayFragment = (WorkDayFragment) fm.findFragmentById(R.id.fragment_workday);
        if(workDayFragment == null)
        workDayFragment = new WorkDayFragment();

        fm.beginTransaction().add(R.id.container_main, workDayFragment, null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
