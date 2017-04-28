package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Message;
import tn.com.hitechart.eds.Entity.Task;

/**
 * Created by BARA' on 17/01/2017.
 */

public class MessageDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public MessageDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addMessage(Message message) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(dbHelper.COL_MSG_BODY, message.getMsg());
        cvalues.put(dbHelper.COL_MSG_TASK_NUM_DOSS, message.getNumDoss());
        cvalues.put(dbHelper.COL_MSG_TASKID, message.getIdTask());

        return db.insert(dbHelper.TABLE_MESSAGE, null, cvalues);
    }


    public int updateMessage(Message message) {
        String whereClause = dbHelper.COL_MSG_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(message.get_id())};
        ContentValues cvalues = new ContentValues();
        cvalues.put(dbHelper.COL_MSG_BODY, message.getMsg());
        cvalues.put(dbHelper.COL_MSG_TASKID, message.getIdTask());
        return db.update(DBHelper.TABLE_MESSAGE, cvalues, whereClause, whereArgs);
    }

    public void deleteMessage(Message message) {
        String WHERE_CLAUSE = dbHelper.COL_MSG_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(message.get_id())};
        db.delete(dbHelper.TABLE_MESSAGE, WHERE_CLAUSE, WHERE_ARGS);
    }

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_MESSAGE, null, null, null, null, null, dbHelper.COL_MSG_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        cursor.close();
        return messages;
    }

    public List<Message> getMessages(Task task) {

        String WHERE_CLAUSE = dbHelper.COL_MSG_TASKID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(task.get_id())};
        List<Message> messages = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_MESSAGE, null, WHERE_CLAUSE, WHERE_ARGS, null, null, dbHelper.COL_MSG_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Message message = cursorToMessage(cursor);
            messages.add(message);
            cursor.moveToNext();
        }
        cursor.close();
        return messages;
    }


    public Message getMessageByTask(Task task) {
        String WHERECLAUSE = DBHelper.COL_MSG_TASKID + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(task.get_id())};

        Message message = new Message();
        Cursor cursor = db.query(dbHelper.TABLE_MESSAGE, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        Log.e("NBR_MSG:", "" + cursor.getCount());
        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();

            message.set_id(cursor.getInt(0));
            message.setMsg(cursor.getString(cursor.getColumnIndex(dbHelper.COL_MSG_BODY)));
            message.setIdTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_MSG_TASKID)));
            return message;

        }


    }



    private Message cursorToMessage(Cursor cursor) {
        Message message = new Message();
        message.set_id(cursor.getInt(0));
        message.setMsg(cursor.getString(cursor.getColumnIndex(dbHelper.COL_MSG_BODY)));
        message.setIdTask(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_MSG_TASKID)));
        message.setNumDoss(cursor.getString(cursor.getColumnIndex(dbHelper.COL_MSG_TASK_NUM_DOSS)));

        return message;
    }
}
