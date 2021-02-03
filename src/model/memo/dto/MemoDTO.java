package model.memo.dto;

import java.sql.Timestamp;

public class MemoDTO {
    
    // Field
    private int memo_no;
    private int memo_writer_no;
    private String memo_subject;
    private String memo_content;
    private Timestamp memo_modi_date;
    private Timestamp memo_regi_date;
    
    
    // Constructor
    public MemoDTO() {}


    // Method
    public int getMemo_no() {
        return memo_no;
    }


    public void setMemo_no(int memo_no) {
        this.memo_no = memo_no;
    }


    public int getMemo_writer_no() {
        return memo_writer_no;
    }


    public void setMemo_writer_no(int memo_writer_no) {
        this.memo_writer_no = memo_writer_no;
    }


    public String getMemo_subject() {
        return memo_subject;
    }


    public void setMemo_subject(String memo_subject) {
        this.memo_subject = memo_subject;
    }


    public String getMemo_content() {
        return memo_content;
    }


    public void setMemo_content(String memo_content) {
        this.memo_content = memo_content;
    }


    public Timestamp getMemo_modi_date() {
        return memo_modi_date;
    }


    public void setMemo_modi_date(Timestamp memo_modi_date) {
        this.memo_modi_date = memo_modi_date;
    }


    public Timestamp getMemo_regi_date() {
        return memo_regi_date;
    }


    public void setMemo_regi_date(Timestamp memo_regi_date) {
        this.memo_regi_date = memo_regi_date;
    }
    
    


}
