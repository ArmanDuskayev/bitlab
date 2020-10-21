<%@ page import="java.util.ArrayList" %>
<%@ page import="kz.bitlab.hotels.db.Hotel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booking</title>
    <%@include file="head.jsp" %>
    <style>
        img {
            max-width: 100%;
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp" %>

<div class="container-flex">
    <div class="container">
        <div class="row mt-3">
            <div class="col-sm-12 mt-2">
                <%
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    ArrayList<Hotel> hotels = (ArrayList<Hotel>) request.getAttribute("hotels");
                    if (hotels != null) {
                        for (Hotel h : hotels) {
                %>
                <div class="jumbotron pb-3 pt-4">
                    <h1><%=h.getName()%></h1>
                    <h4>For <%=h.getPrice()%> USD</h4>
                    <h5><%=h.getStars()%> stars</h5>
                    <hr class="my-4">
                    <div class="d-flex w-100 justify-content-between">
                        <label style="color: gray; font-size: 14px;">posted by <%=h.getAuthor().getFullName()%> at <%=formatter.format(h.getAddedDate())%></label>
                        <div class="text-right">
                            <a class="btn btn-primary btn-sm pull-right" href="/details?id=<%=h.getId()%>" role="button">Details</a>
                        </div>
                    </div>
                </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>
</div>

</body>
</html>
