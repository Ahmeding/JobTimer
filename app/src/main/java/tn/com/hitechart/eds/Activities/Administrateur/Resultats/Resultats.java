package tn.com.hitechart.eds.Activities.Administrateur.Resultats;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import tn.com.hitechart.eds.DAO.ResultatsDAO;
import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Resultat;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.ResultatAdapter;
import tn.com.hitechart.eds.data.UserManager;

public class Resultats extends AppCompatActivity {

    private ResultatsDAO resultatsDAO;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Resultat _getresultat;
    private ArrayList<Resultat> resultats_;
    private ImageView ivdeleteRes, iveditRes;
    private ResultatAdapter resultatAdapter;
    private ListView listResultat;
    private TextView tvloggeduser;
    private User userLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);
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
                AddResultat();
            }
        });
        ivdeleteRes = (ImageView) findViewById(R.id.ivdeleteRes);
        iveditRes = (ImageView) findViewById(R.id.iveditRes);
        listResultat = (ListView) findViewById(R.id.resList);
        resultats_ = new ArrayList<>();
        initDB();
        resultatAdapter = new ResultatAdapter(this, resultats_);
        listResultat.setAdapter(resultatAdapter);

    }

    public void AddResultat() {
        final Resultat resultat = new Resultat();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_res_dialog_box);
        dialog.setTitle("Ajouter un resultat");
        Button btnaddRes = (Button) dialog.findViewById(R.id.btnaddres);
        final EditText etresName = (EditText) dialog.findViewById(R.id.etresName);
        btnaddRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etresName.getText().equals("") || etresName.getText().length() <= 2) {
                    etresName.setError("La taille du nom du résultat doit etre superieur à 3");

                } else {
                    resultat.setName(etresName.getText().toString());
                    resultatsDAO.addResultat(resultat);
                    resultatAdapter.notifyDataSetChanged();
                    //vider & remplir arraylist !!! a ameliorer :(
                    if (resultats_.size() > 0 && resultats_ != null) {
                        resultats_.clear();
                    }
                    initDB();
                    toast("Nouveau Resultat ajouté");
                    dialog.dismiss();
                }
            }
        });
        // show dialog on screen
        dialog.show();
    }
    // private void quicksetResult() {
    //     etQuickset.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    //         @Override
    //         public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
    //            if(actionId == EditorInfo.IME_ACTION_DONE){
    //                if(etQuickset.getText().length()==0)
    //                {
    //                    toast("SVP veuillez saisir un nom du resultat !");
    //                }else{
    //                    String newRes=String.valueOf(etQuickset.getText());
    //                    Resultat resultat=new Resultat();
    //                    resultat.setName(newRes);
    //                    resultatDAO.addResultat(resultat);
    //                    if(resultats_ !=null && resultats_.size()>0){
    //                        resultats_.clear();
    //                    }
    //                    initDB();
    //                    etQuickset.setText("");
    //                    toast("nouveau resultat");
    //                }
    //            }
    //             return false;
    //         }
    //     });
    // }

    private void initDB() {
        resultatsDAO = new ResultatsDAO(this);
        for (Resultat res : resultatsDAO.getResultats()) {
            resultats_.add(res);
        }
    }

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
