package tn.com.hitechart.eds.Activities.Users;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.MainActivity;
import tn.com.hitechart.eds.Activities.Users.GestionPointages.AddPointage;
import tn.com.hitechart.eds.Activities.Users.GestionTaches.StartTask;
import tn.com.hitechart.eds.Activities.Users.Historique.ViewHistoriqueUserActivity;
import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.PointagesDAO;
import tn.com.hitechart.eds.DAO.RapportDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Pointage;
import tn.com.hitechart.eds.Entity.Rapport;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

import static tn.com.hitechart.eds.data.App.getContext;
import static tn.com.hitechart.eds.data.UserManager.disconnect;

public class DashboardUser extends AppCompatActivity {
    private TasksDAO tasksDAO;
    private Task task;
    private Dossier dossier;
    private List<Task> tasks_;
    private List<Task> todayTasks;

    private SimpleDateFormat dateofpointageFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    public SimpleDateFormat formatTimeHoureline = new SimpleDateFormat("HH");
    public SimpleDateFormat formatTimeMinutline = new SimpleDateFormat("mm");
    private TextView tvloggeduser;


    private TextView tvpoinatgeinm;
    private TextView tvpoinatgeoutm;
    private TextView tvpoinatgeinam;
    private TextView tvpoinatgeoutam;

    private Pointage pointage;
    private PointagesDAO pointagesDAO;
    private User currentUser;
    private Button btnviewrapport;
    private Intent intent;
    boolean isToday = false;
    private TextView tvnbrtachesuspendu;
    private TextView tvnbrtacheclosed;
    private DossierDAO dossierDAO;
    private List<Task> tasksByUserByDate;
    private List<Dossier> monoDoss;
    private List<Dossier> multiDoss;
    private List<Dossier> allToDayDoss;
    private int countPause;
    private int countStop;
    private boolean istoday;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        currentUser = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(currentUser.getLogin());

        tvpoinatgeinm = (TextView) findViewById(R.id.tvpoinatgeinm);
        tvpoinatgeoutm = (TextView) findViewById(R.id.tvpoinatgeoutm);
        tvpoinatgeinam = (TextView) findViewById(R.id.tvpoinatgeinam);
        tvpoinatgeoutam = (TextView) findViewById(R.id.tvpoinatgeoutam);

        tvnbrtachesuspendu = (TextView) findViewById(R.id.tvnbrtachesuspendu);
        tvnbrtacheclosed = (TextView) findViewById(R.id.tvnbrtacheclosed);

        btnviewrapport = (Button) findViewById(R.id.btnviewrapport);

        //Log.e("isAdmin", "" + UserManager.load().isAdmin());
        tvpoinatgeinm.setText("--:--");
        tvpoinatgeoutm.setText("--:--");
        tvpoinatgeinam.setText("--:--");
        tvpoinatgeoutam.setText("--:--");
        pointagesDAO = new PointagesDAO(this);


        //  pointage = pointagesDAO.getPointByUser(currentUser.get_id());//getPointByUserByDate(currentUser.get_id(),dateofpointageFormat.format(System.currentTimeMillis()));
        //  if (pointage != null) {
        //   istoday = comparDay(Integer.valueOf(dateofpointageFormat.format(System.currentTimeMillis())),
        //           Integer.valueOf(dateofpointageFormat.format(pointage.getDate_timeInM())));
        //   if (istoday) {// dèja marqué pointage d'aujourdhui
        //       pointage = pointagesDAO.getPointByUser(id);
        //       Log.i("today flag", pointage.getFlag() + "--");
        //       flag = pointage.getFlag();// flag = 1 || 2 || 3
        //   }else{
        //       tvpoinatgeinm.setText("--:--");
        //       tvpoinatgeoutm.setText("--:--");
        //       tvpoinatgeinam.setText("--:--");
        //       tvpoinatgeoutam.setText("--:--");
        //   }
        //   if (pointage.getFlag() == Key.POINTAGE_IN_M) {
        //       tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeInM()));
        //       tvpoinatgeoutm.setText("--:--");
        //       tvpoinatgeinam.setText("--:--");
        //       tvpoinatgeoutam.setText("--:--");
        //   } else if (pointage.getFlag() == Key.POINTAGE_OUT_M) {

        //       tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeInM()));

        //       tvpoinatgeoutm.setText(formatTimeHoureline.format(pointage.getDate_timeOutM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeOutM()));
        //       tvpoinatgeinam.setText("--:--");
        //       tvpoinatgeoutam.setText("--:--");
        //   } else if (pointage.getFlag() == Key.POINTAGE_IN_AM) {
        //       tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeInM()));
        //       tvpoinatgeoutm.setText(formatTimeHoureline.format(pointage.getDate_timeOutM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeOutM()));
        //       tvpoinatgeinam.setText(formatTimeHoureline.format(pointage.getDate_timeInAM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeInAM()));
        //       tvpoinatgeoutam.setText("--:--");
        //   } else if (pointage.getFlag() == Key.POINTAGE_OUT_AM) {
        //       tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeInM()));
        //       tvpoinatgeoutm.setText(formatTimeHoureline.format(pointage.getDate_timeOutM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeOutM()));
        //       tvpoinatgeinam.setText(formatTimeHoureline.format(pointage.getDate_timeInAM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeInAM()));
        //       tvpoinatgeoutam.setText(formatTimeHoureline.format(pointage.getDate_timeOutAM()) + ":"
        //               + formatTimeMinutline.format(pointage.getDate_timeOutAM()));
        //   }
        //

        //else{
        //   tvpoinatgeinm.setText("--:--");
        //   tvpoinatgeoutm.setText("--:--");
        //   tvpoinatgeinam.setText("--:--");
        //   tvpoinatgeoutam.setText("--:--");
        //
        //activerbtnviewrapport();
        User user = UserManager.load();
        pointage = pointagesDAO.getPointByUser(user.get_id());
        if (pointagesDAO.getPointByUser(user.get_id()) != null) { //Deja marqué le pointage
            istoday = comparDay(dateofpointageFormat.format(System.currentTimeMillis()),
                    dateofpointageFormat.format(pointage.getDate_timeInM()));
            if (istoday) {// dèja marqué pointage d'aujourdhui
                pointage = pointagesDAO.getPointByUser(user.get_id());
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

        tasksDAO = new TasksDAO(getContext());
        tasks_ = tasksDAO.getTachesByUser(user);
        todayTasks = new ArrayList();
        if (tasks_ != null) {
            for (Task tsk : tasks_) {
                if (isToday(dateFormat.format(tsk.getTimeStart()))) {
                    //Log.e("TASKS_CURRENT_DAY",
                    //        "Task ID:[" + tsk.get_id() + "]"
                    //                + "Status:[" + tsk.getFlagStatus() + "]"
                    //                + "User:[" + tsk.get_idUser() + "]"
                    //                + "Date of task:[" + dateFormat.format(tsk.getTimeStart()) + "]"
                    //                + "Resultat:[" + tsk.getRes() + "]"
                    //                + "\r\n");
                    todayTasks.add(tsk);
                }
            }
        }
//-------------------------------- SWITCH --------------------------------------//
        switch (flag) {
            case 1:
                tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeInM()));
                tvpoinatgeoutm.setText("--:--");
                tvpoinatgeinam.setText("--:--");
                tvpoinatgeoutam.setText("--:--");
                break;
            case 2:
                tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeInM()));
                tvpoinatgeoutm.setText(formatTimeHoureline.format(pointage.getDate_timeOutM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeOutM()));
                tvpoinatgeinam.setText("--:--");
                tvpoinatgeoutam.setText("--:--");
                break;
            case 3:
                tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeInM()));
                tvpoinatgeoutm.setText(formatTimeHoureline.format(pointage.getDate_timeOutM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeOutM()));
                tvpoinatgeinam.setText(formatTimeHoureline.format(pointage.getDate_timeInAM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeInAM()));
                tvpoinatgeoutam.setText("--:--");
                break;
            case -1:
                tvpoinatgeinm.setText(formatTimeHoureline.format(pointage.getDate_timeInM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeInM()));
                tvpoinatgeoutm.setText(formatTimeHoureline.format(pointage.getDate_timeOutM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeOutM()));
                tvpoinatgeinam.setText(formatTimeHoureline.format(pointage.getDate_timeInAM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeInAM()));
                tvpoinatgeoutam.setText(formatTimeHoureline.format(pointage.getDate_timeOutAM()) + ":"
                        + formatTimeMinutline.format(pointage.getDate_timeOutAM()));
                break;
            default:
                tvpoinatgeinm.setText("--:--");
                tvpoinatgeoutm.setText("--:--");
                tvpoinatgeinam.setText("--:--");
                tvpoinatgeoutam.setText("--:--");

        }

        /*******************************************************************************/

        countPause = 0;
        countStop = 0;

        tasksDAO = new TasksDAO(this);
        dossierDAO = new DossierDAO(this);

        tasks_ = tasksDAO.getTachesByUser(currentUser);
        tasksByUserByDate = new ArrayList();

        for (Task t : tasks_) {
            if (isToday(dateFormat.format(t.getTimeStart()))) {
                tasksByUserByDate.add(t);
            }
        }


        monoDoss = new ArrayList<>();
        multiDoss = new ArrayList<>();
        for (Task t : tasksByUserByDate) {
            if (t.getFlagStatus() == Key.TASK_FLAG_PAUSE) {
                countPause++;
            }
            if (t.getFlagStatus() == Key.TASK_FLAG_STOP) {
                countStop++;
            }

            if (t.getCategory().equals(Key.KEY_TASK_MONO)) {
                monoDoss.add(dossierDAO.getDossierByTaskId(t.get_id()));
                // Log.e("TaskMono",""+t);
            } else {
                multiDoss = dossierDAO.getAllDossiersByTaskId(t.get_id());
                // Log.e("TaskMulti",""+t);
            }
        }

        allToDayDoss = new ArrayList<>(monoDoss);
        allToDayDoss.addAll(multiDoss);

        for (Dossier d : allToDayDoss) {

            Log.e("TaskMulti", "" + d);
        }
        tvnbrtachesuspendu.setText(String.valueOf(countPause));
        tvnbrtacheclosed.setText(String.valueOf(countStop));

        /************************************************************************/


        getRapport();
    }


    public void getRapport() {
        if (pointage != null) {
            RapportDAO rapportDAO = new RapportDAO(this);
            Rapport rapport = rapportDAO.getRapportByUserbyDate(currentUser.get_id(),
                    dateofpointageFormat.format(
                            pointage.getDate_timeInM()));
            Log.e("Rapport", "" + rapport);
        }
    }

    //
    public void toGTaches(View view) {
        if (pointage == null) {
            getErroMessageDialog();
            //Log.e("PNULL", "POINTAGE IS NULL");
        } else if (!isToday(dateFormat.format(pointage.getDate_timeInM()))) {

            // Log.e("PNOTNULL", "POINTAGE IS NOT NULL");
            // Log.e("PFLAG", "" + pointage.getFlag());
            getErroMessageDialog();
        } else {
            // Log.e("FlagOncreate task", "" + pointage.getFlag() + "--" + dateFormat.format(pointage.getDate_timeInM()));
            if (findTaskEnCours() != null) {
                //final
                Task Taskresume = findTaskEnCours();
                // Log.e("FINDOPENTASKfromUDB", "[" + Taskresume.get_id() + "][" + Taskresume.getFlagStatus() + "][" + Taskresume.getRes() + "]");

                intent = new Intent(this, StartTask.class);
                intent.putExtra("Task", Taskresume);
                startActivity(intent);
                finish();
            } else {
                intent = new Intent(this, Taches.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void toPointage(View view) {
        Intent intent = new Intent(this, AddPointage.class);
        startActivity(intent);
        finish();
    }

    public void toLogout(View view) {

        if (getActiveTaskWheneExit()) {
            alertDialogVerifOpenTask();
        } else {
            alertDialogExitApp();
        }


    }

    //
    public void onViewRapport(View view) {

        if (pointage == null) {
            getErroMessageDialog();
            // Log.e("PNULL", "POINTAGE IS NULL");
        } else if (pointage.getFlag() == -1 && isToday(dateFormat.format(pointage.getDate_timeInM()))) {
            Intent intent = new Intent(this, RapportActivity.class);
            startActivity(intent);
            finish();
        } else {
            alertDialogViewRapport();
        }
    }

    public void activerbtnviewrapport() {
        btnviewrapport.setEnabled(true);
    }

    public void alertDialogViewRapport() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Consulter rapport")
                .setMessage("Pour pouvoir consulter le Rapport Journalier, il faut tout d\'abord :\r\n" +
                        "- Clôturer toutes les taches.\r\n" +
                        "- Marquer l'heure de Sortie.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //disconnect();
                        //Intent intent = new Intent(getBaseContext(), RapportActivity.class);
                        //startActivity(intent);
                        //finish();
                    }
                }).show();
    }
    //

    public boolean getActiveTaskWheneExit() {

        boolean isActive = false;

        for (Task t : todayTasks) {

            if ((t.getFlagStatus() == Key.TASK_FLAG_START) || (t.getFlagStatus() == Key.TASK_FLAG_PAUSE)) {

                //Log.e("MY STATUS", "" + t.getFlagStatus() + "+++" + t.get_id());

                isActive = true;
            } else {
                //Log.e("MY STATUS", "" + t.getFlagStatus() + "+++" + t.get_id());
                isActive = false;
            }
        }
        return isActive;
    }

    public Task findTaskEnCours() {
        // TasksDAO tasksDAO = new TasksDAO(this);
        // final List<Task> tasks = tasksDAO.getTasks();
        if (todayTasks != null) {
            for (Task task : todayTasks) {
                if (task.getFlagStatus() == Key.TASK_FLAG_START) {
                    return task;
                }
            }
        }

        return null;
    }

    public boolean getActiveTask() {

        boolean isActive = false;

        for (Task t : todayTasks) {

            if ((t.getFlagStatus() == Key.TASK_FLAG_START)) {

                // Log.e("MY STATUS", "" + t.getFlagStatus() + "+++" + t.get_id());

                isActive = true;
            } else {
                // Log.e("MY STATUS", "" + t.getFlagStatus() + "+++" + t.get_id());
                isActive = false;
            }
        }
        return isActive;
    }

    public void alertDialogExitApp() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Quitter l'application?")
                .setMessage("Êtes-vous sûr de vouloir quitter l'application?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        disconnect();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Non", null).show();
    }

    public void alertDialogVerifOpenTask() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Tache en cours de traitement...")
                .setMessage("Clôturer les taches en cours traitement avant de quitter!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();
    }

    private void getErroMessageDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Pointage")
                .setMessage("Verfier votre pointage d'entrée SVP! ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();

    }

    public boolean isToday(String day) {
        return dateFormat.format(System.currentTimeMillis()).equals(day);
    }
    // TODO: 10/01/2017  vous ete connecté en tanque "username"[DONE]

    public void onConsulterHistoriqueUser(View view) {

        Intent intent = new Intent(this, ViewHistoriqueUserActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean comparDay(String day1, String day2) {
        return day1.equals(day2);
    }
}
