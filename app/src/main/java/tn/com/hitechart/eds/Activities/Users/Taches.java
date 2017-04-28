package tn.com.hitechart.eds.Activities.Users;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.GestionTaches.MultiTaskActivity;
import tn.com.hitechart.eds.Activities.Users.GestionTaches.NewTask;
import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.PointagesDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Pointage;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.DossierAdapter;
import tn.com.hitechart.eds.Util.TaskAdapter;
import tn.com.hitechart.eds.Util.utilMethod.Verif;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

public class Taches extends AppCompatActivity {

    long startTime = 0L;
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    private Handler myHandler = new Handler();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyy");
    //
    private Button btnAddNewTask;
    private boolean isActiv;
    //
    private PointagesDAO pointagesDAO;
    private TasksDAO tasksDAO;
    private DossierDAO dossierDAO;
    //
    private Pointage pointage;
    private Task task;
    private Dossier dossier;
    //
    private List<Task> tasks_;
    private List<Task> tasksByUserByDate;
    private List<Dossier> dossiers;
    private List<Dossier> monoDoss;
    private List<Dossier> multiDoss;
    private List<Dossier> allToDayDoss;
    //
    private TaskAdapter taskAdapter;
    private DossierAdapter dossierAdapter;
    //
    private TextView tvemptyTaskList;
    private TextView tvloggeduser;
    //
    private ListView listTask;
    private ListView listDossier;
    //
    private User userLoaded;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taches);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        userLoaded = UserManager.load();
        id = userLoaded.get_id();
        pointage = new Pointage();

        pointagesDAO = new PointagesDAO(this);
        if (pointagesDAO.getPointByUser(id) == null) {
            Verif.toast("Pointage null");
        } else {
            pointage = pointagesDAO.getPointByUser(id);
        }

        tasksDAO = new TasksDAO(this);
        dossierDAO = new DossierDAO(this);

        tasks_ = tasksDAO.getTachesByUser(userLoaded);
        tasksByUserByDate = new ArrayList();

        for (Task t : tasks_) {
            if (isToday(dateFormat.format(t.getTimeStart()))) {
                tasksByUserByDate.add(t);
            }
        }


        monoDoss = new ArrayList<>();
        multiDoss = new ArrayList<>();
        for (Task t : tasksByUserByDate) {
            if (t.getCategory().equals(Key.KEY_TASK_MONO)) {
                monoDoss.add(dossierDAO.getDossierByTaskId(t.get_id()));
               // Log.e("TaskMono",""+t);
            } else {
                multiDoss = dossierDAO.getAllDossiersByTaskId(t.get_id());
               // Log.e("TaskMulti",""+t);
            }
        }

        allToDayDoss= new ArrayList<>(monoDoss);
        allToDayDoss.addAll(multiDoss);

        for(Dossier d:allToDayDoss){
            Log.e("TaskMulti",""+d);
        }
        listTask = (ListView) findViewById(R.id.taskList);
        btnAddNewTask = (Button) findViewById(R.id.btnAddNewTask);
        tvemptyTaskList = (TextView) findViewById(R.id.tvemptyTL);

        if (allToDayDoss.isEmpty()) {
            tvemptyTaskList.setVisibility(View.VISIBLE);
        } else {
            tvemptyTaskList.setVisibility(View.INVISIBLE);
            taskAdapter = new TaskAdapter(this, tasksByUserByDate);
            listTask.setAdapter(taskAdapter);
        }

        //  for (Task tp : tasksByUserByDate) {
        //      Log.e("MY STATUS", ""+tp);
        //  }
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        btnAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pointage.getFlag() == Key.POINTAGE_OUT_AM) {
                    alertDeniedCreateNewTaskLastExit();
                } else if (pointage.getFlag() == Key.POINTAGE_OUT_M) {
                    alertDeniedCreateNewTaskFirstExit();
                } else if (getActiveTask()) {
                    alertDialogNewTask();
                } else {
                    onAddTache();
                }
            }
        });


    }

    public void alertDeniedCreateNewTaskLastExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Action Réfusée!")
                .setMessage("Poinatge 'Sortie Après-midi' a été marqué, vous ne pouvez pas ajouter une nouvelle tache !")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();
    }

    public void alertDeniedCreateNewTaskFirstExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Action Réfusée!")
                .setMessage("Poinatge 'Sortie Matin' a été marqué, vous ne pouvez pas ajouter une nouvelle tache !")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
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

    //private void getItemOnClickListener(final ListView lv) {
    //    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //        @Override
    //        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
    //            task = (Task) lv.getItemAtPosition(position);
    //            Intent intent = new Intent(getBaseContext(), EditTask.class);
    //            //intent.putExtra("TASK", new Gson().toJson(task));
    //            intent.putExtra("TASK", task);
    //            startActivity(intent);
    //        }
    //    });
    //}

    public void onAddTache() {
        Log.e("here", "-------------tsk choice-");
        // Intent i = new Intent(Taches.this, NewTask.class);
        // startActivity(i);
        // finish();
        alertTypeOfTask();
    }

    public Runnable updateTimerMethod = new Runnable() {
        @Override
        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;
            int seconds = (int) (finalTime / 1000);
            int minutes = seconds / 60;
            int hours = seconds / 3600;
            seconds = seconds % 60;
            int millisenconds = (int) (finalTime % 1000);
            String tmps = "" + String.format("%02d", hours) + ":"
                    + String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds);
            myHandler.postDelayed(this, 0);
        }
    };

    public boolean getActiveTask() {

        for (Task t : tasks_) {

            if ((t.getFlagStatus() == Key.TASK_FLAG_START)) {

                //Log.e("MY STATUS", "" + t.getFlagStatus() + "+++" + t.get_id());

                isActiv = true;
            } else {
                // Log.e("MY STATUS", "" + t.getFlagStatus() + "+++" + t.get_id());
                isActiv = false;
            }
        }
        return isActiv;
    }

    public void alertDialogNewTask() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Tache en cours de traitement...")
                .setMessage("Clôturer la tache en cours, afin de créer une autre!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();
    }

    public boolean isToday(String day) {
        return dateFormat.format(System.currentTimeMillis()).equals(day);
    }

    public void alertTypeOfTask() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.type_task_dialog_box);
        dialog.setTitle("Choix du type de la tache");

        Button btnmnotach = (Button) dialog.findViewById(R.id.btnmnotach);
        Button btnmltitach = (Button) dialog.findViewById(R.id.btnmltitach);
        Button btncancel = (Button) dialog.findViewById(R.id.btncancel);

        btnmnotach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), NewTask.class);
                startActivity(intent);
                finish();
            }
        });

        btnmltitach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MultiTaskActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

