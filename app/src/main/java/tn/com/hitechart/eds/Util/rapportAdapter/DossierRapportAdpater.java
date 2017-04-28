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

import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.Key;

/**
 * Created by BARA' on 25/01/2017.
 */

public class DossierRapportAdpater extends ArrayAdapter<Dossier> {

    Context context;
    List<Dossier> dossiers;
    View v;
    private SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
    private Handler myHandler = new Handler();

    DossierDAO dossierDAO = new DossierDAO(getContext());
    TasksDAO tasksDAO = new TasksDAO(getContext());

    public DossierRapportAdpater(Context context, List<Dossier> dossiers) {
        super(context, 0, dossiers);
        this.context = context;
        this.dossiers = dossiers;
        ;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final DossierRapportAdpater.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new DossierRapportAdpater.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dossier_item_rapport, parent, false);

            viewHolder.tvnumdossier = (TextView) convertView.findViewById(R.id.tvnumdossier);
            viewHolder.tvclient = (TextView) convertView.findViewById(R.id.tvclient);
            viewHolder.tvtypemission = (TextView) convertView.findViewById(R.id.tvtypemission);
            viewHolder.tvdureedossier = (TextView) convertView.findViewById(R.id.tvdureetaskdoss);
            viewHolder.tvresultat = (TextView) convertView.findViewById(R.id.tvresultat);
            viewHolder.dossierRapportList = (ListView) convertView.findViewById(R.id.dossierRapportList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DossierRapportAdpater.ViewHolder) convertView.getTag();
        }
        final Dossier dossier = getItem(position);
        viewHolder.tvnumdossier.setText(dossier.getNumDoss());
        viewHolder.tvclient.setText(dossier.getClient());
        if(tasksDAO.getTaskById(dossier.get_idTask()).getCategory().equals(Key.KEY_TASK_MONO)){
            long t = tasksDAO.getTaskById(dossier.get_idTask()).getTimeA();
            int seconds = (int) (t / 1000) % 60;
            int minutes = (int) ((t / (1000 * 60)) % 60);
            int hours = (int) ((t / (1000 * 60 * 60)) % 24);
            viewHolder.tvdureedossier.setText(""//Verif.timeConvertor(t));
                    + String.format("%02d", hours) + ":"
                    + String.format("%02d", minutes)+":"
                    + String.format("%02d", seconds));
        }else {
            long t = dossier.getTimeDuration();
            int seconds = (int) (t / 1000) % 60;
            int minutes = (int) ((t / (1000 * 60)) % 60);
            int hours = (int) ((t / (1000 * 60 * 60)) % 24);
            viewHolder.tvdureedossier.setText(""//Verif.timeConvertor(t));
                    + String.format("%02d", hours) + ":"
                    + String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds));
        }
        viewHolder.tvtypemission.setText(tasksDAO.getTaskById(dossier.get_idTask()).getType());
        viewHolder.tvresultat.setText(tasksDAO.getTaskById(dossier.get_idTask()).getRes());

        v = convertView;
        return convertView;
    }

    private static class ViewHolder {
        TextView tvnumdossier;
        TextView tvclient;
        TextView tvdureedossier;
        TextView tvresultat;
        TextView tvtypemission;
        ListView dossierRapportList;
    }

    //
    //
    //
    //
    //
    //
    //
    //
}
