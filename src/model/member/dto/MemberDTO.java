package model.member.dto;

import java.sql.Timestamp;

public class MemberDTO {
    
    // Filed
    private int member_no;
    private String member_id;
    private String member_password;
    private String member_password_check;
    private String member_name;
    private String member_gender;
    private int member_born_year;
    private int member_postcode;
    private String member_address;
    private String member_detail_address;
    private String member_extra_address;
    private Timestamp member_regi_date; // import
    
    
    // Constructor
    public MemberDTO() {}


    // Method
    public int getMember_no() {
        return member_no;
    }


    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }


    public String getMember_id() {
        return member_id;
    }


    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }


    public String getMember_password() {
        return member_password;
    }


    public void setMember_password(String member_password) {
        this.member_password = member_password;
    }


    public String getMember_password_check() {
        return member_password_check;
    }


    public void setMember_password_check(String member_password_check) {
        this.member_password_check = member_password_check;
    }


    public String getMember_name() {
        return member_name;
    }


    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }


    public String getMember_gender() {
        return member_gender;
    }


    public void setMember_gender(String member_gender) {
        this.member_gender = member_gender;
    }


    public int getMember_born_year() {
        return member_born_year;
    }


    public void setMember_born_year(int member_born_year) {
        this.member_born_year = member_born_year;
    }
    
    
    public int getMember_postcode() {
        return member_postcode;
    }


    public void setMember_postcode(int member_postcode) {
        this.member_postcode = member_postcode;
    }


    public String getMember_address() {
        return member_address;
    }


    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }


    public String getMember_detail_address() {
        return member_detail_address;
    }


    public void setMember_detail_address(String member_detail_address) {
        this.member_detail_address = member_detail_address;
    }


    public String getMember_extra_address() {
        return member_extra_address;
    }


    public void setMember_extra_address(String member_extra_address) {
        this.member_extra_address = member_extra_address;
    }


    public Timestamp getMember_regi_date() {
        return member_regi_date;
    }


    public void setMember_regi_date(Timestamp member_regi_date) {
        this.member_regi_date = member_regi_date;
    }


    @Override
    public String toString() {
        return "MemberDTO [member_no=" + member_no + ", member_id=" + member_id + ", member_password=" + member_password
                + ", member_password_check=" + member_password_check + ", member_name=" + member_name
                + ", member_gender=" + member_gender + ", member_born_year=" + member_born_year + ", member_regi_date="
                + member_regi_date + "]";
    }
    
    
}
