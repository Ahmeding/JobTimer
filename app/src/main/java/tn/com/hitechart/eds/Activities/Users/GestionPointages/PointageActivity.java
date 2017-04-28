package tn.com.hitechart.eds.Activities.Users.GestionPointages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import tn.com.hitechart.eds.Activities.Users.DashboardUser;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.utilMethod.Verif;

public class PointageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

       }

    public  void toPDemiJournee(View view){
       // Intent intent = new Intent(this,AddPDemiJournee.class);
        //startActivity(intent);
        //finish();
        Verif.toast("Pointage Demi-Journée... En cours de construction...");


    }


    public void toPjourNormale(View view){
        Intent intent = new Intent(this,AddPointage.class);
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
}
