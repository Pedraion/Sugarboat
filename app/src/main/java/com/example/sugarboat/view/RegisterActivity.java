package com.example.sugarboat.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sugarboat.R;
import com.google.android.material.button.*;
import com.google.android.material.textview.*;
import com.google.android.material.textfield.*;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private MaterialButton btnSingup;
    private MaterialTextView txtSignin;
    private TextInputEditText email, password, confirm;
    private TextInputLayout layoutEmail, layoutPassword, layoutConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializando views da activity
        email = findViewById(R.id.txtInputRegisterEmail);
        password = findViewById(R.id.txtInputRegisterPassword);
        confirm = findViewById(R.id.txtInputRegisterConfirmPassword);
        btnSingup = findViewById(R.id.btnRegisterSignup);
        txtSignin = findViewById(R.id.txtRegisterSignIn);
        layoutEmail = findViewById(R.id.txtInputLayoutRegisterEmail);
        layoutPassword = findViewById(R.id.txtInputLayoutRegisterPassword);
        layoutConfirm = findViewById(R.id.txtInputLayoutRegisterConfirmPassword);

        password.addTextChangedListener(passwordChange(password));
        confirm.addTextChangedListener(passwordChange(confirm));

        txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });

        btnSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "";

                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String conf = confirm.getText().toString();

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

                if (conf.isEmpty() || conf.isBlank()) {
                    layoutConfirm.setError(getString(R.string.error_message_empty_field));
                } else if (!conf.equals(pass)) {
                    layoutConfirm.setError(getString(R.string.error_message_different_password));
                } else if (conf.length() < 4) {
                    layoutConfirm.setError(getString(R.string.error_message_invalid_password));
                } else {
                    layoutConfirm.setError(null);
                    double codigo = Math.random() * 1000000;
                    json = String.format("users: {\n\tEmail: %s,\n\tSenha: %s,\n\tConfirmaçao: %s,\n\tCódigo: %d}", mail, pass, conf, (int) codigo);
                }

                System.out.println(json);

                Intent intent = new Intent(RegisterActivity.this, ConfirmActivity.class);

                startActivity(intent);
            }
        });
    }

    /**
     * Define ActionEvent para txtInputRegisterPassword e txtInputRegisterConfirmPassword
     *
     * @param editText View no qual passará os valores
     * @return Mudança de icone na TextInputLayout
     */
    private TextWatcher passwordChange(TextInputEditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 4) {
                    if (editText.getId() == R.id.txtInputRegisterPassword) {
                        layoutPassword.setStartIconDrawable(R.drawable.baseline_rounded_lock_24);
                    }

                    if (editText.getId() == R.id.txtInputRegisterConfirmPassword) {
                        if (confirm.getText().toString().equals(password.getText().toString())) {
                            layoutConfirm.setStartIconDrawable(R.drawable.baseline_rounded_lock_24);
                            layoutPassword.setStartIconDrawable(R.drawable.baseline_rounded_lock_24);
                        } else {
                            layoutPassword.setStartIconDrawable(R.drawable.baseline_rounded_lock_open_right_24);
                            layoutConfirm.setStartIconDrawable(R.drawable.baseline_rounded_lock_open_right_24);
                        }
                    }
                } else {
                    layoutPassword.setStartIconDrawable(R.drawable.baseline_rounded_lock_open_right_24);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }
}