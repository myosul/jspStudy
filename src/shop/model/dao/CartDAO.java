package shop.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import model.board.dto.BoardDTO;
import shop.model.dto.CartDTO;

public class CartDAO {
    
    // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    // Constructor
    public CartDAO() {}
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int setInsert(CartDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into cart";
            sql += " (cart_no, member_no, product_no, product_amount, cart_regi_date)";
            sql += " values";
            sql += " (seq_cart.nextval, ?, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getMember_no());
            pstmt.setInt(2, dto.getProduct_no());
            pstmt.setInt(3, dto.getProduct_amount());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public int getTotalRecord(int member_no) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from cart where member_no = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, member_no);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public ArrayList<CartDTO> getSelectAll(int member_no, int startRecord, int endRecord) {
        ArrayList<CartDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select c.cart_no, c.member_no, c.product_no, c.product_amount, c.cart_regi_date, p.PRODUCT_NAME , p.PRODUCT_PRICE , p.PRODUCT_IMG, (c.product_amount * p.PRODUCT_PRICE) cart_buy_money";
            basicSql += " from cart c, product p";
            basicSql += " where c.product_no = p.product_no";
            basicSql += " and member_no = ?";
            basicSql += " order by cart_regi_date desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, member_no);
            pstmt.setInt(2, startRecord);
            pstmt.setInt(3, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CartDTO dto = new CartDTO();
                dto.setCart_no(rs.getInt("cart_no"));
                dto.setMember_no(rs.getInt("member_no"));
                dto.setProduct_no(rs.getInt("product_no"));
                dto.setProduct_amount(rs.getInt("product_amount"));
                dto.setCart_regi_date(rs.getTimestamp("cart_regi_date"));
                dto.setProduct_name(rs.getString("product_name"));
                dto.setProduct_price(rs.getInt("product_price"));
                dto.setProduct_img(rs.getString("product_img"));
                dto.setCart_buy_money(rs.getInt("cart_buy_money"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public boolean setDeleteBatch(String[] array) {
        int[] count = new int[array.length];
        getConn();
        try {
            conn.setAutoCommit(false);
            
            String sql = "delete from cart where cart_no = ?";
            pstmt = conn.prepareStatement(sql);
            for (int i=0; i<array.length; i++) {
                if (array[i].equals("on")) {
                    continue;
                }
                pstmt.setInt(1, Integer.parseInt(array[i]));
                pstmt.addBatch();
            }
            count = pstmt.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        // 리턴값 -2 는 성공은 했지만, 변경된 row의 갯수를 알 수 없을 때 리턴되는 값이다.
        boolean result = true;
        for (int i=0; i<count.length; i++) {
            // System.out.println(i + ". " + count[i]);
            if (count[i] != -2) {
                result = false;
                break;
            }
        }
        return result;
    }

}
