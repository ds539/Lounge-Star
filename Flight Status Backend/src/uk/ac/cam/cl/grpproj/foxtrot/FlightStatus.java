package uk.ac.cam.cl.grpproj.foxtrot;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class FlightStatus extends ContentProvider {
	private FlightDB dbHelper;
	private static final String AUTH = "uk.ac.cam.cl.grpproj.foxtrot";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTH + "/flightstats");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.loungestar.stats";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.loungestar.stats";
	private static final UriMatcher sUriMatcher;
	public static final int FLIGHTS = 1;
	public static final int FLIGHT_ID = 2;

	@Override
	public boolean onCreate() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db == null)
			return false;
		else
			return true;
	}

	@Override
	public String getType(Uri arg0) {
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		return null;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case FLIGHTS:
			count = db.delete(FlightDB.DATABASE_TABLE_NAME, where, whereArgs);
			break;

		case FLIGHT_ID:
			String flightId = uri.getPathSegments().get(1);
			count = db.delete(FlightDB.DATABASE_TABLE_NAME, "_ID=" + flightId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTH, "flightstats", FLIGHTS);
		sUriMatcher.addURI(AUTH, "flightstats/#", FLIGHT_ID);
	}
}
