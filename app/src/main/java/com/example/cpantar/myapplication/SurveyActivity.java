package com.example.cpantar.myapplication;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.internal.view.menu.MenuView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SurveyActivity extends AppCompatActivity {

    private static List<Survey> surveys = new ArrayList<Survey>();
    static boolean wasPaused = false;
    boolean isNotBg = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ArrayAdapter<String> surveyAdapter = new ArrayAdapter<String>(getListView().getContext(),android.R.layout.simple_list_item_1, surveys);
//        getListView().setAdapter(surveyAdapter)


    }

    @Override
    protected void onStart(){
       // populateSurveys(true);
        super.onStart();
    }

    private void populateSurveys(boolean wasPaused){
        if(!wasPaused){
        Survey surveyToAdd = new Survey("Introduction", 1, false, R.drawable.phone, 100, getIntroSurvey(), SurveyDevice.Mobile);
            if(!surveys.contains(surveyToAdd)){
                surveys.add(surveyToAdd);
            }
        }

        Survey completedSurvey = (Survey) getIntent().getSerializableExtra("completedSurvey");

        if(completedSurvey!= null){
            Iterator<Survey> iter = surveys.iterator();

            while (iter.hasNext()) {
                Survey survey = iter.next();

                if(survey.getId() == completedSurvey.getId())
                    iter.remove();
            }
        }


    }

    public void populateListView(){
        ArrayAdapter<Survey> surveyAdapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.surveyListView);
        list.setAdapter(surveyAdapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.surveyListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View viewClicked,
                                    int position, long id) {

                final Survey clickedSurvey = surveys.get(position);

                switch (clickedSurvey.getSurveyDevice()) {
                    case Desktop:
                        new AlertDialog.Builder(viewClicked.getContext())
                                .setTitle("Desktop Survey")
                                .setMessage("Please take this survey on a Desktop Browser")
                                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        break;
                    case Mobile:
                        isNotBg = false;
                        Intent intent = new Intent(viewClicked.getContext(), TakeSurveyActivity.class);
                        intent.putExtra("clickedSurvey", clickedSurvey);
                        startActivity(intent);
                        break;
                    case Multi:
                        final Context context = viewClicked.getContext();
                        new AlertDialog.Builder(context)
                                .setTitle("Multi-Device")
                                .setMessage("Take this survey on a Desktop browser for 2x Points!")
                                .setPositiveButton("Take on Desktop", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Take on Mobile", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        isNotBg = false;
                                        Intent intent = new Intent(context, TakeSurveyActivity.class);
                                        intent.putExtra("clickedSurvey", clickedSurvey);
                                        startActivity(intent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        break;
                }


            }
        });
    }

    private List<SurveyQuestion> getIntroSurvey(){
        List<SurveyQuestion> surveyQuestions = new ArrayList<SurveyQuestion>();

        SurveyQuestion sq1 = new SurveyQuestion("What is your birthdate ? \r\n (mm/dd/yyyy)",null,QuestionType.Text);
        surveyQuestions.add(sq1);

        List<String> answerssq2 = new ArrayList<String>();
        answerssq2.add(0,"Male");
        answerssq2.add(1,"Female");
        SurveyQuestion sq2 = new SurveyQuestion("What is your gender?",answerssq2,QuestionType.RadioButton);
        surveyQuestions.add(sq2);

        ArrayList<String> ansSq3 = new ArrayList<String>();

        ansSq3.add("Less than $15,000");
        ansSq3.add("$15,000 to $24,999");
        ansSq3.add("$25,000 to $39,999");
        ansSq3.add("$$40,000 to $59,999");
        ansSq3.add("$60,000 to $74,999");
        ansSq3.add("$75,000 to $99,999");
        ansSq3.add("$100,000 or more");

        SurveyQuestion sq3 = new SurveyQuestion("Which category best describes your total yearly household income before taxes ?",ansSq3,QuestionType.RadioButton);
        surveyQuestions.add(sq3);

        return surveyQuestions;
    }

    private List<SurveyQuestion> getProductXSurvey(){
        List<SurveyQuestion> surveyQuestions = new ArrayList<SurveyQuestion>();

        ArrayList<String> ansSq1 = new ArrayList<String>();

        ansSq1.add("Client Brand");
        ansSq1.add("Competitive Brand 1");
        ansSq1.add("Competitive Brand 2");
        ansSq1.add("Competitive Brand 3");
        ansSq1.add("Competitive Brand 4");
        ansSq1.add("None");

        SurveyQuestion sq1 = new SurveyQuestion("Have you heard of any of the following brands of PRODUCT X? )",ansSq1,QuestionType.CheckBox);
        surveyQuestions.add(sq1);

        List<String> answerssq2 = new ArrayList<String>();
        answerssq2.add("Very unfavorable");
        answerssq2.add("Somewhat unfavorable");
        answerssq2.add("Neutral");
        answerssq2.add("Somewhat favorable");
        answerssq2.add("Very favorable");
        SurveyQuestion sq2 = new SurveyQuestion("What is your overall opinion of CLIENT BRAND?",answerssq2,QuestionType.RadioButton);
        surveyQuestions.add(sq2);

        ArrayList<String> ansSq3 = new ArrayList<String>();

        ansSq3.add("Very Unlikely");
        ansSq3.add("Somewhat Unlikely");
        ansSq3.add("Neutral");
        ansSq3.add("Somewhat Likely");
        ansSq3.add("Very Likely");

        SurveyQuestion sq3 = new SurveyQuestion("How likely are you to consider purchasing CLIENT BRAND in the next 6 months?",ansSq3,QuestionType.RadioButton);
        surveyQuestions.add(sq3);

        SurveyQuestion sq4 = new SurveyQuestion("How likely are you to recommend CLIENT BRAND to family and friends?",ansSq3,QuestionType.RadioButton);
        surveyQuestions.add(sq4);

        return surveyQuestions;
    }

    private class MyListAdapter extends ArrayAdapter<Survey>{
        public MyListAdapter(){
            super(SurveyActivity.this, R.layout.item_view, surveys);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // must have view
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }
            Survey currentSurvey = surveys.get(position);

            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentSurvey.getIconID());

            TextView surveyName =(TextView)itemView.findViewById(R.id.item_txtMake);
            surveyName.setText(currentSurvey.getName());

            TextView surveyPoints =(TextView)itemView.findViewById(R.id.item_txtPoints);
            surveyPoints.setText("" + currentSurvey.getPoints() + " points");

            return itemView;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean bg =  isNotBg;
        if(bg){
            isNotBg = false;
            Survey newSurvey = getSecondSurvey();
            Survey videoGame = new Survey("Videogames", 3, false, R.drawable.desktop, 500, null, SurveyDevice.Desktop);
            addNotification(this, newSurvey);

            if(!surveys.contains(newSurvey))
                surveys.add(newSurvey);

            if(!surveys.contains(videoGame))
                surveys.add(videoGame);

            wasPaused = true;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    private Survey getSecondSurvey(){

        Survey surveyToAdd = new Survey("ProductX", 2, false, R.drawable.multidevice, 200, getProductXSurvey(), SurveyDevice.Multi);

        return surveyToAdd;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        populateSurveys(wasPaused);
        populateListView();
        registerClickCallback();
    }




    public boolean isApplicationSentToBackground(final Context context) {
        String topPackageName = "";
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                topPackageName = topActivity.getPackageName();
            }
            if (!topPackageName.equals(context.getPackageName())) {
                return true;
            }

        return false;
    }


    private void addNotification(Context context, Survey newSurvey) {

        int icon = R.drawable.csround;
        long when = System.currentTimeMillis();
        String appname = context.getResources().getString(R.string.app_name);
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification;
        Intent intent = new Intent(context, SurveyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        notification = builder.setContentIntent(contentIntent)
                .setSmallIcon(icon).setTicker(appname).setWhen(0)
                .setAutoCancel(true).setContentTitle(appname)
                .setSubText("Click to take")
                .setContentText("New Surveys Available!").build();

        notificationManager.notify(0 , notification);

    }

}

