package at.htl.schichtbetrieb.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.util.LinkedList;

import at.htl.schichtbetrieb.contracts.WorkerDBContract;
import at.htl.schichtbetrieb.entities.Activity;
import at.htl.schichtbetrieb.entities.Worker;
import at.htl.schichtbetrieb.entities.WorkerImage;

import static at.htl.schichtbetrieb.contracts.WorkerDBContract.*;

/**
 * Created by phili on 22.10.2017.
 */

public class DbHelperAgain extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public DbHelperAgain(Context context) {
        super(context, WorkerDBContract.DATABASE_NAME, null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ACTIVITIES);
        sqLiteDatabase.execSQL(CREATE_WORKERS);
        sqLiteDatabase.execSQL(CREATE_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE + TABLE_NAME_IMAGES);
        sqLiteDatabase.execSQL(DROP_TABLE + TABLE_NAME_WORKERS);
        sqLiteDatabase.execSQL(DROP_TABLE + TABLE_NAME_ACTIVITIES);
        onCreate(sqLiteDatabase);
    }

    public void refreshDatabase(){
        db.close();
        db = getWritableDatabase();
    }

    public void onDestroy(){
        db.close();
    }

    public long insertData(String table, ContentValues cv){
        return db.insert(table, null, cv);
    }

    public LinkedList<Activity> getAllActivities(){
        LinkedList<Activity> listToReturn = new LinkedList<>();
        Cursor c = db.query(TABLE_NAME_ACTIVITIES, null, null, null, null, null, null); // select *

        if(c != null)
            if(c.moveToFirst()){
                do{
                    listToReturn.add(new Activity(c.getInt(0), c.getString(1), Date.valueOf(c.getString(2)), Date.valueOf(c.getString(3))));
                } while(c.moveToNext());

                c.close();
            }
            refreshDatabase();
        return listToReturn;
    }
    public LinkedList<Worker> getAllWorkers(LinkedList<Activity> allActivities){
        LinkedList<Worker> listToReturn = new LinkedList<>();
        Cursor c = db.query(TABLE_NAME_WORKERS, null, null, null, null, null, null); // select *

        if(c != null)
            if(c.moveToFirst()){
                do{
                    listToReturn.add(new Worker(c.getInt(0), c.getString(1), c.getInt(2) == 1, allActivities.get(c.getInt(3))));
                } while(c.moveToNext());

                c.close();
            }
            refreshDatabase();
        return listToReturn;
    }

    public LinkedList<WorkerImage> getAllWorkerImages(){
        LinkedList<WorkerImage> listToReturn = new LinkedList<>();
        Cursor c = db.query(TABLE_NAME_IMAGES, null, null, null, null, null, null); // select *

        if(c != null)
            if(c.moveToFirst()){
                do{
                    listToReturn.add(new WorkerImage(c.getInt(0), c.getString(1), c.getBlob(2)));
                } while(c.moveToNext());

                c.close();
            }
        return listToReturn;
    }

}
