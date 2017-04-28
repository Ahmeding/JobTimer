package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Task;

/**
 * Created by BARA' on 22/01/2017.
 */

public class DossierDAO {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public DossierDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addDossier(Dossier dossier) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_DOSSIER_NUM_DOSS, dossier.getNumDoss());
        cvalues.put(DBHelper.COL_DOSSIER_CLIENT, dossier.getClient());
        cvalues.put(DBHelper.COL_DOSSIER_TIME_DURATION, dossier.getTimeDuration());
        cvalues.put(DBHelper.COL_DOSSIER_START_TIME, dossier.getStartTime());
        cvalues.put(DBHelper.COL_DOSSIER_END_TIME, dossier.getEndTime());
        cvalues.put(DBHelper.COL_DOSSIER_TASK_ID, dossier.get_idTask());

        return db.insert(DBHelper.TABLE_DOSSIER, null, cvalues);
    }

    public int updateDossier(Dossier dossier) {
        String whereClause = DBHelper.COL_DOSSIER_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(dossier.get_id())};

        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_DOSSIER_NUM_DOSS, dossier.getNumDoss());
        cvalues.put(DBHelper.COL_DOSSIER_CLIENT, dossier.getClient());
        cvalues.put(DBHelper.COL_DOSSIER_TIME_DURATION, dossier.getTimeDuration());
        cvalues.put(DBHelper.COL_DOSSIER_START_TIME, dossier.getStartTime());
        cvalues.put(DBHelper.COL_DOSSIER_END_TIME, dossier.getEndTime());

        return db.update(DBHelper.TABLE_DOSSIER, cvalues, whereClause, whereArgs);
    }
    public int updateStartTimeDossier(Dossier dossier) {
        String whereClause = DBHelper.COL_DOSSIER_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(dossier.get_id())};

        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_DOSSIER_START_TIME, dossier.getStartTime());

        return db.update(DBHelper.TABLE_DOSSIER, cvalues, whereClause, whereArgs);
    }

    //
    public int updateTimeDurationDossier(Dossier dossier) {
        String whereClause = DBHelper.COL_DOSSIER_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(dossier.get_id())};

        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_DOSSIER_END_TIME, dossier.getEndTime());
        cvalues.put(DBHelper.COL_DOSSIER_TIME_DURATION, dossier.getTimeDuration());

        return db.update(DBHelper.TABLE_DOSSIER, cvalues, whereClause, whereArgs);
    }

    //
     public void deleteDossier(Dossier dossier) {
         String WHERE_CLAUSE = DBHelper.COL_DOSSIER_ID + "=?";
         String[] WHERE_ARGS = new String[] {String.valueOf(dossier.get_id())};
         db.delete(DBHelper.TABLE_DOSSIER, WHERE_CLAUSE, WHERE_ARGS);
     }

    public Dossier getDossierById(int id) {
        String WHERECLAUSE = DBHelper.COL_DOSSIER_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        Dossier dossier = new Dossier();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            dossier.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_ID)));
            dossier.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_NUM_DOSS)));
            dossier.setClient(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_CLIENT)));
            dossier.setTimeDuration(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TIME_DURATION)));
            dossier.setStartTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_START_TIME)));
            dossier.setEndTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_END_TIME)));
            dossier.set_idTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TASK_ID)));

            return dossier;

        }
    }

    public Dossier getonlyDossierById(int id) {
        String WHERECLAUSE = DBHelper.COL_DOSSIER_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        Dossier dossier = new Dossier();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            dossier.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_ID)));
            dossier.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_NUM_DOSS)));
            dossier.setClient(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_CLIENT)));
            dossier.setTimeDuration(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TIME_DURATION)));
            dossier.setStartTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_START_TIME)));
            dossier.setEndTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_END_TIME)));
            dossier.set_idTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TASK_ID)));

            return dossier;

        }
    }

    public Dossier getDossieryTaskId(int id) {
        String WHERECLAUSE = DBHelper.COL_DOSSIER_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        Dossier dossier = new Dossier();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            dossier.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_ID)));
            dossier.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_NUM_DOSS)));
            dossier.setClient(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_CLIENT)));
            dossier.setTimeDuration(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TIME_DURATION)));
            dossier.setStartTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_START_TIME)));
            dossier.setEndTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_END_TIME)));
            dossier.set_idTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TASK_ID)));
            return dossier;
        }
    }

    public Dossier getDossierByTaskId(int id) {
        String WHERECLAUSE = DBHelper.COL_DOSSIER_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        Dossier dossier = new Dossier();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            dossier.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_ID)));
            dossier.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_NUM_DOSS)));
            dossier.setClient(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_CLIENT)));
            dossier.setTimeDuration(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TIME_DURATION)));
            dossier.setStartTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_START_TIME)));
            dossier.setEndTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_END_TIME)));
            dossier.set_idTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TASK_ID)));

            return dossier;

        }
    }
    public Task getTaskByIdDossier(int id) {
        String WHERECLAUSE = DBHelper.COL_DOSSIER_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        Task task = new Task();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        Log.e("NBR_TASK:", "" + cursor.getCount());
        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            task.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_TASK_ID)));
            task.setTaskName(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_ID)));
            task.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_NUM_DOSS)));
            task.setClient(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_CLIENT)));
            task.setAct(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_ACT)));
            task.setRes(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_RES)));
            task.set_idUser(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_TASK_USER_ID)));
            task.setTimeA(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_TASK_TIMEA)));
            task.setTimeStart(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_TASK_TIME_START)));
            task.setTimeStop(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_TASK_TIME_STOP)));
            task.setDesignation(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_DESIGNATION)));
            task.setMontanttc(cursor.getFloat(cursor.getColumnIndex(dbHelper.COL_TASK_MONTANT_TTC)));
            task.setBesoinComp(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_BESOIN_COMP)));
            task.setType(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_TYPE)));
            task.setFlagStatus(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_TASK_FLAG_STATUS)));
            task.setCategory(cursor.getString(cursor.getColumnIndex(dbHelper.COL_TASK_CATEGORY)));
            return task;

        }



    }

    public List<Dossier> getAllDossiersById(int id) {
        String WHERECLAUSE = DBHelper.COL_DOSSIER_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        List<Dossier> dossiers = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dossier dossier = cursorToDossier(cursor);
            dossiers.add(dossier);
            cursor.moveToNext();
        }
        cursor.close();
        return dossiers;
    }
    public List<Dossier> getAllDossiersByTaskId(int id) {
        String WHERECLAUSE = DBHelper.COL_DOSSIER_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        List<Dossier> dossiers = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dossier dossier = cursorToDossier(cursor);
            dossiers.add(dossier);
            cursor.moveToNext();
        }
        cursor.close();
        return dossiers;
    }


    public List<Dossier> getDossiers() {
        List<Dossier> dossiers = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_DOSSIER, null, null, null, null,
                null, dbHelper.COL_DOSSIER_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dossier dossier = cursorToDossier(cursor);
            dossiers.add(dossier);
            cursor.moveToNext();
        }
        cursor.close();
        return dossiers;
    }

    private Dossier cursorToDossier(Cursor cursor) {
        Dossier dossier = new Dossier();
        dossier.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_ID)));
        dossier.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_NUM_DOSS)));
        dossier.setClient(cursor.getString(cursor.getColumnIndex(dbHelper.COL_DOSSIER_CLIENT)));
        dossier.setTimeDuration(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TIME_DURATION)));
        dossier.setStartTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_START_TIME)));
        dossier.setEndTime(cursor.getLong(cursor.getColumnIndex(dbHelper.COL_DOSSIER_END_TIME)));
        dossier.set_idTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_DOSSIER_TASK_ID)));
        return dossier;
    }


}
