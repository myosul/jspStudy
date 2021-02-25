package shop.model.dto;

import java.sql.Timestamp;

public class ProductDTO {
    
    // Field
    private int product_no;
    private String product_name;
    private int product_price;
    private String product_description;
    private String product_img;
    private Timestamp product_regi_date;
    
    private int product_buy_counter;
    
    private int product_pre_no;
    private String product_pre_name;
    private int product_nxt_no;
    private String product_nxt_name;
    
    // Constructor
    public ProductDTO() {}

    // Method
    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public Timestamp getProduct_regi_date() {
        return product_regi_date;
    }

    public void setProduct_regi_date(Timestamp product_regi_date) {
        this.product_regi_date = product_regi_date;
    }

    public int getProduct_buy_counter() {
        return product_buy_counter;
    }

    public void setProduct_buy_counter(int product_buy_counter) {
        this.product_buy_counter = product_buy_counter;
    }

    public int getProduct_pre_no() {
        return product_pre_no;
    }

    public void setProduct_pre_no(int product_pre_no) {
        this.product_pre_no = product_pre_no;
    }

    public String getProduct_pre_name() {
        return product_pre_name;
    }

    public void setProduct_pre_name(String product_pre_name) {
        this.product_pre_name = product_pre_name;
    }

    public int getProduct_nxt_no() {
        return product_nxt_no;
    }

    public void setProduct_nxt_no(int product_nxt_no) {
        this.product_nxt_no = product_nxt_no;
    }

    public String getProduct_nxt_name() {
        return product_nxt_name;
    }

    public void setProduct_nxt_name(String product_nxt_name) {
        this.product_nxt_name = product_nxt_name;
    }
    
    

}
