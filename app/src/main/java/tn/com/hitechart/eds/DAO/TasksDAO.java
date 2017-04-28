package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Pointage;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.data.Key;

/**
 * Created by BARA' on 26/12/2016.
 */
public class TasksDAO {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public TasksDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addTask(Task task) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_TASK_NAME, task.getTaskName());
        cvalues.put(DBHelper.COL_TASK_NUM_DOSS, task.getNumDoss());
        cvalues.put(DBHelper.COL_TASK_CLIENT, task.getClient());
        cvalues.put(DBHelper.COL_TASK_ACT, task.getAct());
        cvalues.put(DBHelper.COL_TASK_RES, task.getRes());
        cvalues.put(DBHelper.COL_TASK_USER_ID, task.get_idUser());
        cvalues.put(DBHelper.COL_TASK_DESIGNATION, task.getDesignation());
        cvalues.put(DBHelper.COL_TASK_MONTANT_TTC, task.getMontanttc());
        cvalues.put(DBHelper.COL_TASK_TIMEA, task.getTimeA());
        cvalues.put(DBHelper.COL_TASK_TIME_START, task.getTimeStart());
        cvalues.put(DBHelper.COL_TASK_TIME_STOP, task.getTimeStop());
        cvalues.put(DBHelper.COL_TASK_BESOIN_COMP, task.getBesoinComp());
        cvalues.put(DBHelper.COL_TASK_TYPE, task.getType());
        cvalues.put(DBHelper.COL_TASK_FLAG_STATUS, task.getFlagStatus());
        cvalues.put(DBHelper.COL_TASK_CATEGORY, task.getCategory());

        return db.insert(DBHelper.TABLE_TASK, null, cvalues);
    }



    public int updateTaskisCreate(Task task) {
        String whereClause = DBHelper.COL_TASK_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(task.get_id())};

        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_TASK_FLAG_STATUS, task.getFlagStatus());

        return db.update(DBHelper.TABLE_TASK, cvalues, whereClause, whereArgs);
    }

    public int updateTask(Task task) {
        String whereClause = DBHelper.COL_TASK_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(task.get_id())};

        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_TASK_NAME, task.getTaskName());
        cvalues.put(DBHelper.COL_TASK_NUM_DOSS, task.getNumDoss());
        cvalues.put(DBHelper.COL_TASK_CLIENT, task.getClient());
        cvalues.put(DBHelper.COL_TASK_ACT, task.getAct());
        cvalues.put(DBHelper.COL_TASK_RES, task.getRes());
        cvalues.put(DBHelper.COL_TASK_USER_ID, task.get_idUser());
        cvalues.put(DBHelper.COL_TASK_DESIGNATION, task.getDesignation());
        cvalues.put(DBHelper.COL_TASK_MONTANT_TTC, task.getMontanttc());
        cvalues.put(DBHelper.COL_TASK_TIMEA, task.getTimeA());
        cvalues.put(DBHelper.COL_TASK_TIME_START, task.getTimeStart());
        cvalues.put(DBHelper.COL_TASK_TIME_STOP, task.getTimeStop());
        cvalues.put(DBHelper.COL_TASK_BESOIN_COMP, task.getBesoinComp());
        cvalues.put(DBHelper.COL_TASK_TYPE, task.getType());
        cvalues.put(DBHelper.COL_TASK_FLAG_STATUS, task.getFlagStatus());

        return db.update(DBHelper.TABLE_TASK, cvalues, whereClause, whereArgs);
    }

    public int updateTaskDuration(Task task) {
        String whereClause = DBHelper.COL_TASK_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(task.get_id())};
        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_TASK_NAME, task.getTaskName());
        cvalues.put(DBHelper.COL_TASK_NUM_DOSS, task.getNumDoss());
        cvalues.put(DBHelper.COL_TASK_CLIENT, task.getClient());
        cvalues.put(DBHelper.COL_TASK_ACT, task.getAct());
        cvalues.put(DBHelper.COL_TASK_RES, task.getRes());
        cvalues.put(DBHelper.COL_TASK_USER_ID, task.get_idUser());
        cvalues.put(DBHelper.COL_TASK_DESIGNATION, task.getDesignation());
        cvalues.put(DBHelper.COL_TASK_MONTANT_TTC, task.getMontanttc());
        cvalues.put(DBHelper.COL_TASK_TIMEA, task.getTimeA());
        cvalues.put(DBHelper.COL_TASK_TIME_START, task.getTimeStart());
        cvalues.put(DBHelper.COL_TASK_TIME_STOP, task.getTimeStop());
        cvalues.put(DBHelper.COL_TASK_BESOIN_COMP, task.getBesoinComp());
        cvalues.put(DBHelper.COL_TASK_TYPE, task.getBesoinComp());
        cvalues.put(DBHelper.COL_TASK_FLAG_STATUS, task.getFlagStatus());

        return db.update(DBHelper.TABLE_TASK, cvalues, whereClause, whereArgs);
    }

     public void deleteTask(Task task) {
         String WHERE_CLAUSE = DBHelper.COL_TASK_ID + "=?";
         String[] WHERE_ARGS = new String[] {String.valueOf(task.get_id())};
         db.delete(DBHelper.TABLE_TASK, WHERE_CLAUSE, WHERE_ARGS);
     }

    public Task getTaskById(int id) {
        String WHERECLAUSE = DBHelper.COL_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        Task task = new Task();
        Cursor cursor = db.query(dbHelper.TABLE_TASK, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

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


    public Task getTaskByIdDossier(int id) {
        String WHERECLAUSE = DBHelper.COL_TASK_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(id)};

        Task task = new Task();
        Cursor cursor = db.query(dbHelper.TABLE_TASK, null, WHERECLAUSE
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

    public List<Task> getTachesByUser(User user) {

        String WHERECLAUSE = DBHelper.COL_TASK_USER_ID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(user.get_id())};

        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_TASK, null, WHERECLAUSE, WHEREARGS, null,
                null, dbHelper.COL_TASK_ID + " DESC");
        Log.e("NBR_TASK:", "" + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public List<Task> getTachesByOtherActivities(User user) {
        String WHERECLAUSE = DBHelper.COL_TASK_USER_ID + "=? AND "
                + DBHelper.COL_TASK_TYPE + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(user.get_id()), String.valueOf(Key.TASK_TYPE_AUTRE)};

        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_TASK, null, WHERECLAUSE, WHEREARGS, null,
                null, null);
        Log.e("Cur", "" + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task t = cursorToTask(cursor);
            tasks.add(t);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public List<Task> getTachesByUserByDay(User user, Pointage pointage) {

        String WHERECLAUSE = DBHelper.COL_TASK_USER_ID + "=? AND " + DBHelper.COL_TASK_TIME_START + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(user.get_id()), String.valueOf(pointage.getDate_timeInM())};

        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_TASK, null, WHERECLAUSE, WHEREARGS, null,
                null, null);
        Log.e("Cur", "" + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_TASK, null, null, null, null,
                null, dbHelper.COL_TASK_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
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

    public List<Task> getUNFinishedTask(int id) {
        List<Task> tasks = new ArrayList();
        String WHARECLAUSE = dbHelper.COL_TASK_FLAG_STATUS + "!=? AND " + dbHelper.COL_TASK_USER_ID + "=?";
        String[] WHEREARS = new String[]{String.valueOf(Key.TASK_FLAG_STOP), String.valueOf(id)};
        Cursor cursor = db.query(dbHelper.TABLE_TASK, null, WHARECLAUSE, WHEREARS, null,
                null, null);
        Log.e("Cur", "" + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

}
