<%@ page import="kz.bitlab.hotels.db.Hotel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Hotel</title>
    <%@include file="head.jsp" %>
    <script type="text/javascript" src="res/js/tinymce/tinymce.min.js"></script>
    <script>
        tinymce.init({
            selector: 'textarea',
            statusbar: false
        });
    </script>
</head>
<body class="pb-5 mb-5">
<%@include file="navbar.jsp" %>
<div class="container mt-3">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/">Home</a></li>
            <li class="breadcrumb-item active" aria-current="page">Edit Hotel</li>
        </ol>
    </nav>
</div>
<div class="container mt-3">
    <div class="row">
        <div class="col-sm-12">
            <%
                String success = request.getParameter("success");
                if (success != null) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Hotel updated successfully!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
            <%
                String error = request.getParameter("error");
                if (error != null) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Error! Unable to update the hotel!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <%
                }
            %>
        </div>
        <div class="col-sm-12">
            <form action="/edithotel" method="post">
                <%
                    Hotel hotel = (Hotel) request.getAttribute("hotel");
                    if (hotel != null) {
                %>
                <input type="hidden" name="id" value="<%=hotel.getId()%>">
                <div class="form-group">
                    <label>NAME :</label>
                    <input type="text" name="name" class="form-control" value="<%=hotel.getName()%>" required>
                </div>
                <div class="form-group">
                    <label>STARS :</label>
                    <select name="stars" class="form-control">
                        <%
                            for (int i = 1; i < 6; i++) {
                        %>
                        <option <% if (hotel.getStars() == i) { out.print("selected");}%>><%=i%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label>DESCRIPTION :</label>
                    <textarea name="description" cols="30" rows="10" class="form-control"><%=hotel.getDescription()%></textarea>
                </div>
                <div class="form-group">
                    <label>PRICE :</label>
                    <input type="number" name="price" min="0" class="form-control" value="<%=hotel.getPrice()%>" required>
                </div>
                <%
                    }
                %>
                <div class="form-group">
                    <button class="btn btn-success">SAVE HOTEL</button>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
