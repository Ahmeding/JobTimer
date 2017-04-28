package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Resultat;

/**
 * Created by BARA' on 22/12/2016.
 */
public class ResultatsDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ResultatsDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addResultat(Resultat resultat) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(dbHelper.COL_RES_NAME, resultat.getName());
        return db.insert(dbHelper.TABLE_RESULTATS, null, cvalues);
    }


    public int updateResultat(Resultat resultat) {
        String whereClause = dbHelper.COL_RES_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(resultat.get_id())};
        ContentValues cValues = new ContentValues();
        cValues.put(DBHelper.COL_RES_NAME,resultat.getName());
        return db.update(DBHelper.TABLE_RESULTATS, cValues, whereClause, whereArgs);
    }
    public void deleteResultat(Resultat resultat) {
        String WHERE_CLAUSE = dbHelper.COL_RES_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(resultat.get_id())};
        db.delete(dbHelper.TABLE_RESULTATS, WHERE_CLAUSE, WHERE_ARGS);
    }

    public List<Resultat> getResultats() {
        List<Resultat> resultats = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_RESULTATS, null, null, null, null, null, dbHelper.COL_RES_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Resultat resultat = cursorToResultat(cursor);
            resultats.add(resultat);
            cursor.moveToNext();
        }
        cursor.close();
        return resultats;
    }

    private Resultat cursorToResultat(Cursor cursor) {
        Resultat resultat= new Resultat();
        resultat.set_id(cursor.getInt(0));
        resultat.setName(cursor.getString(cursor.getColumnIndex(dbHelper.COL_RES_NAME)));
        return resultat;
    }
}
