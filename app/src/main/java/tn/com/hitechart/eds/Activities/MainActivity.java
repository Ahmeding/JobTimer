package tn.com.hitechart.eds.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.Activities.Users.DashboardUser;
import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.data.UserManager;

public class MainActivity extends AppCompatActivity {

    private Button btnConnect;
    private EditText etUsername;
    private EditText etPassword;
    UsersDAO usersDAO;

    User userload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = (EditText) findViewById(R.id.etUnserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnConnect = (Button) findViewById(R.id.btnLogin);


        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    validUser();
                }
                return false;
            }
        });

        userload=UserManager.load();
    }

    public boolean validUser() {
        Log.e("mehtod", "VLIDATE L & P");
        boolean isValidUser = false;

        usersDAO = new UsersDAO(this);
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (username.isEmpty() && password.isEmpty()) {
            toast("Veuillez entrer vos parametres de connexion");
            Log.e("empty", "fields");
        } else {
            User user;
            user = usersDAO.validUser(username, password);
            if (user != null) {
                Log.e("user", "not null");
                Log.e("user", "" + user.getPassword() + "-" + user.getLogin() + "-" + user.isAdmin());

                if (user.isAdmin()) {
                    Log.e("admin", "true");
                    //Set Admin
                    User admin = new User();
                    admin.set_id(user.get_id());
                    admin.setAdmin(true);
                    admin.setLogin(username);
                    admin.setCode(user.getCode());
                    admin.setPassword(password);
                    UserManager.connect(admin);
                    //
                    Intent i = new Intent(this, DashboardAdmin.class);
                    startActivity(i);
                    finish();
                } else {
                    Log.e("user", "true");
                    //Set User
                    User userv = new User();
                    userv.set_id(user.get_id());
                    userv.setAdmin(false);
                    userv.setLogin(username);
                    userv.setCode(user.getCode());
                    userv.setPassword(password);
                    UserManager.connect(userv);


                    Intent i = new Intent(this, DashboardUser.class);
                    startActivity(i);
                    finish();
                }
                isValidUser = true;
            } else {

                Log.e("empty", "user");
                toast("Erreur !les parametres de connexion ne sont pas valides :" +
                        " Veuillez verifier votre nom d'utilisateur et votre mot de passe stp !");
                isValidUser = false;
            }
        }
        return isValidUser;
    }

    public void onBtnConnectClicked(View view) {
        validUser();

      //  Log.e("isAdmin",""+userload.isAdmin());
        // String username = etUsername.getText().toString().trim();
        // String password = etPassword.getText().toString().trim();
        // if (username.isEmpty() && password.isEmpty()) {
        //     toast("Veuillez entrer vos parametres de connexion");
        // } else if (UserManager.isValidAdmin(username, password)) {
        //     //Set User admin
        //     User user = new User();
        //     user.setAdmin(true);
        //     user.setLogin(username);
        //     user.setPassword(password);
        //     UserManager.connect(user);
        //     //
        //    // Log.i("01", "------------------------------->" + username);
        //    // Log.i("02", "------------------------------->" + password);
        //     Intent i = new Intent(this, DashboardAdmin.class);
        //     startActivity(i);
        //     finish();
        // } else if (UserManager.isValidUser(username, password)) {
        //     //Set User admin
        //     User user = new User();
        //     user.setAdmin(false);
        //     user.setLogin(username);
        //     user.setPassword(password);
        //     UserManager.connect(user);
        //     //
        //     //Log.i("01", "------------------------------->" + username);
        //     //Log.i("02", "------------------------------->" + password);
        //     Intent i = new Intent(this, DashboardUser.class);
        //     startActivity(i);
        //     finish();
        // } else {
        //    // Log.i("01", "------------------------------->" + username);
        //    // Log.i("02", "------------------------------->" + password);
        //     toast("Erreur !les parametres de connexion ne sont pas valides : Veuillez verifier votre nom d'utilisateur et votre mot de passe stp !");
        // }

    }

    public void toast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toDashboard(View view) {
        Intent i = new Intent(MainActivity.this, DashboardAdmin.class);
        startActivity(i);
        finish();
    }

    // public boolean conect(String login,String pwd){
    //     UsersDAO usersDAO = new UsersDAO(this);
    //     String s =usersDAO.getSinlgeEntry(login);
    //     if(pwd.equals(s)){
    //         return true;
    //     }
    //     return false;
    // }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
// TODO: 10/01/2017 session admin destruction [DONE]



}
