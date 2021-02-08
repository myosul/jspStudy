package model.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DbExample;
import model.board.dto.BoardDTO;

public class BoardDAO {
    
    // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    String tableName01 = "board";
    String tableName02 = "board_comment";
    
    
    // Constructor
    public BoardDAO() {}
    
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int getMaxNum() {
        int result = 0;
        conn = getConn();
        try {
            String sql = "select nvl(max(board_num), 0) from" + tableName01;
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
    
    public int getMaxRefNo() {
        int result = 0;
        conn = getConn();
        try {
            String sql = "select nvl(max(board_ref_no), 0) from" + tableName01;
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
    
    public int getMaxNoticeNo(String board_tbl) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "select nvl(max(board_notic_no), 0) from" + tableName01 + "where board_tbl = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board_tbl);
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
    
    public int setInsert(BoardDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into board";
            sql += " (board_no, board_num, board_tbl, board_writer, board_subject, board_content, board_email, board_passwd, board_ref_no, board_step_no, board_level_no, board_parent_no, board_hit, board_ip, member_no, board_notice_no, board_secret, board_regi_date)";
            sql += " values";
            sql += " (seq_board.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getBoard_num());
            pstmt.setString(2, dto.getBoard_tbl());
            pstmt.setString(3, dto.getBoard_writer());
            pstmt.setString(4, dto.getBoard_subject());
            pstmt.setString(5, dto.getBoard_content());
            pstmt.setString(6, dto.getBoard_email());
            pstmt.setString(7, dto.getBoard_passwd());
            pstmt.setInt(8, dto.getBoard_ref_no());
            pstmt.setInt(9, dto.getBoard_step_no());
            pstmt.setInt(10, dto.getBoard_level_no());
            pstmt.setInt(11, dto.getBoard_parent_no());
            pstmt.setInt(12, dto.getBoard_hit());
            pstmt.setString(13, dto.getBoard_ip());
            pstmt.setInt(14, dto.getMember_no());
            pstmt.setInt(15, dto.getBoard_notice_no());
            pstmt.setString(16, dto.getBoard_secret());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }

}
