package at.htl.schichtbetrieb.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import at.htl.schichtbetrieb.R;
import at.htl.schichtbetrieb.activities.StartUpActivity;
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
    public Worker worker1 = StartUpActivity.worker1;
    public Worker worker2 = StartUpActivity.worker2;


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


        iv_worker1 = v.findViewById(R.id.iv_worker1);
        iv_worker2 = v.findViewById(R.id.iv_worker2);
        tv_workday_header = v.findViewById(R.id.tv_workDayHeader);
        cb_worker1_working = v.findViewById(R.id.work1Cb);
        cb_worker2_working = v.findViewById(R.id.worker2Cb);

        Intent intent = new Intent(getContext(),BackgroundService.class);
        intent.setData(Uri.parse("0"));
        getActivity().startService(intent);
        try
        {
            // get input stream
            InputStream ims = getActivity().getAssets().open("worker1.png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            iv_worker1.setImageDrawable(d);

            ims = getActivity().getAssets().open("worker2.jpg");
            d = Drawable.createFromStream(ims, null);

            iv_worker2.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ignored)
        {
            Log.e("Workday","Error in Reading Inputstream!");
            ignored.printStackTrace();
        }
        return v;
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

    public void updateUI(boolean worker1working, boolean worker2working,String time){ //TODO call by Service (timer)
        cb_worker1_working.setEnabled(worker1working);
        cb_worker2_working.setEnabled(worker2working);
        tv_workday_header.setText(time);
    }
    private class DownloadStateReceiver extends BroadcastReceiver
    {
        // Prevents instantiation
        private DownloadStateReceiver() {
        }
        // Called when the BroadcastReceiver gets an Intent it's registered to receive
        @Override
        public void onReceive(Context context, Intent intent) {
            int milliseconds = intent.getIntExtra("Time",0);
            String time = getDate(milliseconds, "hh:mm");
            updateUI(false,true,time);
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
