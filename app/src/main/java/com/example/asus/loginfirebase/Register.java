package com.example.asus.loginfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText edtRUser;
    private EditText edtREmail;
    private EditText edtRPassword;
    private TextView tvUser;
    private TextView tvUserType;
    private String url = "https://testlogindb.firebaseio.com/";

    private CurrentUser currentUser;
    Firebase rootUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);
        currentUser = new CurrentUser(this);

        rootUrl = new Firebase(url);

        tvUser = (TextView) findViewById(R.id.tvUser);
        tvUserType = (TextView) findViewById(R.id.tvUserType);
        tvUser.setText(currentUser.getUser());
        tvUserType.setText(currentUser.getType());
    }

    public void btnRegister(View view) throws UnsupportedEncodingException {
        edtRUser = (EditText) findViewById(R.id.edtRUser);
        edtREmail = (EditText) findViewById(R.id.edtREmail);
        edtRPassword = (EditText) findViewById(R.id.edtRPassword);
        // YA CREO QUE ESTO LO CONOCES, AQUI SIMPLEMENTE ESTOY PUSH A LA DB CON LOS DATOS DEL USUARIO
        Map<String, String> post1 = new HashMap<String, String>();
        // EN LA DB SOLO TIENE EMAIL, CONTRASEÑA Y TYPE
        // POR DEFECTO TODOS LOS USUARIOS LOS PONGO COMO USUARIO EN TYPE
        // QUE SIGNIFICA QUE SON ESTUDIANTES
        // LO PUEDES CAMBIAR COMO QUIERAS
        post1.put("Email", edtREmail.getText().toString());
        byte[] data = edtRPassword.getText().toString().getBytes("UTF-8");
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        post1.put("Password", base64);
        post1.put("Type", "Usuario");

        rootUrl.child("Users").child(edtRUser.getText().toString()).setValue(post1);
        finish();
    }

    public void btnLogout(View view) {
        // ESTE ES EL BOTON DEL LOGOUT
        currentUser.setUser("Users");
        currentUser.setType("Nothing");
        tvUser.setText(currentUser.getUser());
        tvUserType.setText(currentUser.getType());

    }
}
