package at.htl.schichtbetrieb.contracts;
import android.net.Uri;

/**
 * Created by phili on 22.10.2017.
 */

public class WorkerImageProviderContract {
    public static final String AUTHORITY = "at.htl.schichtbetrieb.providers.WorkerImageProvider";
    public static final String BASE_PATH = "images";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
}
