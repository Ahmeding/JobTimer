package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Pointage;

/**
 * Created by BARA' on 26/12/2016.
 */
public class PointagesDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public PointagesDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public long addNewPUserIn(Pointage pointage) {
        ContentValues cvalues = new ContentValues();

        cvalues.put(dbHelper.COL_P_DATIMEIN_M, pointage.getDate_timeInM());
        cvalues.put(DBHelper.COL_P_USER_ID, pointage.get_idUser());
        cvalues.put(DBHelper.COL_P_USEROUT, pointage.isUserOut());
        cvalues.put(DBHelper.COL_P_USERIN, pointage.isUserIn());
        cvalues.put(DBHelper.COL_P_FLAG, pointage.getFlag());
        cvalues.put(DBHelper.COL_P_CATEGORY, pointage.getFlagCategory());
        cvalues.put(DBHelper.COL_P_DATE_POINTAGE, pointage.getDatePointage());

        return db.insert(DBHelper.TABLE_POINTAGE, null, cvalues);
    }

    public int updateUserOutM(Pointage pointage) {
        String whereClause = dbHelper.COL_P_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(pointage.get_id())};

        ContentValues cvalues = new ContentValues();

        cvalues.put(dbHelper.COL_P_DATIMEOUT_M, pointage.getDate_timeOutM());
        cvalues.put(DBHelper.COL_P_USEROUT, pointage.isUserOut());
        cvalues.put(DBHelper.COL_P_USERIN, pointage.isUserIn());
        cvalues.put(DBHelper.COL_P_FLAG, pointage.getFlag());

        return db.update(DBHelper.TABLE_POINTAGE, cvalues, whereClause, whereArgs);

    }

    public int updateUserInAM(Pointage pointage) {
        String whereClause = dbHelper.COL_P_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(pointage.get_id())};
        ContentValues cvalues = new ContentValues();

        cvalues.put(dbHelper.COL_P_DATIMEIN_AM, pointage.getDate_timeInAM());
        cvalues.put(DBHelper.COL_P_USEROUT, pointage.isUserOut());
        cvalues.put(DBHelper.COL_P_USERIN, pointage.isUserIn());
        cvalues.put(DBHelper.COL_P_FLAG, pointage.getFlag());

        return db.update(DBHelper.TABLE_POINTAGE, cvalues, whereClause, whereArgs);
    }

    public int updateUserOutAM(Pointage pointage) {
        String whereClause = dbHelper.COL_P_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(pointage.get_id())};
        ContentValues cvalues = new ContentValues();

        cvalues.put(dbHelper.COL_P_DATIMEOUT_AM, pointage.getDate_timeOutAM());
        cvalues.put(DBHelper.COL_P_USEROUT, pointage.isUserOut());
        cvalues.put(DBHelper.COL_P_USERIN, pointage.isUserIn());
        cvalues.put(DBHelper.COL_P_FLAG, pointage.getFlag());

        return db.update(DBHelper.TABLE_POINTAGE, cvalues, whereClause, whereArgs);
    }

    public void deletePointage(Pointage pointage) {
        String WHERE_CLAUSE = DBHelper.COL_P_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(pointage.get_id())};
        db.delete(DBHelper.TABLE_POINTAGE, WHERE_CLAUSE, WHERE_ARGS);
    }


    public List<Pointage> getPointagesByUser(int userId) {
        String WHERE_CLAUSE = DBHelper.COL_P_USER_ID + "=?";
        String [] WHEREARGS = new String[]{String.valueOf(userId)};
        List<Pointage> pointages = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_POINTAGE, WHEREARGS, WHERE_CLAUSE, null, null,
                null, dbHelper.COL_P_ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pointage pointage = cursorToPointage(cursor);
            pointages.add(pointage);
            cursor.moveToNext();
        }
        cursor.close();
        return pointages;
    }

    public List<Pointage> getPointages() {
        List<Pointage> pointages = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_POINTAGE, null, null, null, null,
                null, dbHelper.COL_P_ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pointage pointage = cursorToPointage(cursor);
            pointages.add(pointage);
            cursor.moveToNext();
        }
        cursor.close();
        return pointages;
    }

  //  public List<Pointage> getDistincPointages() {
  //      List<Pointage> pointages = new ArrayList<>();
  //      Cursor cursor = db.query(true,DBHelper.TABLE_POINTAGE,
  //              new String[]{DBHelper.COL_P_DATE_POINTAGE}
  //              ,null,null,null,null,null,null);
  //      cursor.moveToFirst();
  //      while (!cursor.isAfterLast()) {
  //          Pointage pointage = cursorToPointage(cursor);
  //          pointages.add(pointage);
  //          cursor.moveToNext();
  //      }
  //      cursor.close();
  //      return pointages;
  //  }


    private Pointage cursorToPointage(Cursor cursor) {
        Pointage pointage = new Pointage();
        pointage.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_ID)));
        pointage.setDate_timeInM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEIN_M)));
        pointage.setDate_timeOutM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEOUT_M)));
        pointage.setDate_timeInAM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEIN_AM)));
        pointage.setDate_timeOutAM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEOUT_AM)));
        pointage.setUserIn(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_USERIN)) > 0);
        pointage.setUserOut(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_USEROUT)) > 0);
        pointage.set_idUser(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_USER_ID)));
        pointage.setFlag(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_FLAG)));
        pointage.setFlagCategory(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_CATEGORY)));
        pointage.setDatePointage(cursor.getString(cursor.getColumnIndex(dbHelper.COL_P_DATE_POINTAGE)));

        return pointage;
    }


    public Pointage getPointByUser(int user_id) {

       // SQLiteDatabase db = dbHelper.getReadableDatabase();
        String WHERE_CLAUSE = DBHelper.COL_P_USER_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(user_id)};

        Cursor cursor = db.query(dbHelper.TABLE_POINTAGE, null, WHERE_CLAUSE,
                WHERE_ARGS, null, null, null, null);
        Pointage pointage = new Pointage();

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            pointage.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_ID)));
            pointage.set_idUser(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_USER_ID)));
            pointage.setDate_timeInM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEIN_M)));
            pointage.setDate_timeOutM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEOUT_M)));
            pointage.setDate_timeInAM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEIN_AM)));
            pointage.setDate_timeOutAM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEOUT_AM)));
            pointage.setFlag(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_FLAG)));
            pointage.setFlagCategory(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_CATEGORY)));
            pointage.setDatePointage(cursor.getString(cursor.getColumnIndex(dbHelper.COL_P_DATE_POINTAGE)));

            return pointage;
        }


    }

    public Pointage getPointByUserByDate(int userId,String seslectedDate) {
        String WHERE_CLAUSE = DBHelper.COL_P_USER_ID + "=? AND "
                                +DBHelper.COL_P_DATE_POINTAGE+"=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(userId),String.valueOf(seslectedDate)};

        Cursor cursor = db.query(dbHelper.TABLE_POINTAGE, null, WHERE_CLAUSE,
                WHERE_ARGS, null, null, null, null);
        Pointage pointage = new Pointage();

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            pointage.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_ID)));
            pointage.set_idUser(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_USER_ID)));
            pointage.setDate_timeInM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEIN_M)));
            pointage.setDate_timeOutM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEOUT_M)));
            pointage.setDate_timeInAM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEIN_AM)));
            pointage.setDate_timeOutAM(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_P_DATIMEOUT_AM)));
            pointage.setFlag(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_FLAG)));
            pointage.setFlagCategory(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_P_CATEGORY)));
            pointage.setDatePointage(cursor.getString(cursor.getColumnIndex(dbHelper.COL_P_DATE_POINTAGE)));

            return pointage;
        }

    }
}
