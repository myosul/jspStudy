package model.survey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DbExample;
import model.survey.dto.SurveyAnswerDTO;

public class SurveyAnswerDAO {
    
 // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    // Constructor
    public SurveyAnswerDAO() {}
    
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int setInsert(SurveyAnswerDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into survey_answer";
            sql += " (survey_answer_no, survey_no, survey_answer, survey_answer_regi_date)";
            sql += " values";
            sql += " (seq_survey_answer.nextval, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getSurvey_no());
            pstmt.setInt(2, dto.getSurvey_answer());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public int getTotalRecord(int survey_no) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from survey_answer where survey_answer_no > 0";
            sql += " and survey_no = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, survey_no);
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
