package tn.com.hitechart.eds.Activities.Administrateur.Parametres;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.Activities.Administrateur.GestionUser.ShowUser;
import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.AdminAdapter;
import tn.com.hitechart.eds.data.UserManager;

import static tn.com.hitechart.eds.Util.utilMethod.Verif.isValidEmail;
import static tn.com.hitechart.eds.Util.utilMethod.Verif.toast;

public class ParamAdminActivity extends AppCompatActivity {

    TextView tvUID,
            tvLogin,
            tvType,
            tvTel,
            tvEmail;
    UsersDAO adminsDAO;
    private User admin;
    private ArrayList<User> admins_;
    private AdminAdapter adminAdapter;
    private ListView listAdmin;
    private User adminLoaded;
    private TextView tvloggeduser;
    private User adminuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        adminLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(adminLoaded.getLogin());
        // tvUID = (TextView) findViewById(R.id.tvUID);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvType = (TextView) findViewById(R.id.tvType);
        tvTel = (TextView) findViewById(R.id.tvTel);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        listAdmin = (ListView) findViewById(R.id.adminList);
        admins_ = new ArrayList();
        initDB();
        adminAdapter = new AdminAdapter(this, admins_);
        listAdmin.setAdapter(adminAdapter);
      //  getItemOnClickListener(listAdmin);

         adminuser = new User();
    }

    private void initDB() {
        adminsDAO = new UsersDAO(this);
        for (User res : adminsDAO.getUsers()) {
            Log.e("isAdmin_",""+res.isAdmin()+"id"+res.get_id());
            if(res.isAdmin()) {
                admins_.add(res);
            }
        }
    }

    private void getItemOnClickListener(final ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                admin = (User) lv.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), ShowUser.class);
                intent.putExtra("USER", new Gson().toJson(admin));
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DashboardAdmin.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   public void  onAddAdmin(View view){
       final Dialog dialog = new Dialog(this);
       dialog.setContentView(R.layout.add_admin_dialog_box);
       dialog.setTitle("Ajouter un Administrateur");
       Button btnaddadmin = (Button) dialog.findViewById(R.id.btnaddadmin);
       Button btncancel = (Button) dialog.findViewById(R.id.btncancel);

       final EditText etAdresseEmailadmin = (EditText) dialog.findViewById(R.id.etAdresseEmailadmin);
       final EditText etUNameadmin = (EditText) dialog.findViewById(R.id.etUNameadmin);
       final EditText etPwordadmin = (EditText) dialog.findViewById(R.id.etPwordadmin);
       final EditText etPwordVerifadmin = (EditText) dialog.findViewById(R.id.etPwordVerifadmin);
       final EditText etTeladmin = (EditText) dialog.findViewById(R.id.etTeladmin);

       btnaddadmin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (etAdresseEmailadmin.getText().toString().isEmpty() || !isValidEmail(etAdresseEmailadmin.getText().toString()) || etAdresseEmailadmin.getText().length() >50) {
                   etAdresseEmailadmin.setError("Tapez une adresse email valid!");

               } else  if (etUNameadmin.getText().toString().isEmpty() || etAdresseEmailadmin.getText().length() <3 || etAdresseEmailadmin.getText().length() >20) {
                   etUNameadmin.setError("Tapez un nom d'administrateur valid! (la taille de non d'adminisatrateur doit etre compris entre 3 et 20 caratères)");
               }
               else  if (etPwordadmin.getText().toString().isEmpty() || etPwordadmin.getText().length() <6|| etPwordadmin.getText().length() >15) {
                   etPwordadmin.setError("Tapez une adresse email valid! (la taille du mot de passe doit etre compris entre 6 et 15 caratères)");
               } else  if (etPwordVerifadmin.getText().toString().isEmpty() || !etPwordVerifadmin.getText().toString().equals(etPwordadmin.getText().toString())) {
                   etPwordVerifadmin.setError("les deux mots de passe ne sont pas identiques !");
               } else  if (etTeladmin.getText().toString().isEmpty() || etTeladmin.getText().length() >20) {
                   etTeladmin.setError("Tapez numero de téléphone valid!");
               }else {
                   adminuser.setCode(000);
                   adminuser.setPathPhoto("");
                   adminuser.setType("");
                   adminuser.setEmail(etAdresseEmailadmin.getText().toString());
                   adminuser.setLogin(etUNameadmin.getText().toString());
                   adminuser.setPassword(etPwordadmin.getText().toString());
                   adminuser.setNumTel(etTeladmin.getText().toString());


                   adminsDAO.addAdmin(adminuser);
                   adminAdapter.notifyDataSetChanged();
                  //vide & remplir arralist !!! a ameliorer :(
                  if (admins_.size() > 0 && admins_ != null) {
                      admins_.clear();
                  }
                  initDB();
                  toast("Nouvel administrateur a été ajoutée");
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, ParametreActivity.class);
        startActivity(i);
        finish();
    }
}
