package shop.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import shop.model.dto.ProductDTO;

public class ProductDAO {
    
    // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    // Constructor
    public ProductDAO() {}
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int setInsert(ProductDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into product";
            sql += " (product_no, product_name, product_price, product_description, product_img, product_regi_date)";
            sql += " values";
            sql += " (seq_product.nextval, ?, ?, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getProduct_name());
            pstmt.setInt(2, dto.getProduct_price());
            pstmt.setString(3, dto.getProduct_description());
            pstmt.setString(4, dto.getProduct_img());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public int getTotalRecord(String search_option, String search_data) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from product where product_no > 0";
            
            if (search_option.length() > 0 && search_data.length() > 0) {
                if (search_option.equals("product_name") || search_option.equals("product_description")) {
                    sql += " and " + search_option + " like ?";
                } else if (search_option.equals("name_description")) {
                    sql += " and (product_name like ? or product_description like ?)";
                }
            }
            
            int k = 0;
            pstmt = conn.prepareStatement(sql);
            
            if (search_option.length() > 0 && search_data.length() > 0) {
                if (search_option.equals("product_name") || search_option.equals("product_description")) {
                    pstmt.setString(++k, '%' + search_data + '%');
                } else if (search_option.equals("writer_subject_content")) {
                    pstmt.setString(++k, '%' + search_data + '%');
                    pstmt.setString(++k, '%' + search_data + '%');
                }
            }
            
            // System.out.println(sql);
            
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
    
    public ArrayList<ProductDTO> getSelectAll(int startRecord, int endRecord, String search_option, String search_data) {
        ArrayList<ProductDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select product_no, product_name, product_price, product_description, product_img, product_regi_date from product";
            basicSql += " where product_no > 0";
            
            if (search_option.length() > 0 && search_data.length() > 0) {
                if (search_option.equals("product_name") || search_option.equals("product_description")) {
                    basicSql += " and " + search_option + " like ?";
                } else if (search_option.equals("name_description")) {
                    basicSql += " and (product_name like ? or product_description like ?)";
                }
            }
            
            basicSql += " order by product_no desc";
            
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            
            int k = 0;
            pstmt = conn.prepareStatement(sql);
            
            if (search_option.length() > 0 && search_data.length() > 0) {
                if (search_option.equals("product_name") || search_option.equals("product_description")) {
                    pstmt.setString(++k, '%' + search_data + '%');
                } else if (search_option.equals("writer_subject_content")) {
                    pstmt.setString(++k, '%' + search_data + '%');
                    pstmt.setString(++k, '%' + search_data + '%');
                }
            }
            
            pstmt.setInt(++k, startRecord);
            pstmt.setInt(++k, endRecord);
            
            // System.out.println(sql);
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductDTO dto = new ProductDTO();
                dto.setProduct_no(rs.getInt("product_no"));
                dto.setProduct_name(rs.getString("product_name"));
                dto.setProduct_price(rs.getInt("product_price"));
                dto.setProduct_description(rs.getString("product_description"));
                dto.setProduct_img(rs.getString("product_img"));
                dto.setProduct_regi_date(rs.getTimestamp("product_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ProductDTO getSelect(int product_no) {
        ProductDTO dto = new ProductDTO();
        getConn();
        try {
            String sql = "";
            sql += "SELECT * FROM ";
            sql += "(";
            sql += "select p.*";
            sql += ", LAG(product_no) Over (order by product_no desc) product_pre_no";
            sql += ", LAG(product_name) Over (order by product_no desc) product_pre_name";
            sql += ", LEAD(product_no) Over (order by product_no desc) product_nxt_no";
            sql += ", LEAD(product_name) Over (order by product_no desc) product_nxt_name";
            sql += " from product p order by product_no desc";
            sql += ") WHERE product_no = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, product_no);
            
            // System.out.println(sql);
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto.setProduct_no(rs.getInt("product_no"));
                dto.setProduct_name(rs.getString("product_name"));
                dto.setProduct_price(rs.getInt("product_price"));
                dto.setProduct_description(rs.getString("product_description"));
                dto.setProduct_img(rs.getString("product_img"));
                dto.setProduct_regi_date(rs.getTimestamp("product_regi_date"));
                
                dto.setProduct_pre_no(rs.getInt("product_pre_no"));
                dto.setProduct_pre_name(rs.getString("product_pre_name"));
                dto.setProduct_nxt_no(rs.getInt("product_nxt_no"));
                dto.setProduct_nxt_name(rs.getString("product_nxt_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return dto;
    }
    
    public int setUpdate(ProductDTO dto) {
        int result = 0;
        getConn();
        try {
            String sql = "";
            sql += "update product set product_name = ?, product_price = ?, product_description = ?, product_img = ? where product_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getProduct_name());
            pstmt.setInt(2, dto.getProduct_price());
            pstmt.setString(3, dto.getProduct_description());
            pstmt.setString(4, dto.getProduct_img());
            pstmt.setInt(5, dto.getProduct_no());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public int setDelete(ProductDTO dto) {
        int result = 0;
        getConn();
        try {
            String sql = "";
            sql += "delete from product where product_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getProduct_no());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }

}
