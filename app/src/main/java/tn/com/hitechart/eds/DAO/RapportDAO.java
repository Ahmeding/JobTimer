package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Rapport;
import tn.com.hitechart.eds.Entity.User;

/**
 * Created by BARA' on 17/01/2017.
 */

public class RapportDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public RapportDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addRapport(Rapport rapport) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(dbHelper.COL_RAPPORT_DATE, rapport.getDateOfSending());
        cvalues.put(dbHelper.COL_RAPPORT_ISSENDED, rapport.isSend());
        cvalues.put(dbHelper.COL_RAPPORT_USER_ID, rapport.getUserId());
        cvalues.put(dbHelper.COL_RAPPORT_DATE_OF_CREATION, rapport.getDateofCreation());

        return db.insert(dbHelper.TABLE_RAPPORT, null, cvalues);
    }


    public int updateRapport(Rapport rapport) {
        String whereClause = dbHelper.COL_RAPPORT_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(rapport.get_id())};
        ContentValues cvalues = new ContentValues();

        cvalues.put(dbHelper.COL_RAPPORT_DATE, rapport.getDateOfSending());
        cvalues.put(dbHelper.COL_RAPPORT_ISSENDED, rapport.isSend());

        return db.update(DBHelper.TABLE_RAPPORT, cvalues, whereClause, whereArgs);
    }

    public void deleteRapport(Rapport rapport) {
        String WHERE_CLAUSE = dbHelper.COL_RAPPORT_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(rapport.get_id())};
        db.delete(dbHelper.TABLE_RAPPORT, WHERE_CLAUSE, WHERE_ARGS);
    }

    public List<Rapport> getRapports() {
        List<Rapport> rapports = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_RAPPORT, null, null, null, null, null, dbHelper.COL_RAPPORT_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Rapport rapport = cursorToRapport(cursor);
            rapports.add(rapport);
            cursor.moveToNext();
        }
        cursor.close();
        return rapports;
    }


    public Rapport getRapportByUser(User user) {
        String WHERECLAUSE = DBHelper.COL_RAPPORT_USER_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(user.get_id())};

        Rapport rapport = new Rapport();
        Cursor cursor = db.query(dbHelper.TABLE_RAPPORT, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        Log.e("NBR_RAPPORT:", "" + cursor.getCount());
        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            rapport.set_id(cursor.getInt(0));
            rapport.setDateOfSending(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_RAPPORT_DATE)));
            rapport.setUserId(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_RAPPORT_USER_ID)));
            rapport.setSend(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_RAPPORT_ISSENDED))>0);
            rapport.setDateofCreation(cursor.getString(cursor.getColumnIndex(dbHelper.COL_RAPPORT_DATE_OF_CREATION)));
            return rapport;

        }


    }



    private Rapport cursorToRapport(Cursor cursor) {
        Rapport rapport = new Rapport();
        rapport.set_id(cursor.getInt(0));
        rapport.setDateOfSending(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_RAPPORT_DATE)));
        rapport.setUserId(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_RAPPORT_USER_ID)));
        rapport.setSend(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_RAPPORT_ISSENDED))>0);
        rapport.setDateofCreation(cursor.getString(cursor.getColumnIndex(dbHelper.COL_RAPPORT_DATE_OF_CREATION)));
        return rapport;
    }

    public Rapport getRapportByUserbyDate(int userid, String dateofcreation) {
        String WHERECLAUSE = DBHelper.COL_RAPPORT_USER_ID + "=? AND "
                +DBHelper.COL_RAPPORT_DATE_OF_CREATION+"=?";
        String[] WHEREARGS = new String[]{String.valueOf(userid),String.valueOf(dateofcreation)};

        Rapport rapport = new Rapport();
        Cursor cursor = db.query(dbHelper.TABLE_RAPPORT, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        Log.e("NBR_RAPPORT:", "" + cursor.getCount());
        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            rapport.set_id(cursor.getInt(0));
            rapport.setDateOfSending(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_RAPPORT_DATE)));
            rapport.setUserId(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_RAPPORT_USER_ID)));
            rapport.setSend(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_RAPPORT_ISSENDED))>0);
            rapport.setDateofCreation(cursor.getString(cursor.getColumnIndex(dbHelper.COL_RAPPORT_DATE_OF_CREATION)));
            return rapport;

        }

    }
}
