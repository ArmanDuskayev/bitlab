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
                    <li class="nav-item">
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
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
                </form>
            </div>
        </div>
    </nav>
</div>
