package tn.com.hitechart.eds.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.Activities.Users.DashboardUser;
import tn.com.hitechart.eds.Activities.Users.GestionTaches.GestionDossier.AddDossierActivity;
import tn.com.hitechart.eds.Activities.Users.GestionTaches.StartTask;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, SPLASH_TIME_OUT);

    }

    public void checkUser() {
        Intent intent;
        if (UserManager.isConnect()) {
            if (UserManager.isAdmin()) {
                intent = new Intent(this, DashboardAdmin.class);
            } else {
                //
                Task task = findTaskEnCours();
                Task taskIsCreated = findTaskIsCreated();
                if (task != null) {
                    intent = new Intent(this, StartTask.class);
                    intent.putExtra("Task", task);
                } else if (taskIsCreated != null) {
                    intent = new Intent(this, AddDossierActivity.class);
                    intent.putExtra("Task", taskIsCreated);
                } else {
                    intent = new Intent(this, DashboardUser.class);
                }
                //
            }
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    public Task findTaskEnCours() {
        TasksDAO tasksDAO = new TasksDAO(this);
        final List<Task> tasks = tasksDAO.getTasks();
        if (tasks != null) {
            for (Task task : tasks) {
                if (task.getFlagStatus() == Key.TASK_FLAG_START) {//DBHelper.EN_COURS.equalsIgnoreCase(task.getRes())) {

                    Log.e("Task","is running");
                    return task;
                }
            }
        }
        return null;
    }

    public Task findTaskIsCreated() {
        TasksDAO tasksDAO = new TasksDAO(this);

        final List<Task> tasks = tasksDAO.getTasks();
        if (tasks != null) {
            for (Task task : tasks) {
                Log.e("categoryTASK","category :"+task.getCategory()+" :: status :"+task.getFlagStatus()+" :: ID:"+task.get_id());
                if (task.getFlagStatus() == Key.TASK_FLAG_CREATE) {
                    Log.e("MTask","was created");
                    return task;
                }
            }
        }
        return null;
    }
}
