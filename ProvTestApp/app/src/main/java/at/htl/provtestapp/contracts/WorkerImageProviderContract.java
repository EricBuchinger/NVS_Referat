package at.htl.provtestapp.contracts;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by phili on 22.10.2017.
 */

public class WorkerImageProviderContract {
    public static final String AUTHORITY = "at.htl.schichtbetrieb.providers.workerimageprovider";
    public static final String BASE_PATH = "images";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/images";

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/images";
}
