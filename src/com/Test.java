package com;

import com.dbwork.menu.MenuDAO;
import com.dbwork.orderlist.OrderListDAO;

public class Test {

    public static void main(String[] args) {

        OrderListDAO dao = new OrderListDAO();

        try {
            int iii = dao.select_count(2020, 4, 1);
            System.out.println(iii);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
