package tn.com.hitechart.eds.Activities.Administrateur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import tn.com.hitechart.eds.Activities.Administrateur.Activitees.Activitees;
import tn.com.hitechart.eds.Activities.Administrateur.Historique.ViewHistoriqueAdminActivity;
import tn.com.hitechart.eds.Activities.Administrateur.Parametres.ParametreActivity;
import tn.com.hitechart.eds.Activities.Administrateur.Resultats.Resultats;
import tn.com.hitechart.eds.Activities.MainActivity;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.UserManager;

import static tn.com.hitechart.eds.data.UserManager.disconnect;

public class DashboardAdmin extends AppCompatActivity {


    private TextView tvloggeduser;
    private User userLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        Log.e("isAdmin", "" + UserManager.load().isAdmin());
    }

    public void toGusers(View view) {
        Intent i = new Intent(this, Users.class);
        startActivity(i);
        finish();

    }

    public void toGResultats(View view) {
        Intent i = new Intent(this, Resultats.class);
        startActivity(i);
        finish();

    }

    public void toGActivitees(View view) {
        Intent i = new Intent(this, Activitees.class);
        startActivity(i);
        finish();

    }

    public void toParametre(View view) {
        Intent i = new Intent(this, ParametreActivity.class);
        startActivity(i);
        finish();
        // try {
        //     GMailSender sender = new GMailSender("7amouda22@gmail.com", "#ing#2014#gni#");
        //     sender.sendMail("EDS.Taches",
        //             "Test",
        //             "7amouda22@gmail.com",
        //             "ahmad.bejaoui@gmail.com");
        //     Log.e("email","fgfgfd");
        // } catch (Exception e) {
        //     Log.e("SendMail", e.getMessage(), e);
        // }
    }

    public void toLogoutAdmin(View view) {
        disconnect();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // public void onBackPressed() {
    //     super.onBackPressed();
    //     finish();
    // }
    public void onConsulterHistorique(View view) {
        Intent i = new Intent(this, ViewHistoriqueAdminActivity.class);
        startActivity(i);
        finish();
    }
}
