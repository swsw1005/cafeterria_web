package com.dbwork.info;

import java.sql.*;

public class DBInfo {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "JDBC:oracle:thin:@localhost:1521:xe";
    private static final String ID = "java00";
    private static final String PW = "12345";
    private static boolean checkDriver = false;
    // true : 드라이버 있다 false 드라이버 없다.

    static {
        try {
            /// 1. 드라이버 확인
            Class.forName(DRIVER);
            checkDriver = true;
        } catch (Exception e) {
            e.getStackTrace();
            checkDriver = false;
        }
    }

    public static final Connection getConnection() throws Exception {

        if (!checkDriver) {
            throw new Exception("드라이버 없음");
        }
        /// 2. 연결
        return DriverManager.getConnection(URL, ID, PW);
    } // getConnection() end .............................

    public static final void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws Exception {

        close(con, pstmt);
        if (rs != null) {
            rs.close();
        }
    }

    public static final void close(Connection con, PreparedStatement pstmt) throws Exception {
        if (con != null) {
            con.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
    }
} // class end