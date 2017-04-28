package tn.com.hitechart.eds.Activities.Administrateur.GestionUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tn.com.hitechart.eds.Activities.Administrateur.DashboardAdmin;
import tn.com.hitechart.eds.Activities.Administrateur.Users;
import tn.com.hitechart.eds.DAO.UsersDAO;
import tn.com.hitechart.eds.Entity.User;
import tn.com.hitechart.eds.R;
import tn.com.hitechart.eds.Util.UserAdapter;
import tn.com.hitechart.eds.Util.UserPhotoResizer;
import tn.com.hitechart.eds.Util.Utility;
import tn.com.hitechart.eds.data.UserManager;

public class EditUser extends AppCompatActivity {

    private boolean fcode ;
    private boolean femail;
    private boolean flogin;
    private boolean fnumtel;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int CAM_REQUEST = 1313;

    private EditText etLogin;
    private EditText etTel;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPwordVerif;
    Spinner spType;
    SQLiteDatabase db;
    Button btnPhoto, btnedituser;
    ImageView ivPhoto;
    private UserAdapter userAdapter;
    private Uri mCapturedImageURI;
    private UsersDAO usersDAO;
    private String picturePath, oldType;
    private User editingUser;
    private String refUserForEditing;
    private EditText etcodeuser;
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
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);


        userLoaded = UserManager.load();
        tvloggeduser = (TextView) findViewById(R.id.tvloggeduser);
        tvloggeduser.setText(userLoaded.getLogin());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            refUserForEditing = extras.getString("USER_TO_EDIT");
        }

        editingUser = getUser(refUserForEditing);
        picturePath = editingUser.getPathPhoto();
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        ivPhoto.setImageBitmap(UserPhotoResizer.decodeSampleBitmapfromFil(picturePath, 100, 100));
        btnPhoto = (Button) findViewById(R.id.btnPhoto);

        etcodeuser = (EditText) findViewById(R.id.etcodeuser);
        etcodeuser.setText(String.valueOf(editingUser.getCode()));
        etLogin = (EditText) findViewById(R.id.etUName);
        etLogin.setText(String.valueOf(editingUser.getLogin()));
       // etPassword = (EditText) findViewById(R.id.etPword);
        //etPassword.setText(String.valueOf(editingUser.getPassword()));
        etPwordVerif = (EditText) findViewById(R.id.etPwordVerif);

        spType = (Spinner) findViewById(R.id.spTypeUser);
        List<String> type = new ArrayList();
        type.add("E.D.S.");
        type.add("E.D.S. SARL");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, type);
        spType.setAdapter(arrayAdapter);
        spType.setSelection(getIndex(spType, oldType));
        etTel = (EditText) findViewById(R.id.etTel);
        etTel.setText(String.valueOf(editingUser.getNumTel()));
        etEmail = (EditText) findViewById(R.id.etAdresseEmail);
        etEmail.setText(String.valueOf(editingUser.getEmail()));

        initDB();
        //veriFieleds();
    }




    private void selectImage() {
        final CharSequence[] items = {"Prendre une photo", "Choisir une photo", "Annuler"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(EditUser.this);
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

    private void initDB() {
        usersDAO = new UsersDAO(this);
    }

    private int getIndex(Spinner spType, String oldType) {
        int index = 0;
        for (int i = 0; i < spType.getCount(); i++) {
            if (spType.getItemAtPosition(i).toString().equalsIgnoreCase(oldType)) {
                index = i;
                break;
            }
        }
        return index;
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
                    job.getInt("code"))
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void btnSaveEditUser(View view) {

            if (verifEmptyFields()) {
                if (veriFieleds()) {
                saveUser();
                Intent intent = new Intent(getBaseContext(), ShowUser.class);
                intent.putExtra("USER", new Gson().toJson(editingUser));
                startActivity(intent);
                finish();
            }
        }else {
            toast("Erreur de saisie : verifiez les champs");

        }
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

    /**
     *
     *
     */
    public void saveUser() {
        editingUser.setCode(Integer.valueOf(etcodeuser.getText().toString()));
        editingUser.setLogin(etLogin.getText().toString());
        //editingUser.setPassword(etPassword.getText().toString());
        editingUser.setType(spType.getSelectedItem().toString());
        editingUser.setEmail(etEmail.getText().toString());
        editingUser.setPathPhoto(picturePath);
        editingUser.setNumTel((etTel.getText().toString()));
        usersDAO.updateUser(editingUser);
        toast("Utilisateur modifié");
    }

    /**
     * @param msg
     */
    public void toast(String msg) {
        Toast.makeText(EditUser.this, msg, Toast.LENGTH_SHORT).show();
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


        if (etTel.getText().toString().isEmpty() || etTel.getText().length() < 8) {
            etTel.setError("Tapez un numero mobile valide!");
            fnumtel = false;
            Log.e("TEL", "" + fnumtel);
        } else {
            fnumtel = true;
            Log.e("TEL", "" + fnumtel);
        }
        return (flogin && fcode && femail && fnumtel);
    }
    public boolean veriFieleds() {
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
     // etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
     //     @Override
     //     public void onFocusChange(View v, boolean hasFocus) {

     //         if (!hasFocus) {
     //             if (etPassword.getText().toString().isEmpty() || etPassword.getText().length() < 6 || etPassword.getText().length() > 15) {
     //                 etPassword.setError("Tapez un mot de passe valide! la taille du mot de passe doit être compris entre 6 et 15 caractères");
     //                 fpassword = false;
     //                 Log.e("PWD", "" + fpassword);
     //             } else {
     //                 fpassword = true;
     //                 Log.e("PWD", "" + fpassword);
     //             }
     //         }
     //     }
     // });

     // etPwordVerif.setOnFocusChangeListener(new View.OnFocusChangeListener() {
     //     @Override
     //     public void onFocusChange(View v, boolean hasFocus) {

     //         if (!hasFocus) {
     //             if (etPwordVerif.getText().toString().isEmpty()) {
     //                 etPwordVerif.setError("Veuillez retaper votre mot de passe !");
     //                 fpassword = false;
     //                 Log.e("VERIFPWD", "" + fpassword);
     //             } else if (!etPwordVerif.getText().toString().equals(etPassword.getText().toString())) {
     //                 etPwordVerif.setError("Les mots de passe ne correspondent pas.Réessayer");
     //                 fpassword = false;
     //                 Log.e("VERIFPWD", "" + fpassword);
     //             } else {
     //                 fpassword = true;
     //                 Log.e("VERIFPWD", "" + fpassword);
     //             }
     //         }
     //     }
     // });

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
        Log.e("VALIDFIELD", "-code-" + fcode + "-ntel-" + fnumtel + "-pwd-" + "-login-" + flogin + "-email-" + femail);
        return (fcode && fnumtel && flogin && femail);
    }

    public boolean veriFieledifnochages() {
        //


                    if (etcodeuser.getText().toString().isEmpty() || etcodeuser.getText().length() < 3) {
                        etcodeuser.setError("Tapez un code d'utilistaeur valide !");
                        fcode = false;
                        Log.e("CODE", "" + fcode);
                    } else {
                        fcode = true;
                        Log.e("CODE", "" + fcode);
                    }


        //

                    if (!isValidEmail(etEmail.getText()) || etEmail.getText().length() > 50) {
                        etEmail.setError("Tapez une adresse email valide!");
                        femail = false;
                        Log.e("EMAIL", "" + femail);
                    } else {
                        femail = true;
                        Log.e("EMAIL", "" + femail);
                    }


        //
             if (etLogin.getText().toString().isEmpty() || etLogin.getText().length() < 2 || etcodeuser.getText().length() > 18) {
                        etLogin.setError("Tapez un nom d'utilistaeur valide !");
                        flogin = false;
                        Log.e("LOGIN", "" + flogin);
                    } else {
                        flogin = true;
                        Log.e("LOGIN", "" + flogin);
                    }

                    if (etTel.getText().toString().isEmpty() || etTel.getText().length() < 8) {
                        etTel.setError("Tapez un numero mobile valide!");
                        fnumtel = false;
                        Log.e("TEL", "" + fnumtel);
                    } else {
                        fnumtel = true;
                        Log.e("TEL", "" + fnumtel);
                    }

        //
        Log.e("VALIDFIELD", "-code-" + fcode + "-ntel-" + fnumtel + "-pwd-" + "-login-" + flogin + "-email-" + femail);
        return (fcode && fnumtel && flogin && femail);
    }

    public void btnAddPhotoOnClick(View v) {

            selectImage();

    }


}
