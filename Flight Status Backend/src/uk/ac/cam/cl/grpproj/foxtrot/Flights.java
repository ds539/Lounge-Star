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
	 * The number of the flight.
	 * <P>Type: TEXT</P>
	 */
	public static final String FLIGHT_NO = "flightNo";

	/**
	 * The expected time.
	 * <P>Type: DATETIME</P>
	 */
	public static final String EXPECTED_TIME = "expectedTime";

	/**
	 * The destination airport.
	 * <P>Type: TEXT</P>
	 */
	public static final String DESTINATION = "destination";

	/**
	 * The source airport.
	 * <P>Type: TEXT</P>
	 */
	public static final String AIRPORT = "airport";

	/**
	 * The number of the gate.
	 * <P>Type: TEXT</P>
	 */
	public static final String GATE_NO = "gateNo";

	/**
	 * The default sort order for this table
	 */
	public static final String DEFAULT_SORT_ORDER = EXPECTED_TIME + " DESC";
}
