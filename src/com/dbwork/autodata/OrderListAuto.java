package com.dbwork.autodata;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import com.dbwork.info.DBInfo;
import com.dbwork.orderlist.*;
import com.swsw.util.Rand;

public class OrderListAuto {

    // DB
    Connection con = null;
    // Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    // 이름 담는
    private List<String> name_list = null;

    public OrderListAuto(OrderListAutoBuild ab) {

        // 1. 일단 이름 누구누구? + cnt
        try {
            select_name();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int max_person = name_list.size();
        System.out.print("max_person\t");
        System.out.println(max_person);

        try {
            OrderListDAO dao = new OrderListDAO();

            System.out.println(ab.getHow_many_days() + " 일 동안 입력");

            for (int i = 0; i < ab.getHow_many_days(); i++) {

                try {
                    System.out.println(i + "회 입력");
                    // 오늘 주문할 개수
                    int rand_order_cnt = Rand.between(ab.getMin_order_per_day(), ab.getMax_order_per_day());
                    // 오늘 날짜 yyyy-mm-dd
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar temp = Calendar.getInstance();
                    temp.set(ab.getYear(), ab.getMonth() - 1, ab.getDay() + i);
                    String yyyy_mm_dd = sdf.format(temp.getTime());
                    System.out.println(yyyy_mm_dd + "  " + rand_order_cnt);

                    for (int j = 0; j < rand_order_cnt; j++) {
                        OrderListDTO dto = new OrderListDTO();

                        String name = name_list.get(Rand.between(0, max_person - 1));
                        int count = Rand.between(ab.getMin_count_per_order(), ab.getMax_count_per_order());
                        dto.setName(name);
                        dto.setCount(count);
                        dto.setOrderdate(yyyy_mm_dd);

                        dao.insert(dto);
                    } // for rand_order_cnt() end
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } // for How_many_days() end

        } catch (Exception e) {
            e.getStackTrace();
        }

    }// cons end

    private void select_name() throws Exception {
        // select 1일 주문내역
        name_list = new ArrayList<>();
        try {
            // 1+2
            con = DBInfo.getConnection();
            // 3. 실행쿼리 문자열
            String sql = "select name from orderlist group by name";
            // 4. 실행객체 데이터세팅
            pstmt = con.prepareStatement(sql);
            // 5. 실행 execute
            rs = pstmt.executeQuery();
            // 6. 결과표시(ResultSet) + list.add
            if (rs != null) {
                while (rs.next()) {
                    name_list.add(rs.getString(1));
                } // while end
            } // if end
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("게시판 리스트 처리하는 중 오류가 발생되었습니보벳따우");
        } finally {
            DBInfo.close(con, pstmt, rs);
        }
    }

    public static void main(String[] args) {

        int a1 = 2020;
        int a2 = 8;
        int a3 = 17;
        int a4 = 100;
        int[] b1 = { 1, 4 };// 하루 주문
        int[] b2 = { 1, 5 };

        OrderListAutoBuild ab = new OrderListAutoBuild();
        ab.setYear(a1);
        ab.setMonth(a2);
        ab.setDay(a3);
        ab.setHow_many_days(a4);
        ab.setOrderPerDay(b1);
        ab.setCountPerOrder(b2);

        OrderListAuto oa = new OrderListAuto(ab);
    }

}