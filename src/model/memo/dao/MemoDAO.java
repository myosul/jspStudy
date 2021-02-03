package model.memo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import model.memo.dto.MemoDTO;

public class MemoDAO {
    
    // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    // Constructor
    public MemoDAO() {}
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int setInsert(MemoDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into memo";
            sql += " (memo_no, memo_writer_no, memo_subject, memo_content, memo_modi_date, memo_regi_date)";
            sql += " values";
            sql += " (seq_member.nextval, ?, ?, ?, current_timestamp, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getMemo_writer_no());
            pstmt.setString(2, dto.getMemo_subject());
            pstmt.setString(3, dto.getMemo_content());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public ArrayList<MemoDTO> getSelectAll(int memo_writer_no) {
        ArrayList<MemoDTO> list = new ArrayList<>();
        getConn();
        try {
            String sql = "";
            sql += "select memo_no, memo_writer_no, memo_subject, memo_content, memo_modi_date, memo_regi_date from memo where memo_no > 0";
            sql += " and memo_writer_no = ?";
            sql += " order by memo_regi_date desc";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memo_writer_no);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MemoDTO dto = new MemoDTO();
                dto.setMemo_no(rs.getInt("memo_no"));
                dto.setMemo_writer_no(rs.getInt("memo_writer_no"));
                dto.setMemo_subject(rs.getString("memo_subject"));
                dto.setMemo_content(rs.getString("memo_content"));
                dto.setMemo_modi_date(rs.getTimestamp("memo_modi_date"));
                dto.setMemo_regi_date(rs.getTimestamp("memo_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ArrayList<MemoDTO> getSelectAll(int memo_writer_no, int startRecord, int endRecord) {
        ArrayList<MemoDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select memo_no, memo_writer_no, memo_subject, memo_content, memo_modi_date, memo_regi_date from memo where memo_no > 0";
            basicSql += " and memo_writer_no = ?";
            basicSql += " order by memo_regi_date desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memo_writer_no);
            pstmt.setInt(2, startRecord);
            pstmt.setInt(3, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MemoDTO dto = new MemoDTO();
                dto.setMemo_no(rs.getInt("memo_no"));
                dto.setMemo_writer_no(rs.getInt("memo_writer_no"));
                dto.setMemo_subject(rs.getString("memo_subject"));
                dto.setMemo_content(rs.getString("memo_content"));
                dto.setMemo_modi_date(rs.getTimestamp("memo_modi_date"));
                dto.setMemo_regi_date(rs.getTimestamp("memo_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public int getTotalRecord(int memo_writer_no) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from memo where memo_no > 0";
            sql += " and memo_writer_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, memo_writer_no);
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
