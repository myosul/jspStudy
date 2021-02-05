package model.board.dto;

import java.sql.Timestamp;

public class BoardDTO {
    
    // Field
    private int board_no;
    private int board_num;
    private String board_tbl;
    private String board_writer;
    private String board_subject;
    private String board_content;
    private String board_email;
    private String board_passwd;
    private int board_ref_no;
    private int board_step_no;
    private int board_level_no;
    private int board_parent_no;
    private int board_hit;
    private String board_ip;
    private int member_no;
    private int board_notice_no;
    private String board_secret;
    private Timestamp board_regi_date;
    
    private String board_notice;
    private int board_child_counter;
    
    private int board_pre_no;
    private String board_pre_subject;
    private int board_nxt_no;
    private String board_nxt_subject;
    
    
    // Constructor
    public BoardDTO() {}
    
    
    // Method
    public int getBoard_no() {
        return board_no;
    }
    public void setBoard_no(int board_no) {
        this.board_no = board_no;
    }
    public int getBoard_num() {
        return board_num;
    }
    public void setBoard_num(int board_num) {
        this.board_num = board_num;
    }
    public String getBoard_tbl() {
        return board_tbl;
    }
    public void setBoard_tbl(String board_tbl) {
        this.board_tbl = board_tbl;
    }
    public String getBoard_writer() {
        return board_writer;
    }
    public void setBoard_writer(String board_writer) {
        this.board_writer = board_writer;
    }
    public String getBoard_subject() {
        return board_subject;
    }
    public void setBoard_subject(String board_subject) {
        this.board_subject = board_subject;
    }
    public String getBoard_content() {
        return board_content;
    }
    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }
    public String getBoard_email() {
        return board_email;
    }
    public void setBoard_email(String board_email) {
        this.board_email = board_email;
    }
    public String getBoard_passwd() {
        return board_passwd;
    }
    public void setBoard_passwd(String board_passwd) {
        this.board_passwd = board_passwd;
    }
    public int getBoard_ref_no() {
        return board_ref_no;
    }
    public void setBoard_ref_no(int board_ref_no) {
        this.board_ref_no = board_ref_no;
    }
    public int getBoard_step_no() {
        return board_step_no;
    }
    public void setBoard_step_no(int board_step_no) {
        this.board_step_no = board_step_no;
    }
    public int getBoard_level_no() {
        return board_level_no;
    }
    public void setBoard_level_no(int board_level_no) {
        this.board_level_no = board_level_no;
    }
    public int getBoard_parent_no() {
        return board_parent_no;
    }
    public void setBoard_parent_no(int board_parent_no) {
        this.board_parent_no = board_parent_no;
    }
    public int getBoard_hit() {
        return board_hit;
    }
    public void setBoard_hit(int board_hit) {
        this.board_hit = board_hit;
    }
    public String getBoard_ip() {
        return board_ip;
    }
    public void setBoard_ip(String board_ip) {
        this.board_ip = board_ip;
    }
    public int getMember_no() {
        return member_no;
    }
    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }
    public int getBoard_notice_no() {
        return board_notice_no;
    }
    public void setBoard_notice_no(int board_notice_no) {
        this.board_notice_no = board_notice_no;
    }
    public String getBoard_secret() {
        return board_secret;
    }
    public void setBoard_secret(String board_secret) {
        this.board_secret = board_secret;
    }
    public Timestamp getBoard_regi_date() {
        return board_regi_date;
    }
    public void setBoard_regi_date(Timestamp board_regi_date) {
        this.board_regi_date = board_regi_date;
    }
    public String getBoard_notice() {
        return board_notice;
    }
    public void setBoard_notice(String board_notice) {
        this.board_notice = board_notice;
    }
    public int getBoard_child_counter() {
        return board_child_counter;
    }
    public void setBoard_child_counter(int board_child_counter) {
        this.board_child_counter = board_child_counter;
    }
    public int getBoard_pre_no() {
        return board_pre_no;
    }
    public void setBoard_pre_no(int board_pre_no) {
        this.board_pre_no = board_pre_no;
    }
    public String getBoard_pre_subject() {
        return board_pre_subject;
    }
    public void setBoard_pre_subject(String board_pre_subject) {
        this.board_pre_subject = board_pre_subject;
    }
    public int getBoard_nxt_no() {
        return board_nxt_no;
    }
    public void setBoard_nxt_no(int board_nxt_no) {
        this.board_nxt_no = board_nxt_no;
    }
    public String getBoard_nxt_subject() {
        return board_nxt_subject;
    }
    public void setBoard_nxt_subject(String board_nxt_subject) {
        this.board_nxt_subject = board_nxt_subject;
    }
    

}
