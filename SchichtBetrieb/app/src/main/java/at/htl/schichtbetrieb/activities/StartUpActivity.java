package at.htl.schichtbetrieb.activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.LinkedList;

import at.htl.schichtbetrieb.R;
import at.htl.schichtbetrieb.dataaccess.WorkerDBHelper;
import at.htl.schichtbetrieb.entities.Worker;
import at.htl.schichtbetrieb.fragments.WorkDayFragment;

public class StartUpActivity extends AppCompatActivity implements WorkDayFragment.OnFragmentInteractionListener {

    private WorkerDBHelper dbHelper;
    public static Worker worker1;
    public static Worker worker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        dbHelper = new WorkerDBHelper(this.getApplicationContext());

        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 0, 0);
        initialize();

        FragmentManager fm = getSupportFragmentManager();

        WorkDayFragment workDayFragment = (WorkDayFragment) fm.findFragmentById(R.id.fragment_workday);
        if(workDayFragment == null)
        workDayFragment = new WorkDayFragment();

        fm.beginTransaction().add(R.id.container_main, workDayFragment, null).commit();
    }

    private void initialize() {
        if(worker1 == null || worker2 == null){
            LinkedList<Worker> allWorkers = (LinkedList<Worker>) dbHelper.getAllWorkers();

            if(allWorkers.size() == 0) //no data in database
            {
                dbHelper.insertData("TestWorker1");
                dbHelper.insertData("TestWorker2");
                initialize();
                return;
            }
            worker1 = allWorkers.get(0);
            worker2 = allWorkers.get(1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
