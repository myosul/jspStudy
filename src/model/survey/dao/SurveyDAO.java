package model.survey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import model.survey.dto.SurveyDTO;

public class SurveyDAO {
    
    // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    // Constructor
    public SurveyDAO() {}
    
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int setInsert(SurveyDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into survey";
            sql += " (survey_no, survey_question, survey_answer1, survey_answer2, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date)";
            sql += " values";
            sql += " (seq_survey.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getSurvey_question());
            pstmt.setString(2, dto.getSurvey_answer1());
            pstmt.setString(3, dto.getSurvey_answer2());
            pstmt.setString(4, dto.getSurvey_answer3());
            pstmt.setString(5, dto.getSurvey_answer4());
            pstmt.setString(6, dto.getSurvey_answer5());
            pstmt.setString(7, dto.getSurvey_status());
            pstmt.setTimestamp(8, dto.getSurvey_start_date());
            pstmt.setTimestamp(9, dto.getSurvey_end_date());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public int getTotalRecord() {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from survey where survey_no > 0";
            
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
    
    public int getTotalRecord(String list_gubun) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from survey where survey_no > 0";
            if (list_gubun.equals("ing")) {
                sql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                sql += " and current_timestamp > survey_end_date";
            }
            
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
    
    public int getTotalRecord(String list_gubun, String search_option, String search_data) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from survey where survey_no > 0";
            if (list_gubun.equals("ing")) {
                sql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                sql += " and current_timestamp > survey_end_date";
            }
            if (search_option.length() > 0 && search_data.length() > 0) {
                sql += " and " + search_option + " like ?";
            }
            
            pstmt = conn.prepareStatement(sql);
            int k = 0;
            if (search_option.length() > 0 && search_data.length() > 0) {
                pstmt.setString(++k, "%" + search_data + "%");
            }
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
    
    public int getTotalRecord(String list_gubun, String search_option, String search_data, String search_date_s, String search_date_e) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from survey where survey_no > 0";
            if (list_gubun.equals("ing")) {
                sql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                sql += " and current_timestamp > survey_end_date";
            }
            if (search_option.length() > 0 && search_data.length() > 0) {
                sql += " and " + search_option + " like ?";
            }
            if (search_date_s.length() > 0 && search_date_e.length() > 0) {
                search_date_s = search_date_s + " 00:00:00.0";
                search_date_e = search_date_e + " 23:59:59.999999";
                // java.sql.Timestamp survey_start_date = java.sql.Timestamp.valueOf(search_date_s);
                // java.sql.Timestamp survey_end_date = java.sql.Timestamp.valueOf(search_date_e);
                sql += " and (survey_start_date >= to_timestamp(?) and survey_end_date <= to_timestamp(?))";
            }
            
            pstmt = conn.prepareStatement(sql);
            int k = 0;
            if (search_option.length() > 0 && search_data.length() > 0) {
                pstmt.setString(++k, "%" + search_data + "%");
            }
            if (search_date_s.length() > 0 && search_date_e.length() > 0) {
                pstmt.setString(++k, search_date_s);
                pstmt.setString(++k, search_date_e);
            }
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
    
    public int getTotalRecord(String list_gubun, String search_option, String search_data, String search_date_s, String search_date_e, String search_date_check) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from survey where survey_no > 0";
            if (list_gubun.equals("ing")) {
                sql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                sql += " and current_timestamp > survey_end_date";
            }
            if (search_option.length() > 0 && search_data.length() > 0) {
                sql += " and " + search_option + " like ?";
            }
            if (search_date_check.equals("O") && search_date_s.length() > 0 && search_date_e.length() > 0) {
                search_date_s = search_date_s + " 00:00:00.0";
                search_date_e = search_date_e + " 23:59:59.999999";
                // java.sql.Timestamp survey_start_date = java.sql.Timestamp.valueOf(search_date_s);
                // java.sql.Timestamp survey_end_date = java.sql.Timestamp.valueOf(search_date_e);
                sql += " and (survey_start_date >= to_timestamp(?) and survey_end_date <= to_timestamp(?))";
            }
            
            pstmt = conn.prepareStatement(sql);
            int k = 0;
            if (search_option.length() > 0 && search_data.length() > 0) {
                pstmt.setString(++k, "%" + search_data + "%");
            }
            if (search_date_check.equals("O") && search_date_s.length() > 0 && search_date_e.length() > 0) {
                pstmt.setString(++k, search_date_s);
                pstmt.setString(++k, search_date_e);
            }
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
    
    public ArrayList<SurveyDTO> getSelectAll(int startRecord, int endRecord) {
        ArrayList<SurveyDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select survey_no, survey_question, survey_answer1, survey_answer2, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date from survey";
            basicSql += " where survey_no > 0";
            basicSql += " order by survey_no desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startRecord);
            pstmt.setInt(2, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SurveyDTO dto = new SurveyDTO();
                dto.setSurvey_no(rs.getInt("survey_no"));
                dto.setSurvey_question(rs.getString("survey_question"));
                dto.setSurvey_answer1(rs.getString("survey_answer1"));
                dto.setSurvey_answer2(rs.getString("survey_answer2"));
                dto.setSurvey_answer3(rs.getString("survey_answer3"));
                dto.setSurvey_answer4(rs.getString("survey_answer4"));
                dto.setSurvey_answer5(rs.getString("survey_answer5"));
                dto.setSurvey_status(rs.getString("survey_status"));
                dto.setSurvey_start_date(rs.getTimestamp("survey_start_date"));
                dto.setSurvey_end_date(rs.getTimestamp("survey_end_date"));
                dto.setSurvey_regi_date(rs.getTimestamp("survey_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ArrayList<SurveyDTO> getSelectAll(int startRecord, int endRecord, String list_gubun) {
        ArrayList<SurveyDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select survey_no, survey_question, survey_answer1, survey_answer2, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date from survey";
            basicSql += " where survey_no > 0";
            if (list_gubun.equals("ing")) {
                basicSql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                basicSql += " and current_timestamp > survey_end_date";
            }
            basicSql += " order by survey_no desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startRecord);
            pstmt.setInt(2, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SurveyDTO dto = new SurveyDTO();
                dto.setSurvey_no(rs.getInt("survey_no"));
                dto.setSurvey_question(rs.getString("survey_question"));
                dto.setSurvey_answer1(rs.getString("survey_answer1"));
                dto.setSurvey_answer2(rs.getString("survey_answer2"));
                dto.setSurvey_answer3(rs.getString("survey_answer3"));
                dto.setSurvey_answer4(rs.getString("survey_answer4"));
                dto.setSurvey_answer5(rs.getString("survey_answer5"));
                dto.setSurvey_status(rs.getString("survey_status"));
                dto.setSurvey_start_date(rs.getTimestamp("survey_start_date"));
                dto.setSurvey_end_date(rs.getTimestamp("survey_end_date"));
                dto.setSurvey_regi_date(rs.getTimestamp("survey_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ArrayList<SurveyDTO> getSelectAll(int startRecord, int endRecord, String list_gubun, String search_option, String search_data) {
        ArrayList<SurveyDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select survey_no, survey_question, survey_answer1, survey_answer2, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date from survey";
            basicSql += " where survey_no > 0";
            if (list_gubun.equals("ing")) {
                basicSql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                basicSql += " and current_timestamp > survey_end_date";
            }
            if (search_option.length() > 0 && search_data.length() > 0) {
                basicSql += " and " + search_option + " like ?";
            }
            basicSql += " order by survey_no desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            
            pstmt = conn.prepareStatement(sql);
            int k = 0;
            if (search_option.length() > 0 && search_data.length() > 0) {
                pstmt.setString(++k, "%" + search_data + "%");
            }
            pstmt.setInt(++k, startRecord);
            pstmt.setInt(++k, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SurveyDTO dto = new SurveyDTO();
                dto.setSurvey_no(rs.getInt("survey_no"));
                dto.setSurvey_question(rs.getString("survey_question"));
                dto.setSurvey_answer1(rs.getString("survey_answer1"));
                dto.setSurvey_answer2(rs.getString("survey_answer2"));
                dto.setSurvey_answer3(rs.getString("survey_answer3"));
                dto.setSurvey_answer4(rs.getString("survey_answer4"));
                dto.setSurvey_answer5(rs.getString("survey_answer5"));
                dto.setSurvey_status(rs.getString("survey_status"));
                dto.setSurvey_start_date(rs.getTimestamp("survey_start_date"));
                dto.setSurvey_end_date(rs.getTimestamp("survey_end_date"));
                dto.setSurvey_regi_date(rs.getTimestamp("survey_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ArrayList<SurveyDTO> getSelectAll(int startRecord, int endRecord, String list_gubun, String search_option, String search_data, String search_date_s, String search_date_e) {
        ArrayList<SurveyDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select survey_no, survey_question, survey_answer1, survey_answer2, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date from survey";
            basicSql += " where survey_no > 0";
            if (list_gubun.equals("ing")) {
                basicSql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                basicSql += " and current_timestamp > survey_end_date";
            }
            if (search_option.length() > 0 && search_data.length() > 0) {
                basicSql += " and " + search_option + " like ?";
            }
            if (search_date_s.length() > 0 && search_date_e.length() > 0) {
                search_date_s = search_date_s + " 00:00:00.0";
                search_date_e = search_date_e + " 23:59:59.999999";
                // java.sql.Timestamp survey_start_date = java.sql.Timestamp.valueOf(search_date_s);
                // java.sql.Timestamp survey_end_date = java.sql.Timestamp.valueOf(search_date_e);
                basicSql += " and (survey_start_date >= to_timestamp(?) and survey_end_date <= to_timestamp(?))";
            }
            basicSql += " order by survey_no desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            
            pstmt = conn.prepareStatement(sql);
            int k = 0;
            if (search_option.length() > 0 && search_data.length() > 0) {
                pstmt.setString(++k, "%" + search_data + "%");
            }
            if (search_date_s.length() > 0 && search_date_e.length() > 0) {
                pstmt.setString(++k, search_date_s);
                pstmt.setString(++k, search_date_e);
            }
            pstmt.setInt(++k, startRecord);
            pstmt.setInt(++k, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SurveyDTO dto = new SurveyDTO();
                dto.setSurvey_no(rs.getInt("survey_no"));
                dto.setSurvey_question(rs.getString("survey_question"));
                dto.setSurvey_answer1(rs.getString("survey_answer1"));
                dto.setSurvey_answer2(rs.getString("survey_answer2"));
                dto.setSurvey_answer3(rs.getString("survey_answer3"));
                dto.setSurvey_answer4(rs.getString("survey_answer4"));
                dto.setSurvey_answer5(rs.getString("survey_answer5"));
                dto.setSurvey_status(rs.getString("survey_status"));
                dto.setSurvey_start_date(rs.getTimestamp("survey_start_date"));
                dto.setSurvey_end_date(rs.getTimestamp("survey_end_date"));
                dto.setSurvey_regi_date(rs.getTimestamp("survey_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ArrayList<SurveyDTO> getSelectAll(int startRecord, int endRecord, String list_gubun, String search_option, String search_data, String search_date_s, String search_date_e, String search_date_check) {
        ArrayList<SurveyDTO> list = new ArrayList<>();
        getConn();
        try {
            String subQuery = "";
            subQuery += "(select count(*) from survey_answer where survey_no = survey.survey_no) survey_counter";
            String basicSql = "";
            basicSql += "select survey_no, survey_question, survey_answer1, survey_answer2, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date";
            basicSql += ", " + subQuery;
            basicSql += " from survey";
            basicSql += " where survey_no > 0";
            if (list_gubun.equals("ing")) {
                basicSql += " and current_timestamp between survey_start_date and survey_end_date";
            } else if (list_gubun.equals("end")) {
                basicSql += " and current_timestamp > survey_end_date";
            }
            if (search_option.length() > 0 && search_data.length() > 0) {
                basicSql += " and " + search_option + " like ?";
            }
            if (search_date_check.equals("O") && search_date_s.length() > 0 && search_date_e.length() > 0) {
                search_date_s = search_date_s + " 00:00:00.0";
                search_date_e = search_date_e + " 23:59:59.999999";
                // java.sql.Timestamp survey_start_date = java.sql.Timestamp.valueOf(search_date_s);
                // java.sql.Timestamp survey_end_date = java.sql.Timestamp.valueOf(search_date_e);
                basicSql += " and (survey_start_date >= to_timestamp(?) and survey_end_date <= to_timestamp(?))";
            }
            basicSql += " order by survey_no desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            
            pstmt = conn.prepareStatement(sql);
            int k = 0;
            if (search_option.length() > 0 && search_data.length() > 0) {
                pstmt.setString(++k, "%" + search_data + "%");
            }
            if (search_date_check.equals("O") && search_date_s.length() > 0 && search_date_e.length() > 0) {
                pstmt.setString(++k, search_date_s);
                pstmt.setString(++k, search_date_e);
            }
            pstmt.setInt(++k, startRecord);
            pstmt.setInt(++k, endRecord);
            
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SurveyDTO dto = new SurveyDTO();
                dto.setSurvey_no(rs.getInt("survey_no"));
                dto.setSurvey_question(rs.getString("survey_question"));
                dto.setSurvey_answer1(rs.getString("survey_answer1"));
                dto.setSurvey_answer2(rs.getString("survey_answer2"));
                dto.setSurvey_answer3(rs.getString("survey_answer3"));
                dto.setSurvey_answer4(rs.getString("survey_answer4"));
                dto.setSurvey_answer5(rs.getString("survey_answer5"));
                dto.setSurvey_status(rs.getString("survey_status"));
                dto.setSurvey_start_date(rs.getTimestamp("survey_start_date"));
                dto.setSurvey_end_date(rs.getTimestamp("survey_end_date"));
                dto.setSurvey_regi_date(rs.getTimestamp("survey_regi_date"));
                dto.setSurvey_counter(rs.getInt("survey_counter"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public SurveyDTO getSelect(int survey_no) {
        SurveyDTO dto = new SurveyDTO();
        getConn();
        try {
            String sql = "select survey_no, survey_question, survey_answer1, survey_answer2, survey_answer3, survey_answer4, survey_answer5, survey_status, survey_start_date, survey_end_date, survey_regi_date from survey";
            sql += " where survey_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, survey_no);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto.setSurvey_no(rs.getInt("survey_no"));
                dto.setSurvey_question(rs.getString("survey_question"));
                dto.setSurvey_answer1(rs.getString("survey_answer1"));
                dto.setSurvey_answer2(rs.getString("survey_answer2"));
                dto.setSurvey_answer3(rs.getString("survey_answer3"));
                dto.setSurvey_answer4(rs.getString("survey_answer4"));
                dto.setSurvey_answer5(rs.getString("survey_answer5"));
                dto.setSurvey_status(rs.getString("survey_status"));
                dto.setSurvey_start_date(rs.getTimestamp("survey_start_date"));
                dto.setSurvey_end_date(rs.getTimestamp("survey_end_date"));
                dto.setSurvey_regi_date(rs.getTimestamp("survey_regi_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return dto;
    }
    
    public int setUpdate(SurveyDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "update survey set";
            sql += " survey_question = ?, survey_answer1 = ?, survey_answer2 = ?, survey_answer3 = ?, survey_answer4 = ?, survey_answer5 = ?, survey_status = ?, survey_start_date = ?, survey_end_date = ?";
            sql += " where survey_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getSurvey_question());
            pstmt.setString(2, dto.getSurvey_answer1());
            pstmt.setString(3, dto.getSurvey_answer2());
            pstmt.setString(4, dto.getSurvey_answer3());
            pstmt.setString(5, dto.getSurvey_answer4());
            pstmt.setString(6, dto.getSurvey_answer5());
            pstmt.setString(7, dto.getSurvey_status());
            pstmt.setTimestamp(8, dto.getSurvey_start_date());
            pstmt.setTimestamp(9, dto.getSurvey_end_date());
            pstmt.setInt(10, dto.getSurvey_no());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }

}
