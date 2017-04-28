package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Activitee;

/**
 * Created by BARA' on 22/12/2016.
 */
public class ActiviteesDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ActiviteesDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addActivitee(Activitee activitee) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(dbHelper.COL_ACT_NAME, activitee.getName());
        return db.insert(dbHelper.TABLE_ACTIVITEES, null, cvalues);
    }

    public int updateActivitee(Activitee activitee) {
        String whereClause = dbHelper.COL_ACT_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(activitee.get_id())};
        ContentValues cValues = new ContentValues();
        cValues.put(DBHelper.COL_ACT_NAME,activitee.getName());
        return db.update(DBHelper.TABLE_ACTIVITEES, cValues, whereClause, whereArgs);
    }
    public void deleteActivitee(Activitee activitee) {
        String WHERE_CLAUSE = dbHelper.COL_ACT_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(activitee.get_id())};
        db.delete(dbHelper.TABLE_ACTIVITEES, WHERE_CLAUSE, WHERE_ARGS);
    }

    public List<Activitee> getActivitees() {
        List<Activitee> activitees = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_ACTIVITEES, null, null, null, null, null, dbHelper.COL_ACT_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Activitee activitee= cursorToActivitee(cursor);
            activitees.add(activitee);
            cursor.moveToNext();
        }
        cursor.close();
        return activitees;
    }

    private Activitee cursorToActivitee(Cursor cursor) {
        Activitee activitee= new Activitee();
        activitee.set_id(cursor.getInt(0));
        activitee.setName(cursor.getString(cursor.getColumnIndex(dbHelper.COL_ACT_NAME)));
        return activitee;
    }
}
