package at.htl.schichtbetrieb.dataaccess;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import at.htl.schichtbetrieb.entities.Activity;
import at.htl.schichtbetrieb.entities.Worker;


/**
 * Created by phili on 19.10.2017.
 */

public class WorkerDBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "workers.db";
    static final String TABLE_NAME_WORKERS = "WORKER";
    static final String TABLE_NAME_ACTIVITIES = "ACTIVITY";

    public WorkerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String firstStatement = "CREATE TABLE " + TABLE_NAME_ACTIVITIES + "(ACTIVITY_ID INTEGER PRIMARY KEY AUTOINCREMENT, UNTIL TEXT, _FROM TEXT)";
        String createStatement = "CREATE TABLE " + TABLE_NAME_WORKERS + "(WORKER_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, WORKING NUMBER, ACTIVITY_ID NUMBER, " +
                "FOREIGN KEY(ACTIVITY_ID) references ACTIVITY(ACTIVITY_ID)" +
                ")";

        String imageStatement = "CREATE TABLE WORKERIMAGE (WIMAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, WIMAGE BLOB)";

        db.execSQL(firstStatement);
        db.execSQL(createStatement);
        db.execSQL(imageStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS WORKER");
        db.execSQL("DROP TABLE IF EXISTS ACTIVITY");
        db.execSQL("DROP TABLE IF EXISTS WORKERIMAGE");
        onCreate(db);
    }

    public boolean insertWorker(Worker worker) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", worker.getName());
        contentValues.put("WORKING", worker.isWorking());//TODO CHECK IF BOOLEAN IS CASTED TO INT
        contentValues.put("ACTIVITY_ID", worker.getActivity().getId());
        return db.insert("WORKER", null, contentValues) != -1;
    }

    public boolean insertActivty(Activity activity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UNTIL", String.valueOf(activity.getTil()));
        contentValues.put("_FROM", String.valueOf(activity.getFrom()));
        return db.insert("ACTIVITY", null, contentValues) != -1;
    }

    public boolean deleteWorker(int workerId) {
        if (findWorkerById(workerId) == null) return false; //not in database
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM WORKER WHERE WORKER_ID = " + workerId, null);

        return findWorkerById(workerId) == null; //confirm deleted
    }

    private Worker findWorkerById(int workerId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WORKER WHERE WORKER_ID = " + workerId, null);
        Worker workerToReturn = null;
        if (cursor != null) {
            cursor.moveToFirst();
            Cursor activityCursor = db.rawQuery("SELECT * FROM ACTIVITY WHERE ACTIVITY_ID = " + cursor.getInt(3), null);
            workerToReturn = new Worker(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1, new Activity(activityCursor.getInt(0), activityCursor.getString(1),
                    Date.valueOf(activityCursor.getString(1)), Date.valueOf(activityCursor.getString(2))));
            cursor.close();
            activityCursor.close();
        }
        db.close();

        return workerToReturn;
    }

    private Activity findActivityById(int actId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ACTIVITY WHERE ACTIVITY_ID = " + actId, null);
        Activity actToReturn = null;
        if (cursor != null) {
            cursor.moveToFirst();
            actToReturn = new Activity(cursor.getInt(0), cursor.getString(1),
                    Date.valueOf(cursor.getString(1)), Date.valueOf(cursor.getString(2)));
            cursor.close();
        }
        db.close();

        return actToReturn;
    }

    public List<Activity> getAllActivities() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ACTIVITY", null);
        LinkedList<Activity> activities = new LinkedList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    activities.add(new Activity(cursor.getInt(0), cursor.getString(1),
                            Date.valueOf(cursor.getString(1)), Date.valueOf(cursor.getString(2))));
                } while (cursor.moveToNext());

            }
            cursor.close();
        }
        db.close();
        return activities;
    }

    public List<Worker> getAllWorkers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WORKER", null);
        LinkedList<Worker> workers = new LinkedList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    List<Activity> activities = getAllActivities();
                        for(Activity act : activities) {
                            if (act.getId() == cursor.getInt(3)) {
                                workers.add(new Worker(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1, act));
                            }
                    }
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return workers;
    }

    public void insertImage(String name, byte[] image) throws SQLiteException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put("NAME",    name);
        cv.put("WIMAGE",   image);
        db.insert( "WORKERIMAGE", null, cv);
        db.close();
    }

    public Bitmap getImageById(int wimage_id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WORKERIMAGE WHERE WIMAGE_ID = " + wimage_id, null);

        if(cursor != null)
            if(cursor.moveToFirst()){
                Bitmap b = DbBitmapUtility.getImage(cursor.getBlob(2)); //directly converted to bitmap
                db.close();
                return b;
            }

            db.close();
        return null;
    }

    public List<Bitmap> getAllImages(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WORKERIMAGE", null);
        List<Bitmap> bitmaps = new LinkedList<>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    bitmaps.add(DbBitmapUtility.getImage(cursor.getBlob(2)));
                } while (cursor.moveToNext());
            }
        }

        return bitmaps;
    }

    public long addImage(ContentValues cv){
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("WORKERIMAGE", null, cv);
    }

        /*LinkedList<Worker> workers = new LinkedList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM WORKER", null);
        for (int i = 0; i < cursor.getInt(0); i++) {
            workers.add(findWorkerById(i));
        }
        cursor.close();

        return workers;*/


    /*public List<Activity> getAllActivities() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ACTIVITY", null);
        LinkedList<Activity> activities = new LinkedList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    activities.add(new Activity(cursor.getString(1), Date.valueOf(cursor.getString(2)), Date.valueOf(cursor.getString(3))));
                } while (cursor.moveToNext());

            }
            cursor.close();
        }
        db.close();
        return activities;

    }*/

}

