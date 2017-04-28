package tn.com.hitechart.eds.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.GestionTaches.StartTask;
import tn.com.hitechart.eds.Activities.Users.Taches;
import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.PointagesDAO;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Pointage;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by BARA' on 03/05/2016.
 */
public class TaskAdapter extends ArrayAdapter<Task> {


    Pointage pointage;
    PointagesDAO pointagesDAO = new PointagesDAO(getContext());
    User user = UserManager.load();

    Context context;
    Taches taches = new Taches();
    long startTime = 0L;
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    List<Task> tasks;
    //TaskDAO taskDAO = new TaskDAO(getContext());
    View v;
    String tmps = "00:00:00";
    private SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
    private Handler myHandler = new Handler();

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tasksitem, parent, false);

            viewHolder.tvnumDoss = (TextView) convertView.findViewById(R.id.tvNumDoss);
            viewHolder.tvClient = (TextView) convertView.findViewById(R.id.tvClient);
            viewHolder.tvAct = (TextView) convertView.findViewById(R.id.tvAct);

            viewHolder.tvTimeA = (TextView) convertView.findViewById(R.id.tvTimeA);
            viewHolder.btnresume = (Button) convertView.findViewById(R.id.btnresume);
            viewHolder.tvterminResPause = (TextView) convertView.findViewById(R.id.tvterminResPause);
            viewHolder.tvterminRes = (TextView) convertView.findViewById(R.id.tvterminRes);

            viewHolder.taskList = (ListView) convertView.findViewById(R.id.taskList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DossierDAO dossierDAO = new DossierDAO(getContext());
        final Task task = getItem(position);


        if (pointagesDAO.getPointByUser(user.get_id()) != null) {
            pointage = pointagesDAO.getPointByUser(user.get_id());
        }


        if (task.getCategory().equals(Key.KEY_TASK_MONO)) {
            Log.e("TaskMono", "" + task);
            Dossier d = dossierDAO.getDossierById(task.get_id());
            viewHolder.tvnumDoss.setText(d.getNumDoss());
            viewHolder.tvClient.setText(d.getClient());
        }
        else if (task.getCategory().equals(Key.KEY_TASK_MULTI)) {
            String numalldoss = "";
            String clients = "";
            if (dossierDAO.getAllDossiersByTaskId(task.get_id()) != null) {
                for (Dossier dossier : dossierDAO.getAllDossiersById(task.get_id())) {

                    Log.e("TaskMulti", "" + task);
                    numalldoss = numalldoss + dossier.getNumDoss() + ", ";
                    clients = clients + dossier.getClient() + ",";
                }
                viewHolder.tvnumDoss.setText(numalldoss);
                viewHolder.tvClient.setText(clients);

            }
        }
        if (task.getType().equals(Key.TASK_TYPE_AUTRE)) {
            viewHolder.tvAct.setText(String.valueOf(task.getAct()));
        } else {
            viewHolder.tvAct.setText(String.valueOf(task.getType()));
        }
        String etatfinal = "Etat final : ";
        //Log.e("PAUSE TASK from ta", "["+task.get_id()+"]["+task.getFlagStatus()+"]["+task.getRes()+"]");
        if (task.getFlagStatus() == Key.TASK_FLAG_STOP) {
            viewHolder.btnresume.setVisibility(INVISIBLE);
            viewHolder.tvterminResPause.setVisibility(INVISIBLE);
            viewHolder.tvterminRes.setVisibility(VISIBLE);
            viewHolder.tvterminRes.setText(etatfinal + task.getRes());
        } else {
            viewHolder.tvterminResPause.setText(task.getRes());
            viewHolder.tvterminResPause.setVisibility(VISIBLE);
            viewHolder.btnresume.setVisibility(VISIBLE);
            viewHolder.tvterminRes.setVisibility(INVISIBLE);

            viewHolder.btnresume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pointage.getFlag() == Key.POINTAGE_OUT_M) {
                        //alert
                        alertDeniedCreateNewTaskFirstExit();
                    } else {
                        Intent intent = new Intent(getContext(), StartTask.class);
                        intent.putExtra(Key.KEY_TASK, task);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }
            });
        }

        long t = task.getTimeA();
        int seconds = (int) (t / 1000) % 60;
        int minutes = (int) ((t / (1000 * 60)) % 60);
        int hours = (int) ((t / (1000 * 60 * 60)) % 24);
        //seconds = seconds % 60;
        viewHolder.tvTimeA.setText(""
                + String.format("%02d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds));

        v = convertView;
        return convertView;

    }

    public void alertDeniedCreateNewTaskFirstExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Action Réfusée!")
                .setMessage("Poinatge 'Sortie Matin' a été marqué, vous ne pouvez pas ajouter une nouvelle tache !")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

    private static class ViewHolder {
        TextView tvnumDoss;
        TextView tvClient;
        TextView tvTimeA;
        TextView tvterminResPause;
        TextView tvAct;
        TextView tvterminRes;

        Button btnresume;
        ListView taskList;
    }

}
