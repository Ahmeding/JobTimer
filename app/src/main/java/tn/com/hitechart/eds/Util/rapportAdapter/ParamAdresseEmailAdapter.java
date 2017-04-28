package tn.com.hitechart.eds.Util.rapportAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
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

import tn.com.hitechart.eds.DAO.ParamAdresseEmailDAO;
import tn.com.hitechart.eds.Entity.ParamAdresseEmail;
import tn.com.hitechart.eds.R;

/**
 * Created by BARA' on 10/01/2017.
 */

public class ParamAdresseEmailAdapter extends ArrayAdapter<ParamAdresseEmail> {

    ArrayList<ParamAdresseEmail> paramAdresseEmails;
    ParamAdresseEmailDAO paramAdresseEmailsDAO = new ParamAdresseEmailDAO(getContext());
    View v;
    private Handler myHandler = new Handler();

    public ParamAdresseEmailAdapter(Context context, ArrayList<ParamAdresseEmail> paramAdresseEmails) {
        super(context, 0, paramAdresseEmails);
        this.paramAdresseEmails = paramAdresseEmails;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adresse_email_item, parent, false);

            viewHolder.tvadressemailparam = (TextView) convertView.findViewById(R.id.tvadressemailparam);
            viewHolder.ivdeleteadresseemailparam = (ImageView) convertView.findViewById(R.id.ivdeleteadresseemailparam);
            viewHolder.iveditadressemailparam = (ImageView) convertView.findViewById(R.id.iveditadressemailparam);
            viewHolder.emailparamlist = (ListView) convertView.findViewById(R.id.emailparamlist);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ParamAdresseEmail paramAdresseEmail = getItem(position);
        viewHolder.tvadressemailparam.setText(String.valueOf(paramAdresseEmail.getAdresseEmail()));
        viewHolder.ivdeleteadresseemailparam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("n°Act", "============================================ N° ParamAdresseEmail " + paramAdresseEmail.get_id());
                new AlertDialog.Builder(getContext())
                        .setTitle("Supprimer adresse email")
                        .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cette adresse email ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (paramAdresseEmails.size() > 1){
                                    paramAdresseEmailsDAO.deleteParamAdresseEmail(paramAdresseEmail);
                                paramAdresseEmails.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "Activitée supprimée", Toast.LENGTH_SHORT).show();
                            }else{
                                    ondeleteLastEmailAdresse();
                                }

                            }
                        })
                        .setNegativeButton("Non", null).show();
            }
        });


        viewHolder.iveditadressemailparam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + paramAdresseEmail.get_id());
//  new AlertDialog.Builder(getContext())
//          .setTitle("Modifier activitée")
//          .setMessage("Modifier cette activitée")
//          .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//
//              public void onClick(DialogInterface dialog, int whichButton) {
//
//                  //paramAdresseEmailsDAO.updateResultat(paramAdresseEmail);
//                  //paramAdresseEmails.remove(position);
//                  //notifyDataSetChanged();
//                  Toast.makeText(getContext(), "Activitée modifieré !", Toast.LENGTH_SHORT).show();
//              }
//          })
//          .setNegativeButton("Non", null).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_adress_email_box);
                dialog.setTitle("Modifier Adresse Email");
                Button btnsaveadressemail = (Button) dialog.findViewById(R.id.btnsaveadressemail);
                final EditText etparamadressemail = (EditText) dialog.findViewById(R.id.etparamadressemail);
                etparamadressemail.setText(paramAdresseEmail.getAdresseEmail());
                btnsaveadressemail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(isValidEmail(etparamadressemail.getText().toString()))) {
                            etparamadressemail.setError("tapez une adresse email valide!");

                        } else {
                            paramAdresseEmail.setAdresseEmail(etparamadressemail.getText().toString());
                            paramAdresseEmailsDAO.updateParamAdresseEmail(paramAdresseEmail);
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

    public void ondeleteLastEmailAdresse(){
        new AlertDialog.Builder(getContext())
                .setTitle("Action refusée")
                .setMessage("Impossible de supprimer cette adresse email!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }
    private static class ViewHolder {

        TextView tvadressemailparam;
        ImageView ivdeleteadresseemailparam;
        ImageView iveditadressemailparam;
        ListView emailparamlist;
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
