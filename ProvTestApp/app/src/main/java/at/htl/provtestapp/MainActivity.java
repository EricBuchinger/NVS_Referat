package at.htl.provtestapp;

import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.LinkedList;

import at.htl.provtestapp.contracts.WorkerImageProviderContract;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter adapter;
    public static final int LOADER_ID = 1976;

    final String[] from = new String[] {"WIMAGE"};
    //final int[] to = new int[] {R.id.iv_w1}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv_worker1 = (ImageView) findViewById(R.id.iv_w1);
        ImageView iv_worker2 = (ImageView) findViewById(R.id.iv_w2);


        Bitmap worker1Bitmap = null, worker2Bitmap = null;

        //URI: at.htl.schichtbetrieb.providers.workerimageprovider/images
        Uri yourURI = WorkerImageProviderContract.CONTENT_URI;
        //ContentProviderClient client = getContentResolver().acquireContentProviderClient(yourURI);

        ContentResolver cr = getContentResolver();

        Cursor allpics = null;

        allpics = cr.query(yourURI, null, null, null, null); //FIXME failed: permission denied ?
        /*try {
            allpics = client.query(yourURI, null, null, null, null); //getAll
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
        LinkedList<Bitmap> allBitmaps = new LinkedList<>();

        if(allpics != null)
            if(allpics.moveToFirst()){
                do{
                    allBitmaps.add(DbBitmapUtility.getImage(allpics.getBlob(2)));
                }while(allpics.moveToNext());
            }

            allpics.close();
        worker1Bitmap = allBitmaps.get(0);
        worker2Bitmap = allBitmaps.get(1);

        iv_worker1.setImageDrawable(new BitmapDrawable(getResources(), worker1Bitmap));
        iv_worker2.setImageDrawable(new BitmapDrawable(getResources(), worker2Bitmap));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
