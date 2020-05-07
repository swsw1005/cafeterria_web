package com.dbwork.login;

import java.sql.*;
import com.dbwork.info.DBInfo;

public class AdminListDAO {

    // DB
    Connection con = null;
    // Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    // 출력 데이터
    AdminListDTO temp_dto;

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    // 1,2
    public void connect() {

        System.out.println("---" + getClass().getName());
        System.out.println("--- --- connect()");

        try {
            // 1. 드라이버 확인
            Class.forName(DBInfo.DRIVER);
            // 2. 연결conn
            con = DriverManager.getConnection(DBInfo.URL, DBInfo.UID, DBInfo.UPW);
        } catch (Exception e3) {
            System.out.println("connect() fail");
            e3.getStackTrace();
        } // try-catch end
    } // connect() end

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    public void select(String id) {

        temp_dto = new AdminListDTO();

        try {
            // 1+2
            connect();
            // 3. 실행쿼리 문자열
            String sql = "select * from adminlist where id=?";
            // 4. 실행객체 데이터세팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            // 5. 실행 execute
            rs = pstmt.executeQuery();
            // 6. 결과표시(ResultSet) + list.add
            if (rs != null) {
                while (rs.next()) {
                    temp_dto.setId(rs.getString("id"));
                    temp_dto.setPw(rs.getString("pw"));
                    temp_dto.setStatus(rs.getString("status"));
                } // while end
            } // if end
        } catch (Exception ex) {
            ex.getStackTrace();
        } finally {
            try {
                // 7. 닫기
                if (con != null) {
                    con.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ex2) {
                ex2.getStackTrace();
            }
        } // finally end
    }// select() end

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    public int insert(AdminListDTO dto) {
        // dto 넘어왓는지 확인용
        System.out.println("insert AdminList" + dto.getId());
        // 출력 데이터 선언
        Integer result = 0;
        ///
        try {
            // 1+2
            connect();
            // 3. 실행 쿼리 - 문자열
            String sql = "insert into Adminlist (id, pw, state)   values( ?, ?, '대기')";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "id");
            pstmt.setString(2, "pw");
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1) {
                System.out.println("AdminListDAO.insert():가입 성공!");
            }
        } catch (Exception e) {
            // 예외처리
            e.printStackTrace();
        } finally {
            try {
                // 7. 닫기
                if (con != null)
                    con.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ///
        return result;
    } // insert() end

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    public int update(String id, String pw_before, String pw_after) {
        // dto 넘어왓는지 확인용
        System.out.println("update AdminList");
        // 출력 데이터 선언
        Integer result = 0;
        ///
        try {
            // 1+2
            connect();
            // 3. 실행 쿼리 - 문자열
            String sql = "update adminlist set pw =?  where id = ? and pw = ?";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, pw_before);
            pstmt.setString(2, id);
            pstmt.setString(3, pw_after);
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1) {
                System.out.println("MenuDAO.update():수정 성공!");
            }
        } catch (Exception e) {
            // 예외처리
            e.printStackTrace();
        } finally {
            try {
                // 7. 닫기
                if (con != null)
                    con.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ///
        return result;
    } // update() end

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    public int delete(String id_to_delete) {
        // dto 넘어왓는지 확인용
        System.out.println("delete admin" + id_to_delete);
        // 출력 데이터 선언
        Integer result = 0;

        try {
            // 1+2
            connect();
            // 3. 실행 쿼리 - 문자열
            String sql = "delete from adminlist where id = ?";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id_to_delete);
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1)
                System.out.println("AdminListDAO.delete() : 메뉴삭제 성공!");
        } catch (Exception e) {
            // 예외처리
            e.printStackTrace();
        } finally {
            try {
                // 7. 닫기
                if (con != null)
                    con.close();
                if (pstmt != null)
                    pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

}