package tn.com.hitechart.eds.Activities.Users.GestionTaches;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.DashboardUser;
import tn.com.hitechart.eds.Activities.Users.GestionTaches.GestionDossier.AddDossierActivity;
import tn.com.hitechart.eds.Activities.Users.Taches;
import tn.com.hitechart.eds.DAO.ActiviteesDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Activitee;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.utilMethod.Verif;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static tn.com.hitechart.eds.data.Key.KEY_TASK;
import static tn.com.hitechart.eds.data.Key.KEY_TASK_MULTI;

public class MultiTaskActivity extends AppCompatActivity {

    private ActiviteesDAO activiteeDAO;
    private TasksDAO tasksDAO;

    private List<Activitee> listA;
    private List<String> act;
    private ArrayList<Task> tasks_;

    private Task task;
    private RadioGroup radiotypetache;
    private LinearLayout llact;
    private Spinner spAct;
    private String radioValuetypetache;


    private User userLoaded;
    private int id;
    private TextView tvloggeduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());
        //
        radiotypetache = (RadioGroup) findViewById(R.id.radiotypetache);

        //

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

        //

        tasks_ = new ArrayList();
        initDB();
        ArrayAdapter<String> arrayAdapterAct = new ArrayAdapter<String>(this, R.layout.spinner_item, act);
        spAct.setAdapter(arrayAdapterAct);

        //


    }

    private void initDB() {
        tasksDAO = new TasksDAO(this);
        for (Task task : tasksDAO.getTasks()) {
            tasks_.add(task);

        }
    }

    public void onAddMultiTask(View view) {
        radioValuetypetache = ((RadioButton) this.findViewById(radiotypetache.getCheckedRadioButtonId())).getText().toString();
        task = new Task();
        task.setBesoinComp("");
        task.setDesignation("");
        task.setRes("");//(etDsign.getText().toString());
        task.setMontanttc(0.0f);//
        task.set_idUser(id);
        task.setType(radioValuetypetache);
        //
        if (radioValuetypetache.equals(Key.TASK_TYPE_AUTRE)) {
            task.setAct((spAct.getSelectedItem().toString()));
        } else {
            task.setAct(radioValuetypetache);
        }
        task.setFlagStatus(Key.TASK_FLAG_CREATE);
        task.setCategory(KEY_TASK_MULTI);
        tasks_.add(task);
        //
        final long id = tasksDAO.addTask(task);
        task.set_id((int) id);
        Verif.toast("Nouvelle tache ajoutée");

        Intent intent = new Intent(this, AddDossierActivity.class);
        intent.putExtra(KEY_TASK, task);
        startActivity(intent);
        finish();
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


    // TODO: 10/01/2017  3 types de tache (radio) 1labo 2deplacement 3autre + control des champs [DONE]
    // TODO: 10/01/2017 champs num dossier obligatoire les 4 chiffres [DONE]
    // TODO: 10/01/2017 etat pause !no en attente Arreter et suspendre au lieu de stop[DONE]
    // TODO: 10/01/2017 tail composant achat [DONE]
    // TODO: 10/01/2017 ajouter champ areatext pour la tache [DONE]
    // TODO: 10/01/2017 en cours temps augemnt
    // TODO: 10/01/2017 au moin une tache en cour
    // TODO: 10/01/2017 definir l'etat .... au moment de stop


}
