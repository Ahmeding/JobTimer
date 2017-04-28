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

import java.util.List;

import tn.com.hitechart.eds.DAO.ComposantDAO;
import tn.com.hitechart.eds.Entity.Composant;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 03/01/2017.
 */

public class ComposantAdapter extends ArrayAdapter<Composant> {

    List<Composant> composants;
    ComposantDAO composantsDAO = new ComposantDAO(getContext());
    View v;
    private Handler myHandler = new Handler();

    public ComposantAdapter(Context context, List<Composant> composants) {
        super(context, 0, composants);
        this.composants = composants;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ComposantAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ComposantAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.composant_item, parent, false);
            viewHolder.tvcompname = (TextView) convertView.findViewById(R.id.tvcompname);
            viewHolder.tvqte = (TextView) convertView.findViewById(R.id.tvqte);
            viewHolder.ivdeleteComp = (ImageView) convertView.findViewById(R.id.ivdeleteComp);
            viewHolder.iveditComp = (ImageView) convertView.findViewById(R.id.iveditComp);
            viewHolder.compList = (ListView) convertView.findViewById(R.id.compList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ComposantAdapter.ViewHolder) convertView.getTag();
        }
        final Composant composant = getItem(position);
        viewHolder.tvcompname.setText(String.valueOf(composant.getName()));
        viewHolder.tvqte.setText(String.valueOf(composant.getQte()));

        viewHolder.ivdeleteComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Log.i("n°Act", "============================================ N° Composant " + composant.get_id());
                 new AlertDialog.Builder(getContext())
                         .setTitle("Supprimer Composant")
                         .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cet Composant ?")
                         .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int whichButton) {
                                 composantsDAO.deleteComposant(composant);
                                 composants.remove(position);
                                 notifyDataSetChanged();
                                 Toast.makeText(getContext(), "Composant supprimé", Toast.LENGTH_SHORT).show();
                             }
                         })
                         .setNegativeButton("Non", null).show();
            }
        });
        viewHolder.iveditComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + composant.get_id());
                //  new AlertDialog.Builder(getContext())
                //          .setTitle("Modifier activitée")
                //          .setMessage("Modifier cette activitée")
                //          .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//
                //              public void onClick(DialogInterface dialog, int whichButton) {
//
                //                  //composantsDAO.updateResultat(composant);
                //                  //composants.remove(position);
                //                  //notifyDataSetChanged();
                //                  Toast.makeText(getContext(), "Activitée modifieré !", Toast.LENGTH_SHORT).show();
                //              }
                //          })
                //          .setNegativeButton("Non", null).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_comp_dialog_box);
                dialog.setTitle("Modifier Composant");
                Button btnaddComposant = (Button) dialog.findViewById(R.id.btnaddComposant);
                final EditText etCompname = (EditText) dialog.findViewById(R.id.etCompname);
                final EditText etqte = (EditText) dialog.findViewById(R.id.etqte);

                etCompname.setText(composant.getName());
                etqte.setText(String.valueOf(composant.getQte()));
                btnaddComposant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etCompname.getText().equals("") || etCompname.getText().length() <= 2) {
                            etCompname.setError("Indiquez le nom du composant");
//
                        } else if(etqte.getText().equals("") ){
                            etqte.setError("Indiquez la quantité svp !");
                        }
                            else{
                                composant.setName(etCompname.getText().toString());
                                composantsDAO.updateComposant(composant);
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
        TextView tvcompname;
        TextView tvqte;
        ImageView ivdeleteComp;
        ImageView iveditComp;
        ListView compList;
    }
}
