package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.ParamAdresseEmail;

/**
 * Created by BARA' on 10/01/2017.
 */

public class ParamAdresseEmailDAO {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ParamAdresseEmailDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addParamAdresseEmail(ParamAdresseEmail paramAdresseEmail) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(dbHelper.COL_PARAM_ADRESSEMAIL, paramAdresseEmail.getAdresseEmail());
        return db.insert(dbHelper.TABLE_PARAM_ADRESEMAIL, null, cvalues);
    }

    public int updateParamAdresseEmail(ParamAdresseEmail paramAdresseEmail) {
        String whereClause = dbHelper.COL_PARAM_ADRESSEMAIL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(paramAdresseEmail.get_id())};
        ContentValues cValues = new ContentValues();
        cValues.put(DBHelper.COL_PARAM_ADRESSEMAIL,paramAdresseEmail.getAdresseEmail());
        return db.update(DBHelper.TABLE_PARAM_ADRESEMAIL, cValues, whereClause, whereArgs);
    }
    public void deleteParamAdresseEmail(ParamAdresseEmail paramAdresseEmail) {
        String WHERE_CLAUSE = dbHelper.COL_PARAM_ADRESSEMAIL_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(paramAdresseEmail.get_id())};
        db.delete(dbHelper.TABLE_PARAM_ADRESEMAIL, WHERE_CLAUSE, WHERE_ARGS);
    }

    public List<ParamAdresseEmail> getParamAdresseEmails() {
        List<ParamAdresseEmail> paramAdresseEmails = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_PARAM_ADRESEMAIL, null, null, null, null, null, dbHelper.COL_PARAM_ADRESSEMAIL_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ParamAdresseEmail paramAdresseEmail= cursorToParamAdresseEmail(cursor);
            paramAdresseEmails.add(paramAdresseEmail);
            cursor.moveToNext();
        }
        cursor.close();
        return paramAdresseEmails;
    }

    private ParamAdresseEmail cursorToParamAdresseEmail(Cursor cursor) {
        ParamAdresseEmail paramAdresseEmail= new ParamAdresseEmail();
        paramAdresseEmail.set_id(cursor.getInt(0));
        paramAdresseEmail.setAdresseEmail(cursor.getString(cursor.getColumnIndex(dbHelper.COL_PARAM_ADRESSEMAIL)));
        return paramAdresseEmail;
    }
}
