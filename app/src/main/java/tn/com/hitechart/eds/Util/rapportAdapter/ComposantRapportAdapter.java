package tn.com.hitechart.eds.Util.rapportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Composant;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.UserManager;

/**
 * Created by BARA' on 20/01/2017.
 */

public class ComposantRapportAdapter  extends ArrayAdapter<Composant> {

    Context context;
    User user = UserManager.load();
    List<Composant> composants;
    View v;

    public ComposantRapportAdapter(Context context, List<Composant> composants) {
        super(context, 0,composants);
        this.context=context;
        this.composants=composants;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ComposantRapportAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ComposantRapportAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.composant_item_rapport, parent, false);

            viewHolder.tvnumdossier = (TextView) convertView.findViewById(R.id.tvnumdossier);
            viewHolder.tvdesignation = (TextView) convertView.findViewById(R.id.tvdesignation);
            viewHolder.tvqte = (TextView) convertView.findViewById(R.id.tvqte);

            viewHolder.composantRapportList = (ListView) convertView.findViewById(R.id.compRapportList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ComposantRapportAdapter.ViewHolder) convertView.getTag();
        }
        final Composant composant = getItem(position);
        TasksDAO tasksDAO = new TasksDAO(context);
        Task task = new Task();
        tasksDAO.getTachesByUser(user);


        viewHolder.tvnumdossier.setText(String.valueOf(composant.getNumDoss()));
        viewHolder.tvdesignation.setText(String.valueOf(composant.getName()));
        viewHolder.tvqte.setText(String.valueOf(composant.getQte()));
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {
        TextView tvnumdossier;
        TextView tvdesignation;
        TextView tvqte;
        ListView composantRapportList;
    }

}
