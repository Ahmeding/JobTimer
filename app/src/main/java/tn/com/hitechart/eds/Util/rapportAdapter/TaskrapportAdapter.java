package tn.com.hitechart.eds.Util.rapportAdapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 07/01/2017.
 */

public class TaskrapportAdapter extends ArrayAdapter<Task> {

    Context context;
    List<Task> tasks;
    View v;
    private SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");

    public SimpleDateFormat formatTimeHoureline = new SimpleDateFormat("HH");
    public SimpleDateFormat formatTimeMinutline = new SimpleDateFormat("mm");
    private Handler myHandler = new Handler();

    public TaskrapportAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final TaskrapportAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new TaskrapportAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item_rapport, parent, false);

            viewHolder.tvnumdossier = (TextView) convertView.findViewById(R.id.tvnumdossier);
            viewHolder.tvclient = (TextView) convertView.findViewById(R.id.tvclient);
            viewHolder.tvtypemission = (TextView) convertView.findViewById(R.id.tvtypemission);
            viewHolder.tvdureetask = (TextView) convertView.findViewById(R.id.tvdureetask);
            viewHolder.tvresultat = (TextView) convertView.findViewById(R.id.tvresultat);
           // viewHolder.taskRapportList = (ListView) convertView.findViewById(R.id.taskRapportList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TaskrapportAdapter.ViewHolder) convertView.getTag();
        }


        final Task task = getItem(position);
    //    if (task.getCategory().equals(Key.KEY_TASK_MULTI)) {
    //        DossierDAO dossierDAO = new DossierDAO(getContext());
    //        Log.e("nbrdoss",""+dossierDAO.getAllDossiersById(task.get_id()));
    //        if (dossierDAO.getAllDossiersById(task.get_id()) != null) {
    //            for (Dossier d : dossierDAO.getAllDossiersById(task.get_id())) {
    //                viewHolder.tvnumdossier.setText(d.getNumDoss());
    //                viewHolder.tvclient.setText(d.getClient());
    //                viewHolder.tvtypemission.setText(task.getType());
    //                viewHolder.tvresultat.setText(task.getRes());
    //                long t = d.getTimeDuration();
    //                int seconds = (int) (t / 1000) % 60;
    //                int minutes = (int) ((t / (1000 * 60)) % 60);
    //                int hours = (int) ((t / (1000 * 60 * 60)) % 24);
    //                viewHolder.tvdureetask.setText(""//Verif.timeConvertor(t));
    //                        + String.format("%02d", hours) + ":"
    //                        + String.format("%02d", minutes) + ":"
    //                        + String.format("%02d", seconds));
    //            }
    //        }
    //    } else {
//
    //
    //    }
            viewHolder.tvnumdossier.setText(String.valueOf(task.getNumDoss()));
            viewHolder.tvclient.setText(String.valueOf(task.getClient()));
            viewHolder.tvtypemission.setText(String.valueOf(task.getType()));
            viewHolder.tvresultat.setText(String.valueOf(task.getRes()));
            long t = task.getTimeA();
            int seconds = (int) (t / 1000) % 60;
            int minutes = (int) ((t / (1000 * 60)) % 60);
            int hours = (int) ((t / (1000 * 60 * 60)) % 24);
            viewHolder.tvdureetask.setText(""//Verif.timeConvertor(t));
                    + String.format("%02d", hours) + "H"
                    + String.format("%02d", minutes));
                    //+ ":" + String.format("%02d", seconds));

        v = convertView;
        return convertView;
    }

    private static class ViewHolder {
        TextView tvnumdossier;
        TextView tvclient;
        TextView tvdureetask;
        TextView tvresultat;
        TextView tvtypemission;
        ListView taskRapportList;
    }

}
