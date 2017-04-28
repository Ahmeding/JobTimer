package tn.com.hitechart.eds.Activities.Users.GestionTaches.GestionDossier;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.GestionTaches.StartTask;
import tn.com.hitechart.eds.Activities.Users.Taches;
import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.DossierAdapter;
import tn.com.hitechart.eds.Util.utilMethod.Verif;
import tn.com.hitechart.eds.data.UserManager;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static tn.com.hitechart.eds.Util.utilMethod.Verif.toast;
import static tn.com.hitechart.eds.data.Key.KEY_TASK;
import static tn.com.hitechart.eds.data.Key.TASK_FLAG_START;

public class AddDossierActivity extends AppCompatActivity {


    TasksDAO tasksDAO;
    private Dossier dossier;
    private DossierDAO dossierDAO;
    private DossierAdapter dossierAdapter;
    private List<Dossier> dossiers;

    private ListView dossierList;
    private TextView tvemptyList;
    private Task reqTask;
    private TextView tvloggeduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dossier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(UserManager.load().getLogin());

        tasksDAO = new TasksDAO(this);
        dossierList = (ListView) findViewById(R.id.dossierList);
        tvemptyList = (TextView) findViewById(R.id.tvemptylsitmtask);

        dossierDAO = new DossierDAO(this);
        dossier = new Dossier();
        dossiers = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reqTask = extras.getParcelable(KEY_TASK);
            Log.e("Task", "" + reqTask);
            dossiers = dossierDAO.getAllDossiersById(reqTask.get_id());
        }
        Log.e("task", "" + reqTask);
        if (dossiers.isEmpty()) {
            tvemptyList.setVisibility(VISIBLE);
        } else {
            tvemptyList.setVisibility(INVISIBLE);
        }
        dossierAdapter = new DossierAdapter(this, dossiers);
        dossierList.setAdapter(dossierAdapter);

        for (Dossier d : dossierDAO.getDossiers()) {
            Log.e("timeduration", d.get_id() + "/" + d.getTimeDuration());
        }
    }

    public void onaddDosiertoTask(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_multitask_dialog_box);
        dialog.setTitle("Ajouter un Dossier");

        final Button btnadddossier = (Button) dialog.findViewById(R.id.btnadddossier);
        final Button btncancel = (Button) dialog.findViewById(R.id.btncancel);

        final EditText etNumDoss = (EditText) dialog.findViewById(R.id.etNumDoss);
        final EditText etNumDossP = (EditText) dialog.findViewById(R.id.etNumDossP);
        final RadioGroup radioDossGroup = (RadioGroup) dialog.findViewById(R.id.radiotypedoss);
        final EditText etClient = (EditText) dialog.findViewById(R.id.etClient);
        etClient.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    btnadddossier.performClick();
                }
                return false;
            }
        });
        btnadddossier.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String radioValue = "";
                if (etNumDoss.getText().toString().isEmpty() || etNumDoss.getText().length() < 4) {
                    etNumDoss.setError("Tapez un numero de dossier valide !");
                } else if (etNumDossP.getText().toString().isEmpty() || etNumDossP.getText().length() < 4) {
                    etNumDossP.setError("Tapez un numero de dossier valide !");
                } else if (etClient.getText().toString().isEmpty() || etClient.getText().length() < 2) {
                    etClient.setError("Tapez un nom de client valide !");
                } else {
                    String ndossier = "";
                    radioValue = ((RadioButton) dialog.findViewById(radioDossGroup.getCheckedRadioButtonId())).getText().toString();
                    ndossier = radioValue + etNumDoss.getText().toString() + "/" + etNumDossP.getText().toString();
                    Log.e("Task_AddDoss","+++++++++++++++++++++++++++++++++"+reqTask);
                    //
                    dossier.setNumDoss(ndossier);
                    dossier.setClient(etClient.getText().toString());
                    dossier.set_idTask(reqTask.get_id());
                    dossierDAO.addDossier(dossier);

                    dossierAdapter.notifyDataSetChanged();
                    //vide & remplir arralist !!! a ameliorer :(
                    if (dossiers.size() > 0 && dossiers != null) {
                        dossiers.clear();

                    }
                    initDB();

                    Log.e("size doss", "" + dossiers.size() + dossier);
                    toast("Un nouvel Dossier à été ajouté");
                    dialog.dismiss();
                }
            }


        });
        // show dialog on screen
        dialog.show();
        btncancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void onStartMultiTask(View view) {
        if (dossiers.isEmpty()) {
            errorAlertEmptydoss();
        } else {
            alertStartMulitask();
        }
    }

    public void onCancelDosiertoTask(View view) {
        alertCancelMulitask();
    }

    public void initDB() {
        //  dossierDAO = new DossierDAO(this);
        for (Dossier d : dossierDAO.getAllDossiersById(reqTask.get_id())) {
            dossiers.add(d);
        }
    }

    private void updateStartTime() {
        for (Dossier d : dossiers) {
            d.setStartTime(System.currentTimeMillis());
            dossierDAO.updateStartTimeDossier(d);
        }
    }


    public void alertCancelMulitask() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Annuler la création?")
                .setMessage("Êtes-vous sûr de vouloir annuler la création de la Mutli-Taches?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //
                        annulerMultitask();
                    }
                })
                .setNegativeButton("Non", null).show();

    }

    public void alertStartMulitask() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Annuler la création?")
                .setMessage("Êtes-vous sûr de vouloir Démarrer la Mutli-Taches?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        updateStartTime();
                        Verif.toast("Nouvelle MultiTache a été ajoutée");
                        reqTask.setFlagStatus(TASK_FLAG_START);
                        tasksDAO.updateTaskisCreate(reqTask);
                        Intent intent = new Intent(getApplicationContext(), StartTask.class);
                        intent.putExtra(KEY_TASK, reqTask);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Non", null).show();
        // TODO: 24/01/2017 etes vous sur de vouloir demmarer multi-tache


    }

    public void annulerMultitask() {
        if (!dossiers.isEmpty()) {
            for (Dossier d : dossierDAO.getAllDossiersById(reqTask.get_id())) {
                dossierDAO.deleteDossier(d);
            }
        }
        tasksDAO.deleteTask(reqTask);
        Intent intent = new Intent(this, Taches.class);
        startActivity(intent);
        finish();
    }

    public void errorAlertEmptydoss() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Aucun dossier affecter a cette tache!")
                .setMessage("Ajouter les dossiers svp !")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

}
