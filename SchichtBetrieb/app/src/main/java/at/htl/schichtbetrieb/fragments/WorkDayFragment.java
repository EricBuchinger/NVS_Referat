package at.htl.schichtbetrieb.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import at.htl.schichtbetrieb.R;
import at.htl.schichtbetrieb.activities.StartUpActivity;
import at.htl.schichtbetrieb.entities.Worker;

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
    public ImageView iv_worker1_working, iv_worker2_working;
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

        iv_worker1 = v.findViewById(R.id.iv_worker1);
        iv_worker2 = v.findViewById(R.id.iv_worker2);
        iv_worker1_working = v.findViewById(R.id.iv_worker1_working);
        iv_worker2_working = v.findViewById(R.id.iv_worker2_working);
        tv_workday_header = v.findViewById(R.id.tv_workDayHeader);


        Timer workMinutesTimer = new Timer("WorkMinutesTimer");
        workMinutesTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                WorkDayFragment.minutes++;


                if(minutes == 12)
                    worker1.setWorking(false);

                updateUI(worker1.isWorking(), worker2.isWorking());
            }
        }, 10, 10);

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
        catch(IOException ex)
        {
        }


        //iv_worker1.setima
        return v;
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

    public void updateUI(boolean worker1working, boolean worker2working){ //TODO call by Service (timer)
        iv_worker1_working.setEnabled(worker1working);
        iv_worker2_working.setEnabled(worker2working);
    }
}
