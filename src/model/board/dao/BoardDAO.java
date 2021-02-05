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
    
    public int setInsert(BoardDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into board";
            sql += " (board_no, board_num, board_tbl, board_writer, board_subject, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date)";
            sql += " values";
            sql += " (seq_board.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(3, dto.getBoard_writer());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }

}
