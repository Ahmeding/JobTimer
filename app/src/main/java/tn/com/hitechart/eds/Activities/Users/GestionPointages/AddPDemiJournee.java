package tn.com.hitechart.eds.Activities.Users.GestionPointages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tn.com.hitechart.eds.R;

public class AddPDemiJournee extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pointage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    //Alert Demie-journée
    public void alterpoinatgeDemi_journee() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirmation Pointage Demi-Journée?")
                .setMessage("Êtes-vous sûr de vouloir pointer une \"Demi-Journée\"?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                   //     if (findUnfishTask().size() > 0) {
                   //         onCloseAllTask();
                   //     } else {
                   //         Log.e("alltaskarefinshed", "" + findUnfishTask().size());
                   //         if (pointagesDAO.getPointByUser(id) != null) {
                   //             pointage = pointagesDAO.getPointByUser(id);
                   //             Log.e("CurrentTime:", "" + df.format(System.currentTimeMillis()));
                   //             if(pointage.getFlag()== Key.POINTAGE_IN_M){
                   //                 updatePUserIn_AM();
                   //                 updatePUserOutAM();
                   //                 updatePUserOutAM();
                   //             }
                   //             if (pointage.getFlag() == Key.POINTAGE_OUT_M) {
                   //                 updatePUserIn_AM();
                   //                 updatePUserOutAM();
                   //             } else if (pointage.getFlag()==Key.POINTAGE_IN_AM) {
                   //                 updatePUserOutAM();
                   //             }
//
                   //             tvtimeoutm.setText("--:--:--");
                   //             tvtimeinam.setText("--:--:--");
                   //             tvtimeoutam.setText(df.format(pointage.getDate_timeOutAM()));
                   //             toast(df.format(pointage.getDate_timeOutAM()));
                   //             Log.e("out AM at:", "" + df.format(pointage.getDate_timeOutAM()));
                   //             Log.e("out AM at:", "" + pointage.getDate_timeOutAM());
                   //             redirecGenerRapport();
                      //      }
                       // }
                    }
                }).setNegativeButton("Non", null).show();
    }


}
