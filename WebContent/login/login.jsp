<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.dbwork.adminlist.*"%>

<%
	//자바 구문
	request.setCharacterEncoding("UTF-8");
	System.out.println("------login.jsp");

	String id = request.getParameter("id");
	//out.println(id);
	String pw = request.getParameter("pw");
	//out.println(pw);

	AdminListDAO dao = new AdminListDAO();

	String state = "기본";

	try {
		state =  dao.select(id, pw);
	} catch (Exception e) {
		e.getStackTrace();
	}
	System.out.println("state : ");
	System.out.println(state);

	switch (state) {
	case "기본":
		out.print("<script>alert('서버에서 로그인정보를 받아오지 못했습니다.'); location.href='../index.html'; </script>");
		break;

	case "활동":
		out.print("<script>alert('환영합니다  "
				+ id
				+ " 님!!!'); location.href='../cal/calendar_main.jsp';  </script>");
		break;

	case "중지":
		out.print("<script>alert('정지된 계정입니다.');  location.href='../index.html';   </script>");
		break;
	}
%>