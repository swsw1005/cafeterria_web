<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.util.cal_info.*"%>
<%@ page import="com.dbwork.orderlist.OrderListDAO"%>
<%@ page import="com.dbwork.menu.MenuDAO"%>
<%@ page import="com.util.cal_info.Cal_info"%>

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

<%-- bootstrap 3.4.1 --%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<%-- jquery 3.4.1 --%>
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

.cal_gridframe {
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

#year, #month {
	width: 130px;
}
</style>
<script>
	// 스크립트
	function refresh() {
		var input_y = $("#year").val().trim();
		var input_m = $("#month").val().trim();
		console.log(input_y + " " + input_m);

		$("#cal_grid").load("cal.jsp?y=" + input_y + "&m=" + input_m);
	} // refresh() end

	function refresh_wait() {
		setInterval(refresh, 3000);
	}

	$(function() {
		//refresh_wait();

	});
</script>
</head>
<body>
	<!-- 내용 -->
	<h2>cal.jsp</h2>
	<%
		//자바 구문

		Calendar cal = Calendar.getInstance();

		int y_ = cal.get(Calendar.YEAR);
		int m_ = cal.get(Calendar.MONTH) + 1;

		// 오늘날짜로 가져오도록

		Cal_info ci = new Cal_info(y_, m_);
		int c1 = ci.getVoid_day();
		int c2 = ci.getMax_day();
		int c3 = 42 - (c1 + c2);

		System.out.println("");
		System.out.println("");

		MenuDAO dao = new MenuDAO();
		dao.select_month(y_, m_);

		System.out.println("");
		System.out.println("");

		OrderListDAO orderDAO = new OrderListDAO();
		orderDAO.select_all(y_, m_);
	%>

	<button onclick="refresh()">ddd</button>

	<div id="calframe">
		<div id="cal_head">
			<span id="cal_y_m"> <input id="year" type="number"
				value="<%=y_%>" onchange="refresh()" /> <input id="month"
				type="number" value="<%=m_%>" onchange="refresh()" />

			</span>
		</div>
		<p></p>
		<!-- ///.......................... -->

		<div class="cal_gridframe">
			<!-- ///.......................... -->
			<div class="aa_head">일</div>
			<div class="aa_head">월</div>
			<div class="aa_head">화</div>
			<div class="aa_head">수</div>
			<div class="aa_head">목</div>
			<div class="aa_head">금</div>
			<div class="aa_head">토</div>
			<!-- ///.......................... -->
		</div>

		<div id="cal_grid" class="cal_gridframe">
			<!-- ...달력 내용 들어간다......... -->



		</div>
	</div>

</body>

</html>

