package com.dbwork.menu;

import java.sql.*;
import java.util.*;
import com.dbwork.info.DBInfo;
import com.util.cal_info.Cal_info;

public class MenuDAO {

    // DB
    Connection con = null;
    // Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    // 출력 데이터
    private MenuDTO temp_dto;
    private Hashtable<Integer, MenuDTO> menu_table;

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    // 1,2
    void connect() {

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

    /// select 1일 메뉴
    public void select(int yyyy, int mm, int dd) {

        String y_ = ((yyyy + 10000) + "").substring(1, 5);
        String m_ = ((mm + 10000) + "").substring(3, 5);
        String d_ = ((dd + 10000) + "").substring(3, 5);
        String yyyy_mm_dd = y_ + "-" + m_ + "-" + d_;
        System.out.println("---" + yyyy_mm_dd);
        select(yyyy_mm_dd);
    }

    /// select 1일 메뉴
    public void select(int yyyymmdd) {

        String yyyy_mm_dd = yyyymmdd + "";
        yyyy_mm_dd = yyyy_mm_dd.substring(0, 4) + "-" + yyyy_mm_dd.substring(4, 6) + "-" + yyyy_mm_dd.substring(6, 8);
        System.out.println("---" + yyyy_mm_dd);
        select(yyyy_mm_dd);
    }

    /// select 1일 메뉴
    public void select(String yyyy_mm_dd) {
        // select 1일 주문내역
        temp_dto = new MenuDTO();
        try {
            temp_dto.setOrderdate(yyyy_mm_dd);
            // 1+2
            connect();
            // 3. 실행쿼리 문자열
            String sql = "select * from menu where orderdate=?";
            // 4. 실행객체 데이터세팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, yyyy_mm_dd);
            // 5. 실행 execute
            rs = pstmt.executeQuery();
            // 6. 결과표시(ResultSet) + list.add
            if (rs != null) {
                while (rs.next()) {

                    List<String> temp_list = new ArrayList<>();

                    for (int i = 1; i <= 5; i++) {
                        try {
                            String temp = rs.getString("menu" + i).trim();
                            // System.out.println("--- temp : " + temp);
                            temp_list.add(temp);
                            // System.out.println(temp_list.size());
                        } catch (Exception e) {
                            System.out.println("temp_list.add failed");
                            temp_list.add("");
                        }
                    } // for end

                    temp_dto.setMenu(temp_list);
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

    public void select_month(int yyyymm_) {
        select_month(yyyymm_ + "");
    }

    public void select_month(int yyyy, int mm) {
        String y_ = ((yyyy + 10000) + "").substring(1, 5);
        String m_ = ((mm + 10000) + "").substring(3, 5);
        String temp = y_ + m_;
        select_month(temp);
    }

    public void select_month(String yyyy_mm_) {

        yyyy_mm_ = yyyy_mm_.substring(0, 6);
        int yyyymm01 = Integer.parseInt(yyyy_mm_) * 100 + 01;

        // select 1달 주문내역
        menu_table = new Hashtable<>();

        Cal_info ci = new Cal_info(yyyy_mm_);

        for (int i = 0; i < ci.getMax_day(); i++) {

            select(yyyymm01 + i);
            menu_table.put(i, temp_dto);
        }
        // return menu_table;
    } // select2() end

    // ----- ----- -----

    // Getter
    public String getMenu(int y, int m, int d, int num) {

        String temp = "";
        try {
            temp = menu_table.get(d - 1).getMenu().get(num);
        } catch (Exception e) {
            temp = "qqq";
        }
        return temp;
    }

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    public int insert(MenuDTO dto) {
        // 출력 데이터 선언
        Integer result = 0;
        ///
        try {
            // 1+2
            connect();
            // 3. 실행 쿼리 - 문자열
            String sql = "insert into menu(orderdate, menu1, menu2, menu3, menu4, menu5) "
                    + " values( ?, ?, ?, ?, ?, ?)";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getOrderdate());
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i + 1, dto.getMenu().get(i));
                System.out.println(dto.getMenu().get(i));
                // TODO : null처리??
            }
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1) {
                System.out.println("MenuDAO.insert():주문 성공!");
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

    public int update(MenuDTO dto) {
        // 출력 데이터 선언
        Integer result = 0;
        ///
        try {
            // 1+2
            connect();
            // 3. 실행 쿼리 - 문자열
            String sql = "update menu set menu1 =?, menu2 =?, menu3 =?, menu4 =?, menu5 =? where orderdate = ? ";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);

            for (int i = 0; i < 5; i++) {
                pstmt.setString(i + 1, dto.getMenu().get(i));
            }
            pstmt.setString(6, dto.getOrderdate());
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

    public int delete(int yyyymmdd) {

        String temp_yyyymmdd = yyyymmdd + "";
        temp_yyyymmdd = temp_yyyymmdd.substring(0, 4) + "-" + temp_yyyymmdd.substring(4, 6) + "-"
                + temp_yyyymmdd.substring(6, 8);
        return delete(temp_yyyymmdd);
    }

    public int delete(String orderdate) {
        // dto 넘어왓는지 확인용
        System.out.println("delete Menu" + orderdate);
        // 출력 데이터 선언
        Integer result = 0;

        try {
            // 1+2
            connect();
            // 3. 실행 쿼리 - 문자열
            String sql = "delete from menu where orderdate = ?";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, orderdate);
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1)
                System.out.println("MenuDAO.delete() : 메뉴삭제 성공!");
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