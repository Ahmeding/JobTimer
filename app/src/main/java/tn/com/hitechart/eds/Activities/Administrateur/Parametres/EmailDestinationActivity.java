package tn.com.hitechart.eds.Activities.Administrateur.Parametres;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.DAO.ParamAdresseEmailDAO;
import tn.com.hitechart.eds.Entity.ParamAdresseEmail;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.rapportAdapter.ParamAdresseEmailAdapter;
import tn.com.hitechart.eds.Util.utilMethod.Verif;
import tn.com.hitechart.eds.data.UserManager;

public class EmailDestinationActivity extends AppCompatActivity {


    private ArrayList<ParamAdresseEmail> paramAdresseEmails;
    private ParamAdresseEmailAdapter paramAdresseEmailAdapter;
    private ParamAdresseEmailDAO paramAdresseEmailDAO;
    private ListView listParamAdresseEmail;
    private ImageView ivdeleteAct;
    private ImageView iveditAct;

    private TextView tvloggeduser;
    private User userLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);
        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        listParamAdresseEmail = (ListView) findViewById(R.id.emailparamlist);
        paramAdresseEmails = new ArrayList();
        initDB();
        paramAdresseEmailAdapter = new ParamAdresseEmailAdapter(this, paramAdresseEmails);
        listParamAdresseEmail.setAdapter(paramAdresseEmailAdapter);

    }


    public void onAddEmail(View view) {
        final ParamAdresseEmail paramAdresseEmail = new ParamAdresseEmail();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_adress_email_box);
        dialog.setTitle("Ajouter une adresse email");
        Button btnsaveadressemail = (Button) dialog.findViewById(R.id.btnsaveadressemail);
        final EditText etparamadressemail = (EditText) dialog.findViewById(R.id.etparamadressemail);
        btnsaveadressemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Verif.isValidEmail(etparamadressemail.getText().toString()))) {
                    etparamadressemail.setError("Tapez une adresse email valide!");

                } else {
                    paramAdresseEmail.setAdresseEmail(etparamadressemail.getText().toString());
                    paramAdresseEmailDAO.addParamAdresseEmail(paramAdresseEmail);
                    paramAdresseEmailAdapter.notifyDataSetChanged();
                    //vide & remplir arralist !!! a ameliorer :(
                    if (paramAdresseEmails.size() > 0 && paramAdresseEmails != null) {
                        paramAdresseEmails.clear();
                    }
                    initDB();
                    Verif.toast("Nouvelle adresse email ajoutée");
                    dialog.dismiss();
                }
            }
        });
        // show dialog on screen
        dialog.show();
    }

    private void initDB() {
        paramAdresseEmailDAO = new ParamAdresseEmailDAO(this);
        for (ParamAdresseEmail pae : paramAdresseEmailDAO.getParamAdresseEmails()) {
            paramAdresseEmails.add(pae);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ParametreActivity.class);
        startActivity(intent);
        finish();
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
    // TODO: 10/01/2017 boutton acceuil [DONE]

}
