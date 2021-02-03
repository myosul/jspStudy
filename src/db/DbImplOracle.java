package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbImplOracle implements Db {

    // Field
    private Connection conn;
    
    
    // Constructor
    public DbImplOracle() {}
    
    
    // Method
    @Override
    public Connection dbConn() {
        try {
            String driver = "oracle.jdbc.driver.OracleDriver";
            String dbUrl = "jdbc:oracle:thin:@localhost:1521/xe";
            String dbId = "jspStudy";
            String dbPasswd = "1234";
            
            Class.forName(driver);
            conn = DriverManager.getConnection(dbUrl, dbId, dbPasswd);
            System.out.println("-- 오라클 접속 성공 --");
        } catch (Exception e) {
            System.out.println("-- 오라클 접속 실패 --");
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void dbConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) { rs.close(); }
            if (pstmt != null) { pstmt.close(); }
            if (conn != null) { conn.close(); }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("-- 오라클 접속 끊기 --");
        }
    }

}
