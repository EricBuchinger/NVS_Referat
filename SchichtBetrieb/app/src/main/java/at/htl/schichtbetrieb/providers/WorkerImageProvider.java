package at.htl.schichtbetrieb.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import at.htl.schichtbetrieb.contracts.WorkerImageProviderContract;
import at.htl.schichtbetrieb.dataaccess.WorkerDBHelper;

/**
 * Created by phili on 20.10.2017.
 */

public class WorkerImageProvider extends ContentProvider {

    private WorkerDBHelper dbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*static {
        sUriMatcher.addURI(WorkerImageProviderContract.AUTHORITY, WorkerImageProviderContract.BASE_PATH, 1);
        sUriMatcher.addURI(WorkerImageProviderContract.AUTHORITY, WorkerImageProviderContract.BASE_PATH + "/#", 2);
    }*/

    @Override
    public boolean onCreate() {
        dbHelper = new WorkerDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder)
    {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor c = database.query("WORKERIMAGE", projection, selection, selectionArgs, sortOrder, "", "");
        c.setNotificationUri(getContext().getContentResolver(), uri);
        database.close();
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = 0;
        //SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch(sUriMatcher.match(uri)){
            case 1:
                id = dbHelper.addImage(contentValues);
                break;

            default:
                throw new IllegalArgumentException("wrong uri");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(WorkerImageProviderContract.BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
