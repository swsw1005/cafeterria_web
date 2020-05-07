<%@page import="com.dbwork.orderlist.OrderListDAO"%>
<%@page import="com.dbwork.menu.MenuDAO"%>
<%@page import="com.util.cal_info.Cal_info"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.util.cal_info.*"%>

<%!//변수 선언%>

<%
	request.setCharacterEncoding("UTF-8");
	System.out.println("------cal.jsp");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<title>cal.jsp</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<style>
* {
	margin: 0px;
	padding: 0px;
	/* font-size: large; */
}

#calframe {
	max-width: 1000px;
	background: skyblue;
}

#cal_head {
	width: 100%;
	height: 80px;
	font-size: xx-large;
	text-align: center;
	background: aquamarine;
}

#cal_y_m {
	margin: auto;
	/* top: 50%; */
	text-align: center;
	background: burlywood;
	width: 70%;
}

#cal_year, #cal_month {
	border: 1px solid red;
}

#cal_gridframe {
	display: grid;
	grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr 1fr;
}

.aa {
	border: 1px solid violet;
	text_align: center;
}

.aa_head {
	padding: 5px;
	font-weight: bolder;
	text-align: center;
}

.date_head {
	background: cyan;
	display: grid;
	grid-template-columns: 1fr 1fr;
}

.date_num {
	margin-left: 0.3em;
	font-weight: bolder;
	border: 1px dotted red;
}

.date_cnt {
	text-align: right;
	margin-right: 0.3em;
	font-style: italic;
	border: 1px dotted blue;
}

.date_menu {
	border: 1px solid yellow;
}

.ul {
	text-decoration-style: none;
}

li {
	text-decoration-style: none;
}
</style>
<script>
	// 스크립트
</script>
</head>
<body>
	<!-- 내용 -->
	<h2>cal.jsp</h2>
	<%
		//자바 구문

		//int y_ = Integer.parseInt(request.getParameter("year"));
		//int m_ = Integer.parseInt(request.getParameter("month"));
		int y_ = 2020;
		int m_ = 4;

		// 오늘날짜로 가져오도록

		Cal_info ci = new Cal_info(y_, m_);
		int c1 = ci.getVoid_day();
		int c2 = ci.getMax_day();
		int c3 = 42 - (c1 + c2);

		MenuDAO dao = new MenuDAO();
		dao.select_month(y_, m_);

		OrderListDAO orderDAO = new OrderListDAO();
		orderDAO.select_all(y_, m_);
	%>

	<div id="calframe">
		<div id="cal_head">
			<span id="cal_y_m"> <a id="cal_year"><%=y_%></a> <a
				id="cal_month"><%=m_%></a>
			</span>
		</div>
		<p></p>
		<!-- ///.......................... -->

		<div id="cal_gridframe">
			<!-- ///.......................... -->
			<div class="aa_head">일</div>
			<div class="aa_head">월</div>
			<div class="aa_head">화</div>
			<div class="aa_head">수</div>
			<div class="aa_head">목</div>
			<div class="aa_head">금</div>
			<div class="aa_head">토</div>
			<!-- ///.......................... -->

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
					} //for j (inside for c2)end
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



			<!-- /// -->
		</div>
	</div>

</body>

</html>