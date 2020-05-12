<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="com.util.cal_info.*"%>
<%@ page import="com.dbwork.orderlist.*"%>
<%@ page import="com.dbwork.menu.*"%>

<%
	request.setCharacterEncoding("UTF-8");
	System.out.println("------cal.jsp");
%>

<%
	int y_ = Integer.parseInt(request.getParameter("y"));
	int m_ = Integer.parseInt(request.getParameter("m"));

	Cal_info ci = new Cal_info(y_, m_);
	int c1 = ci.getVoid_day();
	int c2 = ci.getMax_day();
	int c3 = 42 - (c1 + c2);
	
	MenuDAO dao = new MenuDAO();
	dao.select_month(y_, m_);

	System.out.println("");
	System.out.println("");

	OrderListDAO orderDAO = new OrderListDAO();
	orderDAO.select_all(y_, m_);
%>

<%
	for (int i = 0; i < c1; i++) {
%>
<div class="void, aa">dev c1</div>
<%
	} //for c1 end
%>
<!-- ///.......................... -->
<%
	for (int i = 0; i < c2; i++) {
%>
<!-- date.aa -->
<div class="date, aa">
	<div class="date_head">
		<div class="date_num">
			<%=i + 1%>
		</div>
		<div class="date_cnt">
			<%=orderDAO.getOrderCount(i)%>
		</div>
	</div>

	<%
		for (int j = 1; j <= 5; j++) {
	%>
	<div class="date_menu"><%=dao.getMenu(y_, m_, i + 1, j - 1)%></div>
	<%
		} //for j (inside for c2)(menu)end
	%>

</div>
<!-- ............. -->
<%
	} //for c2 end
%>
<!-- ///.......................... -->
<%
	for (int i = 0; i < c3; i++) {
%>
<div class="void, aa">dev c3</div>
<%
	} //for c3 end
%>
<!-- ///.......................... -->

