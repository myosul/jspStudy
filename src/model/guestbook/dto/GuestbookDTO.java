package model.guestbook.dto;

public class GuestbookDTO {
    
    // Field
    private int guestbook_no;
    private String guestbook_writer_name;
    private String guestbook_writer_email;
    private String guestbook_passwd;
    private String guestbook_content;
    private String guestbook_regi_date;
    
    
    // Constructor
    public GuestbookDTO() {}


    // Method
    public int getGuestbook_no() {
        return guestbook_no;
    }


    public void setGuestbook_no(int guestbook_no) {
        this.guestbook_no = guestbook_no;
    }


    public String getGuestbook_writer_name() {
        return guestbook_writer_name;
    }


    public void setGuestbook_writer_name(String guestbook_writer_name) {
        this.guestbook_writer_name = guestbook_writer_name;
    }


    public String getGuestbook_writer_email() {
        return guestbook_writer_email;
    }


    public void setGuestbook_writer_email(String guestbook_writer_email) {
        this.guestbook_writer_email = guestbook_writer_email;
    }


    public String getGuestbook_passwd() {
        return guestbook_passwd;
    }


    public void setGuestbook_passwd(String guestbook_passwd) {
        this.guestbook_passwd = guestbook_passwd;
    }


    public String getGuestbook_content() {
        return guestbook_content;
    }


    public void setGuestbook_content(String guestbook_content) {
        this.guestbook_content = guestbook_content;
    }


    public String getGuestbook_regi_date() {
        return guestbook_regi_date;
    }


    public void setGuestbook_regi_date(String guestbook_regi_date) {
        this.guestbook_regi_date = guestbook_regi_date;
    }
    

}
