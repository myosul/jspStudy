package model.survey.dto;

import java.sql.Timestamp;

public class SurveyAnswerDTO {
    
    // Field
    private int survey_answer_no;
    private int survey_no;
    private int survey_answer;
    private Timestamp survey_answer_regi_date;
    
    
    // Constructor
    public SurveyAnswerDTO() {}


    
    // Method
    public int getSurvey_answer_no() {
        return survey_answer_no;
    }


    public void setSurvey_answer_no(int survey_answer_no) {
        this.survey_answer_no = survey_answer_no;
    }


    public int getSurvey_no() {
        return survey_no;
    }


    public void setSurvey_no(int survey_no) {
        this.survey_no = survey_no;
    }


    public int getSurvey_answer() {
        return survey_answer;
    }


    public void setSurvey_answer(int survey_answer) {
        this.survey_answer = survey_answer;
    }


    public Timestamp getSurvey_answer_regi_date() {
        return survey_answer_regi_date;
    }


    public void setSurvey_answer_regi_date(Timestamp survey_answer_regi_date) {
        this.survey_answer_regi_date = survey_answer_regi_date;
    }
    
    
    @Override
    public String toString() {
        return "SurveyAnswerDTO [survey_answer_no=" + survey_answer_no + ", survey_no=" + survey_no + ", survey_answer="
                + survey_answer + ", survey_answer_regi_date=" + survey_answer_regi_date + "]";
    }
    
    
    
    

}
