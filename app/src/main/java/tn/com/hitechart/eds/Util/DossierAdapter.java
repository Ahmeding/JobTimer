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

import java.util.List;

import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.utilMethod.Verif;

/**
 * Created by BARA' on 22/01/2017.
 */

public class DossierAdapter extends ArrayAdapter<Dossier> {

    List<Dossier> dossiers;
    DossierDAO dossiersDAO = new DossierDAO(getContext());
    View v;

    private Handler myHandler = new Handler();

    public DossierAdapter(Context context, List<Dossier> dossiers) {
        super(context, 0, dossiers);
        this.dossiers = dossiers;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final DossierAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new DossierAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dossier_item, parent, false);
            viewHolder.tvnumdossier = (TextView) convertView.findViewById(R.id.tvnumdoss);
            viewHolder.tvclient = (TextView) convertView.findViewById(R.id.tvclient);
            viewHolder.ivdeleteDossier = (ImageView) convertView.findViewById(R.id.ivdeletedossier);
            viewHolder.iveditDossier = (ImageView) convertView.findViewById(R.id.iveditdossier);
            viewHolder.dossierList = (ListView) convertView.findViewById(R.id.dossierList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (DossierAdapter.ViewHolder) convertView.getTag();
        }
        final Dossier dossier = getItem(position);
        //Log.e("nmbr dossier",""+dossiers.size());
        viewHolder.tvnumdossier.setText(String.valueOf(dossier.getNumDoss()));
        viewHolder.tvclient.setText(String.valueOf(dossier.getClient()));

        viewHolder.ivdeleteDossier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Supprimer Dossier")
                        .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cet Dossier ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dossiersDAO.deleteDossier(dossier);
                                dossiers.remove(position);
                                notifyDataSetChanged();
                                Verif.toast("Dossier supprimé");
                            }
                        })
                        .setNegativeButton("Non", null).show();
            }
        });

        viewHolder.iveditDossier.setVisibility(View.INVISIBLE);
        viewHolder.iveditDossier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + dossier.get_id());


                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_multitask_dialog_box);
                dialog.setTitle("Modifier Dossier");
                Button btnadddossier = (Button) dialog.findViewById(R.id.btnadddossier);
                final EditText etNumDoss = (EditText) dialog.findViewById(R.id.etNumDoss);
                final EditText etNumDossP = (EditText) dialog.findViewById(R.id.etNumDossP);
                final EditText etClient = (EditText) dialog.findViewById(R.id.etClient);

                etNumDoss.setText(dossier.getNumDoss());
                etNumDossP.setText(String.valueOf(dossier.getNumDoss()));
                etClient.setText(String.valueOf(dossier.getClient()));

                boolean d = false, m = false;
                btnadddossier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean d = false, m = false;
                        if (etNumDoss.getText().toString().isEmpty() || etNumDoss.getText().length() <= 2) {
                            etNumDoss.setError("Tapez le numero du dossiers svp !");
                            d = false;
                        } else {
                            d = true;
                        }
                        if (etClient.getText().toString().isEmpty()) {
                            etClient.setError("Tapez le nom du client svp !");
                            m=false;
                        }else
                            {
                            dossier.setNumDoss(etNumDoss.getText().toString());
                                dossier.setClient(etClient.getText().toString());
                            dossiersDAO.updateDossier(dossier);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    }
                });
                //show dialog on screen
                dialog.show();
            }
        });
        v = convertView;
        return convertView;

    }

    private static class ViewHolder {

        TextView tvnumdossier;
        TextView tvclient;

        ImageView ivdeleteDossier;
        ImageView iveditDossier;
        ListView dossierList;
    }

}
