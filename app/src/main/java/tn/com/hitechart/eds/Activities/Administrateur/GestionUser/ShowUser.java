package tn.com.hitechart.eds.Activities.Administrateur.GestionUser;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.Activities.Administrateur.Users;
import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.UserPhotoResizer;
import tn.com.hitechart.eds.data.UserManager;

public class ShowUser extends AppCompatActivity {


    Button btnDelete, btnBack, btnEdit;
    private String picturePath = "drawable://" + R.drawable.img;
    private User user;
    private ImageView ivPhoto;

    private TextView tvUID;
    private TextView tvcodeuser;
    private TextView tvLogin;
    private TextView tvType;
    private TextView tvTel;
    private TextView tvEmail;
    private String jstring;
    private TextView tvloggeduser;
    private User userLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);


        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        // tvUID = (TextView) findViewById(R.id.tvUID);
        tvcodeuser = (TextView) findViewById(R.id.tvcodeuser);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvType = (TextView) findViewById(R.id.tvType);
        tvTel = (TextView) findViewById(R.id.tvTel);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jstring = extras.getString("USER");
        }
        user = getUser(jstring);
        tvcodeuser.setText(String.valueOf(user.getCode()));
        tvLogin.setText(String.valueOf(user.getLogin()));
        tvType.setText(String.valueOf(user.getType()));
        tvTel.setText(String.valueOf(user.getNumTel()));
        tvEmail.setText(String.valueOf(user.getEmail()));
        if (user.getPathPhoto() == null) {
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        ivPhoto.setImageBitmap(UserPhotoResizer.decodeSampleBitmapfromFil(user.getPathPhoto(), width, height));

        deleteUserDialoge();
    }

    private User getUser(String user) {
        try {
            JSONObject job = new JSONObject(user);
            return (new User(
                    job.getInt("_id"),
                    job.getString("type"),
                    job.getString("email"),
                    job.getString("login"),
                    job.getString("pathPhoto"),
                    job.getString("numTel"),
                    job.getBoolean("admin"),
                    job.getInt("code")
                    ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void toEditUser(View view) {
        Intent intent = new Intent(this, EditUser.class);
        intent.putExtra("USER_TO_EDIT", new Gson().toJson(user));
        startActivity(intent);
        finish();
    }

    public void btnBackOnClick(View v) {
        startActivity((new Intent(this, Users.class)));
        finish();
    }

    public void deleteUser() {
        if(user.isAdmin()){
              alertDeniedAction();
        }else {


            UsersDAO usersDAO = new UsersDAO(this);
            usersDAO.deleteUser(user);
            usersDAO.close();
            startActivity(new Intent(this, Users.class));
            finish();
            //notifyDataSetChanged();
            Toast.makeText(ShowUser.this, "Utilisateur " + user.getLogin() + " a été supprimé!", Toast.LENGTH_SHORT).show();
        }
    }

    private void alertDeniedAction(){

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
            alertDialog.setTitle("Action Réfusée!")
                    .setMessage("Vous ne pouvez pas supprimer cet Utilisateur !")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();

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

    public void deleteUserDialoge() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ShowUser.this)
                        .setTitle("Supprimer Utilisateur")
                        .setMessage("Êtes-vous sûr de vouloir supprimer définitivement l'utilisateur " + user.getLogin() + "?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteUser();
                            }
                        })
                        .setNegativeButton("Non", null).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Users.class);
        startActivity(i);
        finish();
    }
}
