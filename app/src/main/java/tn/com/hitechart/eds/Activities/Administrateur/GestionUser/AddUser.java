package tn.com.hitechart.eds.Activities.Administrateur.GestionUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.Activities.Administrateur.Users;
import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.DB.DBHelper;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.UserAdapter;
import tn.com.hitechart.eds.Util.UserPhotoResizer;
import tn.com.hitechart.eds.Util.Utility;
import tn.com.hitechart.eds.data.UserManager;

public class AddUser extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int CAM_REQUEST = 1313;


    private boolean fcode = false;
    private boolean femail = false;
    private boolean flogin = false;
    private boolean fpassword = false;
    private boolean fnumtel = false;

    private EditText etcodeuser;
    private EditText etLogin;
    private EditText etTel;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPwordVerif;
    private Spinner spType;
    private ListView userList;
    private SQLiteDatabase db;
    private Button btnPhoto;
    private ImageView ivPhoto;
    private UsersDAO usersDAO;
    private byte[] photoP;
    private ArrayList<User> users;
    private UserAdapter userAdapter;
    private Uri mCapturedImageURI;
    private DBHelper daOdb;
    private String picturePath;
    private User user;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private Button btnadduser;
    private TextView tvloggeduser;
    private User userLoaded;

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);


        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());
        users = new ArrayList();
        userAdapter = new UserAdapter(this, users);
        initDB();
        picturePath = "drawable://" + R.drawable.img;
        spType = (Spinner) findViewById(R.id.spTypeUser);
        etEmail = (EditText) findViewById(R.id.etAdresseEmail);
        etLogin = (EditText) findViewById(R.id.etUName);
        etcodeuser = (EditText) findViewById(R.id.etcodeuser);
        etPassword = (EditText) findViewById(R.id.etPword);
        etPwordVerif = (EditText) findViewById(R.id.etPwordVerif);

        etTel = (EditText) findViewById(R.id.etTel);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);

        btnadduser = (Button) findViewById(R.id.btnadduser);
        List<String> type = new ArrayList();
        type.add("E.D.S.");
        type.add("E.D.S. SARL");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, type);
        spType.setAdapter(arrayAdapter);

        veriFields();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Prendre une photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("choose from Library"))
                        galleryIntent();
                } else {
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Prendre une photo", "Choisir une photo", "Annuler"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddUser.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(AddUser.this);
                if (items[item].equals("Prendre une photo")) {
                    userChoosenTask = "Prendre une photo";
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Choisir une photo")) {
                    userChoosenTask = "Choisir une photo";
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Annuler")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selectionner une photo"), SELECT_FILE);

    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            // startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    public void initDB() {
        usersDAO = new UsersDAO(this);
        // add photo from db to arraylist
        for (User u : usersDAO.getUsers()) {
            users.add(u);
            //Log.i("idUser", "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<+++++++++++++++" + u.get_id());
        }
    }

    private void onCaptureImageResult(Intent data) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor =
                managedQuery(mCapturedImageURI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        picturePath = cursor.getString(column_index_data);
        ivPhoto.setImageBitmap(UserPhotoResizer.decodeSampleBitmapfromFil(picturePath, 95, 95));

    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver()
                .query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);
        cursor.close();
        ivPhoto.setImageBitmap(UserPhotoResizer.decodeSampleBitmapfromFil(picturePath, 95, 95));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }


    // TODO: 14/01/2017 add 003 | 013 to code user
    // TODO: 15/01/2017 champ num tel !!!!

    /**
     * @param msg
     */
    public void toast(String msg) {
        Toast.makeText(AddUser.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void initView() {
        ivPhoto.setImageResource(R.drawable.img);
        etLogin.setText("");
        etPassword.setText("");
        etTel.setText("");
        etEmail.setText("");
    }

    /**
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI",
                    mCapturedImageURI.toString());
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(
                    savedInstanceState.getString("mCapturedImageURI"));
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

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, Users.class);
        startActivity(i);
        finish();
    }

    public boolean verifEmptyFields() {
        if (etcodeuser.getText().toString().isEmpty()) {
            etcodeuser.setError("Tapez un code d'utilistaeur valide !");
            fcode = false;
            Log.e("CODE", "" + fcode);
        } else {
            fcode = true;
            Log.e("CODE", "" + fcode);
        }

        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Tapez une adresse email valide!");
            femail = false;
            Log.e("EMAIL", "" + femail);
        } else {
            femail = true;
            Log.e("EMAIL", "" + femail);
        }

        if (etLogin.getText().toString().isEmpty()) {
            etLogin.setError("Tapez un nom d'utilistaeur valide !");
            flogin = false;
            Log.e("LOGIN", "" + flogin);
        } else {
            flogin = true;
            Log.e("LOGIN", "" + flogin);
        }

        if (etPassword.getText().toString().isEmpty() || etPassword.getText().length() < 6 || etPassword.getText().length() > 15) {
            etPassword.setError("Tapez un mot de passe valide! la taille du mot de passe doit être compris entre 6 et 15 caractères");
            fpassword = false;
            Log.e("PWD", "" + fpassword);
        } else {
            fpassword = true;
            Log.e("PWD", "" + fpassword);
        }

        if (etPwordVerif.getText().toString().isEmpty()) {
            etPwordVerif.setError("Veuillez retaper votre mot de passe !");
            fpassword = false;
            Log.e("VERIFPWD", "" + fpassword);
        } else if (!etPwordVerif.getText().toString().equals(etPassword.getText().toString())) {
            etPwordVerif.setError("Les mots de passe ne correspondent pas.Réessayer");
            fpassword = false;
            Log.e("VERIFPWD", "" + fpassword);
        } else {
            fpassword = true;
            Log.e("VERIFPWD", "" + fpassword);
        }

        if (etTel.getText().toString().isEmpty() || etTel.getText().length() < 8) {
            etTel.setError("Tapez un numero mobile valide!");
            fnumtel = false;
            Log.e("TEL", "" + fnumtel);
        } else {
            fnumtel = true;
            Log.e("TEL", "" + fnumtel);
        }
        return (flogin && fcode && fpassword && femail && fnumtel);
    }


    public void btnAddPhotoOnClick(View v) {

            selectImage();

    }


    public boolean veriFields() {
        //

        etcodeuser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etcodeuser.getText().toString().isEmpty() || etcodeuser.getText().length() < 3) {
                        etcodeuser.setError("Tapez un code d'utilistaeur valide !");
                        fcode = false;
                        Log.e("CODE", "" + fcode);
                    } else {
                        fcode = true;
                        Log.e("CODE", "" + fcode);
                    }
                }
            }
        });

        //
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (!isValidEmail(etEmail.getText()) || etEmail.getText().length() > 50) {
                        etEmail.setError("Tapez une adresse email valide!");
                        femail = false;
                        Log.e("EMAIL", "" + femail);
                    } else {
                        femail = true;
                        Log.e("EMAIL", "" + femail);
                    }
                }
            }
        });

        //
        etLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etLogin.getText().toString().isEmpty() || etLogin.getText().length() < 2 || etcodeuser.getText().length() > 18) {
                        etLogin.setError("Tapez un nom d'utilistaeur valide !");
                        flogin = false;
                        Log.e("LOGIN", "" + flogin);
                    } else {
                        flogin = true;
                        Log.e("LOGIN", "" + flogin);
                    }
                }
            }
        });
        //
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etPassword.getText().toString().isEmpty() || etPassword.getText().length() < 6 || etPassword.getText().length() > 15) {
                        etPassword.setError("Tapez un mot de passe valide! la taille du mot de passe doit être compris entre 6 et 15 caractères");
                        fpassword = false;
                        Log.e("PWD", "" + fpassword);
                    } else {
                        fpassword = true;
                        Log.e("PWD", "" + fpassword);
                    }
                }
            }
        });

        etPwordVerif.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etPwordVerif.getText().toString().isEmpty()) {
                        etPwordVerif.setError("Veuillez retaper votre mot de passe !");
                        fpassword = false;
                        Log.e("VERIFPWD", "" + fpassword);
                    } else if (!etPwordVerif.getText().toString().equals(etPassword.getText().toString())) {
                        etPwordVerif.setError("Les mots de passe ne correspondent pas.Réessayer");
                        fpassword = false;
                        Log.e("VERIFPWD", "" + fpassword);
                    } else {
                        fpassword = true;
                        Log.e("VERIFPWD", "" + fpassword);
                    }
                }
            }
        });

        etTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (etTel.getText().toString().isEmpty() || etTel.getText().length() < 8) {
                        etTel.setError("Tapez un numero mobile valide!");
                        fnumtel = false;
                        Log.e("TEL", "" + fnumtel);
                    } else {
                        fnumtel = true;
                        Log.e("TEL", "" + fnumtel);
                    }
                }
            }
        });
        //
        Log.e("VALIDFIELD", "-code-" + fcode + "-ntel-" + fnumtel + "-pwd-" + fpassword + "-login-" + flogin + "-email-" + femail);
        return (fcode && fnumtel && fpassword && flogin && femail);
    }

    public void addNewUser() {
        user = new User();
        if (veriFields()) {
            user.setCode(Integer.valueOf(etcodeuser.getText().toString()));
            user.setLogin(etLogin.getText().toString());
            user.setPassword(etPassword.getText().toString());
            user.setType(spType.getSelectedItem().toString());
            user.setEmail(etEmail.getText().toString());
            if (picturePath.equals(null)) {
                picturePath = "drawable://" + R.drawable.img;
            }
            user.setPathPhoto(picturePath);
            user.setAdmin(false);
            user.setNumTel((etTel.getText().toString()));
            ivPhoto.setImageBitmap(UserPhotoResizer.decodeSampleBitmapfromFil(picturePath, 95, 95));
            users.add(user);
            usersDAO.addUser(user);
            toast("Nouveau utilisateur ajouté");
            Intent i = new Intent(this, Users.class);
            startActivity(i);
            finish();
        } else {
            toast("Erreur de saisie : verifiez les champs svp !");
        }
    }

    public void btnSaveUser(View view) {
        if (verifEmptyFields()) {
            addNewUser();
        }

    }


    // TODO: 10/01/2017 label select type user[DONE]
    // TODO: 15/01/2017 unique (code,login,numtel)
    // TODO: 10/01/2017 fieled code user[DONE]
    // TODO: 10/01/2017 emplacement de boutton prendre photo[DONE]


}