<%@ page import="kz.bitlab.hotels.db.User" %>
<%
    User currentUser = (User) session.getAttribute("USER");
%>
<div class="container-flex">
    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #003580;">
        <div class="container">
            <a class="navbar-brand" href="/">
                <img src="/res/booking.png" style="width: 200px;">
            </a>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <%
                        if (currentUser != null) {
                    %>
                    <li class="nav-item" style="display: flex;">
                        <img src="<%=currentUser.getPicture()%>" alt="Your Avatar" width="40px;" style="border-radius: 50%;">
                        <a class="nav-link" href="/profile"><%=currentUser.getFullName()%></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/addhotel">Add Hotel</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Logout</a>
                    </li>
                    <%
                    } else {
                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/register">Register</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                    <%
                        }
                    %>
                </ul>
                <%
                    String key = "";
                    if(request.getParameter("key")!=null){
                        key = request.getParameter("key");
                    }
                    String priceFrom = "";
                    if(request.getParameter("price_from")!=null){
                        priceFrom = request.getParameter("price_from");
                    }
                    String priceTo = "";
                    if(request.getParameter("price_to")!=null){
                        priceTo = request.getParameter("price_to");
                    }
                    String starsFrom = "";
                    if(request.getParameter("stars_from")!=null){
                        starsFrom = request.getParameter("stars_from");
                    }
                    String starsTo = "";
                    if(request.getParameter("stars_to")!=null){
                        starsTo = request.getParameter("stars_to");
                    }
                %>
                <form class="form-inline my-2 my-lg-0" action="/search" method="get">
                    <input class="form-control form-control-sm mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="key" value="<%=key%>">
                    <button class="btn btn-outline-light btn-sm my-2 my-sm-0">Search</button>
                </form>
            </div>
        </div>
    </nav>
</div>
