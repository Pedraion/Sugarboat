package com.example.sugarboat.view;

import android.content.Intent;
import android.os.*;
import android.util.*;
import android.view.*;
import android.text.*;
import android.widget.*;
import androidx.core.view.*;

import androidx.core.graphics.Insets;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sugarboat.R;
import com.google.android.material.button.*;
import com.google.android.material.textview.*;
import com.google.android.material.textfield.*;
import com.example.sugarboat.ConnectionClass;

import java.sql.Connection;
import java.util.concurrent.*;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    String str;
    Connection con;
    ConnectionClass connectionClass;

    private MaterialButton btnLogin;
    private TextInputEditText email, password;
    private MaterialTextView txtSignup, txtForgot;
    private TextInputLayout layoutEmail, layoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializando views da activity
        email = findViewById(R.id.txtInputLoginEmail);
        password = findViewById(R.id.txtInputLoginPassword);
        btnLogin = findViewById(R.id.bntLoginSignin);
        txtSignup = findViewById(R.id.txtLoginSignUp);
        txtForgot = findViewById(R.id.txtLoginForgot);
        layoutEmail = findViewById(R.id.txtInputLayoutLoginEmail);
        layoutPassword = findViewById(R.id.txtInputLayoutLoginPassword);

        password.addTextChangedListener(passwordChange());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if (mail.isEmpty() || mail.isBlank()) {
                    layoutEmail.setError(getString(R.string.error_message_empty_field));
                } else {
                    layoutEmail.setError(null);
                }

                if (pass.isEmpty() || pass.isBlank()) {
                    layoutPassword.setError(getString(R.string.error_message_empty_field));
                } else if (pass.length() < 4) {
                    layoutPassword.setError(getString(R.string.error_message_invalid_password));
                } else {
                    layoutPassword.setError(null);
                }

                String json = String.format("users: {\n\tEmail: %s,\n\tSenha: %s \n}", mail, pass);

                System.out.println(json);
                Toast.makeText(v.getContext(), json, Toast.LENGTH_LONG).show();
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), getText(R.string.error_message_not_implemented), Toast.LENGTH_LONG).show();
            }
        });

        connectionClass = new ConnectionClass();
        connect();
    }

    /**
     * Cria conexão com a base de dados.
     */
    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                con = connectionClass.CONN();
                if(con == null){
                    str = "Error in connection with MySQL server";
                }else{
                    str = "Connected with MySQL server";
                }
            }catch(Exception e){
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Log.e(TAG, e.getMessage());
                }

                Log.i(TAG, str);
            });

        });
    }

    /**
     * Define ActionEvent para txtInputLoginPassword
     *
     * @return Mudança de icone na TextInputLayout
     */
    private TextWatcher passwordChange() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 5) {
                    layoutPassword.setStartIconDrawable(R.drawable.baseline_rounded_lock_open_right_24);
                } else {
                    layoutPassword.setStartIconDrawable(R.drawable.baseline_rounded_lock_24);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }
}