<%@ page language="java"%>
<%@ page import="com.web.model.Actor"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>The Searched Actors</title>
</head>
<body>
	<h1>From ArrayList</h1>
	<p id="myPara"></p>
	<%
		ArrayList<Actor> arrayList = (ArrayList<Actor>) request.getAttribute("actorsList");
		arrayList.forEach(System.out::println);
	%>
	<%=arrayList%>
	<hr />
	<!-- request.getAttribute("searchedActors")  -->
	<!-- out.println(actor); -->
</body>
</html>