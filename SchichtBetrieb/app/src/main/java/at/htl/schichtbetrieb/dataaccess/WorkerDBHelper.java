package at.htl.schichtbetrieb.dataaccess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import at.htl.schichtbetrieb.entities.Worker;


/**
 * Created by phili on 19.10.2017.
 */

public class WorkerDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "workers.db";
    public WorkerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = "CREATE TABLE WORKER(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT)";
        db.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS WORKER");
        onCreate(db);
    }

    public boolean insertData(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", name);
        return db.insert("WORKER", null, contentValues) != -1;
    }

    public List<Worker> getAllWorkers(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM WORKER", null);
        LinkedList<Worker> workers = new LinkedList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    workers.add(new Worker(cursor.getString(1))); //1 because 0 is the unique id
                } while (cursor.moveToNext());

            }
            cursor.close();
        }
        db.close();
        return workers;
    }
}
