package at.htl.androidlivecoding.contracts;
import android.net.Uri;


public class WorkerImageProviderContract {
    public static final String AUTHORITY = "at.htl.androidlivecoding.providers.WorkerImageProvider";
    public static final String BASE_PATH = "images";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
}