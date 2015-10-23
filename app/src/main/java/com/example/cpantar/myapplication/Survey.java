package com.example.cpantar.myapplication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cpantar on 10/16/2015.
 */
public class Survey implements Serializable {
    public String name;
    private int id;
    private boolean isCompleted;
    private int iconID;
    private int points;
    private List<SurveyQuestion> surveyQuestions;
    private SurveyDevice surveyDevice;

    public Survey(String name, int id, boolean isCompleted, int iconID, int points, List<SurveyQuestion> surveyQuestions, SurveyDevice surveyDevice){
        super();
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
        this.iconID = iconID;
        this.points = points;
        this.surveyQuestions = surveyQuestions;
        this.surveyDevice = surveyDevice;
    }

    public SurveyDevice getSurveyDevice(){
        return surveyDevice;
    }

    public String getName(){
        return name;
    }

    public List<SurveyQuestion> getSurveyQuestions(){
        return surveyQuestions;
    }

    public int getId(){
        return id;
    }

    public boolean getIsCompleted(){
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }

    public int getIconID(){
        return iconID;
    }
    public int getPoints(){
        return points;
    }


}
