package com.example.dream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

//Klasse für das Vergessene Passwort
public class ForgotPassword extends AppCompatActivity {

    //Deklarierung vom Text, Button, Progressbar und der Authentifikation
    private TextView banner;
    private EditText emailText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    //onCreate Methode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //findViewById, von GUI
        emailText = (EditText) findViewById(R.id.email);
        resetPasswordButton = (Button) findViewById(R.id.resetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            //onClick Methode
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPw();
            }
        });
    }

    //Methode, welche das Passwort zurücksetzt
    private void resetPw() {
        String email = emailText.getText().toString().trim();
        // Schleifen für die Fehlermeldungen
        if (email.isEmpty()) {
            emailText.setError("Bitte E-Mail eingeben!");
            emailText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Bitte fehlerfreie E-Mail eingeben!");
            emailText.requestFocus();
            return;
        }
        //Der unsichtbare Progressbar wird taucht wieder auf.
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Schleife ob das zurücksetzten erfolgreich war oder nicht
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Gehen Sie zu Ihrem E-Mail und setzen Sie das Password zurück!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                } else {
                    Toast.makeText(ForgotPassword.this, "Bitte geben Sie die richtige E-Mail ein!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}