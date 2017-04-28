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

import tn.com.hitechart.eds.DAO.ResultatsDAO;
import tn.com.hitechart.eds.Entity.Resultat;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 22/12/2016.
 */
public class ResultatAdapter extends ArrayAdapter<Resultat> {

    ArrayList<Resultat> resultats;
    ResultatsDAO resultatsDAO = new ResultatsDAO(getContext());
    View v;
    private Handler myHandler = new Handler();

    public ResultatAdapter(Context context, ArrayList<Resultat> resultats) {
        super(context, 0, resultats);
        this.resultats = resultats;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.res_item, parent, false);
            //viewHolder.tvResID = (TextView) convertView.findViewById(R.id.tvResID);
            viewHolder.tvResName = (TextView) convertView.findViewById(R.id.tvResName);
            viewHolder.ivdeleteRes = (ImageView) convertView.findViewById(R.id.ivdeleteRes);
            viewHolder.iveditRes = (ImageView) convertView.findViewById(R.id.iveditRes);
            viewHolder.resList = (ListView) convertView.findViewById(R.id.resList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Resultat resultat = getItem(position);
        //viewHolder.tvResID.setText(String.valueOf(getItem(position).get_id()));
        viewHolder.tvResName.setText(String.valueOf(resultat.getName()));
        viewHolder.ivdeleteRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + resultat.get_id());
                new AlertDialog.Builder(getContext())
                        .setTitle("Supprimer resultat")
                        .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cet resultat ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                resultatsDAO.deleteResultat(resultat);
                                resultats.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "Resultat supprimé !", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Non", null).show();
            }
        });
        viewHolder.iveditRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + resultat.get_id());
                // new AlertDialog.Builder(getContext())
                //         .setTitle("Modifier resultat")
                //         .setMessage("Modifier cet resultat")
                //         .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//
                //             public void onClick(DialogInterface dialog, int whichButton) {
//
                //                //resultatsDAO.updateResultat(resultat);
                //                //resultats.remove(position);
                //                //notifyDataSetChanged();
                //                 Toast.makeText(getContext(), "Resultat Modifieré !", Toast.LENGTH_SHORT).show();
                //             }
                //         })
                //         .setNegativeButton("Non", null).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.editing_res_dialog_box);
                dialog.setTitle("Modifier Resultat");
                Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
                final EditText etresName = (EditText) dialog.findViewById(R.id.etresName);
                etresName.setText(resultat.getName());
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etresName.getText().equals("") || etresName.getText().length() <= 2) {
                            etresName.setError("La taille du nom du résultat doit etre superieur à 3");

                        } else {
                            resultat.setName(etresName.getText().toString());
                            resultatsDAO.updateResultat(resultat);
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

        TextView tvResID,
                tvResName;
        ImageView ivdeleteRes,
                iveditRes;
        ListView resList;
    }

}
