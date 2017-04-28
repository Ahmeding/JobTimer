package tn.com.hitechart.eds.Activities.Administrateur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import tn.com.hitechart.eds.Activities.Administrateur.GestionUser.AddUser;
import tn.com.hitechart.eds.Activities.Administrateur.GestionUser.ShowUser;
import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.UserAdapter;
import tn.com.hitechart.eds.data.UserManager;

public class Users extends AppCompatActivity {

    TextView tvUID,
            tvLogin,
            tvType,
            tvTel,
            tvEmail;
    UsersDAO usersDAO;
    private User user;
    private ArrayList<User> users_;
    private UserAdapter userAdapter;
    private ListView listUser;
    private User userLoaded;
    private TextView tvloggeduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);


        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        // tvUID = (TextView) findViewById(R.id.tvUID);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvType = (TextView) findViewById(R.id.tvType);
        tvTel = (TextView) findViewById(R.id.tvTel);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        listUser = (ListView) findViewById(R.id.userList);
        users_ = new ArrayList();
        initDB();
        userAdapter = new UserAdapter(this, users_);
        listUser.setAdapter(userAdapter);
        //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //  fab.setOnClickListener(new View.OnClickListener() {
        //      @Override
        //      public void onClick(View view) {
        //          //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //
        //      }
        //  });
        getItemOnClickListener(listUser);
    }

    private void getItemOnClickListener(final ListView lv) {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                user = (User) lv.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), ShowUser.class);
                intent.putExtra("USER", new Gson().toJson(user));
                startActivity(intent);
                finish();
            }
        });
    }

    private void initDB() {
        usersDAO = new UsersDAO(this);
        for (User res : usersDAO.getUsers()) {
            if(!res.isAdmin()) {
                users_.add(res);
            }
        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, DashboardAdmin.class);
        startActivity(i);
        finish();
    }

    public void btnadduser(View view) {
        Intent intent = new Intent(this, AddUser.class);
        startActivity(intent);
        finish();
    }
}
