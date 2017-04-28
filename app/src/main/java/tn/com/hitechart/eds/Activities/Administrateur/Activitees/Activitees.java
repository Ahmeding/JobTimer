package tn.com.hitechart.eds.Activities.Administrateur.Activitees;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.DAO.ActiviteesDAO;
import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Activitee;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.ActiviteeAdapter;
import tn.com.hitechart.eds.data.UserManager;

public class Activitees extends AppCompatActivity {

    EditText etQuicksetac;
    private ActiviteesDAO activiteesDAO;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Activitee _getactivitee;
    private ArrayList<Activitee> activitees_;
    private ActiviteeAdapter activiteeAdapter;
    private ListView listActivitee;
    private ImageView ivdeleteAct;
    private ImageView iveditAct;
    private TextView tvloggeduser;
    private User userLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitees);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);




        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                AddActivitee();
            }
        });
        etQuicksetac = (EditText) findViewById(R.id.etQuicksetac);
        ivdeleteAct = (ImageView) findViewById(R.id.ivdeleteAct);
        iveditAct = (ImageView) findViewById(R.id.iveditAct);
        listActivitee = (ListView) findViewById(R.id.actList);
        activitees_ = new ArrayList();
        initDB();
        activiteeAdapter = new ActiviteeAdapter(this, activitees_);
        listActivitee.setAdapter(activiteeAdapter);

    }

    public void AddActivitee() {
        final Activitee activitee = new Activitee();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_act_dialog_box);
        dialog.setTitle("Ajouter une activitée");
        Button btnaddAct = (Button) dialog.findViewById(R.id.btnaddact);
        final EditText etactName = (EditText) dialog.findViewById(R.id.etactName);
        btnaddAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etactName.getText().equals("") || etactName.getText().length() <= 2) {
                    etactName.setError("La taille du nom de l'acivitée doit etre superieur à 3");

                } else {
                    activitee.setName(etactName.getText().toString());
                    activiteesDAO.addActivitee(activitee);
                    activiteeAdapter.notifyDataSetChanged();
                    //vide & remplir arralist !!! a ameliorer :(
                    if (activitees_.size() > 0 && activitees_ != null) {
                        activitees_.clear();
                    }
                    initDB();
                    toast("Nouvelle Activitée ajoutée");
                    dialog.dismiss();
                }
            }
        });
        // show dialog on screen
        dialog.show();
    }

    private void initDB() {
        activiteesDAO = new ActiviteesDAO(this);
        for (Activitee act : activiteesDAO.getActivitees()) {
            Log.i("Act", "=====================================================n°" + act.get_id());
            activitees_.add(act);
        }
    }


    // private void quicksetResult() {
    //     etQuicksetac.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    //         @Override
    //         public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
    //             if(actionId == EditorInfo.IME_ACTION_DONE){
    //                 if(etQuicksetac.getText().length()==0)
    //                 {
    //                     toast("SVP veuillez saisir un nom de l'activitee !");
    //                 }else{
    //                     String newRes=String.valueOf(etQuicksetac.getText());
    //                     Activitee activitee=new Activitee();
    //                     activitee.setName(newRes);
    //                     activiteeDAO.addActivitee(activitee);
    //                     if(activitees_ !=null && activitees_.size()>0){
    //                         activitees_.clear();
    //                     }
    //                     initDB();
    //                     etQuicksetac.setText("");
    //                     toast("nouveau activitee");
    //                 }
//
    //             }
//
    //             return false;
    //         }
    //     });
    // }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, DashboardAdmin.class);
        startActivity(i);
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
}
