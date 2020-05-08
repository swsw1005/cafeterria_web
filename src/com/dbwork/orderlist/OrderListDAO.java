package com.dbwork.orderlist;

import java.sql.*;
import java.util.*;
import com.dbwork.info.DBInfo;
import com.util.cal_info.Cal_info;

public class OrderListDAO {

    // DB
    Connection con = null;
    // Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    // 출력
    OrderListDTO temp_dto;
    List<OrderListDTO> temp_list;
    Hashtable<Integer, List<OrderListDTO>> temp_table;

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    public void select(int yyyymmdd) throws Exception {
        System.out.println("--- select " + yyyymmdd);
        String yyyy_mm_dd = yyyymmdd + "";
        yyyy_mm_dd = yyyy_mm_dd.substring(0, 4) + "-" + yyyy_mm_dd.substring(4, 6) + "-" + yyyy_mm_dd.substring(6, 8);
        select(yyyy_mm_dd);
    }

    public void select(String yyyy_mm_dd) throws Exception {
        // select 1일 주문내역
        temp_list = new ArrayList<>();
        System.out.println("--- select " + yyyy_mm_dd);
        try {
            // 1+2
            con = DBInfo.getConnection();
            // 3. 실행쿼리 문자열
            String sql = "select * from orderlist where orderdate=?";
            // 4. 실행객체 데이터세팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, yyyy_mm_dd);
            // 5. 실행 execute
            rs = pstmt.executeQuery();
            // 6. 결과표시(ResultSet) + list.add
            if (rs != null) {
                while (rs.next()) {
                    OrderListDTO temp_dto = new OrderListDTO();
                    temp_dto.setOrderdate(yyyy_mm_dd);
                    temp_dto.setNo(rs.getInt("no"));
                    temp_dto.setName(rs.getString("name"));
                    temp_dto.setCount(rs.getInt("count"));
                    temp_list.add(temp_dto);
                    // System.out.println("--- " + temp_dto.getNo() + " --- " + temp_dto.getName());
                } // while end
            } // if end
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("게시판 리스트 처리하는 중 오류가 발생되었습니보벳따우");
        } finally {
            DBInfo.close(con, pstmt, rs);
        }
    }

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    // select() ----- ----- -----
    public void select_all(int yyyy, int mm) {
        String y_ = ((yyyy + 10000) + "").substring(1, 5);
        String m_ = ((mm + 10000) + "").substring(3, 5);
        String temp = y_ + m_;
        select_all(temp);
    }

    // select() ----- ----- -----
    public void select_all(String yyyy_mm_dd) {
        int yyyymmdd = Integer.parseInt(yyyy_mm_dd);
        select_all(yyyymmdd);
    }

    // select() ----- ----- -----
    public void select_all(int yyyymm) {

        String temp1 = yyyymm + "";
        temp1 = temp1.substring(0, 6);
        yyyymm = Integer.parseInt(temp1);
        int yyyymm01 = yyyymm * 100 + 01;

        // select 1달 주문내역
        temp_table = new Hashtable<>();

        Cal_info ci = new Cal_info(yyyymm);

        for (int i = 0; i < ci.getMax_day(); i++) {
            try {
                select(yyyymm01 + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp_table.put(i, temp_list);
        }

    }// select() end

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    // select() ----- ----- -----
    public Hashtable<Integer, Integer> select_count(int yyyymm) {

        // 출력용 객체
        Hashtable<Integer, Integer> temp_count_table = new Hashtable<>();
        // select 1달 주문 count
        int yyyymm01 = yyyymm * 100 + 01;
        Cal_info ci = new Cal_info(yyyymm);

        for (int i = 0; i < ci.getMax_day(); i++) {
            try {
                select(yyyymm01 + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            temp_count_table.put(i, temp_list.size());
        }

        return temp_count_table;
    }// select() end

    // ----- ----- -----

    // Getter
    public int getOrderCount(int index) {

        int cnt = temp_table.get(index).size();
        int temp = 0;
        for (int i = 0; i < cnt; i++) {
            temp += temp_table.get(index).get(i).getCount();
        }
        return temp;
    }

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    // insert() ----- ----- -----
    public int insert(OrderListDTO dto) throws Exception {
        // 출력 데이터 선언
        Integer result = 0;
        ///
        try {
            // 1+2
            con = DBInfo.getConnection();
            // 3. 실행 쿼리 - 문자열
            String sql = "insert into orderlist(no, orderdate, count, name) "
                    + " values((select nvl(max(no), 0) + 1 from orderlist), ?, ?, ?)";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getOrderdate());
            pstmt.setInt(2, dto.getCount());
            pstmt.setString(3, dto.getName());
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1) {
                System.out.println("OrdeListDAO.insert():주문 성공!");
            }
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("게시판 리스트 처리하는 중 오류가 발생되었습니보벳따우");
        } finally {
            DBInfo.close(con, pstmt, rs);
        }
        return result;
    } // insert() end

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    // update() ----- ----- -----
    public int update(OrderListDTO dto) throws Exception {
        // 출력 데이터 선언
        Integer result = 0;
        ///
        try {
            // 1+2
            con = DBInfo.getConnection();
            // 3. 실행 쿼리 - 문자열
            String sql = "update orderlist set name=?, count=? where no = ? ";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, dto.getName());
            pstmt.setInt(2, dto.getCount());
            pstmt.setInt(4, dto.getNo());
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1) {
                System.out.println("OrdeListDAO.update():수정 성공!");
            }
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("게시판 리스트 처리하는 중 오류가 발생되었습니보벳따우");
        } finally {
            DBInfo.close(con, pstmt, rs);
        }
        return result;
    } // update() end

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

    // delete() ----- ----- -----
    public Integer delete(int no) throws Exception {
        // dto 넘어왓는지 확인용
        System.out.println("delete OrderList" + no);
        // 출력 데이터 선언
        Integer result = 0;

        try {
            // 1+2
            con = DBInfo.getConnection();
            // 3. 실행 쿼리 - 문자열
            String sql = "delete from orderlist where no = ?";
            // 4. 실행객체, 데이터 셋팅
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, no);
            // 5. 실행
            // select -> executeQuery(), insert/update/delete->executeUpdate()
            result = pstmt.executeUpdate();
            // 6. 결과표시
            if (result == 1)
                System.out.println("OrderListDAO.delete() : 메뉴삭제 성공!");
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("게시판 리스트 처리하는 중 오류가 발생되었습니보벳따우");
        } finally {
            DBInfo.close(con, pstmt, rs);
        }
        return result;
    }

    // ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- -----

}