package model.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DbExample;
import model.member.dto.MemberDTO;

public class MemberDAO {
    
    // Field
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    // Constructor
    public MemberDAO() {}
    
    
    // Method
    public Connection getConn() {
        conn = DbExample.dbConn();
        return conn;
    }
    
    public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        DbExample.dbConnClose(rs, pstmt, conn);
    }
    
    public int setInsert(MemberDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "insert into member";
            sql += " (member_no, member_id, member_password, member_name, member_gender, member_born_year, member_postcode, member_address, member_detail_address, member_extra_address, member_regi_date)";
            sql += " values";
            sql += " (seq_member.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getMember_id());
            pstmt.setString(2, dto.getMember_password());
            pstmt.setString(3, dto.getMember_name());
            pstmt.setString(4, dto.getMember_gender());
            pstmt.setInt(5, dto.getMember_born_year());
            pstmt.setInt(6, dto.getMember_postcode());
            pstmt.setString(7, dto.getMember_address());
            pstmt.setString(8, dto.getMember_detail_address());
            pstmt.setString(9, dto.getMember_extra_address());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public MemberDTO getLogin(MemberDTO dto) {
        MemberDTO reusultDto = new MemberDTO();
        getConn();
        try {
            String sql = "select member_no, member_id, member_name, member_gender, member_born_year, member_postcode, member_address, member_detail_address, member_extra_address, member_regi_date from member";
            sql += " where member_id = ? and member_password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getMember_id());
            pstmt.setString(2, dto.getMember_password_check());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                reusultDto.setMember_no(rs.getInt("member_no"));
                reusultDto.setMember_id(rs.getString("member_id"));
                reusultDto.setMember_name(rs.getString("member_name"));
                reusultDto.setMember_gender(rs.getString("member_gender"));
                reusultDto.setMember_born_year(rs.getInt("member_born_year"));
                reusultDto.setMember_postcode(rs.getInt("member_postcode"));
                reusultDto.setMember_address(rs.getString("member_address"));
                reusultDto.setMember_detail_address(rs.getString("member_detail_address"));
                reusultDto.setMember_extra_address(rs.getString("member_extra_address"));
                reusultDto.setMember_regi_date(rs.getTimestamp("member_regi_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return reusultDto;
    }
    
    public ArrayList<MemberDTO> getSelectAll() {
        ArrayList<MemberDTO> list = new ArrayList<>();
        getConn();
        try {
            String sql = "";
            sql += "select member_no, member_id, member_name, member_gender, member_born_year, member_postcode, member_address, member_detail_address, member_extra_address, member_regi_date from member";
            sql += " where member_no > 0";
            sql += " order by member_no desc";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MemberDTO dto = new MemberDTO();
                dto.setMember_no(rs.getInt("member_no"));
                dto.setMember_id(rs.getString("member_id"));
                dto.setMember_name(rs.getString("member_name"));
                dto.setMember_gender(rs.getString("member_gender"));
                dto.setMember_born_year(rs.getInt("member_born_year"));
                dto.setMember_postcode(rs.getInt("member_postcode"));
                dto.setMember_address(rs.getString("member_address"));
                dto.setMember_detail_address(rs.getString("member_detail_address"));
                dto.setMember_extra_address(rs.getString("member_extra_address"));
                dto.setMember_regi_date(rs.getTimestamp("member_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public ArrayList<MemberDTO> getSelectAll(int startRecord, int endRecord) {
        ArrayList<MemberDTO> list = new ArrayList<>();
        getConn();
        try {
            String basicSql = "";
            basicSql += "select member_no, member_id, member_name, member_gender, member_born_year, member_postcode, member_address, member_detail_address, member_extra_address, member_regi_date from member";
            basicSql += " where member_no > 0";
            basicSql += " order by member_no desc";
            String sql = "";
            sql += "select * from (select A.*, Rownum Rnum from (";
            sql += basicSql;
            sql += ") A) where Rnum >= ? and Rnum <= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, startRecord);
            pstmt.setInt(2, endRecord);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MemberDTO dto = new MemberDTO();
                dto.setMember_no(rs.getInt("member_no"));
                dto.setMember_id(rs.getString("member_id"));
                dto.setMember_name(rs.getString("member_name"));
                dto.setMember_gender(rs.getString("member_gender"));
                dto.setMember_born_year(rs.getInt("member_born_year"));
                dto.setMember_postcode(rs.getInt("member_postcode"));
                dto.setMember_address(rs.getString("member_address"));
                dto.setMember_detail_address(rs.getString("member_detail_address"));
                dto.setMember_extra_address(rs.getString("member_extra_address"));
                dto.setMember_regi_date(rs.getTimestamp("member_regi_date"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return list;
    }
    
    public MemberDTO getSelect(int member_no) {
        MemberDTO dto = new MemberDTO();
        getConn();
        try {
            String sql = "select member_no, member_id, member_name, member_gender, member_born_year, member_postcode, member_address, member_detail_address, member_extra_address, member_regi_date from member";
            sql += " where member_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, member_no);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dto.setMember_no(rs.getInt("member_no"));
                dto.setMember_id(rs.getString("member_id"));
                dto.setMember_name(rs.getString("member_name"));
                dto.setMember_gender(rs.getString("member_gender"));
                dto.setMember_born_year(rs.getInt("member_born_year"));
                dto.setMember_postcode(rs.getInt("member_postcode"));
                dto.setMember_address(rs.getString("member_address"));
                dto.setMember_detail_address(rs.getString("member_detail_address"));
                dto.setMember_extra_address(rs.getString("member_extra_address"));
                dto.setMember_regi_date(rs.getTimestamp("member_regi_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return dto;
    }
    
    public int setUpdate(MemberDTO dto) {
        int result = 0;
        conn = getConn();
        try {
            String sql = "update member set ";
            sql += " member_name = ?, member_gender = ?, member_born_year = ?, member_postcode = ?, member_address = ?, member_detail_address = ?, member_extra_address = ?";
            sql += " where member_no = ? and member_password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getMember_name());
            pstmt.setString(2, dto.getMember_gender());
            pstmt.setInt(3, dto.getMember_born_year());
            pstmt.setInt(4, dto.getMember_postcode());
            pstmt.setString(5, dto.getMember_address());
            pstmt.setString(6, dto.getMember_detail_address());
            pstmt.setString(7, dto.getMember_extra_address());
            pstmt.setInt(8, dto.getMember_no());
            pstmt.setString(9, dto.getMember_password_check());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public int setDelete(MemberDTO dto) {
        int result = 0;
        getConn();
        try {
            String sql = "delete from member where member_no = ? and member_password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, dto.getMember_no());
            pstmt.setString(2, dto.getMember_password_check());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnClose(rs, pstmt, conn);
        }
        return result;
    }
    
    public int getIdCheck(String member_id) {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from member where member_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member_id);
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
    
    public int getTotalRecord() {
        int result = 0;
        getConn();
        try {
            String sql = "select count(*) count from member where member_no > 0";
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
