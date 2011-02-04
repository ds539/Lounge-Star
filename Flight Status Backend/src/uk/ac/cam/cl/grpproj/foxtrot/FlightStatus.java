package uk.ac.cam.cl.grpproj.foxtrot;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class FlightStatus extends ContentProvider {
	private FlightDB dbHelper;
	private static final UriMatcher sUriMatcher;
	public static final int FLIGHTS = 1;
	public static final int FLIGHT_ID = 2;
	private static HashMap<String, String> dbProjectionMap;

	@Override
	public boolean onCreate() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db == null)
			return false;
		else
			return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case FLIGHTS:
			return Flights.CONTENT_TYPE;

		case FLIGHT_ID:
			return Flights.CONTENT_ITEM_TYPE;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		// Validate the requested uri
		if (sUriMatcher.match(uri) != FLIGHTS) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		}
		else {
			// Cannot insert blank row.
			return null;
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(FlightDB.DATABASE_TABLE_NAME, null, values);
		if (rowId > 0) {
			Uri noteUri = ContentUris.withAppendedId(Flights.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(noteUri, null);
			return noteUri;
		}
		// Something went wrong.
		else return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(FlightDB.DATABASE_TABLE_NAME);

		switch (sUriMatcher.match(uri)) {
		case FLIGHTS:
			qb.setProjectionMap(dbProjectionMap);
			break;

		case FLIGHT_ID:
			qb.setProjectionMap(dbProjectionMap);
			qb.appendWhere(Flights._ID + "=" + uri.getPathSegments().get(1));
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// If no sort order is specified use the default
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = Flights.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		// Get the database and run the query
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

		// Tell the cursor what uri to watch, so it knows when its source data changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
		case FLIGHTS:
			count = db.update(FlightDB.DATABASE_TABLE_NAME, values, where, whereArgs);
			break;

		case FLIGHT_ID:
			String noteId = uri.getPathSegments().get(1);
			count = db.update(FlightDB.DATABASE_TABLE_NAME, values, Flights._ID + "=" + noteId
					+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
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
			count = db.delete(FlightDB.DATABASE_TABLE_NAME, Flights._ID + "=" + flightId
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
		sUriMatcher.addURI(Flights.AUTH, "flightstats", FLIGHTS);
		sUriMatcher.addURI(Flights.AUTH, "flightstats/#", FLIGHT_ID);
	}
}
