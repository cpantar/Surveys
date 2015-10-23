package com.example.cpantar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class TakeSurveyActivity extends AppCompatActivity {
    private Survey clickedSurvey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_survey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout layout = (LinearLayout)findViewById(R.id.survey_layout);

        clickedSurvey = (Survey) getIntent().getSerializableExtra("clickedSurvey");

        List<SurveyQuestion> surveyQuestions = clickedSurvey.getSurveyQuestions();

        for(SurveyQuestion surveyQuestion : surveyQuestions){

            TextView questionView = new TextView(this);
            questionView.setText(surveyQuestion.getQuestion());
            layout.addView(questionView);

            List<String> answers = surveyQuestion.getAnswers();
            QuestionType questionType = surveyQuestion.getQuestionType();
            switch (questionType){

                case Text:
                    EditText answerText = new EditText(this);
                    layout.addView(answerText);
                    break;

                case RadioButton:
                    final RadioButton[] answerRadios = new RadioButton[answers.size()];
                    RadioGroup answerGroup = new RadioGroup(this);
                    answerGroup.setOrientation(RadioGroup.VERTICAL);
                    for(int i=0;i<answers.size();i++){
                        answerRadios[i] = new RadioButton(this);
                        answerRadios[i].setText(answers.get(i));
                        answerRadios[i].setId(i);
                        layout.addView(answerRadios[i]);
                    }
                    break;

                case CheckBox:
                    for (String answer:answers ){
                        CheckBox answerCheckBox = new CheckBox(this);
                        answerCheckBox.setText(answer);
                        layout.addView(answerCheckBox);
                    }
                    break;

            }
        }

        Button submitButton = new Button(this);
        submitButton.setText("Submit");
        layout.addView(submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedSurvey.setIsCompleted(true);
                Intent intent = new Intent(TakeSurveyActivity.this, SurveyActivity.class);
                intent.putExtra("completedSurvey", clickedSurvey);
                startActivity(intent);
            }
        });


    }



}
