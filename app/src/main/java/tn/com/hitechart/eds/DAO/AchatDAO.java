package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Achat;
import tn.com.hitechart.eds.Entity.Task;

/**
 * Created by BARA' on 03/01/2017.
 */

public class AchatDAO {




    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public AchatDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addAchat(Achat achat) {
        ContentValues cvalues = new ContentValues();

        cvalues.put(DBHelper.COL_ACHAT_DESIGNATION, achat.getDesignation());
        cvalues.put(DBHelper.COL_ACHAT_PRIX, achat.getPrix());
        cvalues.put(DBHelper.COL_ACHAT_TOTALP, achat.getTotal());
        cvalues.put(DBHelper.COL_ACHAT_TASK_ID, achat.get_idTask());
        cvalues.put(DBHelper.COL_ACHAT_TASK_NUM_DOSS, achat.getNumDoss());

        return db.insert(DBHelper.TABLE_ACHAT, null, cvalues);
    }

    public int updateAchat(Achat composant) {
        String whereClause = DBHelper.COL_ACHAT_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(composant.get_id())};
        ContentValues cvalues = new ContentValues();


        cvalues.put(DBHelper.COL_ACHAT_DESIGNATION, composant.getDesignation());
        cvalues.put(DBHelper.COL_ACHAT_PRIX, composant.getPrix());
        cvalues.put(DBHelper.COL_ACHAT_TOTALP, composant.getTotal());
        cvalues.put(DBHelper.COL_ACHAT_TASK_ID, composant.get_idTask());

        return db.update(DBHelper.TABLE_ACHAT, cvalues, whereClause, whereArgs);
    }


    public void deleteAchat(Achat composant) {
        String WHERE_CLAUSE = DBHelper.COL_ACHAT_ID + "=?";
        String[] WHERE_ARGS = new String[] {String.valueOf(composant.get_id())};
        db.delete(DBHelper.TABLE_ACHAT, WHERE_CLAUSE, WHERE_ARGS);
    }



    public List<Achat> getAchats(Task task) {

        String WHERECLAUSE= DBHelper.COL_ACHAT_TASK_ID + "=?";
        String[] WHEREARGS = new String[] {String.valueOf(task.get_id())};

        List<Achat> composants = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_ACHAT, null, WHERECLAUSE, WHEREARGS, null,
                null, dbHelper.COL_ACHAT_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Achat composant = cursorToAchat(cursor);
            composants.add(composant);
            cursor.moveToNext();
        }
        cursor.close();
        return composants;
    }

    private Achat cursorToAchat(Cursor cursor) {
        Achat composant = new Achat();
        composant.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ACHAT_ID)));
        composant.setDesignation(cursor.getString(cursor.getColumnIndex(dbHelper.COL_ACHAT_DESIGNATION)));
        composant.setPrix(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ACHAT_PRIX)));
        composant.setTotal(cursor.getFloat(cursor.getColumnIndex(dbHelper.COL_ACHAT_TOTALP)));
        composant.set_idTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ACHAT_TASK_ID)));
        composant.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_ACHAT_TASK_NUM_DOSS)));

        return composant;
    }


}
