package at.htl.schichtbetrieb.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import at.htl.schichtbetrieb.R;
import at.htl.schichtbetrieb.dataaccess.DbBitmapUtility;
import at.htl.schichtbetrieb.dataaccess.WorkerDBHelper;
import at.htl.schichtbetrieb.entities.Activity;
import at.htl.schichtbetrieb.entities.Worker;
import at.htl.schichtbetrieb.services.BackgroundService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkDayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkDayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public ImageView iv_worker1, iv_worker2;
    public CheckBox cb_worker1_working, cb_worker2_working;
    public TextView tv_workday_header;
    public Worker worker1, worker2;

    WorkerDBHelper dbHelper;

    Bitmap worker1bitmap, worker2bitmap;


    public static int minutes = 0;


    public WorkDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkDayFragment newInstance(String param1, String param2) {
        WorkDayFragment fragment = new WorkDayFragment();
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
        View v = inflater.inflate(R.layout.fragment_work_day, container, false);
        IntentFilter statusIntentFilter = new IntentFilter("TimerBroadcast");

        DownloadStateReceiver mDownloadStateReceiver =
                new DownloadStateReceiver();
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mDownloadStateReceiver,statusIntentFilter);

        dbHelper = new WorkerDBHelper(getContext());

        initializeDataBase();

        iv_worker1 = v.findViewById(R.id.iv_worker1);
        iv_worker2 = v.findViewById(R.id.iv_worker2);
        tv_workday_header = v.findViewById(R.id.tv_workDayHeader);
        cb_worker1_working = v.findViewById(R.id.work1Cb);
        cb_worker2_working = v.findViewById(R.id.worker2Cb);

        Intent intent = new Intent(getContext(),BackgroundService.class);
        intent.setData(Uri.parse(String.valueOf(Calendar.getInstance().getTimeInMillis())));
        getActivity().startService(intent);

        //load images from database
        worker1bitmap = dbHelper.getImageById(1); //starts at 1
        worker2bitmap = dbHelper.getImageById(2);

        //region setPictures
        // get input stream
        //InputStream ims = getActivity().getAssets().open("worker1.png");
        // load image as Drawable
        //Drawable d = Drawable.createFromStream(ims, null); TODO
        // set image to ImageView
        Drawable d = new BitmapDrawable(getResources(), worker1bitmap);
        iv_worker1.setImageDrawable(d);

        //ims = getActivity().getAssets().open("worker2.jpg");
        //d = Drawable.createFromStream(ims, null);

        d = new BitmapDrawable(getResources(), worker2bitmap);
        iv_worker2.setImageDrawable(d);
        //ims .close();

        //endregion
        return v;
    }
    private void initializeDataBase(){
        LinkedList<Activity> allActivities = (LinkedList<Activity>) dbHelper.getAllActivities();

        if(allActivities == null || allActivities.size() == 0) {
            long now = Calendar.getInstance().getTimeInMillis();
            Activity activity1 = new Activity(0, "Leichte Arbeit", new Date(now), new Date(now + 5 * 60 * 1000)); //now + 5 minuten // FIXME immer gleiche zeit bei beiden
            Activity activity2 = new Activity(0, "Schwere Arbeit", new Date(now + 20 * 60 * 1000), new Date(now + 60 * 60 * 1000)); //now + 5 minuten | now + 60 minuten
            dbHelper.insertActivty(activity1);
            dbHelper.insertActivty(activity2);
            allActivities = (LinkedList<Activity>) dbHelper.getAllActivities(); //refreshed
        }


            LinkedList<Worker> allWorkers = (LinkedList<Worker>) dbHelper.getAllWorkers();
            if(allWorkers == null || allWorkers.size() == 0) //no workers in database
            {
                dbHelper.insertWorker(new Worker(0, "Eric", true, allActivities.get(0)));
                dbHelper.insertWorker(new Worker(0, "Philipp", true, allActivities.get(1)));
            }

            //allWorkers.forEach(w -> Log.e("worker", w.toString()));

            allWorkers = (LinkedList<Worker>) dbHelper.getAllWorkers(); //refreshed

            for(int i = 0; i < allWorkers.size(); i++)
                Log.e("Worker", allWorkers.get(i).toString());


            worker1 = allWorkers.get(0);
            worker2 = allWorkers.get(1);

            //TODO Share with contentprovider

            //getContext().getAssets().open()

            Bitmap pic_w1 = getBitmapFromAsset(getContext(), "worker1.png");
            Bitmap pic_w2 = getBitmapFromAsset(getContext(), "worker2.jpg");

            dbHelper.insertImage("Worker1Pic", DbBitmapUtility.getBytes(pic_w1));
            dbHelper.insertImage("Worker2Pic", DbBitmapUtility.getBytes(pic_w2));
        dbHelper.close();

    }

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateUI(long currentMillis, String displayTime){ //FIXME Hier wird überprüft ob er gerade arbeitet, müsste eigentlich passen aber checks trotzdem
        worker1.setWorking(false);
        worker2.setWorking(false);
        long t1From = worker1.getActivity().getFrom().getTime();
        long t2From = worker2.getActivity().getFrom().getTime();
        long t1Until = worker1.getActivity().getTil().getTime();
        long t2Until = worker2.getActivity().getTil().getTime() + 1000000;


        if(t1From<=currentMillis && t1Until >= currentMillis){
            worker1.setWorking(true);
        }
        if(t2From<=currentMillis && t2Until >= currentMillis){
            worker2.setWorking(true);
        }


        cb_worker1_working.setEnabled(true);
        cb_worker2_working.setEnabled(true);
        cb_worker1_working.setChecked(worker1.isWorking());
        cb_worker2_working.setChecked(worker2.isWorking());
        tv_workday_header.setText(displayTime);
    }
    private class DownloadStateReceiver extends BroadcastReceiver
    {
        // Prevents instantiation
        private DownloadStateReceiver() {
        }
        // Called when the BroadcastReceiver gets an Intent it's registered to receive
        @Override
        public void onReceive(Context context, Intent intent) {
            Long milliseconds = intent.getLongExtra("Time",0);
            String time = getDate(milliseconds,"dd.MM.YYYY hh:mm");
            updateUI(milliseconds,time);
        }
        private String getDate(long milliSeconds, String dateFormat)
        {

            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        }
    }
}
