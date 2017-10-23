package at.htl.androidlivecoding.contracts;

/**
 * Created by phili on 22.10.2017.
 */

public class WorkerDBContract {
    public static final String DATABASE_NAME = "workers.db";
    public static final String TABLE_NAME_WORKERS = "WORKER";
    public static final String TABLE_NAME_ACTIVITIES = "ACTIVITY";
    public static final String TABLE_NAME_IMAGES = "WORKERIMAGE";

    public static final String CREATE_ACTIVITIES = "CREATE TABLE " + TABLE_NAME_ACTIVITIES + "(ACTIVITY_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, UNTIL TEXT, _FROM TEXT)";
    public static final String CREATE_WORKERS = "CREATE TABLE " + TABLE_NAME_WORKERS + "(WORKER_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, WORKING NUMBER, ACTIVITY_ID NUMBER, FOREIGN KEY(ACTIVITY_ID) references ACTIVITY(ACTIVITY_ID))";
    public static final String CREATE_IMAGES = "CREATE TABLE " + TABLE_NAME_IMAGES + " (WIMAGE_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, WIMAGE BLOB)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
}
