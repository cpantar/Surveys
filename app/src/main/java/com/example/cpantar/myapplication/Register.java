package com.example.cpantar.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister;
    EditText eFName,eLName,eEmail,ePassword,eAge;
    Uri uriUrl = Uri.parse("http://10.50.20.37:3000/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eFName = (EditText)findViewById(R.id.eFName);
        eLName=(EditText)findViewById(R.id.eLName);
        eEmail=(EditText)findViewById(R.id.eEmail);
        ePassword=(EditText)findViewById(R.id.ePassword);
        eAge=(EditText)findViewById(R.id.eAge);
        bRegister =(Button)findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bRegister:
                FileHelper.writeToFile(eEmail.getText() +","+ePassword.getText(), this);
                Intent launchDefaultBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
               startActivity(launchDefaultBrowser);
                //startActivity(new Intent(this, SurveyActivity.class));
                break;
        }
    }
}
