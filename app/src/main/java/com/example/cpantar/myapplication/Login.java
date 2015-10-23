package com.example.cpantar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button bLogin;
    EditText eEmail, ePassword;
    TextView tvRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        eEmail = (EditText) findViewById(R.id.eEmail);
        ePassword = (EditText) findViewById(R.id.ePassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bLogin:
               String data =  FileHelper.readFromFile(this);
                if(data.equals("")){
                    Toast.makeText(getBaseContext(), "No user found, please register",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String email = data.split(",")[0];
                    String pass = data.split(",")[1];
                    if(eEmail.getText().toString().equals(email) && ePassword.getText().toString().equals(pass)) {
                        startActivity(new Intent(this, SurveyActivity.class));
                    }
                    else{
                        Toast.makeText(getBaseContext(), "email/ password incorrect, try again",
                                Toast.LENGTH_SHORT).show();
                    }
                }
               // if(eEmail.getText().toString().equals("jdoe@comscore.com") && ePassword.getText().toString().equals("jdoe101")){

                //}
                break;
            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
