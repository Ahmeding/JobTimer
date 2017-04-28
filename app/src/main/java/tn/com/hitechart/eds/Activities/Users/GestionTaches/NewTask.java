package tn.com.hitechart.eds.Activities.Users.GestionTaches;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.DashboardUser;
import tn.com.hitechart.eds.Activities.Users.Taches;
import tn.com.hitechart.eds.DAO.ActiviteesDAO;
import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Activitee;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.utilMethod.Verif;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static tn.com.hitechart.eds.data.Key.KEY_TASK_MONO;

public class NewTask extends AppCompatActivity {


    private boolean fnumdoss, fnumdossp = false, fclient = false;
    private ActiviteesDAO activiteeDAO;
    private TasksDAO tasksDAO;

    Dossier dossier;
    DossierDAO dossierDAO;

    private List<Activitee> listA;
    private List<String> act;

    private Task task;
    private ArrayList<Task> tasks_;
    private EditText etNumDoss;
    private EditText etClient;
    private EditText etNumDossP;

    private RadioGroup radioDossGroup;


    private RadioGroup radiotypetache;
    private LinearLayout llact;
    private Spinner spAct;
    private String radioValue;
    private String radioValuetypetache;

    private boolean numDoss;
    private boolean client;

    private User userLoaded;
    private int id;
    private TextView tvloggeduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);
        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        //
        radiotypetache = (RadioGroup) findViewById(R.id.radiotypetache);
        radioDossGroup = (RadioGroup) findViewById(R.id.radiotype);
        etNumDoss = (EditText) findViewById(R.id.etNumDoss);
        etClient = (EditText) findViewById(R.id.etClient);
        etNumDossP = (EditText) findViewById(R.id.etNumDossP);


        spAct = (Spinner) findViewById(R.id.spAct);
        llact = (LinearLayout) findViewById(R.id.llact);
        llact.setVisibility(INVISIBLE);
        //

        radioValuetypetache = ((RadioButton) this.findViewById(radiotypetache.getCheckedRadioButtonId())).getText().toString();
        radiotypetache.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioautre) {
                    llact.setVisibility(VISIBLE);

                }
                if (checkedId == R.id.radiolabo || checkedId == R.id.radiodeplacement) {
                    llact.setVisibility(INVISIBLE);

                }
            }
        });

        activiteeDAO = new ActiviteesDAO(this);
        id = userLoaded.get_id();
        listA = new ArrayList();
        listA = activiteeDAO.getActivitees();
        act = new ArrayList<String>();
        for (Activitee a : listA) {
            act.add(a.getName());
        }
        tasks_ = new ArrayList();
        initDB();
        ArrayAdapter<String> arrayAdapterAct = new ArrayAdapter<String>(this, R.layout.spinner_item, act);
        spAct.setAdapter(arrayAdapterAct);

        dossierDAO=new DossierDAO(this);
        veriFieleds();
    }

    private void initDB() {
        tasksDAO = new TasksDAO(this);
        for (Task task : tasksDAO.getTasks()) {
            tasks_.add(task);

        }
    }


    public void addTask() {

        task = new Task();
        dossier = new Dossier();


        if (veriFieleds()) {
            String ndossier = "";
            radioValue = ((RadioButton) this.findViewById(radioDossGroup.getCheckedRadioButtonId())).getText().toString();
            radioValuetypetache = ((RadioButton) this.findViewById(radiotypetache.getCheckedRadioButtonId())).getText().toString();
            ndossier = radioValue + etNumDoss.getText().toString() + "/" + etNumDossP.getText().toString();

            task.setFlagStatus(Key.TASK_FLAG_START);
            task.setBesoinComp("");//(etBesoinComp.getText().toString());
            task.setDesignation("");//(etDsign.getText().toString());
            task.setRes("");//(etDsign.getText().toString());
            task.setMontanttc(0.0f);//
            task.set_idUser(id);
            task.setNumDoss(ndossier);
            dossier.setNumDoss(ndossier);
            task.setClient(etClient.getText().toString());
            dossier.setClient(etClient.getText().toString());
            dossier.setStartTime(System.currentTimeMillis());
            task.setType(radioValuetypetache);
            task.setCategory(KEY_TASK_MONO);
            if (radioValuetypetache.equals(Key.TASK_TYPE_AUTRE)) {
                task.setAct((spAct.getSelectedItem().toString()));
            } else {
                task.setAct(radioValuetypetache);
            }
            tasks_.add(task);
            //
            final long id = tasksDAO.addTask(task);

            task.set_id((int) id);
            dossier.set_idTask((int)id);
            final long dosid=dossierDAO.addDossier(dossier);
            Verif.toast("Nouvelle tache ajoutée");
            Intent intent = new Intent(NewTask.this, StartTask.class);
            intent.putExtra("Task", task);
            intent.putExtra("Dossier", dosid);

            startActivity(intent);
            finish();
        } else {
            Verif.toast("Verfier les champs svp!");
        }

    }

    public void onStartTask(View view) {
        if(onclikverifemtyfields()){
            addTask();
        }else{
            Verif.toast("Verfier les champs svp!");
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Taches.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DashboardUser.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public boolean veriFieleds() {
        //
        etNumDoss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etNumDoss.getText().toString().isEmpty() || etNumDoss.getText().length() < 4) {
                        etNumDoss.setError("Tapez un numero du dossier valide !");
                        fnumdoss = false;
                        Log.e("NUMD", "" + fnumdoss);
                    } else {
                        fnumdoss = true;
                        Log.e("NUMD", "" + fnumdoss);
                    }
                }
            }
        });

        //
        etNumDossP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etNumDossP.getText().toString().isEmpty() || etNumDossP.getText().length() < 4) {
                        etNumDossP.setError("Tapez un numero du dossier valide !");
                        fnumdossp = false;
                        Log.e("NUMDP", "" + fnumdossp);
                    } else {
                        fnumdossp = true;
                        Log.e("NUMDP", "" + fnumdossp);
                    }
                }
            }
        });

        //
        etClient.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etClient.getText().toString().isEmpty() || etClient.getText().length() < 2 || etClient.getText().length() > 18) {
                        etClient.setError("Tapez un nom du client valide !");
                        fclient = false;
                        Log.e("LOGIN", "" + fclient);
                    } else {
                        fclient = true;
                        Log.e("LOGIN", "" + fclient);
                    }
                }
            }
        });
        //

        return (fnumdoss && fnumdossp && fclient);
    }

    // TODO: 10/01/2017  3 types de tache (radio) 1labo 2deplacement 3autre + control des champs [DONE]
    // TODO: 10/01/2017 champs num dossier obligatoire les 4 chiffres [DONE]
    // TODO: 10/01/2017 etat pause !no en attente Arreter et suspendre au lieu de stop[DONE]
    // TODO: 10/01/2017 tail composant achat [DONE]
    // TODO: 10/01/2017 ajouter champ areatext pour la tache [DONE]
    // TODO: 10/01/2017 en cours temps augemnt
    // TODO: 10/01/2017 au moin une tache en cour
    // TODO: 10/01/2017 definir l'etat .... au moment de stop

    public boolean onclikverifemtyfields() {
        if (etNumDoss.getText().toString().isEmpty()) {
            etNumDoss.setError("Tapez un numero du dossier valide !");
            fnumdoss = false;
            Log.e("NUMD", "" + fnumdoss);
        } else {
            fnumdoss = true;
            Log.e("NUMD", "" + fnumdoss);
        }
        if (etNumDossP.getText().toString().isEmpty()) {
            etNumDossP.setError("Tapez un numero du dossier valide !");
            fnumdossp = false;
            Log.e("NUMDP", "" + fnumdossp);
        } else {

            fnumdossp = true;
            Log.e("NUMDP", "" + fnumdossp);
        }

        if (etClient.getText().toString().isEmpty()) {
            etClient.setError("Tapez un nom du client valide !");
            fclient = false;
            Log.e("LOGIN", "" + fclient);
        } else {
            fclient = true;
            Log.e("LOGIN", "" + fclient);
        }

        return (fnumdossp && fnumdoss && fclient);
    }
}
