package com;

import com.dbwork.menu.MenuDAO;

public class Test {

    public static void main(String[] args) {

        String temp = "<div class=\"date_menu1\"><%=dao.getMenu(y_, m_, i+1, 0)%></div>";

        System.out.println(temp);

        MenuDAO dao = new MenuDAO();

        System.out.println(dao.getMenu(2020, 4, 1, 0));

    }
}
