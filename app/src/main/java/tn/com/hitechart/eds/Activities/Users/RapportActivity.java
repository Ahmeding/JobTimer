package tn.com.hitechart.eds.Activities.Users;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.DAO.AchatDAO;
import tn.com.hitechart.eds.DAO.ComposantDAO;
import tn.com.hitechart.eds.DAO.DossierDAO;
import tn.com.hitechart.eds.DAO.MessageDAO;
import tn.com.hitechart.eds.DAO.ParamAdresseEmailDAO;
import tn.com.hitechart.eds.DAO.PointagesDAO;
import tn.com.hitechart.eds.DAO.RapportDAO;
import tn.com.hitechart.eds.DAO.TasksDAO;
import tn.com.hitechart.eds.Entity.Achat;
import tn.com.hitechart.eds.Entity.Composant;
import tn.com.hitechart.eds.Entity.Dossier;
import tn.com.hitechart.eds.Entity.Message;
import tn.com.hitechart.eds.Entity.ParamAdresseEmail;
import tn.com.hitechart.eds.Entity.Pointage;
import tn.com.hitechart.eds.Entity.Rapport;
import tn.com.hitechart.eds.Entity.Task;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.SendMailTask;
import tn.com.hitechart.eds.Util.Utility;
import tn.com.hitechart.eds.Util.pdfRpport.FirstPdf;
import tn.com.hitechart.eds.Util.rapportAdapter.AchatRapportAdapter;
import tn.com.hitechart.eds.Util.rapportAdapter.ActiviteeRapportAdapter;
import tn.com.hitechart.eds.Util.rapportAdapter.ComposantRapportAdapter;
import tn.com.hitechart.eds.Util.rapportAdapter.DossierRapportAdpater;
import tn.com.hitechart.eds.Util.rapportAdapter.MessageRapportAdapter;
import tn.com.hitechart.eds.Util.utilMethod.Connectivity;
import tn.com.hitechart.eds.Util.utilMethod.Verif;
import tn.com.hitechart.eds.data.Key;
import tn.com.hitechart.eds.data.UserManager;

public class RapportActivity extends AppCompatActivity {


    public SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    public SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss");
    public SimpleDateFormat formatTimeHoureline = new SimpleDateFormat("HH");
    public SimpleDateFormat formatTimeMinutline = new SimpleDateFormat("mm");
    public SimpleDateFormat formatDatenamefile = new SimpleDateFormat("ddMMyy");
    public SimpleDateFormat formatDateline = new SimpleDateFormat("ddMMyyyy");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    //
    private PointagesDAO pointagesDAO;
    private TasksDAO tasksDAO;
    private ParamAdresseEmailDAO paramAdresseEmailDAO;
    private AchatDAO achatDAO;
    private ComposantDAO compDAO;
    private MessageDAO msgDAO;
    private RapportDAO rapportDAO;
    private DossierDAO dossierDAO;
    //
    //private Task taskToday;
    private Pointage pointageToday;
    private ParamAdresseEmail paramAdresseEmail;
    private User currentUser;
    private Rapport currentRappot;
    private Dossier dossier;
    //
    private AchatRapportAdapter achatRapportAdapter;
    private ComposantRapportAdapter compRapportAdapter;
    private MessageRapportAdapter msgRapportAdapter;
    private ActiviteeRapportAdapter activiteeRapportAdapter;
    private DossierRapportAdpater dossierRapportAdpater;

    //
    private List<Task> tasks;
    private List<Task> tasksToday;
    private List<Task> monoTask;
    private List<Task> tasksAutreActList;
    private List<Task> tasksAutreActListToday;
    private List<ParamAdresseEmail> paramAdresseEmails;
    private List<String> emailsList;
    private List<Achat> achats;
    private List<Achat> achatsbytask;
    private List<Composant> comps;
    private List<Composant> compsbytask;
    private List<Message> msgs;
    private List<Message> msgsbytask;
    private List<Dossier> dossiers;
    private List<Dossier> dossiertotask;
    private List<Dossier> monoDoss;
    private List<Dossier> multiDoss;
    private List<Dossier> allToDayDoss;


    //
    private String codeuser;
    private String emailSubject;
    private String emailBody;
    private String pointageLine = "";
    private String taskTodayLine = "";
    private Button btnsenmail;
    private String fileNameRp = "rp";
    private String fileNameDs = "ds";
    private String pdfFilename = "rapport";
    //
    private File file1;
    private File file2;
    //
    private static final int REQUEST_INTERNET = 200;
    private int STORAGE_PERMISSION_CODE = 23;
    //
    private TextView tvdate;
    private TextView tvusername;
    private TextView tvtimeinm;
    private TextView tvtimeoutm;
    private TextView tvtimeinam;
    private TextView tvtimeoutam;
    private TextView tvloggeduser;
    private TextView tvissnotented;
    private TextView tvissented;

    //
    private ListView listAutreAct;
    private ListView listAchat;
    private ListView listComp;
    private ListView listMsg;
    private ListView dossierRapportList;

    private String dateToday;
    private String timeinM;
    private String timeinAM;
    private String timeoutAM;
    private String timeoutM;
    String[] tabData;
    private String codeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);


        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvdate = (TextView) findViewById(R.id.tvdate);
        tvusername = (TextView) findViewById(R.id.tvusername);
        tvtimeinm = (TextView) findViewById(R.id.tvtimeinm);
        tvtimeoutm = (TextView) findViewById(R.id.tvtimeoutm);
        tvtimeinam = (TextView) findViewById(R.id.tvtimeinam);
        tvtimeoutam = (TextView) findViewById(R.id.tvtimeoutam);
        tvissnotented = (TextView) findViewById(R.id.tvissnotented);
        tvissented = (TextView) findViewById(R.id.tvissented);

        listAchat = (ListView) findViewById(R.id.achatRapportList);
        listComp = (ListView) findViewById(R.id.compRapportList);
        listMsg = (ListView) findViewById(R.id.msgRapportList);
        dossierRapportList = (ListView) findViewById(R.id.dossierRapportList);

        listAutreAct = (ListView) findViewById(R.id.taskAutreActtList);
        btnsenmail = (Button) findViewById(R.id.btnsendmail);

        btnsenmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogSendMail();
            }
        });

        //----------------------- INIT VARS --------------------------------//

        currentUser = UserManager.load();// USER
        codeUser = String.format("%03d", currentUser.getCode());
        //
        pointagesDAO = new PointagesDAO(this);
        // POINTAGE
        if (pointagesDAO.getPointByUser(currentUser.get_id()) != null) {
            pointageToday = pointagesDAO.getPointByUser(currentUser.get_id());
        }
        //

        //TASK
        tasksDAO = new TasksDAO(this);
        tasks = tasksDAO.getTachesByUser(currentUser);
        tasksToday = new ArrayList();
        for (Task t : tasks) {
            if (dateFormat.format(t.getTimeStart()).equals(dateFormat.format(pointageToday.getDate_timeInM()))) {
                tasksToday.add(t);
            }
        }
        rapportDAO=new RapportDAO(this);
        if(getcurrentRapport()==null){
            Log.e("Raport","not created");
        }else{
            Log.e("Raport","created");
            currentRappot = getcurrentRapport();
        }
        //dossier
        dossierDAO = new DossierDAO(this);
        if (!tasksToday.isEmpty()) {

            monoDoss = new ArrayList<>();
            multiDoss = new ArrayList<>();

            for (Task t : tasksToday) {
                if (t.getCategory().equals(Key.KEY_TASK_MONO)) {
                    monoDoss.add(dossierDAO.getDossierByTaskId(t.get_id()));
                    //Log.e("TaskMono",""+t);
                } else {
                    multiDoss = dossierDAO.getAllDossiersByTaskId(t.get_id());
                    Log.e("TaskMulti", "" + t);
                }
            }

            allToDayDoss = new ArrayList<>(monoDoss);
            allToDayDoss.addAll(multiDoss);

            for (Dossier d : allToDayDoss) {
                Log.e("AllDossier", "" + d);
            }
        }
        //
        if (allToDayDoss != null) {
            Log.e("ALL", "D" + allToDayDoss.size());
            dossierRapportAdpater = new DossierRapportAdpater(this, allToDayDoss);
        }

//------------------------------------------------------------------------------------------//

        tasksAutreActList = tasksDAO.getTachesByOtherActivities(currentUser);
        tasksAutreActListToday = new ArrayList();

        if (tasksAutreActList != null) {
            for (Task t : tasksAutreActList)
                if (dateFormat.format(t.getTimeStart()).equals(dateFormat.format(pointageToday.getDate_timeInM()))) {
                    tasksAutreActListToday.add(t);
                }
        }
        // charger Adapter

        if (tasksAutreActListToday != null) {
            activiteeRapportAdapter = new ActiviteeRapportAdapter(this, tasksAutreActListToday);
        }

        achatDAO = new AchatDAO(this);
        achatsbytask = new ArrayList<>();
        for (Task tachat : tasksToday) {
            achats = achatDAO.getAchats(tachat);
            //Log.e("Achats", "" + achats.size());
            for (Achat a : achats) {
                achatsbytask.add(a);
            }
        }

        achatRapportAdapter = new AchatRapportAdapter(this, achatsbytask);

        compDAO = new ComposantDAO(this);
        compsbytask = new ArrayList<>();
        for (Task tcomp : tasksToday) {
            comps = compDAO.getComposants(tcomp);
            //Log.e("Achats", "" + achats.size());
            for (Composant c : comps) {
                compsbytask.add(c);
            }
        }
        compRapportAdapter = new ComposantRapportAdapter(this, compsbytask);

        msgDAO = new MessageDAO(this);
        msgsbytask = new ArrayList<>();
        for (Task tmsg : tasksToday) {
            msgs = msgDAO.getMessages(tmsg);
            //Log.e("Achats", "" + achats.size());
            for (Message c : msgs) {
                msgsbytask.add(c);
            }
        }
        msgRapportAdapter = new MessageRapportAdapter(this, msgsbytask);

//----------------------------------------------------------------------------------------------//
        paramAdresseEmailDAO = new ParamAdresseEmailDAO(this);
        paramAdresseEmails = paramAdresseEmailDAO.getParamAdresseEmails();
        emailsList = new ArrayList();
        for (ParamAdresseEmail email : paramAdresseEmails) {
            emailsList.add(email.getAdresseEmail());
        }


//----------------------------------------------------------------------------//


//----------------------------------------------------------------------------//


        tvloggeduser.setText(currentUser.getLogin());
        tvusername.setText(currentUser.getLogin());

        tvdate.setText(formatDate.format(pointageToday.getDate_timeInM()));
        //
        tvtimeinm.setText(formatTimeHoureline.format(pointageToday.getDate_timeInM()) + "H"
                + formatTimeMinutline.format(pointageToday.getDate_timeInM()));
        //
        tvtimeoutm.setText(formatTimeHoureline.format(pointageToday.getDate_timeOutM()) + "H"
                + formatTimeMinutline.format(pointageToday.getDate_timeOutM()));
        //
        tvtimeinam.setText(formatTimeHoureline.format(pointageToday.getDate_timeInAM()) + "H"
                + formatTimeMinutline.format(pointageToday.getDate_timeInAM()));
        //
        tvtimeoutam.setText(formatTimeHoureline.format(pointageToday.getDate_timeOutAM()) + "H"
                + formatTimeMinutline.format(pointageToday.getDate_timeOutAM()));
        //
        // listTask.setAdapter(taskrapportAdapter);
        listAutreAct.setAdapter(activiteeRapportAdapter);
        listAchat.setAdapter(achatRapportAdapter);
        listComp.setAdapter(compRapportAdapter);
        listMsg.setAdapter(msgRapportAdapter);
        dossierRapportList.setAdapter(dossierRapportAdpater);
//---------------------------------------------------------------------------//

        setNameFileRp();
        setIsSentedOrNot();
    }

    private void setIsSentedOrNot(){
        if (currentRappot.isSend()) {
            tvissented.setVisibility(View.VISIBLE);
            tvissnotented.setVisibility(View.INVISIBLE);
        } else {

            tvissnotented.setVisibility(View.VISIBLE);
            tvissented.setVisibility(View.INVISIBLE);
        }
    }
    private boolean isRequestInternetAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        }
        //If permission is not granted returning false
        return false;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == REQUEST_INTERNET) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
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

    //----------------------------------------------------------------------------------//
    public void sendMail() {

        boolean result = Utility.checkInternetPermission(this);
        if (result) {
            parametreSendMail();

        }

    }

    //------------------------------------------------------------------------------------//
    private void parametreSendMail() {
        String fromEmail = Key.ADMIN_RAPPORT_EMAIL;
        String fromPassword = Key.KEY_AUTHENTIFICATION;
        emailSubject = "EDS | Rapport Journalier - " + formatDate.format(pointageToday.getDate_timeInM());
        emailBody = "Veuillez trouver ci-joint le rapport jounalier pour la date "
                + formatDate.format(pointageToday.getDate_timeInM()) + "."
                + "\r\n" + "\r\n"
                + "Cordialement." + "\r\n"
                + "Direction Technique" + "\r\n"
                + "E.D.S.";
        if (!emailsList.isEmpty()) {
            new SendMailTask(this).execute(
                    fromEmail,
                    fromPassword,
                    emailsList,
                    emailSubject,
                    emailBody,
                    fileNameRp,
                    fileNameDs,
                    pdfFilename);
        }
        // TODO: 10/01/2017 Au minimum  une adresse doit etre saisie par defaut [DONE]
    }

    //----------------------------------------------------------------------------------------------//
    public void saveFile() {
        getFiletoAttached();
    }

    //---------------------------------------------------------------------------------------//
    public void getFiletoAttached() {
        String content1 = getPointageLine();
        String content2 = getTodayTasksLine();
        FileOutputStream outputStream1 = null;
        FileOutputStream outputStream2 = null;
        //Log.e("LINE POINTAGE", "" + getPointageLine());

        try {
            outputStream1 = openFileOutput(fileNameRp, Context.MODE_PRIVATE);
            outputStream2 = openFileOutput(fileNameDs, Context.MODE_PRIVATE);

            outputStream1.write(content1.getBytes());
            outputStream2.write(content2.getBytes());

            file1 = new File(getFilesDir() + "/" + fileNameRp);
            file2 = new File(getFilesDir() + "/" + fileNameDs);

            //Log.e("the file1 path", "" + file1);
            //Log.e("the file2 path", "" + file2);


            outputStream1.close();
            outputStream2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------------------//
    public String getTodayTasksLine() {
        //Log.e("Taille", "" + tasksToday.size());
        String[] tabStatus = new String[]{"E", "R", "F", "S", "O", "D"};
        int i = 0;
        for (Dossier d : allToDayDoss) {
            taskTodayLine = taskTodayLine +

                    codeUser
                    + String.valueOf(d.getNumDoss())
                    + String.valueOf(formatDateline.format(pointageToday.getDate_timeInM()))
                    + convertLongtoDurationfr(d.getTimeDuration())
                    + convertLongtoDurationfr(d.getTimeDuration())
                    + tasksDAO.getTaskById(d.get_idTask()).getRes().substring(0, 1).toUpperCase()
                    //+ String.valueOf(tabStatus[i])
                    + "\r\n";
            i++;
        }

        //Log.e("tasksLines", "" + taskTodayLine);
        return taskTodayLine;
    }

    //-------------------------------------------------------------------------------------------------//
    public String getPointageLine() {

        pointageLine =
                String.valueOf(formatDateline.format(pointageToday.getDate_timeOutM()))
                        + codeUser
                        + String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeInM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeInM()))
                        + String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeOutM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeOutM()))
                        + String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeInAM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeInAM()))
                        + String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeOutAM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeOutAM()))
                        + "\r\n";

        //Log.e("pointageline", "" + pointageLine);

        return pointageLine;
    }

    //--------------------------------------------------------------------------------------------------------//
    public void setNameFileRp() {
        fileNameRp = fileNameRp + codeUser + formatDatenamefile.format(pointageToday.getDate_timeInM()) + ".txt";
        fileNameDs = fileNameDs + codeUser + formatDatenamefile.format(pointageToday.getDate_timeInM()) + ".txt";
        pdfFilename = pdfFilename + codeUser + formatDatenamefile.format(pointageToday.getDate_timeInM()) + ".pdf";

    }

    //---------------------------------------------------------------------------------------------------------//
    public void getTabData() {
        tabData = new String[]{
                formatDate.format(pointageToday.getDate_timeOutM()),
                String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeInM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeInM())),
                String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeOutM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeOutM())),
                String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeInAM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeInAM())),
                String.valueOf(formatTimeHoureline.format(pointageToday.getDate_timeOutAM())) + "H"
                        + String.valueOf(formatTimeMinutline.format(pointageToday.getDate_timeOutAM()))
        };
    }

    //---------------------------------------------------------------------------------------------------------------//
    public void alertDialogSendMail() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Envoyer Email?")
                .setMessage("Êtes-vous sûr de vouloir envoyer l\'email?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (isConnected()) {
                            if (isSented()) {
                                alertRapportsended();
                            } else {
                                sendCurrentRapport();

                                getTabData();
                                FirstPdf firstPdf = new FirstPdf();
                                firstPdf.createPdf(getApplication(), pdfFilename, tabData, tasksToday,
                                        achatsbytask, compsbytask, msgsbytask, tasksAutreActListToday, allToDayDoss);
                                saveFile();
                                sendMail();
                                setIsSentedOrNot();
                            }
                        } else {
                            Verif.toast("Verfier votre connexion internet !");
                        }

                    }
                }).setNegativeButton("Non", null).show();
    }

    //---------------------------------------------------------------------------------------------------------------//
    public boolean isSented() {
        return getcurrentRapport().isSend();
    }


    //-------------------------------------------------------------------------------------------------------------//
    private Rapport getcurrentRapport() {
        Rapport rapport = rapportDAO.getRapportByUserbyDate(currentUser.get_id(),
                formatDate.format(pointageToday.getDate_timeInM()));
        return rapport;
    }

    public void sendCurrentRapport() {
        //GET rapport by user && by date
        Rapport rapport = getcurrentRapport();
        rapport.setSend(true);
        rapport.setDateOfSending(pointageToday.getDate_timeInM());
        rapportDAO.updateRapport(rapport);

    }

    //-------------------------------------------------------------------------------------------------//
    // Check if there is any connectivity for a Wifi network
    public boolean isConnectedWifi() {
        NetworkInfo info = Connectivity.getNetworkInfo(this);

        if (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------//
    // Check all connectivities whether available or not
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    //-------------------------------------------------------------------------------------//
    // Check if there is any connectivity for a mobile network
    public boolean isConnectedMobile() {
        NetworkInfo info = Connectivity.getNetworkInfo(this);
        if (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    //---------------------------------------------------------------------------------------//
    public boolean isConnected() {
        return isConnectedWifi();
    }

    //---------------------------------------------------------------------------------------//
    public boolean isSentToday(String day) {
        return dateFormat.format(System.currentTimeMillis()).equals(day);
    }

    //---------------------------------------------------------------------------------------------//
    public void alertRapportsended() {
        new AlertDialog.Builder(this)
                .setTitle("Action Réfusée")
                .setMessage("Ce Rapport est dèja envoyé.Vous pouvez envoyer le rapport qu'une seule fois !")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
//------------------------------------------------------------------------------------------------//
// TODO: 12/01/2017 Test Connexion internet

    public String convertLongtoDurationfr(Long t) {
        String duration;
        int seconds = (int) (t / 1000) % 60;
        int minutes = (int) ((t / (1000 * 60)) % 60);
        int hours = (int) ((t / (1000 * 60 * 60)) % 24);
        duration =
                String.format("%02d", hours) + "H"
                        + String.format("%02d", minutes);

        return duration;
    }
}
