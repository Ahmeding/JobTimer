package tn.com.hitechart.eds.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tn.com.hitechart.eds.DAO.ActiviteesDAO;
import tn.com.hitechart.eds.Entity.Activitee;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 22/12/2016.
 */
public class ActiviteeAdapter extends ArrayAdapter<Activitee> {

    ArrayList<Activitee> activitees;
    ActiviteesDAO activiteesDAO = new ActiviteesDAO(getContext());
    View v;
    private Handler myHandler = new Handler();

    public ActiviteeAdapter(Context context, ArrayList<Activitee> activitees) {
        super(context, 0, activitees);
        this.activitees = activitees;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.act_item, parent, false);
            viewHolder.tvActID = (TextView) convertView.findViewById(R.id.tvActID);
            viewHolder.tvActName = (TextView) convertView.findViewById(R.id.tvActName);
            viewHolder.ivdeleteAct = (ImageView) convertView.findViewById(R.id.ivdeleteAct);
            viewHolder.iveditAct = (ImageView) convertView.findViewById(R.id.iveditAct);
            viewHolder.actList = (ListView) convertView.findViewById(R.id.actList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Activitee activitee = getItem(position);
        // viewHolder.tvActID.setText(String.valueOf(activitee.get_id()));
        viewHolder.tvActName.setText(String.valueOf(activitee.getName()));
        viewHolder.ivdeleteAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("n°Act", "============================================ N° Activitee " + activitee.get_id());
                new AlertDialog.Builder(getContext())
                        .setTitle("Supprimer activitée")
                        .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cette activitée ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                activiteesDAO.deleteActivitee(activitee);
                                activitees.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "Activitée supprimée", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Non", null).show();
            }
        });

        viewHolder.iveditAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + activitee.get_id());
                //  new AlertDialog.Builder(getContext())
                //          .setTitle("Modifier activitée")
                //          .setMessage("Modifier cette activitée")
                //          .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//
                //              public void onClick(DialogInterface dialog, int whichButton) {
//
                //                  //activiteesDAO.updateResultat(activitee);
                //                  //activitees.remove(position);
                //                  //notifyDataSetChanged();
                //                  Toast.makeText(getContext(), "Activitée modifieré !", Toast.LENGTH_SHORT).show();
                //              }
                //          })
                //          .setNegativeButton("Non", null).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.editing_act_dialog_box);
                dialog.setTitle("Modifier Activité");
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                final EditText etactName = (EditText) dialog.findViewById(R.id.etactName);
                etactName.setText(activitee.getName());
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etactName.getText().equals("") || etactName.getText().length() <= 2) {
                            etactName.setError("La taille du nom de l'acivitée doit etre superieur à 3");

                        } else {
                            activitee.setName(etactName.getText().toString());
                            activiteesDAO.updateActivitee(activitee);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
                // show dialog on screen
                dialog.show();
            }
        });
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {

        TextView tvActID,
                tvActName;
        ImageView ivdeleteAct,
                iveditAct;
        ListView actList;
    }

}
