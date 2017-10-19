package at.htl.schichtbetrieb.activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import at.htl.schichtbetrieb.R;
import at.htl.schichtbetrieb.entities.Worker;
import at.htl.schichtbetrieb.fragments.WorkDayFragment;

public class StartUpActivity extends AppCompatActivity implements WorkDayFragment.OnFragmentInteractionListener {

    public static Worker worker1 = new Worker("Jakob");
    public static Worker worker2 = new Worker("Moritz");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

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
