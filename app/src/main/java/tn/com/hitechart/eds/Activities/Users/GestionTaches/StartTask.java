package tn.com.hitechart.eds.Activities.Users.GestionTaches;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.Users.Taches;
import tn.com.hitechart.eds.DAO.AchatDAO;
import tn.com.hitechart.eds.DAO.ComposantDAO;
import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.MessageDAO;
import tn.com.hitechart.eds.DAO.ResultatsDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.Achat;
import tn.com.hitechart.eds.Entity.Composant;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Message;
import tn.com.hitechart.eds.Entity.Resultat;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.AchatAdapter;
import tn.com.hitechart.eds.Util.ComposantAdapter;
import tn.com.hitechart.eds.Util.MessageAdapter;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

import static android.view.View.INVISIBLE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

public class StartTask extends AppCompatActivity {

    private List<String> res;
    private ResultatsDAO resultatsDAO;
    private ListView achatList;
    private ListView compList;
    private ListView msgList;

    private Spinner spRes;
    private List<Resultat> listR;
    private AchatDAO achatDAO;
    private Achat achat;
    private List<Achat> achats;
    private AchatAdapter achatAdapter;


    Dossier dossier;
    DossierDAO dossierDAO;
    List<Dossier> dossiers = new ArrayList<>();

    Message message;
    MessageDAO messageDAO;
    MessageAdapter messageAdapter;
    List<Message> messages;

    private ComposantDAO composantDAO;
    private Composant composant;
    private List<Composant> compsants;
    private ComposantAdapter composantAdapter;

    private float value;
    long timeInMillies = 0L;
    long timeSwap = 0L;
    long finalTime = 0L;
    long tima = 0L;
    private boolean flagStart = false;
    private SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
    private TextView tvnumDoss, tvClient, tvtime, tvAct;
    private Button btnstop, btnpause, btnstart;
    private long startTime = 0L;
    private TasksDAO tasksDAO;
    private Handler myHandler = new Handler();
    private Task reqTask;

    //private TextView tvnbrAchat;
    //private TextView tvTotal;
    //private TextView tvnbrComp;
    //private TextView tvTotalcomp;
    private float totalAchat = 0.0f;
    private User userLoaded;
    private TextView tvloggeduser;
    private TextView tvemptylistemsg;
    private TextView tvemptylistcomp;
    private TextView tvemptylistachat;

    private String allnumDoss;
    private String allclients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);


        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());


        tvnumDoss = (TextView) findViewById(R.id.tvNumDoss);
        tvClient = (TextView) findViewById(R.id.tvClient);
        tvtime = (TextView) findViewById(R.id.tvtime);
        tvAct = (TextView) findViewById(R.id.tvAct);
        // tvnbrAchat = (TextView) findViewById(R.id.tvnbrAchat);
        // tvTotal = (TextView) findViewById(R.id.tvTotal);
        // tvnbrComp = (TextView) findViewById(R.id.tvnbrComp);

        btnstop = (Button) findViewById(R.id.btnstop);
        btnpause = (Button) findViewById(R.id.btnpause);

        achatList = (ListView) findViewById(R.id.achatList);
        compList = (ListView) findViewById(R.id.compList);
        msgList = (ListView) findViewById(R.id.msgList);

        tasksDAO = new TasksDAO(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            reqTask = extras.getParcelable("Task");
            reqTask.get_id();
            finalTime = timeSwap = reqTask.getTimeA();

            int seconds = (int) (finalTime / 1000) % 60;
            int minutes = (int) ((finalTime / (1000 * 60)) % 60);
            int hours = (int) ((finalTime / (1000 * 60 * 60)) % 24);

            tvtime.setText(""
                    + String.format("%02d", hours) + ":"
                    + String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds));
        }
        //Log.i("taskcontent", "------------------------------" + reqTask);
        dossierDAO = new DossierDAO(this);
        dossiers = dossierDAO.getAllDossiersById(
                reqTask.get_id());
        allnumDoss = "[";
        allclients = "[";
        for (Dossier d : dossiers) {
            allnumDoss = allnumDoss + d.getNumDoss() + ", ";
            allclients = allclients + d.getClient() + ", ";
        }


        tvemptylistemsg = (TextView) findViewById(R.id.tvemptylistmsg);
        tvemptylistcomp = (TextView) findViewById(R.id.tvemptylistcomp);
        tvemptylistachat = (TextView) findViewById(R.id.tvemptylistachat);

        if (reqTask.getCategory().equals(Key.KEY_TASK_MULTI)) {
            tvClient.setText(allclients + "...]");
            tvnumDoss.setText(allnumDoss + "...]");
            tvAct.setText(reqTask.getAct());
        }else{

            tvClient.setText(reqTask.getClient());
            tvnumDoss.setText(reqTask.getNumDoss());
            tvAct.setText(reqTask.getAct());
        }
        //-------------------------------------------------------------------------------------------------
        achatDAO = new AchatDAO(this);
        achat = new Achat();
        achats = achatDAO.getAchats(reqTask);
        achatAdapter = new AchatAdapter(this, achats);
        achatList.setAdapter(achatAdapter);
        if (achats.isEmpty()) {
            tvemptylistachat.setVisibility(VISIBLE);
        }
        for (Achat a : achats) {
            totalAchat = totalAchat + a.getPrix();
        }
        composantDAO = new ComposantDAO(this);
        composant = new Composant();
        compsants = composantDAO.getComposants(reqTask);

        composantAdapter = new ComposantAdapter(this, compsants);
        compList.setAdapter(composantAdapter);
        if (compsants.isEmpty()) {
            tvemptylistcomp.setVisibility(VISIBLE);
        }
        message = new Message();
        messageDAO = new MessageDAO(this);
        messages = messageDAO.getMessages(reqTask);
        messageAdapter = new MessageAdapter(this, messages);
        if (messages.isEmpty()) {
            tvemptylistemsg.setVisibility(VISIBLE);
        }
        msgList.setAdapter(messageAdapter);
        //-------------------------------------------------------------------


        btnstart = (Button) findViewById(R.id.btnstart);
        btnpause.setEnabled(false);
        btnstop.setEnabled(false);

        btnstart.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                //Log.e("PAUSE_TASK","ID TASK[" + reqTask.get_id() + "]," +
                //        "STATUS[" + reqTask.getFlagStatus() + "]," +
                //        "RESULTAT[" + reqTask.getRes() + "]");
                btnstart.setEnabled(false);
                btnpause.setEnabled(true);
                btnstop.setEnabled(true);
                startTime = SystemClock.uptimeMillis();
                myHandler.postDelayed(updateTimerMethod, 0);
                if (!flagStart) {
                    reqTask.setTimeStart(System.currentTimeMillis());

                }
                reqTask.setRes(DBHelper.EN_COURS);
                reqTask.setFlagStatus(Key.TASK_FLAG_START);
                tasksDAO.updateTask(reqTask);
                //Log.e("RESUME_TASK", "\r\nTASK ID:[" + reqTask.get_id() + "]," +
                //        "STATUS:[" + reqTask.getFlagStatus() + "]," +
                //        "RESULTAT:[" + reqTask.getRes() + "]");
            }
        });

        btnpause.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                alertDialogPauseTask();
                //  Log.e("PAUSE_TASK",
                //          "ID TASK[" + reqTask.get_id() + "]," +
                //          "STATUS[" + reqTask.getFlagStatus() + "]," +
                //          "RESULTAT[" + reqTask.getRes() + "]");


            }
        });

        btnstop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogStopTask();
            }
        });


        //;   tvnbrAchat.setText(String.valueOf(achats.size()));
        //  tvTotal.setText(String.valueOf(totalAchat));
        //  tvnbrComp.setText(String.valueOf(compsants.size()));

        btnstart.performClick();
    }

    // @Override
    // public boolean onOptionsItemSelected(MenuItem item) {
//
    //     switch (item.getItemId()) {
    //         case android.R.id.home:
    //             Intent intent = new Intent(this, DashboardUser.class);
    //             startActivity(intent);
    //             finish();
    //             return true;
    //         default:
    //             return super.onOptionsItemSelected(item);
    //     }
    // }


    public void onAddComp(View view) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_comp_dialog_box);
        dialog.setTitle("Ajouter Composant");
        Button btnaddComposant = (Button) dialog.findViewById(R.id.btnaddComposant);
        final EditText etCompname = (EditText) dialog.findViewById(R.id.etCompname);
        final EditText etqte = (EditText) dialog.findViewById(R.id.etqte);

        btnaddComposant.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCompname.getText().toString().isEmpty()) {
                    etCompname.setError("Indiquez le nom du composant svp !");

                } else if (etqte.getText().toString().isEmpty()) {
                    etqte.setError("Indiquez la quantité svp !");
                } else {
                    composant.setName(etCompname.getText().toString());
                    composant.setQte(Integer.valueOf(etqte.getText().toString()));
                    composant.set_idTask(reqTask.get_id());
                    composant.setNumDoss(reqTask.getNumDoss());
                    compsants.add(composant);

                    tvemptylistcomp.setVisibility(INVISIBLE);
                    composantDAO.addComposant(composant);
                    composantAdapter.notifyDataSetChanged();
                    //vide & remplir arralist !!! a ameliorer :(
                    if (compsants.size() > 0 && compsants != null) {
                        compsants.clear();
                    }
                    initDBcomp();
                    // tvnbrComp.setText(String.valueOf(compsants.size()));
                    dialog.dismiss();

                }
            }
        });// show dialog on screen
        dialog.show();
    }

    //
    public void onAddmsg(View view) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_msg_dialog_box);
        dialog.setTitle("Ajouter un message");
        Button btnaddmsg = (Button) dialog.findViewById(R.id.btnaddmsg);
        final EditText etmsgbody = (EditText) dialog.findViewById(R.id.etmsgbody);

        btnaddmsg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmsgbody.getText().toString().isEmpty() || etmsgbody.getText().length() < 3 || etmsgbody.getText().length() > 100) {
                    etmsgbody.setError("tapez un message  svp !");
                } else {
                    message.setIdTask(reqTask.get_id());
                    message.setMsg(etmsgbody.getText().toString());
                    message.setNumDoss(reqTask.getNumDoss());
                    messageDAO.addMessage(message);
                    messages.add(message);
                    tvemptylistemsg.setVisibility(INVISIBLE);
                    messageAdapter.notifyDataSetChanged();
                    //vide & remplir arralist !!! a ameliorer :(
                    if (messages.size() > 0 && messages != null) {
                        messages.clear();
                    }
                    initDBmessage();
                    // tvnbrAchat.setText(String.valueOf(achats.size()));
                    // tvTotal.setText(String.valueOf(totalAchat));
                    dialog.dismiss();


                }
                // Log.e("DIalog", "ok");
            }
        });
        //   //show dialog on screen
        dialog.show();
    }

    private void initDBmessage() {
        messageDAO = new MessageDAO(this);
        for (Message msg : messageDAO.getMessages(reqTask)) {
            messages.add(msg);
        }
    }

    //ACHAT
    public void onAddAchat(View view) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_achat_dialog_box);
        dialog.setTitle("Ajouter Achat");
        Button btnaddachat = (Button) dialog.findViewById(R.id.btnaddachat);
        final EditText etDsign = (EditText) dialog.findViewById(R.id.etDsign);
        final EditText etMonttc = (EditText) dialog.findViewById(R.id.etMonttc);

        btnaddachat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDsign.getText().equals("") || etDsign.getText().length() <= 2) {
                    etDsign.setError("Indiquez l'achat svp !");
                    etDsign.setFocusable(true);
                } else if (etMonttc.getText().toString().isEmpty()) {
                    etMonttc.setError("indiquez le prix svp !");
                    etMonttc.setFocusable(true);
                } else {

                    if (etMonttc.getText().toString().equals("")) {
                        value = 00.0f;
                    } else {
                        value = Float.parseFloat(etMonttc.getText().toString());
                    }
                    achat.setPrix(value);
                    achat.setDesignation(etDsign.getText().toString());
                    achat.set_idTask(reqTask.get_id());
                    achat.setNumDoss(reqTask.getNumDoss());
                    achats.add(achat);
                    tvemptylistachat.setVisibility(INVISIBLE);
                    achatDAO.addAchat(achat);
                    achatAdapter.notifyDataSetChanged();
                    Log.e("LIST_TAILL", "------" + achats.size());
                    //vide & remplir arralist !!! a ameliorer :(
                    if (achats.size() > 0 && achats != null) {
                        achats.clear();
                    }
                    initDBachat();
                    // tvnbrAchat.setText(String.valueOf(achats.size()));
                    // tvTotal.setText(String.valueOf(totalAchat));
                    dialog.dismiss();


                }
                // Log.e("DIalog", "ok");
            }
        });
        //   //show dialog on screen
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     *
     *
     */
    public void dialogSetResultat() {
        resultatsDAO = new ResultatsDAO(getBaseContext());
        listR = new ArrayList();
        listR = resultatsDAO.getResultats();
        res = new ArrayList<String>();
        for (Resultat r : listR) {
            res.add(r.getName());
        }

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.set_res_dialog_box);
        dialog.setTitle("Indiquez le resultat svp");
        Button btnconfirm = (Button) dialog.findViewById(R.id.btnconfirm);
        Button btncancel = (Button) dialog.findViewById(R.id.btncancel);

        spRes = (Spinner) dialog.findViewById(R.id.spnrRes);
        ArrayAdapter<String> arrayAdapterRes = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, res);
        spRes.setAdapter(arrayAdapterRes);

        btnconfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                timeSwap += timeInMillies;
                myHandler.removeCallbacks(updateTimerMethod);
                reqTask.setRes(spRes.getSelectedItem().toString());
                reqTask.setFlagStatus(Key.TASK_FLAG_STOP);

                tasksDAO.updateTask(reqTask);
                    setTimeDurationToDossier();

                // Log.e("STOP_TASK", "\r\nTASKID:[" + reqTask.get_id() + "]," +
                //         "STATUS:[" + reqTask.getFlagStatus() + "]," +
                //         "RESULTAT:[" + reqTask.getRes() + "]");

                Intent intent = new Intent(getBaseContext(), Taches.class);
                startActivity(intent);
                finish();
                dialog.dismiss();

            }
        });
        dialog.show();
        btncancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            timeInMillies = SystemClock.uptimeMillis() - startTime;
            finalTime = timeSwap + timeInMillies;
            reqTask.setTimeA(finalTime);
            tasksDAO.updateTask(reqTask);

            int seconds = (int) (finalTime / 1000) % 60;
            int minutes = (int) ((finalTime / (1000 * 60)) % 60);
            int hours = (int) ((finalTime / (1000 * 60 * 60)) % 24);
            tvtime.setText(""
                    + String.format("%02d", hours) + ":"
                    + String.format("%02d", minutes) + ":"
                    + String.format("%02d", seconds));
            myHandler.postDelayed(this, 0);
        }

    };


    public void alertDialogStopTask() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Arreter la tache?")
                .setMessage("Êtes-vous sûr de vouloir arreter cette tache?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialogSetResultat();
                    }
                })
                .setNegativeButton("Non", null).show();
    }

    public void alertDialogPauseTask() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Suspendre la tache?")
                .setMessage("Êtes-vous sûr de vouloir Suspendre cette tache?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        setTasktoPause();

                    }
                })
                .setNegativeButton("Non", null).show();
    }

    private void setTasktoPause() {
        // Log.e("TASKContentbfor",""+reqTask);
        // final Task tasktopause = reqTask;
        btnstart.setEnabled(true);
        btnpause.setEnabled(false);
        btnstop.setEnabled(true);
        timeSwap += timeInMillies;
        myHandler.removeCallbacks(updateTimerMethod);
        reqTask.setRes(DBHelper.EN_ATTENTE);
        reqTask.setFlagStatus(Key.TASK_FLAG_PAUSE);

        tasksDAO.updateTask(reqTask);
        Intent intent = new Intent(getBaseContext(), Taches.class);
        startActivity(intent);
        finish();
    }

    private void initDBachat() {
        achatDAO = new AchatDAO(this);
        for (Achat achat : achatDAO.getAchats(reqTask)) {
            achats.add(achat);
        }
    }

    private void initDBcomp() {
        composantDAO = new ComposantDAO(this);
        for (Composant comp : composantDAO.getComposants(reqTask)) {
            compsants.add(comp);
        }
    }

    private void setTimeDurationToDossier() {

        DossierDAO dossierDAO = new DossierDAO(this);
        if (reqTask.getCategory().equals(Key.KEY_TASK_MULTI)) {
            long finalTimeForEachDoss = 0;

            List<Dossier> dossiers = dossierDAO.getAllDossiersByTaskId(reqTask.get_id());
            finalTimeForEachDoss = finalTime / dossiers.size();
            for (Dossier d : dossiers) {
                d.setEndTime(System.currentTimeMillis());
                d.setTimeDuration(finalTimeForEachDoss);
                dossierDAO.updateTimeDurationDossier(d);
            }
        }else{

            Dossier d= dossierDAO.getDossierByTaskId(reqTask.get_id());
            Log.e("doss",""+d);
            d.setEndTime(System.currentTimeMillis());
            d.setTimeDuration(finalTime);
            dossierDAO.updateTimeDurationDossier(d);
            Log.e("doss",""+d);
        }


    }
}

