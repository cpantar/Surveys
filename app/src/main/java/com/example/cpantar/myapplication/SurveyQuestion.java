package com.example.cpantar.myapplication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cpantar on 10/16/2015.
 */
public class SurveyQuestion implements Serializable {
    private String question;
    private QuestionType questionType;
    private List<String> answers;


    public SurveyQuestion(String question,List<String> answers, QuestionType questionType){
        super();

        this.question = question;
        this.questionType = questionType;
        this.answers = answers;
    }

    public String getQuestion(){return question;}

    public QuestionType getQuestionType(){
        return questionType;
    }

    public List<String> getAnswers(){
        return answers;
    }

}
