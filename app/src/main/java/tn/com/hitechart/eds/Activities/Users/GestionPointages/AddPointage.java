package tn.com.hitechart.eds.Activities.Users.GestionPointages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.DashboardUser;
import tn.com.hitechart.eds.Activities.Users.RapportActivity;
import tn.com.hitechart.eds.Activities.Users.Taches;
import tn.com.hitechart.eds.DAO.PointagesDAO;
import tn.com.hitechart.eds.DAO.RapportDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.Pointage;
import tn.com.hitechart.eds.Entity.Rapport;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

public class AddPointage extends AppCompatActivity {

    private SimpleDateFormat datePointage = new SimpleDateFormat("dd/MM/yyyy");
    public SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
    public SimpleDateFormat day = new SimpleDateFormat("dd");
    private TasksDAO tasksDAO;
    private boolean istoday;
    private int flag = 0;
    private Button btnPointage;
    private List<Pointage> pointageList;
    private List<User> userList;
    private PointagesDAO pointagesDAO;
    private UsersDAO usersDAO;
    private Pointage pointage;
    private int id;
    private Button btnTimeInM, btnTimeInAM, btnTimeOutM, btnTimeOutAM;
    private TextView tvtimeinm;
    private TextView tvtimeinam;
    private TextView tvtimeoutm;
    private TextView tvtimeoutam;
    private Calendar calendar;
    private Date today;
    private Date tomorrow;
    private Button btngenrateRapport;
    private TextView tvloggeduser;
    User userLoaded;
    private ImageView btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pointage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);


        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        id = userLoaded.get_id();
        tasksDAO = new TasksDAO(this);
        pointagesDAO = new PointagesDAO(this);

        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        btnTimeInM = (Button) findViewById(R.id.btnTimeInM);
        btnTimeInAM = (Button) findViewById(R.id.btnTimeInAM);
        btnTimeOutM = (Button) findViewById(R.id.btnTimeOutM);
        btnTimeOutAM = (Button) findViewById(R.id.btnTimeOutAM);
        btnExit = (ImageView) findViewById(R.id.btnExit);

        //btngenrateRapport = (Button) findViewById(R.id.btnrapport);

        tvtimeinm = (TextView) findViewById(R.id.tvtimeinm);
        tvtimeinam = (TextView) findViewById(R.id.tvtimeinam);
        tvtimeoutm = (TextView) findViewById(R.id.tvtimeoutm);
        tvtimeoutam = (TextView) findViewById(R.id.tvtimeoutam);

        //btngenrateRapport.setEnabled(false);
        pointage = pointagesDAO.getPointByUser(id);
        if (pointagesDAO.getPointByUser(id) != null) { //Deja marqué le pointage
            istoday = comparDay(Integer.valueOf(day.format(System.currentTimeMillis())),
                    Integer.valueOf(day.format(pointage.getDate_timeInM())));
            if (istoday) {// dèja marqué pointage d'aujourdhui
                pointage = pointagesDAO.getPointByUser(id);
                Log.i("today flag", pointage.getFlag() + "--");
                flag = pointage.getFlag();// flag = 1 || 2 || 3
            } else {// deja pointage sortie d'hier
                flag = 0;// new pointage aujourd'hui
                Log.i("flag", pointage.getFlag() + "--");
            }

        } else {//pointage 1ere fois
            flag = 0;
            Log.i("flag", flag + "--");
        }

        userList = new ArrayList();
        pointageList = new ArrayList();
        usersDAO = new UsersDAO(this);
        Log.e("istoday", "" + istoday);

        //-------------------------------- SWITCH --------------------------------------//
        switch (flag) {
            case 1:
                btnTimeInM.setEnabled(false);
                btnTimeOutM.setEnabled(true);
                btnTimeInAM.setEnabled(false);
                btnTimeOutAM.setEnabled(false);
                btnExit.setEnabled(true);

                tvtimeinm.setText(df.format(pointage.getDate_timeInM()));
                tvtimeoutm.setText("--:--:--");
                tvtimeinam.setText("--:--:--");
                tvtimeoutam.setText("--:--:--");
                break;
            case 2:
                btnTimeInM.setEnabled(false);
                btnTimeOutM.setEnabled(false);
                btnTimeInAM.setEnabled(true);
                btnTimeOutAM.setEnabled(false);
                tvtimeinm.setText(df.format(pointage.getDate_timeInM()));
                tvtimeoutm.setText(df.format(pointage.getDate_timeOutM()));
                tvtimeinam.setText("--:--:--");
                tvtimeoutam.setText("--:--:--");
                break;
            case 3:
                btnTimeInM.setEnabled(false);
                btnTimeOutM.setEnabled(false);
                btnTimeInAM.setEnabled(false);
                btnTimeOutAM.setEnabled(true);
                tvtimeinm.setText(df.format(pointage.getDate_timeInM()));
                tvtimeoutm.setText(df.format(pointage.getDate_timeOutM()));
                tvtimeinam.setText(df.format(pointage.getDate_timeInAM()));
                tvtimeoutam.setText("--:--:--");
                break;
            case -1:
                btnTimeInM.setEnabled(false);
                btnTimeOutM.setEnabled(false);
                btnTimeInAM.setEnabled(false);
                btnTimeOutAM.setEnabled(false);
                btnExit.setEnabled(false);
                tvtimeinm.setText(df.format(pointage.getDate_timeInM()));
                tvtimeoutm.setText(df.format(pointage.getDate_timeOutM()));
                tvtimeinam.setText(df.format(pointage.getDate_timeInAM()));
                tvtimeoutam.setText(df.format(pointage.getDate_timeOutAM()));
                break;
            default:
                btnTimeInM.setEnabled(true);
                btnTimeOutM.setEnabled(false);
                btnTimeInAM.setEnabled(false);
                btnTimeOutAM.setEnabled(false);
                btnExit.setEnabled(false);

                tvtimeinm.setText("--:--:--");
                tvtimeoutm.setText("--:--:--");
                tvtimeinam.setText("--:--:--");
                tvtimeoutam.setText("--:--:--");

        }


        btnTimeInM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointagesDAO.getPointByUser(id) == null) {
                    pointage = pointagesDAO.getPointByUser(id);
                    newPUserIN_M();
                    Log.e("in M at:", "" + df.format(pointage.getDate_timeInM()));
                    Log.e("in M at:", "" + pointage.getDate_timeInM());
                    Log.e("CurrentTime:", "" + df.format(System.currentTimeMillis()));
                    tvtimeinm.setText(df.format(pointage.getDate_timeInM()));

                    startActivity(new Intent(AddPointage.this, DashboardUser.class));
                    finish();

                } else {
                    toast(df.format(pointage.getDate_timeInM()));
                    pointage.getDate_timeInM();
                    btnTimeInM.setEnabled(false);
                    btnTimeOutM.setEnabled(true);
                    tvtimeinm.setText(df.format(pointage.getDate_timeInM()));

                    Log.e("CurrentTime:", "" + df.format(System.currentTimeMillis()));

                    newPUserIN_M();
                    Log.e("in M at:", "" + df.format(pointage.getDate_timeInM()));
                    Log.e("in M at:", "" + pointage.getDate_timeInM());
                    startActivity(new Intent(AddPointage.this, DashboardUser.class));
                    finish();
                }

            }
        });
        btnTimeOutM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointagesDAO.getPointByUser(id) != null) {
                    pointage = pointagesDAO.getPointByUser(id);
                    Log.i("p not empty?", "--" + pointage.get_idUser() + "--" + pointage.getFlag() + "--" + pointage.get_id());
                    flag = pointage.getFlag();
                    Log.e("CurrentTime:", "" + df.format(System.currentTimeMillis()));
                    updatePUserOut_M();
                    tvtimeoutm.setText(df.format(pointage.getDate_timeOutM()));
                    Log.e("out M at:", "" + df.format(pointage.getDate_timeOutM()));
                    Log.e("out M at:", "" + pointage.getDate_timeOutM());
                    startActivity(new Intent(AddPointage.this, DashboardUser.class));
                    finish();
                }
            }
        });
        btnTimeInAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointagesDAO.getPointByUser(id) != null) {
                    pointage = pointagesDAO.getPointByUser(id);
                    Log.i("p not empty?", "--" + pointage.get_idUser() + "--" + pointage.getFlag() + "--" + pointage.get_id());
                    flag = pointage.getFlag();
                    Log.e("CurrentTime:", "" + df.format(System.currentTimeMillis()));
                    updatePUserIn_AM();
                    tvtimeinam.setText(df.format(pointage.getDate_timeInAM()));


                    Log.e("in AM at:", "" + df.format(pointage.getDate_timeInAM()));
                    Log.e("in AM at:", "" + pointage.getDate_timeInAM());
                    toast(df.format(pointage.getDate_timeInM()));
                    startActivity(new Intent(AddPointage.this, DashboardUser.class));
                    finish();
                }
            }
        });


        btnTimeOutAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findUnfishTask().size() > 0) {
                    onCloseAllTask();
                } else {
                    Log.e("alltaskarefinshed", "" + findUnfishTask().size());
                    if (pointagesDAO.getPointByUser(id) != null) {
                        pointage = pointagesDAO.getPointByUser(id);
                        Log.e("CurrentTime:", "" + df.format(System.currentTimeMillis()));

                        updatePUserOutAM();
                        tvtimeoutam.setText(df.format(pointage.getDate_timeOutAM()));
                        toast(df.format(pointage.getDate_timeOutAM()));
                        Log.e("out AM at:", "" + df.format(pointage.getDate_timeOutAM()));
                        Log.e("out AM at:", "" + pointage.getDate_timeOutAM());
                        redirecGenerRapport();
                    }
                }
            }
        });

    }


    private void newPUserIN_M() {
        pointage = new Pointage();
        pointage.set_idUser(id);
        pointage.setDate_timeInM(System.currentTimeMillis());
        pointage.setFlag(1);
        pointage.setDatePointage(datePointage.format(System.currentTimeMillis()));
        pointage.setUserIn(true);
        pointage.setUserOut(false);
        pointagesDAO.addNewPUserIn(pointage);
    }

    private void updatePUserOut_M() {
        btnTimeInAM.setEnabled(true);
        btnTimeOutM.setEnabled(false);
        pointage.setDate_timeOutM(System.currentTimeMillis());
        pointage.setFlag(2);
        pointage.setUserIn(false);
        pointage.setUserOut(true);
        pointagesDAO.updateUserOutM(pointage);
    }

    private void updatePUserIn_AM() {
        btnTimeInAM.setEnabled(false);
        btnTimeOutAM.setEnabled(true);
        pointage.setDate_timeInAM(System.currentTimeMillis());
        pointage.setFlag(3);
        pointage.setUserIn(true);
        pointage.setUserOut(false);
        pointagesDAO.updateUserInAM(pointage);
    }

    private void updatePUserOutAM() {
        btnTimeInM.setEnabled(true);
        btnTimeOutAM.setEnabled(false);
        pointage.setDate_timeOutAM(System.currentTimeMillis());
        pointage.setFlag(-1);
        pointage.setUserIn(false);
        pointage.setUserOut(true);
        pointagesDAO.updateUserOutAM(pointage);
    }


    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG);
    }

    public boolean comparDay(int day1, int day2) {
        return day1 == day2;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, DashboardUser.class);
        startActivity(intent);
        finish();
    }

    public List<Task> findUnfishTask() {
        List<Task> tasks = tasksDAO.getUNFinishedTask(userLoaded.get_id());
        Log.e("", "" + tasks.size());
        return tasks;
    }

    private void onCloseAllTask() {
        new AlertDialog.Builder(this)
                .setTitle("Erreur: Tache(s) ouverte(s)")
                .setMessage("Assurer la fermeture de toutes les taches avant de quitter svp ?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        startActivity(new Intent(AddPointage.this, Taches.class));
                        finish();
                    }
                })
                .show();

    }


    public void genrrappot(View view) {
        Intent i = new Intent(this, RapportActivity.class);
        startActivity(i);
        finish();
    }


    private void redirecGenerRapport() {
        new AlertDialog.Builder(this)
                .setTitle("Génération du rapport")
                .setMessage("Générez voter rapport journalier! ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        generateRapport();
                        Intent intent = new Intent(getBaseContext(), RapportActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DashboardUser.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toExceptExit(View view) {
        alterpoinatgeDemi_journee();
    }

    //Alert Demie-journée
    public void alterpoinatgeDemi_journee() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirmation Pointage Demi-Journée?")
                .setMessage("Êtes-vous sûr de vouloir pointer une \"Demi-Journée\"?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (findUnfishTask().size() > 0) {
                            onCloseAllTask();
                        } else {
                            Log.e("alltaskarefinshed", "" + findUnfishTask().size());
                            if (pointagesDAO.getPointByUser(id) != null) {
                                pointage = pointagesDAO.getPointByUser(id);
                                Log.e("CurrentTime:", "" + df.format(System.currentTimeMillis()));
                                if (pointage.getFlag() == Key.POINTAGE_IN_M) {
                                    updatePUserIn_AM();
                                    updatePUserOutAM();
                                    updatePUserOutAM();
                                }
                                if (pointage.getFlag() == Key.POINTAGE_OUT_M) {
                                    updatePUserIn_AM();
                                    updatePUserOutAM();
                                } else if (pointage.getFlag() == Key.POINTAGE_IN_AM) {
                                    updatePUserOutAM();
                                }

                                tvtimeoutm.setText("--:--:--");
                                tvtimeinam.setText("--:--:--");
                                tvtimeoutam.setText(df.format(pointage.getDate_timeOutAM()));
                                toast(df.format(pointage.getDate_timeOutAM()));
                                Log.e("out AM at:", "" + df.format(pointage.getDate_timeOutAM()));
                                Log.e("out AM at:", "" + pointage.getDate_timeOutAM());
                                redirecGenerRapport();
                            }
                        }
                    }
                }).setNegativeButton("Non", null).show();
    }
    // TODO: 10/01/2017 boutton outAM deverouillier
    // TODO: 10/01/2017 rapport par jour + par user (historique)

    /// GENERATION DE RAPPORT
    private void generateRapport() {
        RapportDAO rapportDAO = new RapportDAO(this);
        Rapport rapport = new Rapport();
        rapport.setUserId(userLoaded.get_id());
        rapport.setSend(false);
        rapport.setDateofCreation(datePointage.format(System.currentTimeMillis()));
        rapportDAO.addRapport(rapport);

        Log.e("Rapport", "was created");
    }
}
