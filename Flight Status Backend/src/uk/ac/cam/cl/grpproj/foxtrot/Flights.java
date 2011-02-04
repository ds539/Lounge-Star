package uk.ac.cam.cl.grpproj.foxtrot;

import android.net.Uri;
import android.provider.BaseColumns;

public class Flights implements BaseColumns {
	public static final String AUTH = "uk.ac.cam.cl.grpproj.foxtrot";
	// This class cannot be instantiated
    private Flights() {}

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTH + "/flightstats");

    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of notes.
     */
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.loungestar.stats";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.loungestar.stats";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "modified DESC";

    /**
     * The title of the note
     * <P>Type: TEXT</P>
     */
    public static final String TITLE = "title";

    /**
     * The note itself
     * <P>Type: TEXT</P>
     */
    public static final String NOTE = "note";

    /**
     * The timestamp for when the note was created
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
    public static final String CREATED_DATE = "created";

    /**
     * The timestamp for when the note was last modified
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
    public static final String MODIFIED_DATE = "modified";
}
