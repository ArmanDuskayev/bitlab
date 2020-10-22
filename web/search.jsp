<%@ page import="java.util.ArrayList" %>
<%@ page import="kz.bitlab.hotels.db.Hotel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.TimeZone" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
    <%@include file="head.jsp" %>
    <style>
        img {
            max-width: 100%;
        }
    </style>
</head>
<body>
<%@include file="navbar.jsp" %>
<div class="container mt-3">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Search Hotels</li>
        </ol>
    </nav>
</div>
<div class="container mt-3">
    <form action="/search" method="get">
        <div class="row">
            <div class="col-sm-3">
                <label>NAME : </label>
            </div>
            <div class="col-sm-2">
                <label>PRICE FROM : </label>
            </div>
            <div class="col-sm-2">
                <label>PRICE TO : </label>
            </div>
            <div class="col-sm-2">
                <label>STARS FROM : </label>
            </div>
            <div class="col-sm-2">
                <label>STARS TO : </label>
            </div>
            <div class="col-sm-1">
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3">
                <input type="text" class="form-control form-control-sm" name="key" value="<%=key%>">
            </div>
            <div class="col-sm-2">
                <input type="number" class="form-control form-control-sm" name="price_from" value="<%=priceFrom%>">
            </div>
            <div class="col-sm-2">
                <input type="number" class="form-control form-control-sm" name="price_to" value="<%=priceTo%>">
            </div>
            <div class="col-sm-2">
                <input type="number" class="form-control form-control-sm" name="stars_from" value="<%=starsFrom%>">
            </div>
            <div class="col-sm-2">
                <input type="number" class="form-control form-control-sm" name="stars_to" value="<%=starsTo%>">
            </div>
            <div class="col-sm-1">
                <button class="btn btn-primary btn-sm btn-block">FILTER</button>
            </div>
        </div>
    </form>
</div>
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
