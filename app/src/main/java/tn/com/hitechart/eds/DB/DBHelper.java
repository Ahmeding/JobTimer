package tn.com.hitechart.eds.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static tn.com.hitechart.eds.data.UserManager.ADMIN_CODE;
import static tn.com.hitechart.eds.data.UserManager.ADMIN_EMAIL;
import static tn.com.hitechart.eds.data.UserManager.ADMIN_ISADIN;
import static tn.com.hitechart.eds.data.UserManager.ADMIN_LOGIN;
import static tn.com.hitechart.eds.data.UserManager.ADMIN_NUMTEL;
import static tn.com.hitechart.eds.data.UserManager.ADMIN_PASSWORD;
import static tn.com.hitechart.eds.data.UserManager.ADMIN_PATHPHOTO;
import static tn.com.hitechart.eds.data.UserManager.ADMIN_TYPE;

/**
 * Created by BARA' on 22/12/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "edsdb.db";
    private static final int DB_VERSION = 1;

    public static final String COMMA_SEP = ",";
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String REAL_TYPE = " REAL";
    public static final String NUMERIC_TYPE = " NUMERIC";
    public static final String LONG_TYPE = " BIGINT";
    //
    public static final String EN_ATTENTE = "En attente";
    public static final String EN_COURS = "En cours";
    public static final String FINI = "Fini";

    //--------------------------------------------------------------

    // DOSSIER TABLE
    public static final String TABLE_DOSSIER= "dossier";
    //COLUMNS NAME
    public static final String COL_DOSSIER_ID = "_id";
    public static final String COL_DOSSIER_NUM_DOSS = "numdoss";
    public static final String COL_DOSSIER_CLIENT = "client";
    public static final String COL_DOSSIER_TIME_DURATION= "duration";
    public static final String COL_DOSSIER_START_TIME= "starttime";
    public static final String COL_DOSSIER_END_TIME= "endtime";
    public static final String COL_DOSSIER_TASK_ID= "_idtask";



    // CREATE QUERY - TABLE DOSSIER -
    String CREATE_DOSSIER_TABLE =
            "CREATE TABLE " + TABLE_DOSSIER + "("
                    + COL_DOSSIER_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_DOSSIER_NUM_DOSS + TEXT_TYPE + COMMA_SEP
                    + COL_DOSSIER_CLIENT + TEXT_TYPE + COMMA_SEP
                    + COL_DOSSIER_TIME_DURATION+ LONG_TYPE + COMMA_SEP
                    + COL_DOSSIER_START_TIME+ LONG_TYPE + COMMA_SEP
                    + COL_DOSSIER_END_TIME+ LONG_TYPE + COMMA_SEP
                    + COL_DOSSIER_TASK_ID + INTEGER_TYPE + COMMA_SEP
                    + " FOREIGN KEY(" + COL_DOSSIER_TASK_ID + ")" +
                    " REFERENCES " + TABLE_TASK + "( " + COL_TASK_ID + " )"
                    + ");";


    // RAPPORT TABLE
    public static final String TABLE_RAPPORT = "rapport";

    //COLUMNS NAME
    public static final String COL_RAPPORT_ID = "_id";
    public static final String COL_RAPPORT_DATE = "datesending";
    public static final String COL_RAPPORT_ISSENDED = "isSended";
    public static final String COL_RAPPORT_USER_ID = "_idUser";
    public static final String COL_RAPPORT_DATE_OF_CREATION = "dateofcreation";

    // CREATE QUERY - TABLE MESSAGE -
    String CREATE_RAPPORT_TABLE =
            "CREATE TABLE " + TABLE_RAPPORT + "("
                    + COL_RAPPORT_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_RAPPORT_DATE + LONG_TYPE + COMMA_SEP
                    + COL_RAPPORT_ISSENDED + INTEGER_TYPE + COMMA_SEP
                    + COL_RAPPORT_DATE_OF_CREATION + TEXT_TYPE + COMMA_SEP
                    + COL_RAPPORT_USER_ID + INTEGER_TYPE + COMMA_SEP
                    + " FOREIGN KEY(" + COL_RAPPORT_USER_ID + ")" +
                    " REFERENCES " + TABLE_USERS + "( " + COL_USER_ID + " )"
                    + ");";


    //----------------------------------------------------------------
    // MESSAGE TABLE
    public static final String TABLE_MESSAGE = "messge";

    //COLUMNS NAME
    public static final String COL_MSG_ID = "_id";
    public static final String COL_MSG_BODY = "message";
    public static final String COL_MSG_TASKID = "_idTask";
    public static final String COL_MSG_TASK_NUM_DOSS = "numDoss";


    // CREATE QUERY - TABLE MESSAGE -
    String CREATE_MSG_TABLE =
            "CREATE TABLE " + TABLE_MESSAGE + "("
                    + COL_MSG_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_MSG_BODY + TEXT_TYPE + COMMA_SEP
                    + COL_MSG_TASK_NUM_DOSS + TEXT_TYPE + COMMA_SEP
                    + COL_MSG_TASKID + INTEGER_TYPE + COMMA_SEP
                    + " FOREIGN KEY(" + COL_MSG_TASKID + ")" +
                    " REFERENCES " + TABLE_TASK + "( " + COL_TASK_ID + " )"
                    + ");";


    // PARAPADRESSEEMAIL TABLE
    public static final String TABLE_PARAM_ADRESEMAIL = "adressemail";

    //COLUMNS NAME
    public static final String COL_PARAM_ADRESSEMAIL_ID = "_id";
    public static final String COL_PARAM_ADRESSEMAIL = "adressemail";


    // CREATE QUERY - TABLE PARAMADRESSEEMAIL -
    String CREATE_PRAM_ADRESSEMAIL_TABLE =
            "CREATE TABLE " + TABLE_PARAM_ADRESEMAIL + "("
                    + COL_PARAM_ADRESSEMAIL_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_PARAM_ADRESSEMAIL + TEXT_TYPE
                    + ");";

    // ACHAT TABLE
    public static final String TABLE_ACHAT = "achat";

    //COLUMNS NAME
    public static final String COL_ACHAT_ID = "_id";
    public static final String COL_ACHAT_DESIGNATION = "designiation";
    public static final String COL_ACHAT_PRIX = "prix";
    public static final String COL_ACHAT_TOTALP = "totalprix";
    public static final String COL_ACHAT_TASK_ID = "_idTask";
    public static final String COL_ACHAT_TASK_NUM_DOSS = "numDoss";


    // CREATE QUERY - TABLE ACHAT -
    String CREATE_ACHAT_TABLE =
            "CREATE TABLE " + TABLE_ACHAT + "("
                    + COL_ACHAT_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_ACHAT_DESIGNATION + TEXT_TYPE + COMMA_SEP
                    + COL_ACHAT_PRIX + REAL_TYPE + COMMA_SEP
                    + COL_ACHAT_TOTALP + REAL_TYPE + COMMA_SEP
                    + COL_ACHAT_TASK_NUM_DOSS + TEXT_TYPE + COMMA_SEP
                    + COL_ACHAT_TASK_ID + INTEGER_TYPE + COMMA_SEP
                    + " FOREIGN KEY(" + COL_ACHAT_TASK_ID + ")" +
                    " REFERENCES " + TABLE_TASK + "( " + COL_TASK_ID + " )"
                    + ");";


    // COMPOSANT TABLE
    public static final String TABLE_COMPOSANT = "composant";
    //COLUMNS NAME
    public static final String COL_COMP_ID = "_id";
    public static final String COL_COMP_NAME = "name";
    public static final String COL_COMP_QTE = "quantite";
    public static final String COL_COMP_TASK_ID = "_idtask";
    public static final String COL_COMP_TASK_NUM_DOSS = "numDoss";

    // CREATE QUERY - TABLE ACHAT -
    String CREATE_COMPOSANT_TABLE =
            "CREATE TABLE " + TABLE_COMPOSANT + "("
                    + COL_COMP_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_COMP_NAME + TEXT_TYPE + COMMA_SEP
                    + COL_COMP_QTE + NUMERIC_TYPE + COMMA_SEP
                    + COL_COMP_TASK_NUM_DOSS + TEXT_TYPE + COMMA_SEP
                    + COL_COMP_TASK_ID + INTEGER_TYPE + COMMA_SEP
                    + " FOREIGN KEY(" + COL_COMP_TASK_ID + ")" +
                    " REFERENCES " + TABLE_TASK + "( " + COL_TASK_ID + " )"
                    + ");";

    // RESULTATS TABLE
    public static final String TABLE_RESULTATS = "resultats";
    //COLUMNS NAME
    public static final String COL_RES_ID = "_id";
    public static final String COL_RES_NAME = "name";

    // ACTIVITEE TABLE
    public static final String TABLE_ACTIVITEES = "activitees";
    //COLUMNS NAME
    public static final String COL_ACT_ID = "_id";
    public static final String COL_ACT_NAME = "name";

    // USERS TABLE
    public static final String TABLE_USERS = "users";
    //COLUMNS NAME
    public static final String COL_USER_ID = "_id";
    public static final String COL_USER_CODE = "code";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_PASSWORD = "password";
    public static final String COL_USER_TYPE = "type";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_NUMTEL = "numTel";
    public static final String COL_USER_PATHPHTO = "photo";
    public static final String COL_USER_ADMIN = "admin";
    // CREATE QUERY - TABLE USERS -
    String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + "("
                    + COL_USER_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_USER_CODE + NUMERIC_TYPE + COMMA_SEP
                    + COL_USER_NAME + TEXT_TYPE + COMMA_SEP
                    + COL_USER_PASSWORD + TEXT_TYPE + COMMA_SEP
                    + COL_USER_EMAIL + TEXT_TYPE + COMMA_SEP
                    + COL_USER_NUMTEL + TEXT_TYPE + COMMA_SEP
                    + COL_USER_PATHPHTO + TEXT_TYPE + COMMA_SEP
                    + COL_USER_TYPE + TEXT_TYPE + COMMA_SEP
                    + COL_USER_ADMIN + NUMERIC_TYPE
                    + ");";


    // Pointage Table
    public static final String TABLE_POINTAGE = "pointage";
    //COLUMNS NAME
    public static final String COL_P_ID = "_id";
    public static final String COL_P_DATIMEIN_M = "datetimeInM";
    public static final String COL_P_DATIMEOUT_M = "datetimeOutM";
    public static final String COL_P_DATIMEIN_AM = "datetimeInAM";
    public static final String COL_P_DATIMEOUT_AM = "datetimeOutAM";
    public static final String COL_P_USERIN = "userIn";
    public static final String COL_P_USEROUT = "userOut";
    public static final String COL_P_USER_ID = "_idUser";
    public static final String COL_P_FLAG = "flag";
    public static final String COL_P_CATEGORY = "category";
    public static final String COL_P_DATE_POINTAGE = "datePointage";



    // Task Table
    public static final String TABLE_TASK = "task";
    //COLUMNS NAME
    public static final String COL_TASK_ID = "_id";
    public static final String COL_TASK_NAME = "taskname";
    public static final String COL_TASK_CLIENT = "client";
    public static final String COL_TASK_TIMEA = "timeA";
    public static final String COL_TASK_TIME_START = "timeStart";
    public static final String COL_TASK_TIME_STOP = "timeStop";
    public static final String COL_TASK_NUM_DOSS = "numdoss";
    public static final String COL_TASK_USER_ID = "_idUser";
    public static final String COL_TASK_DESIGNATION = "designation";
    public static final String COL_TASK_MONTANT_TTC = "montanttc";
    public static final String COL_TASK_BESOIN_COMP = "besoinComp";
    public static final String COL_TASK_TYPE = "type";
    public static final String COL_TASK_ACT = "act";
    public static final String COL_TASK_RES = "res";
    public static final String COL_TASK_FLAG_STATUS = "flagstatus";
    public static final String COL_TASK_CATEGORY = "category";


    // CREATE QUERY - TABLE POINTAGE -
    String CREATE_TASK_TABLE =
            "CREATE TABLE " + TABLE_TASK + "("
                    + COL_TASK_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_TASK_NAME + TEXT_TYPE + COMMA_SEP
                    + COL_TASK_CLIENT + TEXT_TYPE + COMMA_SEP
                    + COL_TASK_TIMEA + LONG_TYPE + COMMA_SEP
                    + COL_TASK_TIME_START + LONG_TYPE + COMMA_SEP
                    + COL_TASK_TIME_STOP + LONG_TYPE + COMMA_SEP
                    + COL_TASK_NUM_DOSS + NUMERIC_TYPE + COMMA_SEP
                    + COL_TASK_USER_ID + NUMERIC_TYPE + COMMA_SEP
                    + COL_TASK_ACT + TEXT_TYPE + COMMA_SEP
                    + COL_TASK_RES + TEXT_TYPE + COMMA_SEP
                    + COL_TASK_DESIGNATION + TEXT_TYPE + COMMA_SEP
                    + COL_TASK_MONTANT_TTC + REAL_TYPE + COMMA_SEP
                    + COL_TASK_BESOIN_COMP + TEXT_TYPE + COMMA_SEP
                    + COL_TASK_TYPE + TEXT_TYPE + COMMA_SEP
                    + COL_TASK_FLAG_STATUS + NUMERIC_TYPE + COMMA_SEP
                    + COL_TASK_CATEGORY + TEXT_TYPE + COMMA_SEP

                    + " FOREIGN KEY(" + COL_USER_ID + ")"
                    + " REFERENCES " + TABLE_USERS + "( " + COL_USER_ID + " )"
                    + ");";

    // CREATE QUERY - TABLE RESULTATS -
    String CREATE_RESULTATS_TABLE =
            "CREATE TABLE " + TABLE_RESULTATS + "("
                    + COL_RES_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_RES_NAME + TEXT_TYPE + ");";
    // CREATE QUERY - TABLE ACTIVITEE -
    String CREATE_ACTIVITEE_TABLE =
            "CREATE TABLE " + TABLE_ACTIVITEES + "("
                    + COL_ACT_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_ACT_NAME + TEXT_TYPE + ");";
    // CREATE QUERY - TABLE POINTAGE -
    String CREATE_POINTAGE_TABLE =
            "CREATE TABLE " + TABLE_POINTAGE + "("
                    + COL_P_ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                    + COL_P_DATIMEIN_M + LONG_TYPE + COMMA_SEP
                    + COL_P_DATIMEOUT_M + LONG_TYPE + COMMA_SEP
                    + COL_P_DATIMEIN_AM + LONG_TYPE + COMMA_SEP
                    + COL_P_DATIMEOUT_AM + LONG_TYPE + COMMA_SEP
                    + COL_P_USERIN + NUMERIC_TYPE + COMMA_SEP
                    + COL_P_USEROUT + NUMERIC_TYPE + COMMA_SEP
                    + COL_P_USER_ID + NUMERIC_TYPE + COMMA_SEP
                    + COL_P_CATEGORY + NUMERIC_TYPE + COMMA_SEP
                    + COL_P_DATE_POINTAGE + TEXT_TYPE + COMMA_SEP
                    + COL_P_FLAG + NUMERIC_TYPE + COMMA_SEP
                    + " FOREIGN KEY(" + COL_P_USER_ID + ")" +
                    " REFERENCES " + TABLE_USERS + "( " + COL_USER_ID + " )"
                    + ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESULTATS_TABLE);
        db.execSQL(CREATE_ACTIVITEE_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_POINTAGE_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_ACHAT_TABLE);
        db.execSQL(CREATE_COMPOSANT_TABLE);
        db.execSQL(CREATE_PRAM_ADRESSEMAIL_TABLE);
        db.execSQL(CREATE_MSG_TABLE);
        db.execSQL(CREATE_RAPPORT_TABLE);
        db.execSQL(CREATE_DOSSIER_TABLE);


        // db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('Labo')");
        // db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('Déplacement')");
        db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('SME')");
        db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('Data')");
        db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('TEI')");
        db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('GCE')");
        db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('C electronix')");
        // db.execSQL("INSERT INTO " + TABLE_ACTIVITEES + "(" + COL_ACT_NAME + ") VALUES ('Autre')");
        //
        db.execSQL("INSERT INTO " + TABLE_RESULTATS + "(" + COL_RES_NAME + ") VALUES (\'" + EN_ATTENTE + "\')");
        db.execSQL("INSERT INTO " + TABLE_RESULTATS + "(" + COL_RES_NAME + ") VALUES (\'" + EN_COURS + "\')");
        db.execSQL("INSERT INTO " + TABLE_RESULTATS + "(" + COL_RES_NAME + ") VALUES (\'" + FINI + "\')");


        db.execSQL("INSERT INTO " + TABLE_PARAM_ADRESEMAIL + "(" + COL_PARAM_ADRESSEMAIL + ") " +
                "                               VALUES (\'" + "ahmad.bejaoui@gmail.com" + "\')");

        db.execSQL("INSERT INTO " + TABLE_USERS + "("
                + COL_USER_CODE + ","
                + COL_USER_NAME + ","
                + COL_USER_PASSWORD + ","
                + COL_USER_EMAIL + ","
                + COL_USER_NUMTEL + ","
                + COL_USER_PATHPHTO + ","
                + COL_USER_TYPE + ","
                + COL_USER_ADMIN + ")"
                + " VALUES (\'"
                + ADMIN_CODE + "\',\'"
                + ADMIN_LOGIN + "\',\'"
                + ADMIN_PASSWORD + "\',\'"
                + ADMIN_EMAIL + "\',\'"
                + ADMIN_NUMTEL + "\',\'"
                + ADMIN_PATHPHOTO + "\',\'"
                + ADMIN_TYPE + "\',\'"
                + ADMIN_ISADIN + "\'"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_RESULTATS);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_ACTIVITEES);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_TASK);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_POINTAGE);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_ACHAT);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_COMPOSANT);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_PARAM_ADRESEMAIL);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_RAPPORT);
        db.execSQL("DROP TABLE IF EXISTE " + TABLE_DOSSIER);
        onCreate(db);
    }
}
