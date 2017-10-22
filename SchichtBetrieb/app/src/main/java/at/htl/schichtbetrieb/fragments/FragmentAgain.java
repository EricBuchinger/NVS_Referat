package at.htl.schichtbetrieb.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Objects;

import at.htl.schichtbetrieb.R;
import at.htl.schichtbetrieb.contracts.WorkerDBContract;
import at.htl.schichtbetrieb.dataaccess.DbBitmapUtility;
import at.htl.schichtbetrieb.dataaccess.DbHelperAgain;
import at.htl.schichtbetrieb.entities.Activity;
import at.htl.schichtbetrieb.entities.Worker;
import at.htl.schichtbetrieb.entities.WorkerImage;

import static at.htl.schichtbetrieb.contracts.WorkerDBContract.*;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAgain.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAgain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAgain extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private DbHelperAgain dbHelperAgain;
    private TextView tv_workers, tv_activities;
    private ImageView iv_w1, iv_w2;

    private LinkedList<Worker> allWorkers;
    private LinkedList<Activity> allActivities;
    private LinkedList<WorkerImage> allWorkerImages;
    private Worker workerToUpdate;

    public FragmentAgain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAgain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAgain newInstance(String param1, String param2) {
        FragmentAgain fragment = new FragmentAgain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_again, container, false);

        dbHelperAgain = new DbHelperAgain(getContext());

        initializeDB();

        //play around with the CRUD
        dbHelperAgain.deleteData(WorkerDBContract.TABLE_NAME_WORKERS, 1);
        dbHelperAgain.updateData(WorkerDBContract.TABLE_NAME_WORKERS, workerToUpdate.toCV(), workerToUpdate.getId());

        getDataFromDB();

        tv_activities = v.findViewById(R.id.tv_activities);
        tv_workers = v.findViewById(R.id.tv_workers);
        iv_w1 = v.findViewById(R.id.iv_w1);
        iv_w2 = v.findViewById(R.id.iv_w2);


        tv_activities.setText(showActivities(allActivities));
        tv_workers.setText(showWorkers(allWorkers));
        iv_w1.setImageDrawable(new BitmapDrawable(DbBitmapUtility.getImage(allWorkerImages.get(0).getWimage())));
        iv_w2.setImageDrawable(new BitmapDrawable(DbBitmapUtility.getImage(allWorkerImages.get(1).getWimage())));


        return v;
    }

    private String showActivities(LinkedList<Activity> list){
        String stringToReturn = "";
        for(int i = 0; i < list.size(); i ++)
        {
            stringToReturn += (list.get(i).toString()) + "\n";
        }
        return stringToReturn;
    }

    private String showWorkers(LinkedList<Worker> list){
        String stringToReturn = "";
        for(int i = 0; i < list.size(); i ++)
        {
            stringToReturn += (list.get(i).toString()) + "\n";
        }
        return stringToReturn;
    }

    private void getDataFromDB() {
        allActivities = dbHelperAgain.getAllActivities();
        allWorkers = dbHelperAgain.getAllWorkers(allActivities);
        allWorkerImages = dbHelperAgain.getAllWorkerImages();
    }

    private void initializeDB() {
        long now = Calendar.getInstance().getTimeInMillis();
        Activity activity1 = new Activity(0, "Leichte Arbeit", new Date(now), new Date(now + 5 * 60 * 1000)); //now + 5 minuten
        Activity activity2 = new Activity(0, "Schwere Arbeit", new Date(now + 20 * 60 * 1000), new Date(now + 60 * 60 * 1000));

        Worker worker1 = new Worker(0, "Eric", true, activity1);
        Worker worker2 = new Worker(0, "Philipp", true, activity2);
        Worker worker3 = new Worker(0, "Jakob", true, activity2);
        Worker worker4 = new Worker(0, "Moritz", true, activity1);
        Worker worker5 = new Worker(0, "Thomas", true, activity2);

        workerToUpdate = worker3;
        workerToUpdate.setName("UPDATED_JAKOB");

        Bitmap pic_w1 = DbBitmapUtility.getBitmapFromAsset(getContext(), "worker1.png");
        Bitmap pic_w2 = DbBitmapUtility.getBitmapFromAsset(getContext(), "worker2.jpg");

        WorkerImage wimage1 = new WorkerImage(0, worker1.getName(), DbBitmapUtility.getBytes(pic_w1));
        WorkerImage wimage2 = new WorkerImage(0, worker2.getName(), DbBitmapUtility.getBytes(pic_w2));

        dbHelperAgain.insertData(TABLE_NAME_ACTIVITIES, activity1.toCV());
        dbHelperAgain.insertData(TABLE_NAME_ACTIVITIES, activity2.toCV());
        dbHelperAgain.insertData(TABLE_NAME_IMAGES, wimage1.toCV());
        dbHelperAgain.insertData(TABLE_NAME_IMAGES, wimage2.toCV());
        dbHelperAgain.insertData(TABLE_NAME_WORKERS, worker1.toCV());
        dbHelperAgain.insertData(TABLE_NAME_WORKERS, worker2.toCV());
        dbHelperAgain.insertData(TABLE_NAME_WORKERS, worker2.toCV());
        dbHelperAgain.insertData(TABLE_NAME_WORKERS, worker3.toCV());
        dbHelperAgain.insertData(TABLE_NAME_WORKERS, worker4.toCV());
        dbHelperAgain.insertData(TABLE_NAME_WORKERS, worker5.toCV());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
