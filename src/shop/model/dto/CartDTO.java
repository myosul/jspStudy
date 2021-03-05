package shop.model.dto;

import java.sql.Timestamp;

public class CartDTO {
    
    // Filed
    private int cart_no;
    private int member_no;
    private int product_no;
    private int product_amount;
    private Timestamp cart_regi_date;
    
    private String product_name;
    private int product_price;
    private String product_description;
    private String product_img;
    
    private int cart_buy_money;
    
    
    // Constructor
    public CartDTO() {}

    
    // Method
    public int getCart_no() {
        return cart_no;
    }

    public void setCart_no(int cart_no) {
        this.cart_no = cart_no;
    }

    public int getMember_no() {
        return member_no;
    }

    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }

    public int getProduct_amount() {
        return product_amount;
    }

    public void setProduct_amount(int product_amount) {
        this.product_amount = product_amount;
    }

    public Timestamp getCart_regi_date() {
        return cart_regi_date;
    }

    public void setCart_regi_date(Timestamp cart_regi_date) {
        this.cart_regi_date = cart_regi_date;
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

    public int getCart_buy_money() {
        return cart_buy_money;
    }

    public void setCart_buy_money(int cart_buy_money) {
        this.cart_buy_money = cart_buy_money;
    }


    @Override
    public String toString() {
        return "CartDTO [cart_no=" + cart_no + ", member_no=" + member_no + ", product_no=" + product_no
                + ", product_amount=" + product_amount + ", cart_regi_date=" + cart_regi_date + ", product_name="
                + product_name + ", product_price=" + product_price + ", product_description=" + product_description
                + ", product_img=" + product_img + ", cart_buy_money=" + cart_buy_money + "]";
    }
    
    

}
