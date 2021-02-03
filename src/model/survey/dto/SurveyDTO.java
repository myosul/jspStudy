package model.survey.dto;

import java.sql.Timestamp;

public class SurveyDTO {
    
    // Field
    private int survey_no;
    private String survey_question;
    private String survey_answer1;
    private String survey_answer2;
    private String survey_answer3;
    private String survey_answer4;
    private String survey_answer5;
    private String survey_status;
    private Timestamp survey_start_date;
    private Timestamp survey_end_date;
    private Timestamp survey_regi_date;
    private int survey_counter;
    
    
    // Constructor
    public SurveyDTO() {}


    // Method
    public int getSurvey_no() {
        return survey_no;
    }


    public void setSurvey_no(int survey_no) {
        this.survey_no = survey_no;
    }


    public String getSurvey_question() {
        return survey_question;
    }


    public void setSurvey_question(String survey_question) {
        this.survey_question = survey_question;
    }


    public String getSurvey_answer1() {
        return survey_answer1;
    }


    public void setSurvey_answer1(String survey_answer1) {
        this.survey_answer1 = survey_answer1;
    }


    public String getSurvey_answer2() {
        return survey_answer2;
    }


    public void setSurvey_answer2(String survey_answer2) {
        this.survey_answer2 = survey_answer2;
    }


    public String getSurvey_answer3() {
        return survey_answer3;
    }


    public void setSurvey_answer3(String survey_answer3) {
        this.survey_answer3 = survey_answer3;
    }


    public String getSurvey_answer4() {
        return survey_answer4;
    }


    public void setSurvey_answer4(String survey_answer4) {
        this.survey_answer4 = survey_answer4;
    }


    public String getSurvey_answer5() {
        return survey_answer5;
    }


    public void setSurvey_answer5(String survey_answer5) {
        this.survey_answer5 = survey_answer5;
    }


    public String getSurvey_status() {
        return survey_status;
    }


    public void setSurvey_status(String survey_status) {
        this.survey_status = survey_status;
    }


    public Timestamp getSurvey_start_date() {
        return survey_start_date;
    }


    public void setSurvey_start_date(Timestamp survey_start_date) {
        this.survey_start_date = survey_start_date;
    }


    public Timestamp getSurvey_end_date() {
        return survey_end_date;
    }


    public void setSurvey_end_date(Timestamp survey_end_date) {
        this.survey_end_date = survey_end_date;
    }


    public Timestamp getSurvey_regi_date() {
        return survey_regi_date;
    }


    public void setSurvey_regi_date(Timestamp survey_regi_date) {
        this.survey_regi_date = survey_regi_date;
    }
    
    
    public int getSurvey_counter() {
        return survey_counter;
    }


    public void setSurvey_counter(int sruvey_counter) {
        this.survey_counter = sruvey_counter;
    }

    @Override
    public String toString() {
        return "SurveyDTO [survey_no=" + survey_no + ", survey_question=" + survey_question + ", survey_answer1="
                + survey_answer1 + ", survey_answer2=" + survey_answer2 + ", survey_answer3=" + survey_answer3
                + ", survey_answer4=" + survey_answer4 + ", survey_answer5=" + survey_answer5 + ", survey_status="
                + survey_status + ", survey_start_date=" + survey_start_date + ", survey_end_date=" + survey_end_date
                + ", survey_regi_date=" + survey_regi_date + ", survey_counter=" + survey_counter + "]";
    }
    

}
