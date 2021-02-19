package model.board.dto;

import java.sql.Timestamp;

public class BoardCommentDTO {
    
    // Field
    private int board_comment_no;
    private int board_no;
    private String board_comment_writer;
    private String board_comment_content;
    private String board_comment_passwd;
    private int member_no;
    private String board_comment_ip;
    private Timestamp board_comment_regi_date;
    
    // Constructor
    public BoardCommentDTO() {}

    // Method
    public int getBoard_comment_no() {
        return board_comment_no;
    }

    public void setBoard_comment_no(int board_comment_no) {
        this.board_comment_no = board_comment_no;
    }

    public int getBoard_no() {
        return board_no;
    }

    public void setBoard_no(int board_no) {
        this.board_no = board_no;
    }

    public String getBoard_comment_writer() {
        return board_comment_writer;
    }

    public void setBoard_comment_writer(String board_comment_writer) {
        this.board_comment_writer = board_comment_writer;
    }

    public String getBoard_comment_content() {
        return board_comment_content;
    }

    public void setBoard_comment_content(String board_comment_content) {
        this.board_comment_content = board_comment_content;
    }

    public String getBoard_comment_passwd() {
        return board_comment_passwd;
    }

    public void setBoard_comment_passwd(String board_comment_passwd) {
        this.board_comment_passwd = board_comment_passwd;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public String getBoard_comment_ip() {
        return board_comment_ip;
    }

    public void setBoard_comment_ip(String board_comment_ip) {
        this.board_comment_ip = board_comment_ip;
    }

    public Timestamp getBoard_comment_regi_date() {
        return board_comment_regi_date;
    }

    public void setBoard_comment_regi_date(Timestamp board_comment_regi_date) {
        this.board_comment_regi_date = board_comment_regi_date;
    }

    @Override
    public String toString() {
        return "BoardCommentDTO [board_comment_no=" + board_comment_no + ", board_no=" + board_no
                + ", board_comment_writer=" + board_comment_writer + ", board_comment_content=" + board_comment_content
                + ", board_comment_passwd=" + board_comment_passwd + ", member_no=" + member_no + ", board_comment_ip="
                + board_comment_ip + ", board_comment_regi_date=" + board_comment_regi_date + "]";
    }
    

}
