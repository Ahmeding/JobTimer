package tn.com.hitechart.eds.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.data.UserManager;

/**
 * Created by BARA' on 22/12/2016.
 */
public class UsersDAO {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public UsersDAO(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addUser(User user) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_USER_NAME, user.getLogin());
        cvalues.put(DBHelper.COL_USER_PASSWORD, user.getPassword());
        cvalues.put(DBHelper.COL_USER_EMAIL, user.getEmail());
        cvalues.put(DBHelper.COL_USER_NUMTEL, user.getNumTel());
        cvalues.put(DBHelper.COL_USER_PATHPHTO, user.getPathPhoto());
        cvalues.put(DBHelper.COL_USER_TYPE, user.getType());
        cvalues.put(DBHelper.COL_USER_CODE, user.getCode());
        cvalues.put(DBHelper.COL_USER_ADMIN, user.isAdmin());

        return db.insert(DBHelper.TABLE_USERS, null, cvalues);
    }


    public long addAdmin(User admin) {
        ContentValues cvalues = new ContentValues();
        cvalues.put(DBHelper.COL_USER_NAME, admin.getLogin());
        cvalues.put(DBHelper.COL_USER_PASSWORD, admin.getPassword());
        cvalues.put(DBHelper.COL_USER_EMAIL, admin.getEmail());
        cvalues.put(DBHelper.COL_USER_NUMTEL, admin.getNumTel());
        cvalues.put(DBHelper.COL_USER_PATHPHTO, admin.getPathPhoto());
        cvalues.put(DBHelper.COL_USER_TYPE, admin.getType());
        cvalues.put(DBHelper.COL_USER_CODE, admin.getCode());
        cvalues.put(DBHelper.COL_USER_ADMIN, UserManager.ADMIN_ISADIN);

        Log.e("from DAO", "======+======" + admin.isAdmin());
        return db.insert(DBHelper.TABLE_USERS, null, cvalues);
    }

    public int updateUser(User user) {
        String whereClause = dbHelper.COL_USER_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(user.get_id())};
        ContentValues cValues = new ContentValues();
        //cValues.put(DatabaseHelper.COL_ID,user.get_id());
        cValues.put(DBHelper.COL_USER_NAME, user.getLogin());
        cValues.put(DBHelper.COL_USER_PASSWORD, user.getPassword());
        cValues.put(DBHelper.COL_USER_EMAIL, user.getEmail());
        cValues.put(DBHelper.COL_USER_NUMTEL, user.getNumTel());
        cValues.put(DBHelper.COL_USER_PATHPHTO, user.getPathPhoto());
        cValues.put(DBHelper.COL_USER_TYPE, user.getType());
        cValues.put(DBHelper.COL_USER_CODE, user.getCode());

        return db.update(DBHelper.TABLE_USERS, cValues, whereClause, whereArgs);

    }

    public void deleteUser(User user) {
        String WHERE_CLAUSE = DBHelper.COL_USER_ID + "=?";
        String[] WHERE_ARGS = new String[]{String.valueOf(user.get_id())};
        db.delete(DBHelper.TABLE_USERS, WHERE_CLAUSE, WHERE_ARGS);
    }


    public User getUserByLogin(String username) {
        String WHERECLAUSE = DBHelper.COL_USER_NAME + "=?";
        String[] WHEREARGS = new String[]{String.valueOf(username)};

        User user = new User();
        Cursor cursor = db.query(dbHelper.TABLE_USERS, null, WHERECLAUSE
                , WHEREARGS, null, null, null, null);

        if (cursor.getCount() == 0) {
            return null;
        } else {
            cursor.moveToLast();
            user.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_ID)));
            user.setLogin(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_NAME)));
            user.setType(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_TYPE)));
            user.setCode(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_CODE)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_EMAIL)));
            user.setNumTel(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_NUMTEL)));
            return user;

        }
    }
    public List<User> getUsers() {
        // String WHERECLAUSE = DBHelper.COL_USER_ADMIN + "!=1";

        List<User> users = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_USERS, null, null, null, null, null, dbHelper.COL_USER_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    //-------------------------------------------------------------------------

    public List<User> getOnlyUsers() {
         String WHERECLAUSE = DBHelper.COL_USER_ADMIN + "!=1";

        List<User> users = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_USERS, null, WHERECLAUSE, null, null, null, dbHelper.COL_USER_ID + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_ID)));
        user.setLogin(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_PASSWORD)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_EMAIL)));
        user.setType(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_TYPE)));
        user.setNumTel(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_NUMTEL)));
        user.setPathPhoto(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_PATHPHTO)));
        user.setCode(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_CODE)));
        user.setAdmin(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_ADMIN)) > 0);


        //  user.setAdmin(cursor.getcursor.getColumnIndex(dbHelper.COL_USER_ADMIN)));

        return user;
    }

    public User validUser(String userName, String password) {
        User userlogged = new User();
        String WHERECLAUSE = dbHelper.COL_USER_NAME + " =? AND "
                + dbHelper.COL_USER_PASSWORD + " =?";
        String[] WHEREARGS = new String[]{String.valueOf(userName), String.valueOf(password)};


        Cursor cursor = db.query(dbHelper.TABLE_USERS, null, WHERECLAUSE, WHEREARGS, null, null, null, null);
        if (cursor.getCount() > 0) {
            if (cursor != null) {
                cursor.moveToFirst();

                userlogged.setLogin(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_NAME)));
                userlogged.setPassword(cursor.getString(cursor.getColumnIndex(dbHelper.COL_USER_PASSWORD)));
                userlogged.setAdmin(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_ADMIN)) > 0);
                userlogged.set_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_ID)));
                userlogged.setCode(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_USER_CODE)));
                return userlogged;
            } else
                return userlogged;
        } else return null;
    }

}
