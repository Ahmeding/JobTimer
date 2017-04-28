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
import tn.com.hitechart.eds.Entity.Achat;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.UserManager;

/**
 * Created by BARA' on 07/01/2017.
 */

public class AchatRapportAdapter extends ArrayAdapter<Achat> {

    Context context;
    User user = UserManager.load();
    List<Achat> achats;
    View v;

    public AchatRapportAdapter(Context context, List<Achat> achats) {
        super(context, 0,achats);
        this.context=context;
        this.achats=achats;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final AchatRapportAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new AchatRapportAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.achat_item_rapport, parent, false);

            viewHolder.tvnumdossier = (TextView) convertView.findViewById(R.id.tvnumdossier);
            viewHolder.tvdesignation = (TextView) convertView.findViewById(R.id.tvdesignation);
            viewHolder.tvmntttc = (TextView) convertView.findViewById(R.id.tvmntttc);

            viewHolder.achatRapportList = (ListView) convertView.findViewById(R.id.achatRapportList);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AchatRapportAdapter.ViewHolder) convertView.getTag();
        }
        final Achat achat = getItem(position);
        TasksDAO tasksDAO = new TasksDAO(context);
        Task task = new Task();
        tasksDAO.getTachesByUser(user);



        viewHolder.tvnumdossier.setText(String.valueOf(tasksDAO.getTaskById(achat.get_idTask()).getNumDoss()));
        viewHolder.tvdesignation.setText(String.valueOf(achat.getDesignation()));
        viewHolder.tvmntttc.setText(String.valueOf(achat.getPrix()));
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {
        TextView tvnumdossier;
        TextView tvdesignation;
        TextView tvmntttc;
        ListView achatRapportList;
    }

}
