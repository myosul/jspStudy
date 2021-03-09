package email.model.dto;

import java.sql.Timestamp;

public class EmailDTO {
    
    // Filed
    private String fromName;
    private String fromEmail;
    private String toEmail;
    private String subject;
    private String content;
    private Timestamp regi_date;
    
    // Constructor
    public EmailDTO() {}

    // Method
    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getRegi_date() {
        return regi_date;
    }

    public void setRegi_date(Timestamp regi_date) {
        this.regi_date = regi_date;
    }

    @Override
    public String toString() {
        return "EmailDTO [fromName=" + fromName + ", fromEmail=" + fromEmail + ", toEmail=" + toEmail + ", subject="
                + subject + ", content=" + content + ", regi_date=" + regi_date + "]";
    }
    
    

}
