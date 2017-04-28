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

import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.UserManager;

import static tn.com.hitechart.eds.Util.utilMethod.Verif.isValidEmail;
import static tn.com.hitechart.eds.Util.utilMethod.Verif.toast;

/**
 * Created by BARA' on 18/01/2017.
 */

public class AdminAdapter extends ArrayAdapter<User> {

    ArrayList<User> admins;
    UsersDAO adminsDAO = new UsersDAO(getContext());
    View v;
    private Handler myHandler = new Handler();

    public AdminAdapter(Context context, ArrayList<User> admins) {
        super(context, 0, admins);
        this.admins = admins;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final AdminAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new AdminAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.admin_item, parent, false);

            viewHolder.tvLogin = (TextView) convertView.findViewById(R.id.tvLoginadmin);
            viewHolder.tvTel = (TextView) convertView.findViewById(R.id.tvTeladmin);
            viewHolder.tvEmail = (TextView) convertView.findViewById(R.id.tvEmailadmin);
            viewHolder.userList = (ListView) convertView.findViewById(R.id.adminList);
            viewHolder.btneditadmin = (ImageView) convertView.findViewById(R.id.iveditadmin);
            viewHolder.btneditpwdadmin = (ImageView) convertView.findViewById(R.id.iveditpwdadmin);
            viewHolder.btndeleteadmin = (ImageView) convertView.findViewById(R.id.ivdeleteadmin);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (AdminAdapter.ViewHolder) convertView.getTag();
        }
        final User user = getItem(position);
        viewHolder.tvLogin.setText(String.valueOf(user.getLogin()));
        viewHolder.tvTel.setText(String.valueOf(user.getNumTel()));
        viewHolder.tvEmail.setText(String.valueOf(user.getEmail()));
        Log.e("isAdmin", "" + user.isAdmin());


        viewHolder.btndeleteadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("n°Act", "============================================ N° Activitee " + activitee.get_id());
                new AlertDialog.Builder(getContext())
                        .setTitle("Supprimer Administrateur")
                        .setMessage("Êtes-vous sûr de vouloir supprimer définitivement cet administrateur ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(user.get_id()== UserManager.load().get_id() || admins.size()==1){
                                    alertDeleteCurrentadmin();
                                }
                                else{
                                adminsDAO.deleteUser(user);
                                admins.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(getContext(), "administrateur supprimée", Toast.LENGTH_SHORT).show();}
                            }
                        })
                        .setNegativeButton("Non", null).show();
            }
        });
//--------------------------------------------------- EDIT PASSEWORD  ----------------------- //

        viewHolder.btneditpwdadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + user.get_id());
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.reset_pwd_dialog_box);
                dialog.setTitle("Modifier le Mot De Passe de l'Adminsitarteur");

                Button btnaddadmin = (Button) dialog.findViewById(R.id.btnaddadmin);
                Button btncancel = (Button) dialog.findViewById(R.id.btncancel);

                final EditText etPwordadmin = (EditText) dialog.findViewById(R.id.etPwordadmin);
                final EditText etPwordVerifadmin = (EditText) dialog.findViewById(R.id.etPwordVerifadmin);

                btnaddadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etPwordadmin.getText().toString().isEmpty() || etPwordadmin.getText().length() <6|| etPwordadmin.getText().length() >15) {
                            etPwordadmin.setError("Tapez une adresse email valid! (la taille du mot de passe doit etre compris entre 6 et 15 caratères)");
                        } else  if (etPwordVerifadmin.getText().toString().isEmpty() || !etPwordVerifadmin.getText().toString().equals(etPwordadmin.getText().toString())) {
                            etPwordVerifadmin.setError("les deux mots de passe ne sont pas identiques !");
                        } else {

                            user.setPassword(etPwordadmin.getText().toString());
                            adminsDAO.updateUser(user);
                            notifyDataSetChanged();
                            //vide & remplir arralist !!! a ameliorer :(
                            // if (admins.size() > 0 && admins != null) {
                            //     admins.clear();
                            // }
                            // initDB();
                            toast("Le mot de passe a été modifié");
                            dialog.dismiss();
                        }

                    }
                });
                // show dialog on screen
                dialog.show();
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

//---------------------------------------------------END EDITE PASSEWORD  ----------------------- //
//--------------------------------------------------- EDITE ADMIN  ----------------------- //
        viewHolder.btneditadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("hey", "I m here ! " + user.get_id());
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.add_admin_dialog_box);
                dialog.setTitle("Modifier Adminsitarteur");

                Button btnaddadmin = (Button) dialog.findViewById(R.id.btnaddadmin);
                Button btncancel = (Button) dialog.findViewById(R.id.btncancel);

                final EditText etAdresseEmailadmin = (EditText) dialog.findViewById(R.id.etAdresseEmailadmin);
                final EditText etUNameadmin = (EditText) dialog.findViewById(R.id.etUNameadmin);
                final EditText etPwordadmin = (EditText) dialog.findViewById(R.id.etPwordadmin);
                final EditText etPwordVerifadmin = (EditText) dialog.findViewById(R.id.etPwordVerifadmin);
                final EditText etTeladmin = (EditText) dialog.findViewById(R.id.etTeladmin);

                etAdresseEmailadmin.setText(user.getEmail());
                etUNameadmin.setText(user.getLogin());
                etPwordadmin.setEnabled(false);
                etPwordVerifadmin.setEnabled(false);
                etTeladmin.setText(user.getNumTel());

                btnaddadmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etAdresseEmailadmin.getText().toString().isEmpty() || !isValidEmail(etAdresseEmailadmin.getText().toString()) || etAdresseEmailadmin.getText().length() > 50) {
                            etAdresseEmailadmin.setError("Tapez une adresse email valid!");

                        } else if (etUNameadmin.getText().toString().isEmpty() || etAdresseEmailadmin.getText().length() < 3 || etAdresseEmailadmin.getText().length() > 20) {
                            etUNameadmin.setError("Tapez un nom d'administrateur valid! (la taille de non d'adminisatrateur doit etre compris entre 3 et 20 caratères)");
                        } else if (etTeladmin.getText().toString().isEmpty() || etTeladmin.getText().length() > 20) {
                            etTeladmin.setError("Tapez numero de téléphone valid!");
                        } else {
                            user.setEmail(etAdresseEmailadmin.getText().toString());
                            user.setLogin(etUNameadmin.getText().toString());
                            user.setNumTel(etTeladmin.getText().toString());
                            adminsDAO.updateUser(user);
                            notifyDataSetChanged();
                            //vide & remplir arralist !!! a ameliorer :(
                            // if (admins.size() > 0 && admins != null) {
                            //     admins.clear();
                            // }
                            // initDB();
                            toast("Administrateur a été modifiée");
                            dialog.dismiss();
                        }

                    }
                });
                // show dialog on screen
                dialog.show();
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

//--------------------------------------------------- END EDITE ADMIN ----------------------- //

        v = convertView;
        return convertView;
    }

    private static class ViewHolder {

        TextView tvLogin, tvTel, tvEmail;
        ImageView btneditadmin;
        ImageView btneditpwdadmin;
        ImageView btndeleteadmin;

        ListView userList;
    }

    private void initDB() {
        adminsDAO = new UsersDAO(getContext());
        for (User res : adminsDAO.getUsers()) {
            Log.e("isAdmin_", "" + res.isAdmin() + "id" + res.get_id());
            if (res.isAdmin()) {
                admins.add(res);
            }
        }
    }
    public void alertDeleteCurrentadmin(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Action Réfusée")
                .setMessage("Impossible de supprimer l'utilisateur courant!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).show();
    }
}