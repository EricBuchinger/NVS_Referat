package at.htl.androidlivecoding.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import at.htl.androidlivecoding.R;
import at.htl.androidlivecoding.entities.Activity;
import at.htl.androidlivecoding.entities.Worker;
import at.htl.androidlivecoding.entities.WorkerImage;

public class WorkdayFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private LinkedList<Worker> allWorkers;
    private LinkedList<Activity> allActivities;
    private LinkedList<WorkerImage> allWorkerImages;

    public ImageView iv_worker1, iv_worker2;
    public CheckBox cb_worker1_working, cb_worker2_working;
    public TextView tv_workday_header, tv_workers, tv_activities;

    public WorkdayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_workday, container, false);
    }

    private void initializeDB() {

    }

    private void getDataFromDB(){

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
