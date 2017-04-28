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

import tn.com.hitechart.eds.DAO.AchatDAO;
import tn.com.hitechart.eds.Entity.Achat;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.utilMethod.Verif;

/**
 * Created by BARA' on 03/01/2017.
 */

public class AchatAdapter extends ArrayAdapter<Achat> {

    List<Achat> achats;
    AchatDAO achatsDAO = new AchatDAO(getContext());
    View v;

    private Handler myHandler = new Handler();

    public AchatAdapter(Context context, List<Achat> achats) {
        super(context, 0, achats);
        this.achats = achats;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final AchatAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new AchatAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.achat_item, parent, false);
            viewHolder.tvDesigniation = (TextView) convertView.findViewById(R.id.tvDesigniation);
            viewHolder.tvprix = (TextView) convertView.findViewById(R.id.tvprix);
            viewHolder.ivdeleteAchat = (ImageView) convertView.findViewById(R.id.ivdeleteAchat);
            viewHolder.iveditAchat = (ImageView) convertView.findViewById(R.id.iveditAchat);
            viewHolder.achatList = (ListView) convertView.findViewById(R.id.achatList);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (AchatAdapter.ViewHolder) convertView.getTag();
        }
        final Achat achat = getItem(position);
        //Log.e("nmbr achat",""+achats.size());
        viewHolder.tvDesigniation.setText(String.valueOf(achat.getDesignation()));
        viewHolder.tvprix.setText(String.valueOf(achat.getPrix()));

        viewHolder.ivdeleteAchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Supprimer Achat")
                        .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cet Achat ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                achatsDAO.deleteAchat(achat);
                                achats.remove(position);
                                notifyDataSetChanged();
                                Verif.toast("Achat supprimé");
                            }
                        })
                        .setNegativeButton("Non", null).show();
            }
        });

      viewHolder.iveditAchat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Log.i("hey", "I m here ! " + achat.get_id());


              final Dialog dialog = new Dialog(getContext());
              dialog.setContentView(R.layout.add_achat_dialog_box);
              dialog.setTitle("Modifier Achat");
              Button btnaddachat = (Button) dialog.findViewById(R.id.btnaddachat);
              final EditText etDsign = (EditText) dialog.findViewById(R.id.etDsign);
              final EditText etMonttc = (EditText) dialog.findViewById(R.id.etMonttc);
              etDsign.setText(achat.getDesignation());
              etMonttc.setText(String.valueOf(achat.getPrix()));

              boolean d = false, m = false;
              btnaddachat.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      boolean d = false, m = false;
                      if (etDsign.getText().toString().isEmpty() || etDsign.getText().length() <= 2) {
                          etDsign.setError("Tapez la Designation de l'achats svp !");
                          d = false;
                      } else {
                          d = true;
                      }
                      if (etMonttc.getText().toString().isEmpty()) {
                          etMonttc.setError("Tapez le montant de l'achat svp !");
                          m=false;
                      }else{
                          m=true;
                      }
                      if (d && m) {
                          achat.setDesignation(etDsign.getText().toString());
                          achatsDAO.updateAchat(achat);
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

        TextView tvDesigniation;
        TextView tvprix;

        ImageView ivdeleteAchat;
        ImageView iveditAchat;
        ListView achatList;
    }

}
