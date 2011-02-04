package uk.ac.cam.cl.grpproj.foxtrot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FlightDB extends SQLiteOpenHelper {
	private static final String TAG = "FlightDB";
	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "loungestar";
	public static final String DATABASE_TABLE_NAME = "flightstats";
	public static final String FLIGHT_NO = "flightNo";
	public static final String EXPECTED_TIME = "expectedTime";
	public static final String DESTINATION = "destination";
	public static final String AIRPORT = "airport";
	public static final String GATE_NO = "gateNo";
	private static final String DATABASE_TABLE_CREATE =
		"CREATE TABLE " + DATABASE_TABLE_NAME + " (" +
		FLIGHT_NO + " TEXT, " +
		EXPECTED_TIME + " DATETIME, " + AIRPORT + " TEXT, " + DESTINATION + " TEXT, " + GATE_NO + " TEXT);";

	public FlightDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS flightstats");
		onCreate(db);
	}

}
