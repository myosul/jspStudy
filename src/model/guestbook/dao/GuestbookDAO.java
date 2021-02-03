package model.guestbook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import model.guestbook.dto.GuestbookDTO;

public class GuestbookDAO {
    
    // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    // Constructor
    public GuestbookDAO() {}
    
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int setInsert(GuestbookDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into guestbook";
            sql += " (guestbook_no, guestbook_writer_name, guestbook_writer_email, guestbook_passwd, guestbook_content, guestbook_regi_date)";
            sql += " values";
            sql += " (seq_guestbook.nextval, ?, ?, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getGuestbook_writer_name());
            pstmt.setString(2, dto.getGuestbook_writer_email());
            pstmt.setString(3, dto.getGuestbook_passwd());
            pstmt.setString(4, dto.getGuestbook_content());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public ArrayList<GuestbookDTO> getSelectAll() {
        ArrayList<GuestbookDTO> list = new ArrayList<>();
        getConn();
        try {
            String sql = "";
            sql += "select guestbook_no, guestbook_writer_name, guestbook_writer_email, guestbook_passwd, guestbook_content, guestbook_regi_date from guestbook where guestbook_no > 0";
            sql += " order by guestbook_regi_date desc";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                GuestbookDTO dto = new GuestbookDTO();
                dto.setGuestbook_no(rs.getInt("guestbook_no"));
                dto.setGuestbook_writer_name(rs.getString("guestbook_writer_name"));
                dto.setGuestbook_writer_email(rs.getString("guestbook_writer_email"));
                dto.setGuestbook_passwd(rs.getString("guestbook_passwd"));
                dto.setGuestbook_content(rs.getString("guestbook_content"));
                dto.setGuestbook_regi_date(rs.getString("guestbook_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ArrayList<GuestbookDTO> getSelectAll(int startRecord, int endRecord) {
        ArrayList<GuestbookDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select guestbook_no, guestbook_writer_name, guestbook_writer_email, guestbook_passwd, guestbook_content, guestbook_regi_date from guestbook where guestbook_no > 0";
            basicSql += " order by guestbook_regi_date desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startRecord);
            pstmt.setInt(2, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                GuestbookDTO dto = new GuestbookDTO();
                dto.setGuestbook_no(rs.getInt("guestbook_no"));
                dto.setGuestbook_writer_name(rs.getString("guestbook_writer_name"));
                dto.setGuestbook_writer_email(rs.getString("guestbook_writer_email"));
                dto.setGuestbook_passwd(rs.getString("guestbook_passwd"));
                dto.setGuestbook_content(rs.getString("guestbook_content"));
                dto.setGuestbook_regi_date(rs.getString("guestbook_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public int getTotalRecord() {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from guestbook where guestbook_no > 0";
            pstmt = conn.prepareStatement(sql);
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

}
