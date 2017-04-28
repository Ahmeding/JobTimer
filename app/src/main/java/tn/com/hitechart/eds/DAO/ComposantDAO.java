package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Composant;
import tn.com.hitechart.eds.Entity.Task;

/**
 * Created by BARA' on 03/01/2017.
 */

public class ComposantDAO {


    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public ComposantDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addComposant(Composant composant) {
        ContentValues cvalues = new ContentValues();

        cvalues.put(DBHelper.COL_COMP_NAME, composant.getName());
        cvalues.put(DBHelper.COL_COMP_QTE, composant.getQte());
        cvalues.put(DBHelper.COL_COMP_TASK_ID, composant.get_idTask());
        cvalues.put(DBHelper.COL_COMP_TASK_NUM_DOSS, composant.getNumDoss());

        return db.insert(DBHelper.TABLE_COMPOSANT, null, cvalues);
    }

    public int updateComposant(Composant composant) {
        String whereClause = DBHelper.COL_COMP_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(composant.get_id())};
        ContentValues cvalues = new ContentValues();

        cvalues.put(DBHelper.COL_COMP_NAME, composant.getName());
        cvalues.put(DBHelper.COL_COMP_QTE, composant.getQte());
        cvalues.put(DBHelper.COL_COMP_TASK_ID, composant.get_idTask());

        return db.update(DBHelper.TABLE_COMPOSANT, cvalues, whereClause, whereArgs);
    }


    public void deleteComposant(Composant composant) {
        String WHERE_CLAUSE = DBHelper.COL_COMP_ID + "=?";
        String[] WHERE_ARGS = new String[] {String.valueOf(composant.get_id())};
        db.delete(DBHelper.TABLE_COMPOSANT, WHERE_CLAUSE, WHERE_ARGS);
    }

    public List<Composant> getComposants(Task task) {
        String WHERECLAUSE= DBHelper.COL_COMP_TASK_ID + "=?";
        String[] WHEREARGS = new String[] {String.valueOf(task.get_id())};

        List<Composant> composants = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_COMPOSANT, null, WHERECLAUSE, WHEREARGS, null,
                null, dbHelper.COL_COMP_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Composant composant = cursorToComposant(cursor);
            composants.add(composant);
            cursor.moveToNext();
        }
        cursor.close();
        return composants;
    }

    private Composant cursorToComposant(Cursor cursor) {
        Composant composant = new Composant();
        composant.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_COMP_ID)));
        composant.setName(cursor.getString(cursor.getColumnIndex(dbHelper.COL_COMP_NAME)));
        composant.setQte(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_COMP_QTE)));
        composant.set_idTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_COMP_TASK_ID)));
        composant.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_COMP_TASK_NUM_DOSS)));

        return composant;
    }


}
